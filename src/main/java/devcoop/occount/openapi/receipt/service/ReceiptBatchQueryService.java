package devcoop.occount.openapi.receipt.service;

import devcoop.occount.openapi.receipt.api.ReceiptBatchResponse;
import devcoop.occount.openapi.receipt.repository.ReceiptRepository;
import devcoop.occount.openapi.support.BadRequestException;
import devcoop.occount.openapi.support.page.PageRequestSpec;
import devcoop.occount.openapi.support.sort.SortOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReceiptBatchQueryService {

    private static final Set<String> ALLOWED_RECEIPT_SORT_PROPERTIES = Set.of("receiptId", "itemCode", "saleDate");

    private final ReceiptRepository receiptRepository;

    public List<ReceiptBatchResponse> getReceipts(LocalDate saleDateFrom, LocalDate saleDateTo, Integer page, Integer size, String sort) {
        validateDateRange(saleDateFrom, saleDateTo);
        PageRequestSpec pageRequest = PageRequestSpec.of(page, size, 500);
        SortOption sortOption = SortOption.parse(sort, ALLOWED_RECEIPT_SORT_PROPERTIES, "saleDate");

        if (!pageRequest.isPaged()) {
            return receiptRepository.findBatch(
                    saleDateFrom,
                    saleDateTo,
                    sortOption.property(),
                    sortOption.direction(),
                    null,
                    null
            );
        }

        return receiptRepository.findBatch(
                saleDateFrom,
                saleDateTo,
                sortOption.property(),
                sortOption.direction(),
                pageRequest.offset(),
                pageRequest.size()
        );
    }

    private void validateDateRange(LocalDate saleDateFrom, LocalDate saleDateTo) {
        if (saleDateFrom != null && saleDateTo != null && saleDateFrom.isAfter(saleDateTo)) {
            throw new BadRequestException("saleDateFrom은 saleDateTo보다 늦을 수 없습니다.");
        }
    }
}
