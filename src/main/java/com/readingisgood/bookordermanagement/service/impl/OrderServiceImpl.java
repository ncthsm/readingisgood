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
import com.readingisgood.bookordermanagement.service.OrderService;
import com.readingisgood.bookordermanagement.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final CustomerService customerService;

    private final BookService bookService;

    private final SequenceGeneratorService sequenceGeneratorService;

    public OrderDTO addItemToBasket(AddItemToBasketRequest addItemToBasketRequest){

        Customer customer = customerService.findCustomerById(addItemToBasketRequest.getCustomerId());

        if(customer == null){
            log.info(" Customer Not Found By id={}",addItemToBasketRequest.getCustomerId().toString());
            return null;
        }

        Book book = bookService.findBookById(addItemToBasketRequest.getOrderItem().getBookId());

        if(book == null){
            log.info("Book not found by id ={}",addItemToBasketRequest.getOrderItem().getBookId());
            return null;
        }

        if(addItemToBasketRequest.getOrderItem().getQuantity()>book.getStock()){
            log.info("Book stock not enough for quantity ,bookId={},stock={} quantity={}",
                    book.getId(),book.getStock(),book.getId());
            return null;
        }

        Optional<Order> dbInBasketOrderOptional = orderRepository.getOrderByOrderStatusAndCustomerId(OrderStatus.BASKET, customer.getId());

        if(dbInBasketOrderOptional.isPresent()){
          return  this.updateBasket(dbInBasketOrderOptional.get(),addItemToBasketRequest.getOrderItem(),book.getStock());
        }else{
           return this.createBasket(addItemToBasketRequest.getOrderItem());
        }

    }

    private OrderDTO updateBasket(Order dbInBasketOrder,OrderItem requestOrderItem,Integer stockCount){

        List<OrderItem> orderItems = dbInBasketOrder.getOrderItems().stream().filter(orderItem -> orderItem.getBookId() ==
                requestOrderItem.getBookId()).collect(Collectors.toList());

        if(orderItems != null && orderItems.size()>0){
            int updatedQuantity  = orderItems.get(0).getQuantity() + requestOrderItem.getQuantity();
            if(updatedQuantity>stockCount){
                log.info("Book stock not enough for quantity ,bookId={},stock={} quantity={}",
                        requestOrderItem.getBookId(),stockCount,updatedQuantity);
                return null;
            }else{
               int index = dbInBasketOrder.getOrderItems().indexOf(orderItems.get(0));
               dbInBasketOrder.getOrderItems().get(index).setQuantity(index);
                log.info("Update basket for quantity OrderItem={}, OrderId={}",requestOrderItem,
                        dbInBasketOrder.getId());
               orderRepository.save(dbInBasketOrder);
            }
        }else{
            dbInBasketOrder.getOrderItems().add(requestOrderItem);
            log.info("Update basket for new orderItem={}, OrderId={}",requestOrderItem,
                    dbInBasketOrder.getId());
            orderRepository.save(dbInBasketOrder);
        }

        return OrderDTO.fromOrder(dbInBasketOrder);
    }

    private OrderDTO createBasket(OrderItem orderItem){
        ArrayList<OrderItem> orders = new ArrayList<>();
        orders.add(orderItem);
        Order order = Order.builder().id(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME)).
                orderStatus(OrderStatus.BASKET).orderItems(orders).build();
        log.info("Create New Basket Status Order ",order.toString());
        orderRepository.save(order);
        return OrderDTO.fromOrder(order);
    }

    public boolean deleteBasket(Long customerId){

        Optional<Order> basketOrder = orderRepository.getOrderByOrderStatusAndCustomerId(OrderStatus.BASKET,customerId);

        if(!basketOrder.isPresent()){
            log.info("Basket not found by customerId={}",customerId);
            return  false;
        }

        orderRepository.delete(basketOrder.get());

        return true;
    }

    public OrderDTO confirmBasket(Long customerId)  {

        Optional<Order> basketOrder = orderRepository.getOrderByOrderStatusAndCustomerId(OrderStatus.BASKET,customerId);

        if(!basketOrder.isPresent()){
            log.info("Basket not found by customerId={}",customerId);
            return  null;
        }

        Order order = basketOrder.get();

        List<Book> bookList = order.getOrderItems().stream()
                .map(bookOrder -> bookService.getBookFromStock(bookOrder.getBookId(), bookOrder.getQuantity()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (bookList.size() < order.getOrderItems().size()) {
            log.error("Order is not created due to lack of requested books");
            return null;
        }

        Map<Long,Integer > orderMap =order.getOrderItems().stream()
                .collect(Collectors.toMap(OrderItem::getBookId,OrderItem::getQuantity));

        Double totalAmount = bookList.stream()
                .map(book -> orderMap.get(book.getId()) * book.getAmount())
                .reduce(0.0, Double::sum);

        Integer totalBooks = orderMap.values().stream()
                .reduce(0, Integer::sum);

        order.setOrderStatus(OrderStatus.DONE);
        order.setTotalAmount(totalAmount);
        order.setTotalQuantity(totalBooks);
        order.setDoneDate(LocalDateTime.now());

        orderRepository.save(order);

        log.info("Order is successfully , customerId={} books={},orderId={}", customerId, bookList,order.getId());

        return OrderDTO.fromOrder(order);

    }

    @Override
    public OrderDTO getOrderByStatusAndCustomerId(OrderStatus orderStatus,Long customerId){
        Optional<Order> order = orderRepository.getOrderByOrderStatusAndCustomerId(orderStatus, customerId);
        return OrderDTO.fromOrder(order.get());
    }

    @Override
    public Page<Order> getOrderDoneStatusAndCustomerId( Long customerId, Pageable page){
        Page<Order> order = orderRepository.getOrderByOrderStatusAndCustomerId(OrderStatus.DONE, customerId,page);
        return order;
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {

        final Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            log.error("Order is not found with id={}", orderId);
            return null;
        }
        return OrderDTO.fromOrder(orderOptional.get());

    }

    @Override
    public List<OrderDTO> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate,Pageable pageable) {

        Page<Order> orders = orderRepository.findByDoneDateBetween(startDate, endDate,pageable);
        List<Order> customerOrders = orders.getContent();

        return customerOrders.stream()
                .map(OrderDTO::fromOrder)
                .collect(Collectors.toList());

    }

    @Override
    public List<OrderDTO> getOrdersByCustomerId(String customerId, Pageable pageable) {
        return null;
    }

    public List<OrderDTO> getCustomerDoneOrders(Long customerId, Pageable pageable){

        if(customerService.findCustomerById(customerId) == null){
            return null;
        }

        Page<Order> ordersPage = this.getOrderDoneStatusAndCustomerId(customerId,pageable);

        List<Order> customerOrders = ordersPage.getContent();

        return customerOrders.stream()
                .map(OrderDTO::fromOrder)
                .collect(Collectors.toList());

    }



}
