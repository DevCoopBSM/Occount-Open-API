package devcoop.occount.openapi.receipt.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import devcoop.occount.openapi.receipt.api.ReceiptBatchResponse;

import java.time.LocalDate;
import java.util.List;

import static devcoop.occount.openapi.receipt.domain.QReceiptEntity.receiptEntity;

public class ReceiptRepositoryImpl implements ReceiptBatchRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReceiptRepositoryImpl(jakarta.persistence.EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ReceiptBatchResponse> findBatch(
            LocalDate saleDateFrom,
            LocalDate saleDateTo,
            String sortProperty,
            String sortDirection,
            Integer offset,
            Integer limit
    ) {
        var query = queryFactory
                .select(Projections.constructor(
                        ReceiptBatchResponse.class,
                        receiptEntity.receiptId,
                        receiptEntity.itemCode,
                        receiptEntity.itemName,
                        receiptEntity.saleQty,
                        receiptEntity.tradedPoint,
                        receiptEntity.saleDate
                ))
                .from(receiptEntity)
                .where(
                        saleDateFromGoe(saleDateFrom),
                        saleDateToLoe(saleDateTo)
                )
                .orderBy(receiptOrderSpecifier(sortProperty, sortDirection));

        if (offset != null) {
            query.offset(offset);
        }
        if (limit != null) {
            query.limit(limit);
        }

        return query.fetch();
    }

    @Override
    public long countBatch(LocalDate saleDateFrom, LocalDate saleDateTo) {
        Long count = queryFactory
                .select(receiptEntity.count())
                .from(receiptEntity)
                .where(
                        saleDateFromGoe(saleDateFrom),
                        saleDateToLoe(saleDateTo)
                )
                .fetchOne();
        return count == null ? 0L : count;
    }

    private com.querydsl.core.types.Predicate saleDateFromGoe(LocalDate saleDateFrom) {
        return saleDateFrom == null ? null : receiptEntity.saleDate.goe(saleDateFrom);
    }

    private com.querydsl.core.types.Predicate saleDateToLoe(LocalDate saleDateTo) {
        return saleDateTo == null ? null : receiptEntity.saleDate.loe(saleDateTo);
    }

    private OrderSpecifier<?> receiptOrderSpecifier(String sortProperty, String sortDirection) {
        Order order = "desc".equalsIgnoreCase(sortDirection) ? Order.DESC : Order.ASC;
        return switch (sortProperty) {
            case "receiptId" -> new OrderSpecifier<>(order, receiptEntity.receiptId);
            case "itemCode" -> new OrderSpecifier<>(order, receiptEntity.itemCode);
            default -> new OrderSpecifier<>(order, receiptEntity.saleDate);
        };
    }
}
