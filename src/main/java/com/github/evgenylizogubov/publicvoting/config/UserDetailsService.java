package com.github.evgenylizogubov.publicvoting.config;

import com.github.evgenylizogubov.publicvoting.controller.AuthUser;
import com.github.evgenylizogubov.publicvoting.mapper.UserToUserDtoMapper;
import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;
    private final UserToUserDtoMapper userToUserDtoMapper;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Authentication by '{}'", email);
        Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(email);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User '" + email + "' was not found"));
        return new AuthUser(userToUserDtoMapper.toDto(user));
    }
}
