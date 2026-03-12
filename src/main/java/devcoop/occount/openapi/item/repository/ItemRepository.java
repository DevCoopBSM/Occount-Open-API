package devcoop.occount.openapi.item.repository;

import devcoop.occount.openapi.item.domain.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer>, ItemBatchRepositoryCustom {
}
