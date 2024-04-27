package edu.tcu.cs.superfrogscheduler.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import edu.tcu.cs.superfrogscheduler.model.*;
import edu.tcu.cs.superfrogscheduler.model.dto.ProfileUpdateDTO;
import edu.tcu.cs.superfrogscheduler.system.SuperFrogStudentService;


//handles user specific requests from superfrog students without admin role
//use cases 20-23
@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private SuperFrogStudentService studentService;

    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateDTO profileUpdate) {
        try {
            SuperFrogStudent updatedStudent = studentService.updateStudentProfile(profileUpdate.getEmail(), profileUpdate);
            return ResponseEntity.ok(updatedStudent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
