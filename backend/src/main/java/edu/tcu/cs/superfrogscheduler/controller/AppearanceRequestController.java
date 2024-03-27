package edu.tcu.cs.superfrogscheduler.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.tcu.cs.superfrogscheduler.model.SuperFrogAppearance;
import edu.tcu.cs.superfrogscheduler.model.SuperFrogStudent;
import edu.tcu.cs.superfrogscheduler.system.SuperFrogAppearanceService;


// AppearanceRequestController
// manages appearance requests
// this could include creating, editing, viewing, and deleting requests
// as well as approval/rejection by the Spirit Director.

// use Cases 1-12, 24, 25, 26 i believe


@RestController
@RequestMapping("/api/appearances")
public class AppearanceRequestController {
    // use case 1
    @PostMapping("/request")
    public ResponseEntity<String> customerRequestSuperFrogAppearance(@RequestBody SuperFrogAppearance newAppearance){
        try {
            // may not need createdAppearance since the response entity tells the frontend it was created, don't know yet
            SuperFrogAppearance createdAppearance = SuperFrogAppearanceService.createAppearanceRequest(newAppearance);
            return ResponseEntity.ok("SuperFrog Appearance request was sent.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating request: " + e.getMessage());
        }
    }

    // other methods
}
