package devcoop.occount.openapi.support.page;

import devcoop.occount.openapi.support.BadRequestException;

public record PageRequestSpec(
        Integer page,
        Integer size
) {
    public static final int DEFAULT_PAGE_SIZE = 100;

    public static PageRequestSpec of(Integer page, Integer size, int maxPageSize) {
        if (page == null) {
            return new PageRequestSpec(null, null);
        }
        if (page < 0) {
            throw new BadRequestException("page는 0 이상이어야 합니다.");
        }

        int resolvedSize = size == null ? DEFAULT_PAGE_SIZE : size;
        if (resolvedSize < 1 || resolvedSize > maxPageSize) {
            throw new BadRequestException("size는 1 이상 " + maxPageSize + " 이하여야 합니다.");
        }

        return new PageRequestSpec(page, resolvedSize);
    }

    public boolean isPaged() {
        return page != null;
    }

    public int offset() {
        if (!isPaged()) {
            throw new IllegalStateException("페이지 조회가 아닐 때는 offset을 계산할 수 없습니다.");
        }
        return page * size;
    }
}
