package com.github.evgenylizogubov.publicvoting.web.user;

import com.github.evgenylizogubov.publicvoting.dto.UserDto;
import com.github.evgenylizogubov.publicvoting.dto.UserRequestDto;
import com.github.evgenylizogubov.publicvoting.dto.UserResponseDto;
import com.github.evgenylizogubov.publicvoting.mapper.UserDtoMapper;
import com.github.evgenylizogubov.publicvoting.mapper.UserMapper;
import com.github.evgenylizogubov.publicvoting.mapper.UserResponseMapper;
import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
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

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminUserController {
    static final String REST_URL = "/api/admin/users";
    
    private final UserDtoMapper userDtoMapper;
    private final UserResponseMapper userResponseMapper;
    private final UserService userService;
    private final Logger log = getLogger(getClass());
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        log.info("get {}", id);
        UserDto userDto = userService.get(id);
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id=" + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userResponseMapper.toDto(userDto));
    }
    
    @GetMapping("/by-email")
    public ResponseEntity<?> getByEmail(@RequestParam String email) {
        log.info("getByEmail {}", email);
        UserDto userDto = userService.getByEmail(email);
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email \"" + email + "\" not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userResponseMapper.toDto(userDto));
    }
    
    @GetMapping
    public List<UserDto> getAll() {
        log.info("getAll");
        return userService.getAll();
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> createWithLocation(@Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("create {}", userRequestDto);
        UserDto created = userService.create(userDtoMapper.toEntity(userRequestDto));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(userResponseMapper.toDto(created));
    }
    
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto update(@Valid @RequestBody UserRequestDto userRequestDto, @PathVariable int id) {
        log.info("update {} with id={}", userRequestDto, id);
        UserDto updated = userService.update(userDtoMapper.toEntity(userRequestDto), id);
        return userResponseMapper.toDto(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        log.info("delete {}", id);
        if (userService.delete(id) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity with id=" + id + " not found");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
