package cn.yusiwen.spring.commons.security.test;

import cn.yusiwen.spring.commons.security.core.jwt.JwtTokenUtil;
import cn.yusiwen.spring.commons.security.core.handler.JwtAuthenticationEntryPoint;
import cn.yusiwen.spring.commons.security.core.filter.JwtAuthenticationTokenFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SecuritySmokeTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        assertThat(context).isNotNull();
    }

    @Test
    void jwtTokenUtilIsRegistered() {
        assertThat(context.getBean(JwtTokenUtil.class)).isNotNull();
    }

    @Test
    void entryPointIsRegistered() {
        assertThat(context.getBean(JwtAuthenticationEntryPoint.class)).isNotNull();
    }

    @Test
    void jwtFilterIsRegistered() {
        assertThat(context.getBean(JwtAuthenticationTokenFilter.class)).isNotNull();
    }
}
