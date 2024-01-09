package ProiectLaborator.utils;

import ProiectLaborator.entity.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class DataUtils {

    public static Address getAddress() {
        return Address.builder()
                .id(1L)
                .city("city test")
                .country("country test")
                .street("street test")
                .deliveryInstructions("instructions")
                .postalCode("code")
                .build();
    }

    public static Customer getCustomerTest() {
        Address addressTestWithoutCustomer = getAddress();

        Customer customer = Customer.builder()
                .id(1L)
                .email("customer@email.com")
                .name("customer")
                .address(addressTestWithoutCustomer)
                .build();

        addressTestWithoutCustomer.setCustomer(customer);

        return customer;
    }

    public static Category getCategoryTest() {
        return Category.builder()
                .name("category1")
                .id(1L)
                .build();
    }

    public static Book getBookTest() {
        Category category = getCategoryTest();

        Book book = Book.builder()
                .id(1L)
                .title("title")
                .price(12.20)
                .year(2010)
                .author("author")
                .categories(Set.of(category))
                .build();

        category.setBooks(Set.of(book));

        return book;
    }

    public static OrderItem getOrderItem() {
        return OrderItem.builder()
                .id(1L)
                .quantity(1)
                .books(getBookTest())
                .build();
    }

    public static Order getOrderTest() {
        return Order.builder()
                .orderDate(Date.valueOf(LocalDate.now()))
                .totalAmount(10.0)
                .id(1L)
                .customer(getCustomerTest())
                .orderItems(List.of(getOrderItem()))
                .build();
    }

}
