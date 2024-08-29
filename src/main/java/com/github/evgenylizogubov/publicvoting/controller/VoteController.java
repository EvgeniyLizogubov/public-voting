package com.github.evgenylizogubov.publicvoting.controller;

import com.github.evgenylizogubov.publicvoting.model.Vote;
import com.github.evgenylizogubov.publicvoting.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = VotingController.REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class VoteController {
    static final String REST_URL = "/api/votes";
    
    private final VoteService voteService;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        log.info("get {}", id);
        Vote vote = voteService.get(id);
        if (vote == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vote with id=" + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(vote);
    }
    
    @GetMapping("/count-by-voting-and-suggestion")
    public int getCountByVotingIdAndSuggestionId(@RequestParam int votingId, @RequestParam int suggestionId) {
        log.info("get count by voting id={} and suggestion id={}", votingId, suggestionId);
        return voteService.getCountAllByVotingIdAndChosenSuggestionId(votingId, suggestionId);
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Vote vote) {
        log.info("create {}", vote);
        voteService.create(vote);
    }
}
