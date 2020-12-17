package blueship.vehicle.controller;

import blueship.vehicle.Common.TestConstants;
import blueship.vehicle.common.ErrorCode;
import blueship.vehicle.dto.MaintenanceDto;
import blueship.vehicle.dto.VehicleDto;
import blueship.vehicle.exception.VmException;
import blueship.vehicle.service.VehicleService;
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
class VehicleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Value("${info.app.id}")
    private String appId;

    @MockBean
    private VehicleService vehicleService;

    @Autowired
    private JacksonTester<VehicleDto> vehicleDtoJacksonTester;

    private final Integer USER_ID = 5;
    private final String VEHICLE_ID = "10";
    private final String MAINTEMANCE_ID = "100";

    @Test
    void getAllVehicles_EmptyList() throws Exception {
        when(vehicleService.getAllVehicles()).thenReturn(Collections.emptyList());
        mockMvc.perform(get(TestConstants.VEHICLE_V1_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getAllVehicles_ReturnList() throws Exception {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        VehicleDto vehicleDto1 = new VehicleDto();
        vehicleDto1.setEngineNumber("18Z7-89832");
        vehicleDto1.setUserId(5);
        VehicleDto vehicleDto2 = new VehicleDto();
        vehicleDto2.setEngineNumber("16N2-6833");
        vehicleDto2.setUserId(10);
        vehicleDtoList.add(vehicleDto1);
        vehicleDtoList.add(vehicleDto2);
        when(vehicleService.getAllVehicles()).thenReturn(vehicleDtoList);
        mockMvc.perform(get(TestConstants.VEHICLE_V1_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].engineNumber").value("18Z7-89832"))
                .andExpect(jsonPath("$[0].userId", is(5)))
                .andExpect(jsonPath("$[1].engineNumber").value("16N2-6833"))
                .andExpect(jsonPath("$[1].userId", is(10)));
    }

    @Test
    void getMaintenancesByVehicle_VehicleDoesNotExist() throws Exception {
        // Build path
        UriTemplate uriTemplate = new UriTemplate(TestConstants.VEHICLE_V1_GET_MAINTENANCES_URL);
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("vehicleId", VEHICLE_ID);

        when(vehicleService.getMaintenancesByVehicle(Integer.valueOf(VEHICLE_ID))).thenThrow(new VmException(null, ErrorCode.VEHICLE_NOT_EXIST, new StringBuilder("Vehicle does not exist")));
        mockMvc.perform(get(uriTemplate.expand(uriVariables).toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(appId + ErrorCode.VEHICLE_NOT_EXIST.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.VEHICLE_NOT_EXIST.getMessageCode()));
    }

    @Test
    void getMaintenancesByVehicle_EmptyList() throws Exception {
        // Build path
        UriTemplate uriTemplate = new UriTemplate(TestConstants.VEHICLE_V1_GET_MAINTENANCES_URL);
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("vehicleId", VEHICLE_ID);

        when(vehicleService.getMaintenancesByVehicle(Integer.valueOf(VEHICLE_ID))).thenReturn(Collections.emptyList());
        mockMvc.perform(get(uriTemplate.expand(uriVariables).toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getMaintenancesByVehicle_ReturnList() throws Exception {
        // Build path
        UriTemplate uriTemplate = new UriTemplate(TestConstants.VEHICLE_V1_GET_MAINTENANCES_URL);
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("vehicleId", VEHICLE_ID);
        // Buid the returned object
        List<MaintenanceDto> maintenanceDtoList = new ArrayList<>();
        MaintenanceDto maintenanceDto1 = new MaintenanceDto();
        maintenanceDto1.setVehicleId(10);
        MaintenanceDto maintenanceDto2 = new MaintenanceDto();
        maintenanceDto2.setVehicleId(10);
        maintenanceDtoList.add(maintenanceDto1);
        maintenanceDtoList.add(maintenanceDto2);

        when(vehicleService.getMaintenancesByVehicle(Integer.valueOf(VEHICLE_ID))).thenReturn(maintenanceDtoList);
        mockMvc.perform(get(uriTemplate.expand(uriVariables).toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].vehicleId", is(10)))
                .andExpect(jsonPath("$[1].vehicleId", is(10)));
    }

    @Test
    void getMaintenancesByVehicle_WrongPathVariable() throws Exception {
        // Build path
        UriTemplate uriTemplate = new UriTemplate(TestConstants.VEHICLE_V1_GET_MAINTENANCES_URL);
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("vehicleId", VEHICLE_ID);
        // Buid the returned object
        List<MaintenanceDto> maintenanceDtoList = new ArrayList<>();
        MaintenanceDto maintenanceDto1 = new MaintenanceDto();
        maintenanceDto1.setVehicleId(10);
        MaintenanceDto maintenanceDto2 = new MaintenanceDto();
        maintenanceDto2.setVehicleId(10);
        maintenanceDtoList.add(maintenanceDto1);
        maintenanceDtoList.add(maintenanceDto2);

        when(vehicleService.getMaintenancesByVehicle(Integer.parseInt(VEHICLE_ID) + 10)).thenReturn(maintenanceDtoList);
        mockMvc.perform(get(uriTemplate.expand(uriVariables).toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getMaintenanceByVehicleAndMaintenanceId_VehicleDoesNotExist() throws Exception {
        // Build path
        UriTemplate uriTemplate = new UriTemplate(TestConstants.VEHICLE_V1_GET_MAINTENANCE_URL);
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("vehicleId", VEHICLE_ID);
        uriVariables.put("maintenanceId", MAINTEMANCE_ID);

        when(vehicleService.getMaintenancesByVehicleAndId(Integer.valueOf(VEHICLE_ID), Integer.valueOf(MAINTEMANCE_ID)))
                .thenThrow(new VmException(null, ErrorCode.VEHICLE_NOT_EXIST, new StringBuilder("Vehicle does not exist")));
        mockMvc.perform(get(uriTemplate.expand(uriVariables).toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(appId + ErrorCode.VEHICLE_NOT_EXIST.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.VEHICLE_NOT_EXIST.getMessageCode()));
    }

    @Test
    void getMaintenanceByVehicleAndMaintenanceId_MaintenanceDoesNotExist() throws Exception {
        // Build path
        UriTemplate uriTemplate = new UriTemplate(TestConstants.VEHICLE_V1_GET_MAINTENANCE_URL);
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("vehicleId", VEHICLE_ID);
        uriVariables.put("maintenanceId", MAINTEMANCE_ID);

        when(vehicleService.getMaintenancesByVehicleAndId(Integer.valueOf(VEHICLE_ID), Integer.valueOf(MAINTEMANCE_ID)))
                .thenThrow(new VmException(null, ErrorCode.MAINTENANCE_NOT_EXIST, new StringBuilder("Maintenance does not exist")));
        mockMvc.perform(get(uriTemplate.expand(uriVariables).toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(appId + ErrorCode.MAINTENANCE_NOT_EXIST.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.MAINTENANCE_NOT_EXIST.getMessageCode()));
    }

    @Test
    void getMaintenanceByVehicleAndMaintenanceId_Success() throws Exception {
        // Build path
        UriTemplate uriTemplate = new UriTemplate(TestConstants.VEHICLE_V1_GET_MAINTENANCE_URL);
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("vehicleId", VEHICLE_ID);
        uriVariables.put("maintenanceId", MAINTEMANCE_ID);
        // Buid the returned object
        MaintenanceDto maintenanceDto = new MaintenanceDto();
        maintenanceDto.setVehicleId(10);

        when(vehicleService.getMaintenancesByVehicleAndId(Integer.valueOf(VEHICLE_ID), Integer.valueOf(MAINTEMANCE_ID))).thenReturn(maintenanceDto);
        mockMvc.perform(get(uriTemplate.expand(uriVariables).toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.vehicleId", is(10)));
    }

    @Test
    void createVehicle_ThrowException() throws Exception {
        VehicleDto vehicleDto = VehicleDto.builder().engineNumber("18Z7-8932").userId(USER_ID).build();
        when(vehicleService.saveVehicle(any(VehicleDto.class))).thenThrow(new VmException(null, ErrorCode.FAILED_PERSIST_DATA, new StringBuilder("")));
        mockMvc.perform(post(TestConstants.VEHICLE_V1_URL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(vehicleDtoJacksonTester.write(vehicleDto).getJson()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(appId + ErrorCode.FAILED_PERSIST_DATA.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.FAILED_PERSIST_DATA.getMessageCode()));
    }

    @Test
    void createVehicle_Success() throws Exception {
        VehicleDto vehicleDto = VehicleDto.builder().engineNumber("18Z7-8932").userId(USER_ID).build();
        when(vehicleService.saveVehicle(any(VehicleDto.class))).thenReturn(vehicleDto);
        mockMvc.perform(post(TestConstants.VEHICLE_V1_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(vehicleDtoJacksonTester.write(vehicleDto).getJson()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.engineNumber").value("18Z7-8932"))
                .andExpect(jsonPath("$.userId").value(USER_ID));
    }
}