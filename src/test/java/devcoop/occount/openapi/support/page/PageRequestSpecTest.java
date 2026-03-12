package devcoop.occount.openapi.support.page;

import devcoop.occount.openapi.support.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PageRequestSpecTest {

    @Test
    void createsUnpagedSpecWhenPageIsNull() {
        PageRequestSpec spec = PageRequestSpec.of(null, null, 200);

        assertThat(spec.isPaged()).isFalse();
        assertThat(spec.page()).isNull();
        assertThat(spec.size()).isNull();
    }

    @Test
    void appliesDefaultPageSizeWhenSizeIsNull() {
        PageRequestSpec spec = PageRequestSpec.of(2, null, 200);

        assertThat(spec.isPaged()).isTrue();
        assertThat(spec.page()).isEqualTo(2);
        assertThat(spec.size()).isEqualTo(PageRequestSpec.DEFAULT_PAGE_SIZE);
    }

    @Test
    void rejectsNegativePage() {
        assertThatThrownBy(() -> PageRequestSpec.of(-1, 10, 200))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("page는 0 이상이어야 합니다.");
    }

    @Test
    void rejectsSizeOutOfRange() {
        assertThatThrownBy(() -> PageRequestSpec.of(0, 201, 200))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("size는 1 이상 200 이하여야 합니다.");
    }

    @Test
    void calculatesOffsetFromPageAndSize() {
        PageRequestSpec spec = PageRequestSpec.of(3, 20, 200);

        assertThat(spec.offset()).isEqualTo(60);
    }

    @Test
    void rejectsOffsetCalculationWhenNotPaged() {
        PageRequestSpec spec = PageRequestSpec.of(null, null, 200);

        assertThatThrownBy(spec::offset)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("페이지 조회가 아닐 때는 offset을 계산할 수 없습니다.");
    }
}
