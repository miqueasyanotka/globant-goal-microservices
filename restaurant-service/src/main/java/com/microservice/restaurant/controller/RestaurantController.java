package com.microservice.restaurant.controller;

import com.microservice.restaurant.entity.Restaurant;
import com.microservice.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Restaurant> saveRestaurant(@RequestBody Restaurant restaurant) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createRestaurant(restaurant));
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getAll());
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable String restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.getRestaurant(restaurantId));
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String restaurantId, @RequestBody Restaurant restaurant) {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.updateRestaurant(restaurantId, restaurant));
    }

    @DeleteMapping("/{restaurantId}")
    public void deleteRestaurant(@PathVariable String restaurantId){
        restaurantService.deleteRestaurant(restaurantId);
    }

}
