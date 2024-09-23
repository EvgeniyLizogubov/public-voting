package com.github.evgenylizogubov.publicvoting.controller;

import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserRequest;
import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserResponse;
import com.github.evgenylizogubov.publicvoting.mapper.user.UserDtoToUserResponseMapper;
import com.github.evgenylizogubov.publicvoting.mapper.user.UserRequestToUserDtoMapper;
import com.github.evgenylizogubov.publicvoting.service.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {
    static final String REST_URL = "/api/admin/users";
    
    private final UserRequestToUserDtoMapper userRequestToUserDtoMapper;
    private final UserDtoToUserResponseMapper userDtoToUserResponseMapper;
    private final UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        log.info("get {}", id);
        UserDto userDto = userService.get(id);
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id=" + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDtoToUserResponseMapper.toResponse(userDto));
    }
    
    @GetMapping("/by-email")
    public ResponseEntity<?> getByEmail(@RequestParam String email) {
        log.info("getByEmail {}", email);
        UserDto userDto = userService.getByEmail(email);
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email \"" + email
                    + "\" not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDtoToUserResponseMapper.toResponse(userDto));
    }
    
    @GetMapping
    public List<UserResponse> getAll() {
        log.info("getAll");
        List<UserDto> users = userService.getAll();
        return userDtoToUserResponseMapper.toResponseList(users);
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> createWithLocation(@Valid @RequestBody UserRequest userRequest) {
        log.info("create {}", userRequest);
        UserDto created = userService.create(userRequestToUserDtoMapper.toDto(userRequest));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(userDtoToUserResponseMapper.toResponse(created));
    }
    
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse update(@Valid @RequestBody UserRequest userRequest, @PathVariable int id) {
        log.info("update {} with id={}", userRequest, id);
        UserDto updated = userService.update(userRequestToUserDtoMapper.toDto(userRequest), id);
        return userDtoToUserResponseMapper.toResponse(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        log.info("delete {}", id);
        if (userService.delete(id) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id=" + id + " not found");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
