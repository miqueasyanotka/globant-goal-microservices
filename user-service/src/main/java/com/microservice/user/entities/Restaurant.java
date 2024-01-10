package com.microservice.user.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    private String restaurantId;
    private String name;
    private String location;
    private String information;

}

