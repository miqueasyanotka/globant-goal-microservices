package com.microservice.qualification.service;

import com.microservice.qualification.entity.Qualification;

import java.util.List;

public interface QualificationService {

    Qualification createQualification(Qualification qualification);
    List<Qualification> getQualifications();
    List<Qualification> getQualificationsByUserId(String userId);
    List<Qualification> getQualificationsByRestaurantId(String restaurantId);


    Qualification updateQualification(String qualificationId, Qualification qualification);
    void deleteQualification(String qualificationId);


}
