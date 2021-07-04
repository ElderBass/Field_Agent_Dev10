package learn.field_agent.controllers;

import learn.field_agent.domain.Result;
import learn.field_agent.domain.SecurityClearanceService;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Location;
import learn.field_agent.models.SecurityClearance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/security_clearance")
public class SecurityClearanceController {

    private final SecurityClearanceService service;

    public SecurityClearanceController(SecurityClearanceService service) { this.service = service; }

    @GetMapping
    public List<SecurityClearance> findAll() {
        return service.findAll();
    }

    @GetMapping("/{scId}")
    public ResponseEntity<SecurityClearance> findById(@PathVariable int scId) {
        SecurityClearance sc = service.findById(scId);
        if (sc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sc);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody SecurityClearance sc) {
        Result<SecurityClearance> result = service.add(sc);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{scId}")
    public ResponseEntity<Object> update(@PathVariable int scId, @RequestBody SecurityClearance sc) {
        if (scId != sc.getSecurityClearanceId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<SecurityClearance> result = service.update(sc);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{scId}")
    public ResponseEntity<Void> deleteById(@PathVariable int scId) {
        if (service.deleteById(scId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
