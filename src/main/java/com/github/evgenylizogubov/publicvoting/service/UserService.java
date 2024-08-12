package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserDto;
import com.github.evgenylizogubov.publicvoting.error.IllegalRequestDataException;
import com.github.evgenylizogubov.publicvoting.error.NotFoundException;
import com.github.evgenylizogubov.publicvoting.mapper.UserToUserDtoMapper;
import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserToUserDtoMapper userToUserDtoMapper;
    private final PasswordEncoder passwordEncoder;
    
    public UserDto get(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userToUserDtoMapper::toDto).orElse(null);
    }
    
    public UserDto getByEmail(String email) {
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        return user.map(userToUserDtoMapper::toDto).orElse(null);
    }
    
    public List<UserDto> getAll() {
        return userToUserDtoMapper.toDtoList(userRepository.findAll());
    }
    
    @Transactional
    public UserDto create(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalRequestDataException("User with email \"" + userDto.getEmail() + "\" already exists");
        }
        
        User user = userToUserDtoMapper.toEntity(userDto);
        prepareToSave(user);
        User saved = userRepository.save(user);
        return userToUserDtoMapper.toDto(saved);
    }
    
    @Transactional
    public UserDto update(UserDto userDto, int id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id=" + id + " not found");
        }
        
        Optional<User> checkedUser = userRepository.findByEmailIgnoreCase(userDto.getEmail());
        if (checkedUser.isPresent() && checkedUser.get().getId() != id) {
            throw new IllegalRequestDataException("User with email \"" + userDto.getEmail() + "\" already exists");
        }
        
        userDto.setId(id);
        User user = userToUserDtoMapper.toEntity(userDto);
        prepareToSave(user);
        User updated = userRepository.save(user);
        return userToUserDtoMapper.toDto(updated);
    }
    
    public int delete(int id) {
        return userRepository.removeById(id);
    }
    
    private void prepareToSave(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
    }
}
