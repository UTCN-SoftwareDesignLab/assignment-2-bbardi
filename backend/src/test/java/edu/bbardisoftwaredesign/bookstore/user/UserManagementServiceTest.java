package edu.bbardisoftwaredesign.bookstore.user;


import edu.bbardisoftwaredesign.bookstore.TestCreationFactory;
import edu.bbardisoftwaredesign.bookstore.user.mapper.UserMapper;
import edu.bbardisoftwaredesign.bookstore.user.mapper.UserMapperImpl;
import edu.bbardisoftwaredesign.bookstore.user.model.ERole;
import edu.bbardisoftwaredesign.bookstore.user.model.Role;
import edu.bbardisoftwaredesign.bookstore.user.model.User;
import edu.bbardisoftwaredesign.bookstore.user.model.dto.UserDTO;
import edu.bbardisoftwaredesign.bookstore.user.repository.RoleRepository;
import edu.bbardisoftwaredesign.bookstore.user.repository.UserRepository;
import edu.bbardisoftwaredesign.bookstore.user.service.UserManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static edu.bbardisoftwaredesign.bookstore.TestCreationFactory.randomLong;
import static edu.bbardisoftwaredesign.bookstore.TestCreationFactory.randomString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserManagementServiceTest {

    @InjectMocks
    private UserManagementService userManagementService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userManagementService = new UserManagementService(userRepository, roleRepository, userMapper, passwordEncoder);
    }

    @Test
    void findAll() {
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userRepository.findAll()).thenReturn(users);
        List<UserDTO> all = userManagementService.findAll();
        Assertions.assertEquals(all.size(), users.size());
    }

    @Test
    void createUser() {
        String role = "ADMIN";
        UserDTO user = UserDTO.builder()
                .username(randomString())
                .password(randomString())
                .email(randomString())
                .roles(Set.of(role))
                .build();
        User actUser = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(rol -> Role.builder().name(ERole.valueOf(rol)).build()).collect(Collectors.toSet()))
                .build();
        when(userMapper.fromDto(user)).thenReturn(actUser);
        when(userMapper.toDto(any())).thenReturn(user);
        when(roleRepository.findByName(any())).thenReturn(Optional.of(Role.builder().name(ERole.ADMIN).build()));
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn(randomString());
        when(userRepository.save(any())).thenReturn(any());
        Assertions.assertEquals(userManagementService.createUser(user), user);
    }

    @Test
    void deleteUser() {
        String role = "ADMIN";
        UserDTO user = UserDTO.builder()
                .id(randomLong())
                .username(randomString())
                .password(randomString())
                .email(randomString())
                .roles(Set.of(role))
                .build();
        userManagementService.deleteUser(user.getId());
    }

    @Test
    void editUser(){
        String role = "ADMIN";
        UserDTO user = UserDTO.builder()
                .username(randomString())
                .password(randomString())
                .email(randomString())
                .roles(Set.of(role))
                .build();
        User actUser = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(rol -> Role.builder().name(ERole.valueOf(rol)).build()).collect(Collectors.toSet()))
                .build();
        when(userMapper.fromDto(user)).thenReturn(actUser);
        when(userMapper.toDto(any())).thenReturn(user);
        when(roleRepository.findByName(any())).thenReturn(Optional.of(Role.builder().name(ERole.ADMIN).build()));
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn(randomString());
        when(userRepository.save(any())).thenReturn(actUser);
        when(userRepository.findById(any())).thenReturn(Optional.of(actUser));
        Assertions.assertEquals(userManagementService.editUser(user.getId(),user), user);
    }
}
