package devcoop.occount.openapi.receipt.api;

import devcoop.occount.openapi.support.ApiIntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ReceiptBatchApiIntegrationTest extends ApiIntegrationTestSupport {

    @Test
    void returnsReceiptBatchFilteredBySaleDate() throws Exception {
        mockMvc.perform(get("/api/v1/receipts")
                        .header("X-API-Key", "test-key")
                        .param("page", "0")
                        .param("saleDateFrom", "2026-03-11")
                        .param("saleDateTo", "2026-03-11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].itemCode").value("880002"))
                .andExpect(jsonPath("$[0].saleDate").value("2026-03-11"));
    }

    @Test
    void doesNotExposeSensitiveReceiptFields() throws Exception {
        mockMvc.perform(get("/api/v1/receipts")
                        .header("X-API-Key", "test-key")
                        .param("saleDateFrom", "2026-03-10")
                        .param("saleDateTo", "2026-03-10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemCode").value("880001"))
                .andExpect(jsonPath("$[0].userCode").doesNotExist())
                .andExpect(jsonPath("$[0].userCodeMasked").doesNotExist())
                .andExpect(jsonPath("$[0].saleType").doesNotExist())
                .andExpect(jsonPath("$[0].dailyNum").doesNotExist());
    }

    @Test
    void returnsAllReceiptsWhenPageIsNotProvided() throws Exception {
        mockMvc.perform(get("/api/v1/receipts")
                        .header("X-API-Key", "test-key"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].receiptId").exists());
    }

    @Test
    void rejectsInvalidReceiptDateRange() throws Exception {
        mockMvc.perform(get("/api/v1/receipts")
                        .header("X-API-Key", "test-key")
                        .param("saleDateFrom", "2026-03-12")
                        .param("saleDateTo", "2026-03-11"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {"message":"saleDateFrom은 saleDateTo보다 늦을 수 없습니다."}
                        """));
    }

    @Test
    void rejectsNegativeReceiptPage() throws Exception {
        mockMvc.perform(get("/api/v1/receipts")
                        .header("X-API-Key", "test-key")
                        .param("page", "-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {"message":"page는 0 이상이어야 합니다."}
                        """));
    }

    @Test
    void rejectsReceiptSizeOverLimit() throws Exception {
        mockMvc.perform(get("/api/v1/receipts")
                        .header("X-API-Key", "test-key")
                        .param("page", "0")
                        .param("size", "501"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {"message":"size는 1 이상 500 이하여야 합니다."}
                        """));
    }

    @Test
    void rejectsUnsupportedReceiptSortProperty() throws Exception {
        mockMvc.perform(get("/api/v1/receipts")
                        .header("X-API-Key", "test-key")
                        .param("sort", "unknown,asc"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {"message":"지원하지 않는 정렬 기준입니다: unknown"}
                        """));
    }

    @Test
    void rejectsUnsupportedReceiptSortDirection() throws Exception {
        mockMvc.perform(get("/api/v1/receipts")
                        .header("X-API-Key", "test-key")
                        .param("sort", "saleDate,sideways"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {"message":"지원하지 않는 정렬 방향입니다: sideways"}
                        """));
    }
}
