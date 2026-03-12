package devcoop.occount.openapi.support.sort;

import devcoop.occount.openapi.support.BadRequestException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SortOptionTest {

    private static final Set<String> ALLOWED_PROPERTIES = Set.of("itemCode", "itemName", "saleDate");

    @Test
    void appliesDefaultSortWhenInputIsBlank() {
        SortOption sortOption = SortOption.parse(" ", ALLOWED_PROPERTIES, "saleDate");

        assertThat(sortOption.property()).isEqualTo("saleDate");
        assertThat(sortOption.direction()).isEqualTo("desc");
    }

    @Test
    void treatsFieldOnlyAsAscending() {
        SortOption sortOption = SortOption.parse("itemCode", ALLOWED_PROPERTIES, "saleDate");

        assertThat(sortOption.property()).isEqualTo("itemCode");
        assertThat(sortOption.direction()).isEqualTo("asc");
    }

    @Test
    void parsesExplicitDescendingSort() {
        SortOption sortOption = SortOption.parse("itemName,desc", ALLOWED_PROPERTIES, "saleDate");

        assertThat(sortOption.property()).isEqualTo("itemName");
        assertThat(sortOption.direction()).isEqualTo("desc");
    }

    @Test
    void rejectsUnsupportedSortProperty() {
        assertThatThrownBy(() -> SortOption.parse("unknown,asc", ALLOWED_PROPERTIES, "saleDate"))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("지원하지 않는 정렬 기준입니다: unknown");
    }

    @Test
    void rejectsUnsupportedSortDirection() {
        assertThatThrownBy(() -> SortOption.parse("saleDate,sideways", ALLOWED_PROPERTIES, "saleDate"))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("지원하지 않는 정렬 방향입니다: sideways");
    }
}
