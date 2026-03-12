package devcoop.occount.openapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI occountDataPipelineOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("데브쿠프팀 배치 OPEN API")
                        .version("v1")
                        .description("필드 마스킹 기능을 제공하는 팀 내부용 배치 JSON 조회 API입니다.\n" +
                                "Swagger는 문서 확인 및 탐색을 위해 제공되며, 실제 운영 환경의 클라이언트는 X-API-Key를 사용해 인증합니다.\n" +
                                "현재 제공 데이터셋은 상품 마스터와 영수증입니다."))
                .components(new Components().addSecuritySchemes(
                        "apiKey",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("X-API-Key")
                                .description("운영 환경의 클라이언트는 X-API-Key 헤더를 사용하여 인증합니다.")
                ));
    }
}
