package com.microservice.user.external.services;

import com.microservice.user.entities.Qualification;
import com.microservice.user.entities.Restaurant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "QUALIFICATION-SERVICE", url = "http://localhost:8083")
public interface QualificationService {

    @PostMapping
    ResponseEntity<Qualification> saveQualification(Qualification qualification);

    @PostMapping("/qualifications/{qualificationId}")
    ResponseEntity<Qualification> updateQualification(@PathVariable String qualificationId, Qualification qualification);

    @DeleteMapping("/qualifications/{qualificationId}")
    void deleteQualification(@PathVariable String qualificationId);

    @GetMapping("/qualifications/users/{userId}")
    ResponseEntity<List<Qualification>> getQualificationsByUserId(@PathVariable("userId") String userId);

}
