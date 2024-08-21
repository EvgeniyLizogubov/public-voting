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
    
    @GetMapping
    public List<Voting> getAll() {
        log.info("getAll");
        return votingService.getAll();
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
