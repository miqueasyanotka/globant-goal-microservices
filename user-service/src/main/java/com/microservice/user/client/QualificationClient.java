//package com.microservice.user.client;
//
//import com.microservice.user.entities.Qualification;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import retrofit2.Call;
//import retrofit2.http.GET;
//
//import java.util.List;
//
////@FeignClient(name = "QUALIFICATION-SERVICE")
//@Component
//public interface QualificationClient {
//
//    @PostMapping
//    ResponseEntity<Qualification> saveQualification(Qualification qualification);
//
//    @PostMapping("/qualifications/{qualificationId}")
//    ResponseEntity<Qualification> updateQualification(@PathVariable String qualificationId);
//
//    @DeleteMapping("/qualifications/{qualificationId}")
//    void deleteQualification(@PathVariable String qualificationId);
//
//    @GET("/qualifications/users/{userId}")
//    Call<List<Qualification>> getQualificationsByUserId(@PathVariable String userId);
//
//}
