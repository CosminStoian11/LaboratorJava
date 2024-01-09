package ProiectLaborator.service;

import ProiectLaborator.entity.Book;
import ProiectLaborator.entity.Customer;
import ProiectLaborator.entity.Order;
import ProiectLaborator.entity.OrderItem;
import ProiectLaborator.mapper.OrderMapper;
import ProiectLaborator.repository.BookRepository;
import ProiectLaborator.repository.CustomerRepository;
import ProiectLaborator.repository.OrderRepository;
import ProiectLaborator.request.OrderRequest;
import ProiectLaborator.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookRepository bookRepository;

    public OrderResponse createOrder(OrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<OrderItem> items = request.getItems().stream()
                .map(itemRequest -> {
                    Book book = bookRepository.findById(itemRequest.getBookId())
                            .orElseThrow(() -> new RuntimeException("Book not found"));
                    OrderItem item = new OrderItem();
                    item.setBooks(book);
                    item.setQuantity(itemRequest.getQuantity());
                    return item;
                }).collect(Collectors.toList());

        Order order = OrderMapper.toEntity(customer, items);
        order = orderRepository.save(order);
        return OrderMapper.toResponse(order);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found for id: " + id));
        return OrderMapper.toResponse(order);
    }

    public OrderResponse updateOrder(Long id, OrderRequest request) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found for id: " + id));

        // Create a map of existing order items for faster lookup
        Map<Long, OrderItem> existingOrderItemMap = existingOrder.getOrderItems().stream()
                .collect(Collectors.toMap(item -> item.getBooks().getId(), Function.identity()));

        List<OrderItem> items = request.getItems().stream()
                .map(itemRequest -> {
                    Book book = bookRepository.findById(itemRequest.getBookId())
                            .orElseThrow(() -> new RuntimeException("Book not found"));

                    OrderItem existingItem = existingOrderItemMap.get(book.getId());

                    if (existingItem == null) {
                        // If the item is not in the existing order, create a new one
                        existingItem = new OrderItem();
                        existingItem.setBooks(book);
                    }

                    existingItem.setQuantity(itemRequest.getQuantity());
                    return existingItem;
                })
                .collect(Collectors.toList());

        // Clear the existing order items and add the updated items
        existingOrder.getOrderItems().clear();
        existingOrder.getOrderItems().addAll(items);

        // Update total amount
        existingOrder.setTotalAmount(OrderMapper.calculateTotal(items));

        orderRepository.save(existingOrder);
        return OrderMapper.toResponse(existingOrder);
    }



    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found for id: " + id);
        }

        orderRepository.deleteById(id);
    }

}