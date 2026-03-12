package devcoop.occount.openapi.item.api;

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
class ItemBatchApiIntegrationTest extends ApiIntegrationTestSupport {

    @Test
    void returnsPagedItemBatchWhenApiKeyIsValid() throws Exception {
        mockMvc.perform(get("/api/v1/items")
                        .header("X-API-Key", "test-key")
                        .param("page", "0")
                        .param("category", "DRINK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].itemCode").value("880001"))
                .andExpect(jsonPath("$[0].itemName").value("Milk"));
    }

    @Test
    void returnsAllItemsWhenPageIsNotProvided() throws Exception {
        mockMvc.perform(get("/api/v1/items")
                        .header("X-API-Key", "test-key"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].itemCode").exists());
    }

    @Test
    void rejectsNegativeItemPage() throws Exception {
        mockMvc.perform(get("/api/v1/items")
                        .header("X-API-Key", "test-key")
                        .param("page", "-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {"message":"page는 0 이상이어야 합니다."}
                        """));
    }

    @Test
    void rejectsItemSizeOverLimit() throws Exception {
        mockMvc.perform(get("/api/v1/items")
                        .header("X-API-Key", "test-key")
                        .param("page", "0")
                        .param("size", "201"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {"message":"size는 1 이상 200 이하여야 합니다."}
                        """));
    }

    @Test
    void rejectsUnsupportedItemSortProperty() throws Exception {
        mockMvc.perform(get("/api/v1/items")
                        .header("X-API-Key", "test-key")
                        .param("sort", "unknown,asc"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {"message":"지원하지 않는 정렬 기준입니다: unknown"}
                        """));
    }

    @Test
    void rejectsUnsupportedItemSortDirection() throws Exception {
        mockMvc.perform(get("/api/v1/items")
                        .header("X-API-Key", "test-key")
                        .param("sort", "itemCode,sideways"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {"message":"지원하지 않는 정렬 방향입니다: sideways"}
                        """));
    }
}
