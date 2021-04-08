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
public class UsersController {
    private final UserManagementService userManagementService;

    @GetMapping(FIND_ALL)
    public List<UserDTO> findAllUsers() {
        return userManagementService.findAll();
    }

    @PostMapping(CREATE)
    public UserDTO createUser(@RequestBody UserDTO user) {
        return userManagementService.createUser(user);
    }

    @DeleteMapping(DELETE)
    public void deleteUser(@RequestBody UserDTO user) {
        userManagementService.deleteUser(user);
    }

    @PatchMapping(EDIT)
    public UserDTO editUser(@RequestBody UserDTO user) {
        return userManagementService.editUser(user);
    }
}
