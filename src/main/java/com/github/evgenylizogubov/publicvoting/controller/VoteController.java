package com.github.evgenylizogubov.publicvoting.controller;

import com.github.evgenylizogubov.publicvoting.controller.dto.vote.VoteDto;
import com.github.evgenylizogubov.publicvoting.mapper.vote.VoteDtoToVoteResponseMapper;
import com.github.evgenylizogubov.publicvoting.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class VoteController {
    static final String REST_URL = "/api/votes";
    
    private final VoteService voteService;
    private final VoteDtoToVoteResponseMapper voteDtoToVoteResponseMapper;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("get {}", id);
        VoteDto vote = voteService.get(id, authUser.getUserDto());
        if (vote == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vote with id=" + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(voteDtoToVoteResponseMapper.toResponse(vote));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void vote(@RequestParam int chosenSuggestionId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("user {} votes for the suggestion with id = {} ", authUser.getUserDto(), chosenSuggestionId);
        voteService.create(chosenSuggestionId, authUser.getUserDto());
    }
}
