package com.github.evgenylizogubov.publicvoting.controller;

import com.github.evgenylizogubov.publicvoting.controller.dto.voting.VotingDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.voting.VotingResponse;
import com.github.evgenylizogubov.publicvoting.mapper.voting.VotingDtoToVotingResponseMapper;
import com.github.evgenylizogubov.publicvoting.service.VotingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final VotingDtoToVotingResponseMapper votingDtoToVotingResponseMapper;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        log.info("get {}", id);
        VotingDto votingDto = votingService.get(id);
        if (votingDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Voting with id=" + id + " not found");
        }
        return ResponseEntity
                .status(HttpStatus.OK).body(votingDtoToVotingResponseMapper.toResponse(votingDto));
    }
    
    @GetMapping("/active")
    public ResponseEntity<?> getActive() {
        log.info("get active voting");
        VotingDto activeVotingDto = votingService.getActive();
        if (activeVotingDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Active voting not found");
        }
        return ResponseEntity
                .status(HttpStatus.OK).body(votingDtoToVotingResponseMapper.toResponse(activeVotingDto));
    }
    
    @GetMapping
    public List<VotingResponse> getAll() {
        log.info("getAll");
        List<VotingDto> votingDto = votingService.getAll();
        return votingDtoToVotingResponseMapper.toResponseList(votingDto);
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/start-new")
    public ResponseEntity<?> createWithLocation() {
        log.info("start new voting");
        VotingDto created = votingService.create();
        
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("There are no themes for voting");
        }
        
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity
                .created(uriOfNewResource).body(votingDtoToVotingResponseMapper.toResponse(created));
    }
    
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        log.info("delete {}", id);
        if (votingService.delete(id) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Voting with id=" + id + " not found");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
