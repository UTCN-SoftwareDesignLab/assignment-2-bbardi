package edu.bbardisoftwaredesign.bookstore.user.service;

import edu.bbardisoftwaredesign.bookstore.user.mapper.UserMapper;
import edu.bbardisoftwaredesign.bookstore.user.model.Role;
import edu.bbardisoftwaredesign.bookstore.user.model.User;
import edu.bbardisoftwaredesign.bookstore.user.model.dto.UserDTO;
import edu.bbardisoftwaredesign.bookstore.user.repository.RoleRepository;
import edu.bbardisoftwaredesign.bookstore.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    private User setRoleIDs(User user) {
        Set<Role> roleSet = new HashSet<>();
        user.getRoles()
                .forEach(role -> {
                    Role newRole = roleRepository.findByName(role.getName()).orElseThrow(() -> new RuntimeException("Unable to find role"));
                    roleSet.add(newRole);
                });
        user.setRoles(roleSet);
        return user;
    }

    private void verifyDataUnique(User user) {
        User DBUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Unable to find user"));
        if (!DBUser.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (!DBUser.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
    }

    private void getPasswordFromDB(User user) {
        User DBUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Unable to find user"));
        user.setPassword(DBUser.getPassword());
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }


    public UserDTO createUser(UserDTO user) {
        User actUser = setRoleIDs(userMapper.fromDto(user));
        if (userRepository.existsByUsername(actUser.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        if (userRepository.existsByEmail(actUser.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        actUser.setPassword(encoder.encode(actUser.getPassword()));
        return userMapper.toDto(userRepository.save(actUser));
    }

    public void deleteUser(UserDTO user) {
        userRepository.delete(userMapper.fromDto(user));
    }

    public UserDTO editUser(UserDTO user) {
        User actUser = setRoleIDs(userMapper.fromDto(user));
        verifyDataUnique(actUser);
        if (user.getPassword().equals("")) {
            getPasswordFromDB(actUser);
        } else {
            actUser.setPassword(encoder.encode(actUser.getPassword()));
        }
        return userMapper.toDto(userRepository.save(actUser));
    }
}
