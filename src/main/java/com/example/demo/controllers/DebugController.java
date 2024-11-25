package main.java.com.example.demo.controllers;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private WasabiStorageService wasabiStorageService;

    @GetMapping("/bucket")
    public ResponseEntity<String> testBucketConnection() {
        try {
            wasabiStorageService.testWasabiConnection();
            return ResponseEntity.ok("Bucket connection successful!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
