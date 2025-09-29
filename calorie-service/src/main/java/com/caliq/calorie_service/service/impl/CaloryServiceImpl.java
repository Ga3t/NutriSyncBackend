package com.caliq.calorie_service.service.impl;

import com.caliq.calorie_service.exeptions.UserNotFoundException;
import com.caliq.calorie_service.models.entity.CaloryLogsEntity;
import com.caliq.calorie_service.models.response.MainPageResponse;
import com.caliq.calorie_service.models.response.MealByDateResponse;
import com.caliq.calorie_service.models.entity.MealEntity;
import com.caliq.calorie_service.models.entity.UserModel;
import com.caliq.calorie_service.models.types.MealType;
import com.caliq.calorie_service.repository.CaloryLogsRepository;
import com.caliq.calorie_service.repository.CaloryRepository;
import com.caliq.calorie_service.repository.UserRepository;
import com.caliq.calorie_service.service.CaloryService;
import com.caliq.calorie_service.service.MealProducer;
import com.caliq.core.dto.MealDto;
import com.caliq.core.message.MealMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Primary
public class CaloryServiceImpl implements CaloryService {



    private MealProducer mealProducer;
    private CaloryRepository caloryRepository;
    private UserRepository userRepository;
    private CaloryLogsRepository caloryLogsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public CaloryServiceImpl(CaloryRepository caloryRepository, UserRepository userRepository,  CaloryLogsRepository caloryLogsRepository, MealProducer mealProducer) {
        this.mealProducer = mealProducer;
        this.caloryRepository = caloryRepository;
        this.userRepository = userRepository;
        this.caloryLogsRepository = caloryLogsRepository;
    }

    @Override
    public String saveMealToDb(Long userId, MealDto mealDto, LocalDateTime time, MealType mealType) {
        MealEntity mealEntity = new MealEntity();
        UserModel userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        mealEntity.setUser(userEntity);
        mealEntity.setMealType(mealType);

        com.fasterxml.jackson.databind.JsonNode mealJson = objectMapper.valueToTree(mealDto);
        mealEntity.setMeal(mealJson);

        mealEntity.setDateTime(time);
        caloryRepository.save(mealEntity);

        saveToCaloryLogs(mealDto, time, userEntity);

        MealMessage message = new MealMessage();
        message.setUserId(userId);
        message.setMeal(mealDto);
        message.setSentAt(LocalDateTime.now());

        mealProducer.sendMeal(message);
        return "Meal saved successfully";
    }

    @Override
    public MealByDateResponse getMealByDate(Long userId, LocalDate time) {

        LocalDateTime startOfDay = time.atStartOfDay();
        LocalDateTime endOfDay = time.atTime(23, 59, 59);

        List<MealEntity> mealsFromDb = caloryRepository.findByUser_IdAndDateTimeBetween(
                userId, startOfDay, endOfDay
        );

        List<MealDto> mealDtos = mealsFromDb.stream()
                .map(mealEntity -> {
                    try {
                        return objectMapper.treeToValue(mealEntity.getMeal(), MealDto.class);
                    } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                        throw new RuntimeException("Error parsing JSON from db", e);
                    }
                })
                .toList();
        MealByDateResponse response = new MealByDateResponse();
        response.setDate(time.toString());
        response.setMeals(mealDtos);

        return response;
    }

    @Override
    @Transactional
    public MainPageResponse showMainPageInfo(Long userId) {

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        LocalDate today = LocalDate.now();

        CaloryLogsEntity caloryLogsEntity = caloryLogsRepository.findByUserAndDate(user, today)
                .orElseGet(() -> {
                    CaloryLogsEntity e = new CaloryLogsEntity();
                    e.setUser(user);
                    e.setDate(today);
                    e.setCarbohydrates(BigDecimal.ZERO);
                    e.setProtein(BigDecimal.ZERO);
                    e.setFat(BigDecimal.ZERO);
                    e.setDrankWaterMl(BigDecimal.ZERO);
                    e.setTotalCalory(BigDecimal.ZERO);
                    return caloryLogsRepository.save(e);
                });

        LocalDate start = today.minusDays(6);
        List<CaloryLogsEntity> logs = caloryLogsRepository
                .findAllByUser_IdAndDateBetweenOrderByDateAsc(user.getId(), start, today);

        Map<LocalDate, BigDecimal> totalByDate = logs.stream()
                .collect(Collectors.toMap(
                        CaloryLogsEntity::getDate,
                        l -> nz(l.getTotalCalory()),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        MainPageResponse response = new MainPageResponse();
        response.setCalorieGoal(nz(userRepository.findBmrByUserId(userId)));
        response.setWaterGoal(nz(userRepository.findWaterNeedsByUserId(userId)));
        response.setCalorieValue(nz(caloryLogsEntity.getTotalCalory()));
        response.setCurrentWeight(nz(user.getCurrentWeight()));
        response.setProteinValue(nz(caloryLogsEntity.getProtein()));
        response.setWaterValue(nz(caloryLogsEntity.getDrankWaterMl()));
        response.setFatValue(nz(caloryLogsEntity.getFat()));
        response.setCarbohydrateValue(nz(caloryLogsEntity.getCarbohydrates()));


        return response;
    }

    @Override
    public void saveToCaloryLogs(MealDto mealDto, LocalDateTime time, UserModel user) {
        CaloryLogsEntity  caloryLogsEntity = caloryLogsRepository.findByUserAndDate(user, time.toLocalDate())
                .orElseGet(()->{
                    CaloryLogsEntity e = new CaloryLogsEntity();
                    e.setUser(user);
                    e.setDate(time.toLocalDate());
                    e.setCarbohydrates(BigDecimal.valueOf(0));
                    e.setProtein(BigDecimal.valueOf(0));
                    e.setFat(BigDecimal.valueOf(0));
                    e.setDrankWaterMl(BigDecimal.valueOf(0));
                    e.setTotalCalory(BigDecimal.valueOf(0));
                    return e;
                });
        BigDecimal totalCalory = BigDecimal.ZERO;
        BigDecimal totalProtein = BigDecimal.ZERO;
        BigDecimal totalCarbohydrate = BigDecimal.ZERO;
        BigDecimal totalFat =  BigDecimal.ZERO;
        boolean hasCalories = false;
        boolean hasFat = false;
        boolean hasProtein = false;
        boolean hasCarbohydrate = false;

        if (mealDto != null && mealDto.dishes() != null && mealDto.dishes().dish() != null) {
            for (MealDto.Dish d : mealDto.dishes().dish()) {
                if (d.calories() != null) {
                    totalCalory = totalCalory.add(d.calories());
                    hasCalories = true;
                }
                if (d.protein()!= null) {
                  totalProtein = totalProtein.add(d.protein());
                  hasProtein = true;
                }
                if (d.carbohydrates() != null) {
                    totalCarbohydrate = totalCarbohydrate.add(d.carbohydrates());
                    hasCarbohydrate = true;
                }
                if(d.fat()!= null) {
                    totalFat = totalFat.add(d.fat());
                    hasFat = true;
                }
            }
        }
        if (hasCalories) caloryLogsEntity.setTotalCalory(nz(caloryLogsEntity.getTotalCalory()).add(totalCalory));
        if (hasProtein)      caloryLogsEntity.setProtein(nz(caloryLogsEntity.getProtein()).add(totalProtein));
        if (hasCarbohydrate)        caloryLogsEntity.setCarbohydrates(nz(caloryLogsEntity.getCarbohydrates()).add(totalCarbohydrate));
        if (hasFat)          caloryLogsEntity.setFat(nz(caloryLogsEntity.getFat()).add(totalFat));

        caloryLogsRepository.save(caloryLogsEntity);
    }

    @Override
    public BigDecimal addWaterToLogs(Long userId, BigDecimal water, LocalDate date) {

        UserModel user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found"));

        CaloryLogsEntity caloryLogs = caloryLogsRepository.findByUserAndDate(user,date).orElseGet(()->{
            CaloryLogsEntity e = new CaloryLogsEntity();
            e.setUser(user);
            e.setDate(date);
            e.setCarbohydrates(BigDecimal.valueOf(0));
            e.setProtein(BigDecimal.valueOf(0));
            e.setFat(BigDecimal.valueOf(0));
            e.setDrankWaterMl(BigDecimal.valueOf(0));
            e.setTotalCalory(BigDecimal.valueOf(0));
            return e;
        });

        caloryLogs.setDrankWaterMl(caloryLogs.getDrankWaterMl().add(water));
        caloryLogsRepository.save(caloryLogs);
        return  caloryLogs.getDrankWaterMl();
    }

    private static BigDecimal nz(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }
}
