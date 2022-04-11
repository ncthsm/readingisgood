package com.readingisgood.bookordermanagement.controller;

import com.google.gson.Gson;
import com.readingisgood.bookordermanagement.controller.request.CreateCustomerRequest;
import com.readingisgood.bookordermanagement.dto.CustomerDTO;
import com.readingisgood.bookordermanagement.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@WebMvcTest(value = CustomerController.class)
class CustomerControllerTest {

    private static final String EMAIL = "test@gmail.com";
    private static final String FIRST_NAME = "Test";
    private static final String PHONE = "+905********";
    private static final String ADDRESS = "Test Address";

    private MockMvc mockMvc;

    @Autowired
    protected Gson gson;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void afterTest()
    {
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .email(EMAIL)
                .name(FIRST_NAME)
                .gsm(PHONE)
                .address(ADDRESS)
                .build();

        CreateCustomerRequest request = new CreateCustomerRequest();

        Mockito.when(customerService.createCustomer(any(CreateCustomerRequest.class))).thenReturn(customerDTO);

       /* this.mockMvc.perform(post("/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        Mockito.verify(customerService).createNewCustomer(any(CustomerAddRequest.class));*/
    }

    @Test
    void getCustomerOrders() throws Exception {
        /*String customerId = "c1L";
        OrderDto orderDto = OrderDto.builder().build();
        List<OrderDto> orderDtoList = Collections.singletonList(orderDto);
        PageRequest pageRequest = PageRequest.of(0, 20);

        when(customerService.getCustomerAllOrders(customerId, pageRequest)).thenReturn(orderDtoList);

        String url = "/customer/create";
        this.mockMvc.perform(get("/customer/{id}/orders", customerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(customerService).getCustomerAllOrders(customerId, pageRequest);
    }*/
    }
}