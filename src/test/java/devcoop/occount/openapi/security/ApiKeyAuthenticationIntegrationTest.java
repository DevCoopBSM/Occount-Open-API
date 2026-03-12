package devcoop.occount.openapi.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ApiKeyAuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void rejectsBatchRequestWithoutApiKey() throws Exception {
        mockMvc.perform(get("/api/v1/receipts"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json("""
                        {"message":"API 키가 없거나 올바르지 않습니다."}
                        """));
    }

    @Test
    void rejectsBatchRequestWithInvalidApiKey() throws Exception {
        mockMvc.perform(get("/api/v1/receipts")
                        .header("X-API-Key", "wrong-key"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json("""
                        {"message":"API 키가 없거나 올바르지 않습니다."}
                        """));
    }
}
