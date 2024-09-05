package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.controller.dto.theme.ThemeDto;
import com.github.evgenylizogubov.publicvoting.error.IllegalRequestDataException;
import com.github.evgenylizogubov.publicvoting.error.NotFoundException;
import com.github.evgenylizogubov.publicvoting.mapper.theme.ThemeDtoToThemeMapper;
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
    private final ThemeDtoToThemeMapper themeDtoToThemeMapper;

    public ThemeDto get(int id) {
        Optional<Theme> theme = themeRepository.findById(id);
        return theme.map(themeDtoToThemeMapper::toDto).orElse(null);
    }

    public List<ThemeDto> getAll() {
        return themeDtoToThemeMapper.toDtoList(themeRepository.findAll());
    }
    
    public Theme getFirstUnused() {
        Theme theme = themeRepository.getFirstByIsUsedFalse();
        if (theme == null) {
            themeRepository.setUsageForAll(false);
        }
        return themeRepository.getFirstByIsUsedFalse();
    }

    @Transactional
    public ThemeDto create(ThemeDto themeDto) {
        if (themeRepository.existsByDescription(themeDto.getDescription())) {
            throw new IllegalRequestDataException("Theme with description \"" + themeDto.getDescription() +
                    "\" already exists");
        }
        
        Theme saved = themeRepository.save(themeDtoToThemeMapper.toEntity(themeDto));
        return themeDtoToThemeMapper.toDto(saved);
    }

    @Transactional
    public ThemeDto update(ThemeDto themeDto, int id) {
        if (!themeRepository.existsById(id)) {
            throw new NotFoundException("Theme with id=" + id + " not found");
        }

        Theme checkedTheme = themeRepository.findByDescription(themeDto.getDescription());
        if (checkedTheme != null && checkedTheme.getId() != id) {
            throw new IllegalRequestDataException("Theme with description \"" + themeDto.getDescription() +
                    "\" already exists");
        }

        themeDto.setId(id);
        Theme updated = themeRepository.save(themeDtoToThemeMapper.toEntity(themeDto));
        return themeDtoToThemeMapper.toDto(updated);
    }

    public int delete(int id) {
        return themeRepository.removeById(id);
    }
}
