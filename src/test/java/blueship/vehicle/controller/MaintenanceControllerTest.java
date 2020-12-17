package blueship.vehicle.controller;

import blueship.vehicle.Common.TestConstants;
import blueship.vehicle.common.ErrorCode;
import blueship.vehicle.dto.MaintenanceDto;
import blueship.vehicle.exception.VmException;
import blueship.vehicle.service.MaintemanceService;
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

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class MaintenanceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Value("${info.app.id}")
    private String appId;

    @MockBean
    private MaintemanceService maintemanceService;

    @Autowired
    private JacksonTester<MaintenanceDto> maintenanceDtoJacksonTester;

    private final String MAINTEMANCE_ID = "100";

    @Test
    void getAllMaintenances_EmptyList() throws Exception {
        when(maintemanceService.getAllMaintenances()).thenReturn(Collections.emptyList());
        mockMvc.perform(get(TestConstants.MAINTENANCES_V1_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getAllMaintenances_ReturnList() throws Exception {
        List<MaintenanceDto> maintenanceDtoList = new ArrayList<>();
        MaintenanceDto maintenanceDto1 = new MaintenanceDto();
        maintenanceDto1.setVehicleId(10);
        MaintenanceDto maintenanceDto2 = new MaintenanceDto();
        maintenanceDto2.setVehicleId(10);

        maintenanceDtoList.add(maintenanceDto1);
        maintenanceDtoList.add(maintenanceDto2);
        when(maintemanceService.getAllMaintenances()).thenReturn(maintenanceDtoList);
        mockMvc.perform(get(TestConstants.MAINTENANCES_V1_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].vehicleId").value(10))
                .andExpect(jsonPath("$[1].vehicleId", is(10)));
    }

    @Test
    void createMaintenance_ThrowException() throws Exception {
        MaintenanceDto maintenanceDto = MaintenanceDto.builder()
                .vehicleId(10)
                .price(200.2f)
                .bookingDate(LocalDate.now())
                .build();
        when(maintemanceService.saveMaintenance(any(MaintenanceDto.class))).thenThrow(new VmException(null, ErrorCode.FAILED_PERSIST_DATA, new StringBuilder("")));
        mockMvc.perform(post(TestConstants.MAINTENANCES_V1_URL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(maintenanceDtoJacksonTester.write(maintenanceDto).getJson()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(appId + ErrorCode.FAILED_PERSIST_DATA.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.FAILED_PERSIST_DATA.getMessageCode()));
    }

    @Test
    void createMaintenance_Success() throws Exception {
        MaintenanceDto maintenanceDto = MaintenanceDto.builder()
                                                .vehicleId(10)
                                                .price(200.2f)
                                                .bookingDate(LocalDate.now())
                                                .build();
        when(maintemanceService.saveMaintenance(any(MaintenanceDto.class))).thenReturn(maintenanceDto);

        mockMvc.perform(post(TestConstants.MAINTENANCES_V1_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(maintenanceDtoJacksonTester.write(maintenanceDto).getJson()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(10));
    }

    @Test
    void deleteMaintenance_Success() throws Exception {
        // Build path
        UriTemplate uriTemplate = new UriTemplate(TestConstants.MAINTENANCES_V1_GET_MAINTENANCE_URL);
        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put("maintenanceId", MAINTEMANCE_ID);

        doNothing().when(maintemanceService).deleteMaintenance(Integer.valueOf(MAINTEMANCE_ID));
        mockMvc.perform(delete(uriTemplate.expand(uriVariables).toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}