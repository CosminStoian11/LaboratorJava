package ProiectLaborator.service;

import ProiectLaborator.entity.Book;
import ProiectLaborator.entity.Customer;
import ProiectLaborator.entity.Order;
import ProiectLaborator.mapper.OrderMapper;
import ProiectLaborator.repository.BookRepository;
import ProiectLaborator.repository.CustomerRepository;
import ProiectLaborator.repository.OrderRepository;
import ProiectLaborator.request.OrderRequest;
import ProiectLaborator.response.OrderResponse;
import ProiectLaborator.utils.DataUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private OrderMapper orderMapper;

    @Test
    void createOrder() {
        Customer customer = DataUtils.getCustomerTest();
        Order order = DataUtils.getOrderTest();
        OrderRequest request = OrderMapper.toOrderRequestFromOrder(order);

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));

        Book book = DataUtils.getBookTest();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderResponse response = orderService.createOrder(request);

        assertEquals(response, OrderMapper.toResponse(order));
        verify(customerRepository, atMostOnce()).findById(any(Long.class));
        verify(orderRepository, atMostOnce()).save(any(Order.class));
        verifyNoMoreInteractions(customerRepository, orderRepository);
    }

    @Test
    void getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(DataUtils.getOrderTest());
        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderResponse> responses = orderService.getAllOrders();

        assertEquals(1, responses.size());

        verify(orderRepository, times(1)).findAll();
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void getOrderById() {
        Order order = DataUtils.getOrderTest();
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));


        OrderResponse response = orderService.getOrderById(1L);

        assertNotNull(response);
        assertEquals(response, OrderMapper.toResponse(order));
        verify(orderRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void deleteOrder() {
        when(orderRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> orderService.deleteOrder(1L));

        verify(orderRepository, times(1)).existsById(anyLong());
        verify(orderRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(orderRepository);
    }

}