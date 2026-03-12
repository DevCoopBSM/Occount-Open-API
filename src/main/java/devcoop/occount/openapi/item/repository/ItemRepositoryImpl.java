package devcoop.occount.openapi.item.repository;

import com.querydsl.core.types.Predicate;
import devcoop.occount.openapi.item.api.ItemBatchResponse;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static devcoop.occount.openapi.item.domain.QItemEntity.itemEntity;

public class ItemRepositoryImpl implements ItemBatchRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(jakarta.persistence.EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ItemBatchResponse> findBatch(String category, String sortProperty, String sortDirection, Integer offset, Integer limit) {
        var query = queryFactory
                .select(Projections.constructor(
                        ItemBatchResponse.class,
                        itemEntity.itemCode,
                        itemEntity.itemName,
                        itemEntity.itemCategory,
                        itemEntity.itemPrice
                ))
                .from(itemEntity)
                .where(categoryEq(category))
                .orderBy(itemOrderSpecifier(sortProperty, sortDirection));

        if (offset != null) {
            query.offset(offset);
        }

        if (limit != null) {
            query.limit(limit);
        }

        return query.fetch();
    }

    @Override
    public long countBatch(String category) {
        Long count = queryFactory
                .select(itemEntity.count())
                .from(itemEntity)
                .where(categoryEq(category))
                .fetchOne();
        return count == null ? 0L : count;
    }

    private Predicate categoryEq(String category) {
        return category == null ? null : itemEntity.itemCategory.lower().eq(category.toLowerCase());
    }

    private OrderSpecifier<?> itemOrderSpecifier(String sortProperty, String sortDirection) {
        Order order = "desc".equalsIgnoreCase(sortDirection) ? Order.DESC : Order.ASC;
        return switch (sortProperty) {
            case "itemCode" -> new OrderSpecifier<>(order, itemEntity.itemCode);
            case "itemName" -> new OrderSpecifier<>(order, itemEntity.itemName);
            case "itemCategory" -> new OrderSpecifier<>(order, itemEntity.itemCategory);
            case "itemPrice" -> new OrderSpecifier<>(order, itemEntity.itemPrice);
            default -> new OrderSpecifier<>(order, itemEntity.itemId);
        };
    }
}
