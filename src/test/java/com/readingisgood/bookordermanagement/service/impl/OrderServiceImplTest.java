package com.readingisgood.bookordermanagement.service.impl;

import com.readingisgood.bookordermanagement.controller.request.AddItemToBasketRequest;
import com.readingisgood.bookordermanagement.dto.OrderDTO;
import com.readingisgood.bookordermanagement.model.Book;
import com.readingisgood.bookordermanagement.model.Customer;
import com.readingisgood.bookordermanagement.model.Order;
import com.readingisgood.bookordermanagement.model.OrderItem;
import com.readingisgood.bookordermanagement.model.enums.OrderStatus;
import com.readingisgood.bookordermanagement.repository.OrderRepository;
import com.readingisgood.bookordermanagement.service.BookService;
import com.readingisgood.bookordermanagement.service.CustomerService;
import com.readingisgood.bookordermanagement.service.SequenceGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BookService bookService;

    @Mock
    private CustomerService customerService;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;


    @Test
    void addItemToBasket_createNew(){

        Customer customer = Customer.builder().id(5L).build();
        Book book = Book.builder().id(5L).stock(2).build();

        OrderItem orderItem = new OrderItem(5L,1);

        Mockito.when(customerService.findCustomerById(Mockito.any(Long.class))).thenReturn(customer);
        Mockito.when(bookService.findBookById(Mockito.any(Long.class))).thenReturn(book);
        AddItemToBasketRequest addItemToBasketRequest = new AddItemToBasketRequest(5L,orderItem);
        OrderDTO orderDTO = orderService.addItemToBasket(addItemToBasketRequest);

        assertNotNull(orderDTO);

    }

    @Test
    void addItemToBasket_existBasket(){

        Customer customer = Customer.builder().id(5L).build();
        Book book = Book.builder().id(5L).stock(10).build();

        Order dbOrder =Order.builder().orderStatus(OrderStatus.BASKET).id(5L).build();

        OrderItem orderItem = new OrderItem(5L,1);
        OrderItem orderIte2 = new OrderItem(5L,3);


        dbOrder.getOrderItems().add(orderIte2);

        Mockito.when(customerService.findCustomerById(Mockito.any(Long.class))).thenReturn(customer);
        Mockito.when(bookService.findBookById(Mockito.any(Long.class))).thenReturn(book);
        Mockito.when(orderRepository.getOrderByOrderStatusAndCustomerId(
                Mockito.any(OrderStatus.class),Mockito.any(Long.class))).thenReturn(Optional.of(dbOrder));

        AddItemToBasketRequest addItemToBasketRequest = new AddItemToBasketRequest(5L,orderItem);
        OrderDTO orderDTO = orderService.addItemToBasket(addItemToBasketRequest);

        assertNotNull(orderDTO);
        assertEquals(orderIte2.getQuantity(),orderDTO.getOrderItems().get(0).getQuantity());
    }

    @Test
    void addItemToBasket_existBasketDifferentBook(){

        Customer customer = Customer.builder().id(5L).build();
        Book book = Book.builder().id(5L).stock(10).build();

        Order dbOrder =Order.builder().orderStatus(OrderStatus.BASKET).id(5L).build();

        OrderItem orderItem = new OrderItem(5L,1);
        OrderItem orderIte2 = new OrderItem(6L,3);


        dbOrder.getOrderItems().add(orderIte2);

        Mockito.when(customerService.findCustomerById(Mockito.any(Long.class))).thenReturn(customer);
        Mockito.when(bookService.findBookById(Mockito.any(Long.class))).thenReturn(book);
        Mockito.when(orderRepository.getOrderByOrderStatusAndCustomerId(
                Mockito.any(OrderStatus.class),Mockito.any(Long.class))).thenReturn(Optional.of(dbOrder));

        AddItemToBasketRequest addItemToBasketRequest = new AddItemToBasketRequest(5L,orderItem);
        OrderDTO orderDTO = orderService.addItemToBasket(addItemToBasketRequest);

        assertNotNull(orderDTO);
        assertEquals(orderIte2.getQuantity(),orderDTO.getOrderItems().get(0).getQuantity());
    }

    @Test
    void addItemToBasket_notHaveStock() {

        Customer customer = Customer.builder().id(5L).build();
        Book book = Book.builder().id(5L).stock(2).build();

        OrderItem orderItem = new OrderItem(5L,4);

        Mockito.when(customerService.findCustomerById(Mockito.any(Long.class))).thenReturn(customer);
        Mockito.when(bookService.findBookById(Mockito.any(Long.class))).thenReturn(book);
        AddItemToBasketRequest addItemToBasketRequest = new AddItemToBasketRequest(5L,orderItem);
        OrderDTO orderDTO = orderService.addItemToBasket(addItemToBasketRequest);

        assertNull(orderDTO);

    }

    @Test
    void deleteBasket() {
    }

    @Test
    void confirmBasket() {
    }

    @Test
    void getOrderByStatusAndCustomerId() {
    }

    @Test
    void getOrderDoneStatusAndCustomerId() {
    }

    @Test
    void getOrderById() {
    }

    @Test
    void getOrdersByDate() {
    }

    @Test
    void getOrdersByCustomerId() {
    }

    @Test
    void getCustomerDoneOrders() {
    }
}