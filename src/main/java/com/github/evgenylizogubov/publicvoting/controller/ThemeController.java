package com.github.evgenylizogubov.publicvoting.controller;

import com.github.evgenylizogubov.publicvoting.controller.dto.theme.ThemeDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.theme.ThemeRequest;
import com.github.evgenylizogubov.publicvoting.controller.dto.theme.ThemeResponse;
import com.github.evgenylizogubov.publicvoting.mapper.theme.ThemeDtoToThemeResponseMapper;
import com.github.evgenylizogubov.publicvoting.mapper.theme.ThemeRequestToThemeDtoMapper;
import com.github.evgenylizogubov.publicvoting.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping(value = ThemeController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class ThemeController {
    static final String REST_URL = "/api/themes";
    
    private final ThemeService themeService;
    private final ThemeRequestToThemeDtoMapper themeRequestToThemeDtoMapper;
    private final ThemeDtoToThemeResponseMapper themeDtoToThemeResponseMapper;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        log.info("get {}", id);
        ThemeDto themeDto = themeService.get(id);
        if (themeDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theme with id=" + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(themeDtoToThemeResponseMapper.toResponse(themeDto));
    }
    
    @GetMapping
    public List<ThemeResponse> getAll() {
        log.info("getAll");
        List<ThemeDto> themes = themeService.getAll();
        return themeDtoToThemeResponseMapper.toResponseList(themes);
    }
    
    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThemeResponse> createWithLocation(@Valid @RequestBody ThemeRequest themeRequest) {
        log.info("create {}", themeRequest);
        ThemeDto created = themeService.create(themeRequestToThemeDtoMapper.toDto(themeRequest));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(themeDtoToThemeResponseMapper.toResponse(created));
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ThemeResponse update(@Valid @RequestBody ThemeRequest themeRequest, @PathVariable int id) {
        log.info("update {} with id={}", themeRequest, id);
        ThemeDto updated = themeService.update(themeRequestToThemeDtoMapper.toDto(themeRequest), id);
        return themeDtoToThemeResponseMapper.toResponse(updated);
    }
    
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        log.info("delete {}", id);
        if (themeService.delete(id) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theme with id=" + id + " not found");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
