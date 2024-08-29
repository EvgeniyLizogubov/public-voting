package com.github.evgenylizogubov.publicvoting.controller;

import com.github.evgenylizogubov.publicvoting.model.Voting;
import com.github.evgenylizogubov.publicvoting.service.VotingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = VotingController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class VotingController {
    static final String REST_URL = "/api/voting";
    
    private final VotingService votingService;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        log.info("get {}", id);
        Voting voting = votingService.get(id);
        if (voting == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Voting with id=" + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(voting);
    }
    
    @GetMapping("/current-active")
    public ResponseEntity<?> getCurrentActive() {
        log.info("get current active voting");
        Voting currentActiveVoting = votingService.getCurrentActive();
        if (currentActiveVoting == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Current active voting not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(currentActiveVoting);
    }
    
    @GetMapping("/all-active")
    public ResponseEntity<?> getAllActive() {
        log.info("get all active voting");
        List<Voting> activeVoting = votingService.getAllActive();
        if (activeVoting == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Active voting not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(activeVoting);
    }
    
    @GetMapping("/last-finished")
    public ResponseEntity<?> getLastFinished() {
        log.info("get last finished");
        Voting lastFinished = votingService.getLastFinished();
        if (lastFinished == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Last finished voting not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(lastFinished);
    }
    
    @GetMapping
    public List<Voting> getAll() {
        log.info("getAll");
        return votingService.getAll();
    }
    
    @GetMapping("/start-new")
    public ResponseEntity<?> createWithLocation() {
        log.info("start new voting");
        Voting created = votingService.create();
        
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("There are no themes for voting");
        }
        
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
    
    @PutMapping("/{id}")
    public Voting update(@Valid @RequestBody Voting voting, @PathVariable int id) {
        log.info("update {} with id={}", voting, id);
        return votingService.update(voting, id);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        log.info("delete {}", id);
        if (votingService.delete(id) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Voting with id=" + id + " not found");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
