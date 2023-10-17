package io.hexbit.api.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static net.bytebuddy.implementation.FixedValue.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CustomParameterHandlerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void beforeNode() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 로그한글깨짐 문제해결
                .alwaysDo(print())
                .build();
    }

    @Test
    void paramsTrimmed() throws Exception {
        mockMvc.perform(get("/test?params=   string   "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("string"));
    }

    @Test
    void paramsTrimmedThenNull() throws Exception {
        mockMvc.perform(get("/test?params=       "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(nullValue()));
    }


}
