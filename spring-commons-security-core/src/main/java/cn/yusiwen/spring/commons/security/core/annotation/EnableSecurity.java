package cn.yusiwen.spring.commons.security.core.annotation;

import cn.yusiwen.spring.commons.security.core.config.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SecurityAutoConfiguration.class)
public @interface EnableSecurity {
}
