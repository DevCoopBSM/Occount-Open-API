package devcoop.occount.openapi.item.api;

public record ItemBatchResponse(
        String itemCode,
        String itemName,
        String itemCategory,
        int itemPrice
) {
}
