package ru.gb.spring_test.servise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.spring_test.converters.UserMapper;
import ru.gb.spring_test.dto.UserDto;
import ru.gb.spring_test.entities.Role;
import ru.gb.spring_test.entities.User;
import ru.gb.spring_test.repositories.RoleRepository;
import ru.gb.spring_test.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Transactional
    public void registration(UserDto userDto) {
        if(userRepository.existsByUsername(userDto.getUsername())) {
            log.info("User with this name already exists");
            throw new RuntimeException("User with this name already exists");
        }
        userDto.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt()));
        User user = UserMapper.MAPPER.toUser(userDto);
        user.setRoles(List.of(roleRepository.findById(1L).orElseThrow()));
        userRepository.save(user);
    }
}
