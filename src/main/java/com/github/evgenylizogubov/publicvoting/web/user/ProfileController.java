package com.github.evgenylizogubov.publicvoting.web.user;

import com.github.evgenylizogubov.publicvoting.dto.UserDto;
import com.github.evgenylizogubov.publicvoting.dto.UserRequestDto;
import com.github.evgenylizogubov.publicvoting.mapper.UserDtoMapper;
import com.github.evgenylizogubov.publicvoting.mapper.UserResponseMapper;
import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.service.UserService;
import com.github.evgenylizogubov.publicvoting.web.AuthUser;
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
    public UserDto get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get {}", authUser);
        return authUser.getUserDto();
    }
    
    @DeleteMapping
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete {}", authUser.id());
        userService.delete(authUser.id());
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("registered {}", userRequestDto);
        UserDto created = userService.create(userDtoMapper.toEntity(userRequestDto));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
    
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto update(@RequestBody @Valid UserRequestDto userRequestDto, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update {} with id={}", userRequestDto, authUser.id());
        UserDto userDto = authUser.getUserDto();
        return userService.update(userDtoMapper.updateFromDto(userDto, userRequestDto), authUser.id());
    }
}
