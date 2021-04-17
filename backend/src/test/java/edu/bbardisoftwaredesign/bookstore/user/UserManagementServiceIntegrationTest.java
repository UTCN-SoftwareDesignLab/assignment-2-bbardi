package edu.bbardisoftwaredesign.bookstore.user;

import edu.bbardisoftwaredesign.bookstore.TestCreationFactory;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static edu.bbardisoftwaredesign.bookstore.TestCreationFactory.randomString;

@SpringBootTest
public class UserManagementServiceIntegrationTest {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void findAll(){
        List<User> users = TestCreationFactory.listOf(User.class);
        userRepository.saveAll(users);
        List<UserDTO> userDTO = userManagementService.findAll();
        Assertions.assertEquals(userDTO.size(),users.size());
    }
    @Test
    void createUser(){
        String role = "ADMIN";
        roleRepository.save(Role.builder().name(ERole.ADMIN).build());
        UserDTO user = UserDTO.builder()
                .username(randomString())
                .password(randomString())
                .email(randomString())
                .roles(Set.of(role))
                .build();
        Assertions.assertNotNull(userManagementService.createUser(user).getId());
    }
    @Test
    void deleteUser(){
        String role = "ADMIN";
        roleRepository.save(Role.builder().name(ERole.ADMIN).build());
        UserDTO user = UserDTO.builder()
                .username(randomString())
                .password(randomString())
                .email(randomString())
                .roles(Set.of(role))
                .build();
        user = userManagementService.createUser(user);
        userManagementService.deleteUser(user.getId());
    }
    @Test
    void editUser(){
        String role = "ADMIN";
        roleRepository.save(Role.builder().name(ERole.ADMIN).build());
        UserDTO user = UserDTO.builder()
                .username(randomString())
                .password(randomString())
                .email(randomString())
                .roles(Set.of(role))
                .build();
        user = userManagementService.createUser(user);
        user.setUsername("AM_I_EDITED");
        Assertions.assertEquals(userManagementService.editUser(user.getId(),user).getUsername(),"AM_I_EDITED");
    }
}
