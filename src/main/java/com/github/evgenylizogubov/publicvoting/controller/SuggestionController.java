package com.github.evgenylizogubov.publicvoting.controller;

import com.github.evgenylizogubov.publicvoting.model.Suggestion;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import com.github.evgenylizogubov.publicvoting.service.SuggestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = SuggestionController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class SuggestionController {
    public static final String REST_URL = "/api/suggestions";
    
    private final SuggestionService suggestionService;
    
    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        log.info("get {}", id);
        Suggestion suggestion = suggestionService.get(id);
        if (suggestion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Suggestion with id=" + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(suggestion);
    }
    
    @GetMapping("/by-voting")
    public List<Suggestion> getAllByVoting(@RequestBody Voting voting) {
        log.info("get all by voting {}", voting);
        return suggestionService.getAllByVoting(voting);
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Suggestion> createWithLocation(@Valid @RequestBody Suggestion suggestion) {
        log.info("create {}", suggestion);
        Suggestion created = suggestionService.create(suggestion);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
    
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Suggestion update(@Valid @RequestBody Suggestion suggestion, @PathVariable int id) {
        log.info("update {} with id={}", suggestion, id);
        return suggestionService.update(suggestion, id);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        log.info("delete {}", id);
        if (suggestionService.delete(id) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Suggestion with id=" + id + " not found");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
