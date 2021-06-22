package com.mgmt.todo.service;

import com.mgmt.todo.persistence.model.UserAuth;
import com.mgmt.todo.persistence.repository.UserRepository;
import com.mgmt.todo.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDTO saveUser(UserDTO userDTO) {


        UserAuth user = UserAuth.builder()
                .username(userDTO.getUsername())
                .password(bCryptPasswordEncoder
                        .encode(userDTO.getPassword()))
                .build();
        UserAuth saved = userRepository.save(user);
        UserDTO userDTOSaved = userDTO.builder()
                .username(saved.getUsername())
                .build();
        return userDTOSaved;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
