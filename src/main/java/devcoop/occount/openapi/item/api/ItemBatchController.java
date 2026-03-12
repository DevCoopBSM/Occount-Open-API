package devcoop.occount.openapi.item.api;

import devcoop.occount.openapi.item.service.ItemBatchQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "상품")
@RestController
@RequestMapping("/api/v1/items")
public class ItemBatchController {

    private final ItemBatchQueryService itemBatchQueryService;

    public ItemBatchController(ItemBatchQueryService itemBatchQueryService) {
        this.itemBatchQueryService = itemBatchQueryService;
    }

    @Operation(summary = "상품을 페이지 단위 또는 전체로 조회합니다")
    @GetMapping
    public List<ItemBatchResponse> getItems(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "itemId,desc") String sort
    ) {
        return itemBatchQueryService.getItems(category, page, size, sort);
    }
}
