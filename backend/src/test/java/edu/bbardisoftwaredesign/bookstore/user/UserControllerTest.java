package edu.bbardisoftwaredesign.bookstore.user;

import edu.bbardisoftwaredesign.bookstore.BaseControllerTest;
import edu.bbardisoftwaredesign.bookstore.TestCreationFactory;
import edu.bbardisoftwaredesign.bookstore.user.model.dto.UserDTO;
import edu.bbardisoftwaredesign.bookstore.user.service.UserManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static edu.bbardisoftwaredesign.bookstore.TestCreationFactory.*;
import static edu.bbardisoftwaredesign.bookstore.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {
    @InjectMocks
    private UserController usersController;

    @Mock
    private UserManagementService userManagementService;

    @BeforeEach
    protected void setUp(){
        super.setUp();
        MockitoAnnotations.openMocks(this);
        usersController = new UserController(userManagementService);
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @Test
    void findAllUsers() throws Exception{
        List<UserDTO> userDTOList = TestCreationFactory.listOf(UserDTO.class);
        when(userManagementService.findAll()).thenReturn(userDTOList);
        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDTOList));

    }

    @Test
    void createUser() throws Exception{
        UserDTO user = UserDTO.builder()
                .id(randomLong())
                .email(randomEmail())
                .username(randomString())
                .password(randomString())
                .build();
        when(userManagementService.createUser(user)).thenReturn(user);
        ResultActions result = performPostWithRequestBody(USERS,user);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }

    @Test
    void deleteUser() throws Exception{
        UserDTO user = UserDTO.builder()
                .id(randomLong())
                .email(randomEmail())
                .username(randomString())
                .password(randomString())
                .build();
        ResultActions result = performDeleteWithPathVariable(USERS+ENTITY,user.getId().toString());
        result.andExpect(status().isOk());
    }

    @Test
    void editUser() throws Exception{
        UserDTO user = UserDTO.builder()
                .id(randomLong())
                .email(randomEmail())
                .username(randomString())
                .password(randomString())
                .build();
        when(userManagementService.editUser(user.getId(),user)).thenReturn(user);
        ResultActions result = performPatchWithRequestBodyAndPathVariable(USERS+ENTITY,user.getId().toString(),user);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }
}
