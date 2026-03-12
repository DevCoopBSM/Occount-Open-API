package devcoop.occount.openapi.receipt.repository;

import devcoop.occount.openapi.receipt.api.ReceiptBatchResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReceiptBatchRepositoryCustom {

    List<ReceiptBatchResponse> findBatch(
            LocalDate saleDateFrom,
            LocalDate saleDateTo,
            String sortProperty,
            String sortDirection,
            Integer offset,
            Integer limit
    );

    long countBatch(LocalDate saleDateFrom, LocalDate saleDateTo);
}
