package com.test.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

/**
 * 测试类demo
 * <p>
 * Created by Rao on 2023/8/30 16:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Commit
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RequestControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void insertTest() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/request/create"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        System.out.println(response.getContentAsString(Charset.forName("UTF-8")));
    }
}
