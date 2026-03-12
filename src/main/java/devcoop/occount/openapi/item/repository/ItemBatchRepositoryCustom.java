package devcoop.occount.openapi.item.repository;

import devcoop.occount.openapi.item.api.ItemBatchResponse;

import java.util.List;

public interface ItemBatchRepositoryCustom {

    List<ItemBatchResponse> findBatch(String category, String sortProperty, String sortDirection, Integer offset, Integer limit);

    long countBatch(String category);
}
