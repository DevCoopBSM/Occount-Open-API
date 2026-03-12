package devcoop.occount.openapi.item.service;

import devcoop.occount.openapi.item.api.ItemBatchResponse;
import devcoop.occount.openapi.item.repository.ItemRepository;
import devcoop.occount.openapi.support.page.PageRequestSpec;
import devcoop.occount.openapi.support.sort.SortOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemBatchQueryService {

    private static final Set<String> ALLOWED_SORT_PROPERTIES = Set.of("itemId", "itemCode", "itemName", "itemCategory", "itemPrice");

    private final ItemRepository itemRepository;

    public List<ItemBatchResponse> getItems(String category, Integer page, Integer size, String sort) {
        PageRequestSpec pageRequest = PageRequestSpec.of(page, size, 200);
        SortOption sortOption = SortOption.parse(sort, ALLOWED_SORT_PROPERTIES, "itemId");
        String normalizedCategory = StringUtils.hasText(category) ? category : null;

        if (!pageRequest.isPaged()) {
            return itemRepository.findBatch(
                    normalizedCategory,
                    sortOption.property(),
                    sortOption.direction(),
                    null,
                    null
            );
        }

        return itemRepository.findBatch(
                normalizedCategory,
                sortOption.property(),
                sortOption.direction(),
                pageRequest.offset(),
                pageRequest.size()
        );
    }
}
