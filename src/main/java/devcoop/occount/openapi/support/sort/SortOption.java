package devcoop.occount.openapi.support.sort;

import devcoop.occount.openapi.support.BadRequestException;
import org.springframework.util.StringUtils;

import java.util.Set;

public record SortOption(
        String property,
        String direction
) {
    public static SortOption parse(String sort, Set<String> allowedProperties, String defaultProperty) {
        if (!StringUtils.hasText(sort)) {
            return new SortOption(defaultProperty, "desc");
        }

        String[] tokens = sort.split(",", 2);
        String property = tokens[0].trim();
        if (!allowedProperties.contains(property)) {
            throw new BadRequestException("지원하지 않는 정렬 기준입니다: " + property);
        }

        String direction = tokens.length == 1 ? "asc" : tokens[1].trim().toLowerCase();
        if (!direction.equals("asc") && !direction.equals("desc")) {
            throw new BadRequestException("지원하지 않는 정렬 방향입니다: " + tokens[1]);
        }

        return new SortOption(property, direction);
    }
}
