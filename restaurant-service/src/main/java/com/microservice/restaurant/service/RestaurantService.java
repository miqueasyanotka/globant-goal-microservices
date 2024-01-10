package com.microservice.restaurant.service;

import com.microservice.restaurant.entity.Restaurant;

import java.util.List;

public interface RestaurantService {

    Restaurant createRestaurant(Restaurant restaurant);

    List<Restaurant> getAll();

    Restaurant getRestaurant(String restaurantId);

    Restaurant updateRestaurant(String restaurantId, Restaurant restaurant);
    void deleteRestaurant(String restaurantId);
}
