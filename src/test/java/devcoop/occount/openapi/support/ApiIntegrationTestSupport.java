package devcoop.occount.openapi.support;

import devcoop.occount.openapi.item.domain.ItemEntity;
import devcoop.occount.openapi.item.repository.ItemRepository;
import devcoop.occount.openapi.receipt.domain.ReceiptEntity;
import devcoop.occount.openapi.receipt.repository.ReceiptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

public abstract class ApiIntegrationTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @BeforeEach
    void setUpBatchData() {
        receiptRepository.deleteAll();
        itemRepository.deleteAll();

        itemRepository.save(new ItemEntity(
                1,
                "880001",
                "Milk",
                null,
                "Fresh milk",
                "DRINK",
                2500,
                5,
                "NONE",
                null,
                null
        ));
        itemRepository.save(new ItemEntity(
                2,
                "880002",
                "Bread",
                null,
                "Soft bread",
                "FOOD",
                1800,
                3,
                "ONE_PLUS_ONE",
                LocalDate.of(2026, 3, 1),
                LocalDate.of(2026, 3, 31)
        ));

        receiptRepository.save(new ReceiptEntity(
                null,
                "880001",
                "Milk",
                "ST123456",
                1,
                2500,
                (byte) 0,
                LocalDate.of(2026, 3, 10),
                201
        ));
        receiptRepository.save(new ReceiptEntity(
                null,
                "880002",
                "Bread",
                "TE999900",
                2,
                3600,
                (byte) 1,
                LocalDate.of(2026, 3, 11),
                102
        ));
    }
}
