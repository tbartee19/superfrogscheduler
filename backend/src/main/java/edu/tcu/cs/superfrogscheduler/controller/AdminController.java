package edu.tcu.cs.superfrogscheduler.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import edu.tcu.cs.superfrogscheduler.system.SuperFrogStudentService;

// AdminController
// handles user-related functionalities like account creation, deactivation, editing 
// and managing schedules for spirit directors with admin role

// use cases 13-16

@RestController
@RequestMapping("/api/admin") 
public class AdminController {

   @Autowired
    private SuperFrogStudentService studentService;

    //use case 13
    @PostMapping("/createStudent")
    public ResponseEntity<String> createSuperFrogStudent(@RequestBody SuperFrogStudent newStudent) {
        try {
            // may not need createdStudent since the response entity tells the frontend it was created, don't know yet
            SuperFrogStudent createdStudent = studentService.createSuperFrogStudent(newStudent);
            return ResponseEntity.ok("SuperFrog Student account created successfully with temporary password.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating account: " + e.getMessage());
        }
    }
    
    @PostMapping("/deactivateStudent/{studentId}")
    public ResponseEntity<String> deactivateStudent(@PathVariable String studentId, @RequestParam(required = false) String reason) {
        try {
            studentService.deactivateSuperFrogStudent(studentId, reason);
            return ResponseEntity.ok("Student account deactivated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deactivating account: " + e.getMessage());
        }
    }
    @GetMapping("/searchStudents")
    public ResponseEntity<List<SuperFrogStudent>> searchSuperFrogStudents(
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) String phoneNumber,
        @RequestParam(required = false) String email) {
        try {
            System.out.println(firstName);
            List<SuperFrogStudent> students = studentService.findSuperFrogStudents(
                Optional.ofNullable(firstName), 
                Optional.ofNullable(lastName), 
                Optional.ofNullable(phoneNumber), 
                Optional.ofNullable(email));
            if (students.isEmpty()) {
                System.out.println("No students found.");
            return ResponseEntity.ok(Collections.emptyList());
            }
            else {
                System.out.println("Found students: " + students.size());
                return ResponseEntity.ok(students);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> viewStudentDetails(@PathVariable String studentId) {
        try {
            Optional<SuperFrogStudent> studentOpt = studentService.findStudentById(studentId);
            if (studentOpt.isPresent()) {
                return ResponseEntity.ok(studentOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving student details: " + e.getMessage());
        }
    }




    // other methods 
}


