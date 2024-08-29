package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.controller.dto.theme.ThemeDto;
import com.github.evgenylizogubov.publicvoting.error.IllegalRequestDataException;
import com.github.evgenylizogubov.publicvoting.error.NotFoundException;
import com.github.evgenylizogubov.publicvoting.mapper.theme.ThemeToThemeDtoMapper;
import com.github.evgenylizogubov.publicvoting.model.Theme;
import com.github.evgenylizogubov.publicvoting.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final ThemeToThemeDtoMapper themeToThemeDtoMapper;

    public ThemeDto get(int id) {
        Optional<Theme> theme = themeRepository.findById(id);
        return theme.map(themeToThemeDtoMapper::toDto).orElse(null);
    }

    public List<ThemeDto> getAll() {
        return themeToThemeDtoMapper.toDtoList(themeRepository.findAll());
    }
    
    public Theme getFirstUnused() {
        Theme theme = themeRepository.getFirstByIsUsedFalse();
        if (theme == null) {
            themeRepository.resetUsage();
        }
        return themeRepository.getFirstByIsUsedFalse();
    }

    @Transactional
    public ThemeDto create(ThemeDto themeDto) {
        if (themeRepository.existsByDescription(themeDto.getDescription())) {
            throw new IllegalRequestDataException("Theme with description \"" + themeDto.getDescription() +
                    "\" already exists");
        }
        
        Theme saved = themeRepository.save(themeToThemeDtoMapper.toEntity(themeDto));
        return themeToThemeDtoMapper.toDto(saved);
    }

    @Transactional
    public ThemeDto update(ThemeDto themeDto, int id) {
        if (!themeRepository.existsById(id)) {
            throw new NotFoundException("Theme with id=" + id + " not found");
        }

        Optional<Theme> checkedTheme = themeRepository.findByDescription(themeDto.getDescription());
        if (checkedTheme.isPresent() && checkedTheme.get().getId() != id) {
            throw new IllegalRequestDataException("Theme with description \"" + themeDto.getDescription() +
                    "\" already exists");
        }

        themeDto.setId(id);
        Theme updated = themeRepository.save(themeToThemeDtoMapper.toEntity(themeDto));
        return themeToThemeDtoMapper.toDto(updated);
    }

    public int delete(int id) {
        return themeRepository.removeById(id);
    }
}
