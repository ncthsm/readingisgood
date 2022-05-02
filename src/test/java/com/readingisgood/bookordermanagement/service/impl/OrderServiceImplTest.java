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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

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

        Customer customer = Customer.builder().id(5L).build();
        Book book = Book.builder().id(5L).stock(2).build();

        OrderItem orderItem = new OrderItem(5L,4);

        Mockito.when(customerService.findCustomerById(Mockito.any(Long.class))).thenReturn(customer);
        Mockito.when(bookService.findBookById(Mockito.any(Long.class))).thenReturn(book);
        AddItemToBasketRequest addItemToBasketRequest = new AddItemToBasketRequest(5L,orderItem);
        boolean retVal = orderService.deleteBasket(5L);

        assertEquals(false,retVal);
    }

    @Test
    void confirmBasket() {

        OrderItem orderItem = new OrderItem(5L,4);
        Order dbOrder =Order.builder().orderStatus(OrderStatus.BASKET).id(5L).build();
        dbOrder.getOrderItems().add(orderItem);

        Book book = Book.builder().stock(10).amount(10.0).id(5L).bookName("Savaş ve Barış").build();

        Mockito.when(bookService.getBookFromStock(Mockito.any(Long.class),Mockito.any(Integer.class))).
                thenReturn(book);
        Mockito.when(orderRepository.getOrderByOrderStatusAndCustomerId(
                Mockito.any(OrderStatus.class),Mockito.any(Long.class))).thenReturn(Optional.of(dbOrder));

        orderService.confirmBasket(5L);

    }

    @Test
    void getOrdersByDate() {

        LocalDateTime startDate = LocalDateTime.of(2022, 10, 1, 12, 30);
        LocalDateTime endDate = LocalDateTime.of(2022, 10, 15, 12, 30);

        Order order1 = Order.builder()
                .doneDate(LocalDateTime.of(2021, 3, 11, 13, 30))
                .build();

        Order order2 = Order.builder()
                .doneDate(LocalDateTime.of(2022, 2, 9, 11, 30))
                .build();

        Page<Order> page = new PageImpl<>(Arrays.asList(order1,order2));

        when(orderRepository.findByDoneDateBetweenAndOrderStatus(startDate, endDate,Pageable.unpaged(),OrderStatus.DONE))
                .thenReturn(page);

        List<OrderDTO> orders = orderService.getOrdersByDate(startDate, endDate, Pageable.unpaged());

        assertFalse(orders.isEmpty());
        assertEquals(2, orders.size());


    }

    @Test
    void getOrderByStatusAndCustomerId() {
    }

    @Test
    void getOrderDoneStatusAndCustomerId() {
    }

    @Test
    void getOrderById() {

        Long customerId = 101L;
        Long orderId = 11L;
        OrderItem bookOrder1 = new OrderItem(1l, 1);
        OrderItem bookOrder2 = new OrderItem(2l, 2);
        final List<OrderItem>  orderItemList= Arrays.asList(bookOrder1, bookOrder2);
        Double totalAmount= 49.9;

        Order order = Order.builder()
                .customerId(customerId)
                .orderItems(orderItemList)
                .totalAmount(totalAmount)
                .build();

        when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(order));

        OrderDTO orderDto = orderService.getOrderById(orderId);

        assertNotNull(orderDto);
        assertEquals(customerId, orderDto.getCustomerId());
        assertEquals(orderItemList.size(), orderDto.getOrderItems().size());
        assertEquals(totalAmount, orderDto.getTotalAmount());

    }



    @Test
    void getOrdersByCustomerId() {
    }

    @Test
    void getCustomerDoneOrders() {
    }
}