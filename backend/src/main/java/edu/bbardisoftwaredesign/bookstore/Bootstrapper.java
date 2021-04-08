package edu.bbardisoftwaredesign.bookstore;

import edu.bbardisoftwaredesign.bookstore.books.repository.BookRepository;
import edu.bbardisoftwaredesign.bookstore.books.repository.GenreRepository;
import edu.bbardisoftwaredesign.bookstore.user.model.ERole;
import edu.bbardisoftwaredesign.bookstore.user.model.Role;
import edu.bbardisoftwaredesign.bookstore.user.model.dto.UserDTO;
import edu.bbardisoftwaredesign.bookstore.user.repository.RoleRepository;
import edu.bbardisoftwaredesign.bookstore.user.repository.UserRepository;
import edu.bbardisoftwaredesign.bookstore.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final UserManagementService userManagementService;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            userRepository.deleteAll();
            roleRepository.deleteAll();
            bookRepository.deleteAll();
            genreRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            UserDTO newAdmin = UserDTO.builder()
                    .email("admin@admin.com")
                    .username("admin")
                    .password("administrator")
                    .roles(Set.of("ADMIN"))
                    .build();
            userManagementService.createUser(newAdmin);
            UserDTO newUser = UserDTO.builder()
                    .email("user@user.com")
                    .username("user")
                    .password("user")
                    .roles(Set.of("EMPLOYEE"))
                    .build();
            userManagementService.createUser(newUser);
        }
    }
}
