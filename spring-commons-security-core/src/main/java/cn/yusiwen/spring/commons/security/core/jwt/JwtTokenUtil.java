package cn.yusiwen.spring.commons.security.core.jwt;

import cn.yusiwen.spring.commons.security.core.config.SecurityProperties;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    private final String secret;
    private final long expirationMs;

    public JwtTokenUtil(SecurityProperties properties) {
        this.secret = properties.getJwtSecret();
        this.expirationMs = properties.getJwtExpirationSec() * 1000;
    }

    public JwtTokenUtil(String secret, long expirationMs) {
        this.secret = secret;
        this.expirationMs = expirationMs;
    }

    public String generateToken(JwtPayLoad payLoad) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", payLoad.getUserId());
        claims.put("account", payLoad.getAccount());
        if (payLoad.getExtra() != null) {
            claims.putAll(payLoad.getExtra());
        }
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public JwtPayLoad parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            JwtPayLoad payLoad = new JwtPayLoad();
            payLoad.setUserId(claims.get("userId", String.class));
            payLoad.setAccount(claims.get("account", String.class));
            return payLoad;
        } catch (ExpiredJwtException e) {
            log.warn("JWT token expired");
            throw e;
        } catch (JwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            throw e;
        }
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
