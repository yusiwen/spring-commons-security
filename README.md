# spring-commons-security

Spring Boot security starter providing JWT authentication, XSS filter, and Spring Security auto-configuration.

## Features

- **JWT Token Utilities** — `JwtTokenUtil` for token generation/parsing/validation (HS512)
- **JWT Authentication Filter** — `OncePerRequestFilter` that extracts JWT from `Authorization` header and sets `SecurityContext`
- **Spring Security Template** — Stateless session, CORS enabled, CSRF disabled, configurable whitelist URLs
- **XSS Filter** — `javax.servlet.Filter` that strips common XSS patterns from request parameters and headers
- **Extensible** — Provide your own `UserDetailsService` bean for custom authentication logic

## Modules

| Module | Description |
|---|---|
| `spring-commons-security-core` | JWT utils, filters, entry point, security config |
| `spring-commons-security-starter` | Spring Boot auto-configuration |
| `spring-commons-security-test` | Integration tests |

## Quick Start

```xml
<dependency>
    <groupId>cn.yusiwen.spring</groupId>
    <artifactId>spring-commons-security-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Configuration

```yaml
commons:
  security:
    enabled: true
    jwt-secret: your-256-bit-secret-key-here-change-me
    jwt-expiration-sec: 86400
    xss:
      enabled: true
    whitelist-urls:
      - /auth/**
      - /captcha/**
      - /actuator/health
```

## Usage

### 1. Create a UserDetailsService

```java
@Component
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        // Load user from database
        return new User(username, password, authorities);
    }
}
```

### 2. Generate a JWT token

```java
@Autowired
private JwtTokenUtil jwtTokenUtil;

public String login(String username, String password) {
    // authenticate...
    JwtPayLoad payLoad = new JwtPayLoad(userId, username);
    return jwtTokenUtil.generateToken(payLoad);
}
```

### 3. Call authenticated APIs

```
Authorization: Bearer <token>
```

## Requirements

- Java 8+
- Spring Boot 2.7.x

## License

MIT
