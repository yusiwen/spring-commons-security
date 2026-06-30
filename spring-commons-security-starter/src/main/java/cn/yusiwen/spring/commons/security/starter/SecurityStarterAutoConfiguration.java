package cn.yusiwen.spring.commons.security.starter;

import cn.yusiwen.spring.commons.security.core.config.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SecurityAutoConfiguration.class)
public class SecurityStarterAutoConfiguration {
}
