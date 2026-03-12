package devcoop.occount.openapi.receipt.api;

import java.time.LocalDate;

public record ReceiptBatchResponse(
        long receiptId,
        String itemCode,
        String itemName,
        int saleQty,
        int tradedPoint,
        LocalDate saleDate
) {
}
