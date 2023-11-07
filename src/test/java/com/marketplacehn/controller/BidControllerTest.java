package com.marketplacehn.controller;

import com.marketplacehn.entity.Bid;
import com.marketplacehn.response.Response;
import com.marketplacehn.service.BidService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@WebMvcTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BidController.class)
class BidControllerTest {

    @MockBean
    private BidService underTest;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;

    private Response<Bid> response;
    private static final String BID_ID = UUID.randomUUID().toString();
    private static final String PREFIX = "/api/v1";

    @BeforeEach()
    void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void shouldFindBidById() throws Exception {

        when(underTest.findBidById(BID_ID))
                .thenReturn((Bid) response);
        ResultActions result =  mvc.perform(get(PREFIX + "/bids/{bidId}", BID_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Bid was found."));
    }
}
