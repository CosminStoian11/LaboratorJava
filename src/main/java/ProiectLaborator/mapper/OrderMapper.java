package ProiectLaborator.mapper;

import ProiectLaborator.entity.Customer;
import ProiectLaborator.entity.Order;
import ProiectLaborator.entity.OrderItem;
import ProiectLaborator.request.OrderItemRequest;
import ProiectLaborator.request.OrderRequest;
import ProiectLaborator.response.OrderItemResponse;
import ProiectLaborator.response.OrderResponse;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class OrderMapper {

    public static OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();

        response.setId(order.getId());
        response.setOrderDate(order.getOrderDate());
        response.setTotalAmount(order.getTotalAmount());
        response.setItems(order.getOrderItems().stream()
                .map(item -> new OrderItemResponse(item.getId(), item.getBooks().getTitle(), item.getQuantity()))
                .collect(Collectors.toList()));

        return response;
    }

    public static Order toEntity(Customer customer, List<OrderItem> items) {
        Order order = new Order();

        order.setCustomer(customer);
        order.setOrderDate(new Date());
        order.setTotalAmount(calculateTotal(items));
        order.setOrderItems(items);

        return order;
    }

    public static OrderItemRequest toOrderItemReqFromOrderItem(OrderItem item) {
        return OrderItemRequest.builder()
                .bookId(item.getBooks().getId())
                .quantity(item.getQuantity())
                .build();
    }

    public static OrderRequest toOrderRequestFromOrder(Order order) {
        List<OrderItemRequest> itemsReqs = order.getOrderItems().stream()
                .map(OrderMapper::toOrderItemReqFromOrderItem)
                .collect(Collectors.toList());

        return OrderRequest.builder()
                .customerId(order.getCustomer().getId())
                .items(itemsReqs)
                .build();
    }

    public static Double calculateTotal(List<OrderItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getBooks().getPrice())
                .sum();
    }
}
