# AGENTS.md

## Build Commands

```bash
# Full compile
./mvnw compile

# Run tests
./mvnw test

# Run tests with output
./mvnw test -pl spring-commons-security-test -am 2>&1 | tail -20
```

## Prerequisites

- JDK 8 installed at `~/.sdkman/candidates/java/8` (Azul)
- `~/.m2/toolchains.xml` configured with JDK 8

## Project Structure

```
spring-commons-security (parent)
├── spring-commons-security-core/     — JWT, filters, entry point, security config
├── spring-commons-security-starter/  — Spring Boot auto-configuration wrapper
└── spring-commons-security-test/     — integration tests
```

## Architecture

- **SecurityAutoConfiguration**: auto-configures JwtTokenUtil, JwtAuthenticationTokenFilter, WebSecurityConfigurerAdapter, XssFilter
- **JwtTokenUtil**: HS512 JWT generation and validation
- **JwtAuthenticationTokenFilter**: extracts Bearer token, sets SecurityContext
- **JwtAuthenticationEntryPoint**: returns 401 JSON response
- **XssFilter**: wraps HttpServletRequest to strip XSS patterns
- **SecurityProperties**: configurable via `commons.security.*`
