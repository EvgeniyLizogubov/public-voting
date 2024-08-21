package com.github.evgenylizogubov.publicvoting.controller;

import com.github.evgenylizogubov.publicvoting.model.Theme;
import com.github.evgenylizogubov.publicvoting.service.ThemeService;
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
@RequestMapping(value = ThemeController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class ThemeController {
    static final String REST_URL = "/api/themes";

    private final ThemeService themeService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        log.info("get {}", id);
        Theme theme = themeService.get(id);
        if (theme == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theme with id=" + id + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(theme);
    }

    @GetMapping
    public List<Theme> getAll() {
        log.info("getAll");
        return themeService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Theme> createWithLocation(@Valid @RequestBody Theme theme) {
        log.info("create {}", theme);
        Theme created = themeService.create(theme);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Theme update(@Valid @RequestBody Theme theme, @PathVariable int id) {
        log.info("update {} with id={}", theme, id);
        return themeService.update(theme, id);
    }
    
    @DeleteMapping("{/id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        log.info("delete {}", id);
        if (themeService.delete(id) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity with id=" + id + " not found");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
