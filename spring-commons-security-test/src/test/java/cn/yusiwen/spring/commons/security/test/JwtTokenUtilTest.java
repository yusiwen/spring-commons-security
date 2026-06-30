package cn.yusiwen.spring.commons.security.test;

import cn.yusiwen.spring.commons.security.core.jwt.JwtPayLoad;
import cn.yusiwen.spring.commons.security.core.jwt.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtTokenUtilTest {

    private final JwtTokenUtil jwtUtil = new JwtTokenUtil("test-secret-key-32-chars-minimum!", 3600);

    @Test
    void generateAndParseToken() {
        JwtPayLoad payLoad = new JwtPayLoad("user123", "admin");
        String token = jwtUtil.generateToken(payLoad);

        assertThat(token).isNotNull();

        JwtPayLoad parsed = jwtUtil.parseToken(token);
        assertThat(parsed.getUserId()).isEqualTo("user123");
        assertThat(parsed.getAccount()).isEqualTo("admin");
    }

    @Test
    void validateValidToken() {
        JwtPayLoad payLoad = new JwtPayLoad("user123", "admin");
        String token = jwtUtil.generateToken(payLoad);
        assertThat(jwtUtil.validateToken(token)).isTrue();
    }

    @Test
    void validateInvalidToken() {
        assertThat(jwtUtil.validateToken("invalid-token")).isFalse();
    }

    @Test
    void expiredTokenThrowsException() {
        JwtTokenUtil shortLived = new JwtTokenUtil("test-secret-key-32-chars-minimum!", 0);
        JwtPayLoad payLoad = new JwtPayLoad("user123", "admin");
        String token = shortLived.generateToken(payLoad);

        // Wait a tiny bit for expiration
        try { Thread.sleep(10); } catch (InterruptedException ignored) {}

        assertThrows(ExpiredJwtException.class, () -> shortLived.parseToken(token));
    }
}
