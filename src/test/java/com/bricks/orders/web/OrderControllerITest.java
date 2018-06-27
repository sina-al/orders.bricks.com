package com.bricks.orders.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createOrderReturnsReference() throws Exception {
        String uniqueReference = "507f1f77bcf86cd799439011";
        mockMvc.perform(post("/api/order")
                .param("id", uniqueReference)
                .param("quantity", "10"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string(uniqueReference));
    }

    @Test
    public void createOrderWithNonUniqueReferenceFails() throws Exception {
        String nonUniqueReference = "570f1f77bcf86cd799439011";

        // First time: ok.
        mockMvc.perform(post("/api/order")
                .param("id", nonUniqueReference)
                .param("quantity", "10"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string(nonUniqueReference));

        // Second time: bad request.
        mockMvc.perform(post("/api/order")
                .param("id", nonUniqueReference)
                .param("quantity", "20"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(nonUniqueReference));
    }

    @Test
    public void createOrderSucceeds() throws Exception {
        mockMvc.perform(post("/api/order")
                .param("quantity", "10"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN));
    }
}
