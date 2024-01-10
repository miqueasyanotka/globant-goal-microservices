package com.microservice.user.external.services;

import com.microservice.user.entities.Restaurant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RESTAURANT-SERVICE", url = "http://localhost:8082")
public interface RestaurantService {

    @GetMapping("/restaurants/{restaurantId}")
    Restaurant getRestaurant(@PathVariable("restaurantId") String restaurantId);
}
