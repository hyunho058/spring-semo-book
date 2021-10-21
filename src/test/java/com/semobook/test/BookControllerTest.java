package com.semobook.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest{

    @Autowired
    private MockMvc mockMvc;
//    @Autowired
//    BookController bookController;
//
//    @Override
//    protected Object controller() {
//        return bookController;
//    }

    @DisplayName("testBookData")
    @Test
    private void testBookData() throws Exception {
        String isbn = "9788901219943";
        mockMvc.perform(
                        get("/api/book").param("isbn", isbn))
                .andExpect(status().isOk());
    }


}