package com.github.evgenylizogubov.publicvoting.controller;

import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserRequest;
import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserResponse;
import com.github.evgenylizogubov.publicvoting.mapper.UserDtoMapper;
import com.github.evgenylizogubov.publicvoting.mapper.UserResponseMapper;
import com.github.evgenylizogubov.publicvoting.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProfileController {
    static final String REST_URL = "/api/profile";
    
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;
    private final UserResponseMapper userResponseMapper;
    private final Logger log = getLogger(getClass());
    
    @GetMapping
    public UserResponse get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get {}", authUser);
        return userResponseMapper.toDto(authUser.getUserDto());
    }
    
    @DeleteMapping
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete {}", authUser.getUserDto().getId());
        userService.delete(authUser.getUserDto().getId());
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        log.info("registered {}", userRequest);
        UserDto created = userService.create(userDtoMapper.toEntity(userRequest));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/" + created.getId()).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(userResponseMapper.toDto(created));
    }
    
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse update(@RequestBody @Valid UserRequest userRequest, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update {} with id={}", userRequest, authUser.getUserDto().getId());
        UserDto userDto = authUser.getUserDto();
        UserDto updated = userService.update(userDtoMapper.updateFromDto(userDto, userRequest), userDto.getId());
        return userResponseMapper.toDto(updated);
    }
}
