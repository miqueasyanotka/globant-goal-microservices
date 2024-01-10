package com.microservice.restaurant.service;

import com.microservice.restaurant.entity.Restaurant;
import com.microservice.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;


    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        String restaurantId = UUID.randomUUID().toString();
        restaurant.setRestaurantId(restaurantId);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant getRestaurant(String restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow();
    }

    @Override
    public Restaurant updateRestaurant(String restaurantId, Restaurant restaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Restaurant doesn't exist"));

        existingRestaurant.setInformation(restaurant.getInformation());
        existingRestaurant.setName(restaurant.getName());
        existingRestaurant.setLocation(restaurant.getLocation());

        return restaurantRepository.save(existingRestaurant);
    }

    @Override
    public void deleteRestaurant(String restaurantId) {
        Restaurant existingRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Restaurant doesn't exist"));
        restaurantRepository.delete(existingRestaurant);
    }

}
