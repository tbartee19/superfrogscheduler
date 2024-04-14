package edu.tcu.cs.superfrogscheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import edu.tcu.cs.superfrogscheduler.system.SuperFrogStudentService;

// UserController
// handles user-related functionalities like account creation, deactivation, editing 
// and managing schedules for both the Spirit Director and SuperFrog Students.

// use cases 13-16, 20-23

@RestController
@RequestMapping("/api/users") 
public class UserController {

//   @Autowired
//    private SuperFrogStudentService studentService;
//
//    //use case 13
//    @PostMapping("/create")
//    public ResponseEntity<String> createSuperFrogStudent(@RequestBody SuperFrogStudent newStudent) {
//        try {
//            // may not need createdStudent since the response entity tells the frontend it was created, don't know yet
//            SuperFrogStudent createdStudent = studentService.createSuperFrogStudent(newStudent);
//            return ResponseEntity.ok("SuperFrog Student account created successfully with temporary password.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error creating account: " + e.getMessage());
//        }
//    }
    
    // other methods 
}
