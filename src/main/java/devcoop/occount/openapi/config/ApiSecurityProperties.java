package devcoop.occount.openapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security")
public record ApiSecurityProperties(
        String apiKey
) {
    public String getApiKey() {
        return apiKey;
    }
}
