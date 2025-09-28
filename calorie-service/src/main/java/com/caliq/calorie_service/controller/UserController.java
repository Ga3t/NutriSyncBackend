package com.caliq.calorie_service.controller;


import com.caliq.calorie_service.models.dto.UpdateUserDetailsDto;
import com.caliq.calorie_service.models.dto.UserDetailsDto;
import com.caliq.calorie_service.models.response.UserDetailsResponse;
import com.caliq.calorie_service.models.response.WeightLogsResponse;
import com.caliq.calorie_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/userdetails")
public class UserController {


    private UserService userService;

    public UserController(UserService userDetailsService) {
        this.userService = userDetailsService;
    }

    @PostMapping("/setuserdetails")
    public ResponseEntity<UserDetailsResponse> setUserDetails(@RequestBody UserDetailsDto userDetails,
                                                             @RequestParam("X-UserID-X")Long userId){
        UserDetailsResponse userDetailsResponse = userService.saveUserDetails(userDetails, userId);
        return ResponseEntity.ok(userDetailsResponse);
    }

    @PostMapping("/updateuserdetails")
    public ResponseEntity<String>  updateUserDetails(@RequestBody UpdateUserDetailsDto updateUserDetailsDto,
                                                     @RequestParam("X-UserID-X")Long userId){
        return ResponseEntity.ok(userService.updateUserDetails(updateUserDetailsDto, userId));
    }

    @GetMapping("/newWeighing")
    public ResponseEntity<WeightLogsResponse> setNewWeighing(@RequestParam("new_weight")BigDecimal newWeight,
                                                             @RequestParam("X-UserID-X")Long userId){
        return ResponseEntity.ok(userService.setNewWeight(newWeight, userId));
    }



}
