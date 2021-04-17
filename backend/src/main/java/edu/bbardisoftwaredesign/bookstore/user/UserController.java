package edu.bbardisoftwaredesign.bookstore.user;

import edu.bbardisoftwaredesign.bookstore.user.model.dto.UserDTO;
import edu.bbardisoftwaredesign.bookstore.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.bbardisoftwaredesign.bookstore.UrlMapping.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {
    private final UserManagementService userManagementService;

    @GetMapping
    public List<UserDTO> findAllUsers() {
        return userManagementService.findAll();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO user) {
        return userManagementService.createUser(user);
    }

    @DeleteMapping(ENTITY)
    public void deleteUser(@PathVariable Long id) {
        userManagementService.deleteUser(id);
    }

    @PatchMapping(ENTITY)
    public UserDTO editUser(@PathVariable Long id, @RequestBody UserDTO user) {
        return userManagementService.editUser(id, user);
    }
}
