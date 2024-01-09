package ProiectLaborator.controller;

import ProiectLaborator.mapper.OrderMapper;
import ProiectLaborator.request.OrderRequest;
import ProiectLaborator.response.OrderResponse;
import ProiectLaborator.service.OrderService;
import ProiectLaborator.utils.DataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrderTest() throws Exception {
        OrderResponse orderResponse = OrderMapper.toResponse(DataUtils.getOrderTest());
        OrderRequest orderRequest = OrderMapper.toOrderRequestFromOrder(DataUtils.getOrderTest());

        Mockito.when(orderService.createOrder(orderRequest)).thenReturn(orderResponse);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderResponse.getId()))
                .andExpect(jsonPath("$.totalAmount").value(orderResponse.getTotalAmount()));
    }

    @Test
    void getAllOrdersTest() throws Exception {
        List<OrderResponse> orderResponses = List.of(OrderMapper.toResponse(DataUtils.getOrderTest()));
        Mockito.when(orderService.getAllOrders()).thenReturn(orderResponses);

        mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(orderResponses.size()));
    }

    @Test
    void getOrderByIdTest() throws Exception {
        OrderResponse orderResponse = OrderMapper.toResponse(DataUtils.getOrderTest());
        Long orderId = orderResponse.getId();
        Mockito.when(orderService.getOrderById(orderId)).thenReturn(orderResponse);

        mockMvc.perform(get("/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderResponse.getId()))
                .andExpect(jsonPath("$.totalAmount").value(orderResponse.getTotalAmount()));
    }

    @Test
    void updateOrderTest() throws Exception {
        OrderResponse updatedOrderResponse = OrderMapper.toResponse(DataUtils.getOrderTest());
        Long orderId = updatedOrderResponse.getId();
        OrderRequest orderRequest = OrderMapper.toOrderRequestFromOrder(DataUtils.getOrderTest());

        Mockito.when(orderService.updateOrder(orderId, orderRequest)).thenReturn(updatedOrderResponse);

        mockMvc.perform(put("/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedOrderResponse.getId()))
                .andExpect(jsonPath("$.totalAmount").value(updatedOrderResponse.getTotalAmount()));
    }

    @Test
    void deleteOrderTest() throws Exception {
        Long orderId = 1L;
        Mockito.doNothing().when(orderService).deleteOrder(orderId);

        mockMvc.perform(delete("/orders/{id}", orderId))
                .andExpect(status().isOk());
    }
}
