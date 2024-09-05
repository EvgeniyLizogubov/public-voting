package com.github.evgenylizogubov.publicvoting.controller;

import com.github.evgenylizogubov.publicvoting.controller.dto.suggestion.SuggestionDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.suggestion.SuggestionRequest;
import com.github.evgenylizogubov.publicvoting.controller.dto.suggestion.SuggestionResponse;
import com.github.evgenylizogubov.publicvoting.mapper.suggestion.SuggestionDtoToSuggestionResponseMapper;
import com.github.evgenylizogubov.publicvoting.mapper.suggestion.SuggestionRequestToSuggestionDtoMapper;
import com.github.evgenylizogubov.publicvoting.service.SuggestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final SuggestionRequestToSuggestionDtoMapper suggestionRequestToSuggestionDtoMapper;
    private final SuggestionDtoToSuggestionResponseMapper suggestionDtoToSuggestionResponseMapper;
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        log.info("get {}", id);
        SuggestionDto suggestionDto = suggestionService.get(id);
        if (suggestionDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Suggestion with id=" + id + " not found");
        }
        return ResponseEntity
                .status(HttpStatus.OK).body(suggestionDtoToSuggestionResponseMapper.toResponse(suggestionDto));
    }
    
    @GetMapping("/by-voting")
    public List<SuggestionResponse> getAllByVotingId(@RequestParam int votingId) {
        log.info("get all by votingId {}", votingId);
        List<SuggestionDto> suggestions = suggestionService.getAllByVotingId(votingId);
        return suggestionDtoToSuggestionResponseMapper.toResponseList(suggestions);
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuggestionResponse> createWithLocation(
            @Valid @RequestBody SuggestionRequest suggestionRequest,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        log.info("create {}", suggestionRequest);
        SuggestionDto suggestionDto = suggestionRequestToSuggestionDtoMapper.toDto(suggestionRequest);
        SuggestionDto created = suggestionService.create(suggestionDto, authUser.getUserDto());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity
                .created(uriOfNewResource).body(suggestionDtoToSuggestionResponseMapper.toResponse(created));
    }
    
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuggestionResponse update(
            @Valid @RequestBody SuggestionRequest suggestionRequest,
            @PathVariable int id,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        log.info("update {} with id={}", suggestionRequest, id);
        SuggestionDto toUpdate = suggestionRequestToSuggestionDtoMapper.toDto(suggestionRequest);
        SuggestionDto updated = suggestionService.update(toUpdate, id, authUser.getUserDto());
        return suggestionDtoToSuggestionResponseMapper.toResponse(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("delete {}", id);
        if (suggestionService.delete(id, authUser.getUserDto()) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Suggestion with id=" + id + " not found");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
