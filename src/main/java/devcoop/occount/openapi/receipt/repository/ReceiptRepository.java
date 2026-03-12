package devcoop.occount.openapi.receipt.repository;

import devcoop.occount.openapi.receipt.domain.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Long>, ReceiptBatchRepositoryCustom {
}
