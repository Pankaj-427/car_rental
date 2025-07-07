package com.car;

import com.car.controller.AdminController;
import com.car.entity.Car;
import com.car.entity.CarStatus;
import com.car.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.ResultMatcher;


import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetCarById_ShouldReturnCar() throws Exception {
        Car car = new Car();
        car.setId(1L);
        car.setModel("Tesla Model 3");
        car.setStatus(CarStatus.IDLE);

        when(carService.getCar(1L)).thenReturn(car);

        mockMvc.perform(get("/admin/car/1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id", is(1)))
                .andExpect((ResultMatcher) jsonPath("$.model", is("Tesla Model 3")))
                .andExpect((ResultMatcher) jsonPath("$.status", is("IDLE")));
    }

    @Test
    void testUpdateCar_ShouldCallService() throws Exception {
        Car car = new Car();
        car.setId(1L);
        car.setModel("BMW i4");
        car.setStatus(CarStatus.IDLE);

        mockMvc.perform(put("/admin/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk());

        verify(carService).updateCar(Mockito.any(Car.class));
    }
}
