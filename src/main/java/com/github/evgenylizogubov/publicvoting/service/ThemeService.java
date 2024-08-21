package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.error.IllegalRequestDataException;
import com.github.evgenylizogubov.publicvoting.error.NotFoundException;
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

    public Theme get(int id) {
        return themeRepository.findById(id).orElse(null);
    }

    public List<Theme> getAll() {
        return themeRepository.findAll();
    }

    @Transactional
    public Theme create(Theme theme) {
        if (themeRepository.existsByDescription(theme.getDescription())) {
            throw new IllegalRequestDataException("Theme with description \"" + theme.getDescription() +
                    "\" already exists");
        }

        return themeRepository.save(theme);
    }

    @Transactional
    public Theme update(Theme theme, int id) {
        if (!themeRepository.existsById(id)) {
            throw new NotFoundException("Theme with id=" + id + " not found");
        }

        Optional<Theme> checkedTheme = themeRepository.findByDescription(theme.getDescription());
        if (checkedTheme.isPresent() && checkedTheme.get().getId() != id) {
            throw new IllegalRequestDataException("Theme with description \"" + theme.getDescription() +
                    "\" already exists");
        }

        theme.setId(id);
        return themeRepository.save(theme);
    }

    public int delete(int id) {
        return themeRepository.removeById(id);
    }
}
