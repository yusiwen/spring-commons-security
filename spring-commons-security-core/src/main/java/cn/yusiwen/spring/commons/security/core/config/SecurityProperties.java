package cn.yusiwen.spring.commons.security.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "commons.security")
public class SecurityProperties {

    private String jwtSecret = "default-secret-change-me";

    private long jwtExpirationSec = 86400L;

    private String[] whitelistUrls = {
            "/auth/**",
            "/captcha/**",
            "/actuator/health",
            "/swagger-resources/**",
            "/webjars/**",
            "/v2/api-docs",
            "/doc.html"
    };

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public long getJwtExpirationSec() {
        return jwtExpirationSec;
    }

    public void setJwtExpirationSec(long jwtExpirationSec) {
        this.jwtExpirationSec = jwtExpirationSec;
    }

    public String[] getWhitelistUrls() {
        return whitelistUrls;
    }

    public void setWhitelistUrls(String[] whitelistUrls) {
        this.whitelistUrls = whitelistUrls;
    }
}
