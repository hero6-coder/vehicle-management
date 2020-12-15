package blueship.vehicle.controller;

import blueship.vehicle.Common.TestConstants;
import blueship.vehicle.common.Constants;
import blueship.vehicle.common.ErrorCode;
import blueship.vehicle.dto.UserDto;
import blueship.vehicle.dto.VehicleDto;
import blueship.vehicle.exception.VmException;
import blueship.vehicle.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriTemplate;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Value("${info.app.id}")
    private String appId;

    @MockBean
    private UserService userService;

    @Autowired
    private JacksonTester<UserDto> userDtoJacksonTester;

    private final String USER_ID = "5";

    @Test
    void getActiveUsers_EmptyList() throws Exception {
        when(userService.getActiveUser()).thenReturn(Collections.emptyList());
        mockMvc.perform(get(TestConstants.USER_V1_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getActiveUsers_ReturnList() throws Exception {
        List<UserDto> userDtoList = new ArrayList<>();
        UserDto userDto1 = new UserDto();
        userDto1.setEmail("hero6-coder@gmail.com");
        UserDto userDto2 = new UserDto();
        userDto2.setEmail("andy@gmail.com");
        userDtoList.add(userDto1);
        userDtoList.add(userDto2);
        when(userService.getActiveUser()).thenReturn(userDtoList);
        mockMvc.perform(get(TestConstants.USER_V1_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].email").value("hero6-coder@gmail.com"))
                .andExpect(jsonPath("$[0].status", is(Constants.ACTIVE)))
                .andExpect(jsonPath("$[1].email").value("andy@gmail.com"))
                .andExpect(jsonPath("$[1].status", is(Constants.ACTIVE)));
    }

    @Test
    void getVehiclesByUser_UserDoesNotExist() throws Exception {
        // Build path
        UriTemplate uriTemplate = new UriTemplate(TestConstants.USER_V1_GET_VEHICLES_URL);
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("userId", USER_ID);

        when(userService.getVehiclesByUser(5)).thenThrow(new VmException(null, ErrorCode.USER_NOT_EXIST, new StringBuilder("UserId does not exist")));
        mockMvc.perform(get(uriTemplate.expand(uriVariables).toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(appId + ErrorCode.USER_NOT_EXIST.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_EXIST.getMessageCode()));
    }

    @Test
    void getVehiclesByUser_EmptyList() throws Exception {
        // Build path
        UriTemplate uriTemplate = new UriTemplate(TestConstants.USER_V1_GET_VEHICLES_URL);
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("userId", USER_ID);

        when(userService.getVehiclesByUser(5)).thenReturn(Collections.emptyList());
        mockMvc.perform(get(uriTemplate.expand(uriVariables).toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getVehiclesByUser_ReturnList() throws Exception {
        // Build path
        UriTemplate uriTemplate = new UriTemplate(TestConstants.USER_V1_GET_VEHICLES_URL);
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("userId", USER_ID);
        // Buid the returned object
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        VehicleDto vehicleDto1 = new VehicleDto();
        vehicleDto1.setUserId(5);
        vehicleDto1.setEngineNumber("18Z7-8932");
        VehicleDto vehicleDto2 = new VehicleDto();
        vehicleDto2.setUserId(5);
        vehicleDto2.setEngineNumber("16N2-6833");
        vehicleDtoList.add(vehicleDto1);
        vehicleDtoList.add(vehicleDto2);

        when(userService.getVehiclesByUser(5)).thenReturn(vehicleDtoList);
        mockMvc.perform(get(uriTemplate.expand(uriVariables).toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].engineNumber").value("18Z7-8932"))
                .andExpect(jsonPath("$[0].userId", is(5)))
                .andExpect(jsonPath("$[1].engineNumber").value("16N2-6833"))
                .andExpect(jsonPath("$[1].userId", is(5)));
    }

    @Test
    void saveUser_ThrowException() throws Exception {
        UserDto userDto = UserDto.builder().email("hero6-coder@gmail.com").password("abc123").build();
        when(userService.saveUser(any(UserDto.class))).thenThrow(new VmException(null, ErrorCode.FAILED_PERSIST_DATA, new StringBuilder("")));
        mockMvc.perform(post(TestConstants.USER_V1_URL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(userDtoJacksonTester.write(userDto).getJson()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(appId + ErrorCode.FAILED_PERSIST_DATA.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.FAILED_PERSIST_DATA.getMessageCode()));
    }

    @Test
    void saveUser_Success() throws Exception {
        UserDto userDto = UserDto.builder().email("hero6-coder@gmail.com").password("abc123").build();
        when(userService.saveUser(any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(post(TestConstants.USER_V1_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(userDtoJacksonTester.write(userDto).getJson()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("hero6-coder@gmail.com"))
                .andExpect(jsonPath("$.password").value("abc123"));
    }
}