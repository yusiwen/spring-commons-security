package cn.yusiwen.spring.commons.security.core.config;

import cn.yusiwen.spring.commons.security.core.aspect.DataScopeAspect;
import cn.yusiwen.spring.commons.security.core.aspect.PermissionAspect;
import cn.yusiwen.spring.commons.security.core.filter.JwtAuthenticationTokenFilter;
import cn.yusiwen.spring.commons.security.core.filter.XssFilter;
import cn.yusiwen.spring.commons.security.core.filter.repeat.RepeatableFilter;
import cn.yusiwen.spring.commons.security.core.handler.JwtAuthenticationEntryPoint;
import cn.yusiwen.spring.commons.security.core.jwt.JwtTokenUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@ComponentScan("cn.yusiwen.spring.commons.security.core")
@EnableConfigurationProperties(SecurityProperties.class)
@ConditionalOnProperty(prefix = "commons.security", name = "enabled", matchIfMissing = true)
public class SecurityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenUtil jwtTokenUtil(SecurityProperties properties) {
        return new JwtTokenUtil(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(
            JwtTokenUtil jwtTokenUtil, SecurityProperties properties) {
        return new JwtAuthenticationTokenFilter(jwtTokenUtil, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter(
            JwtAuthenticationTokenFilter jwtFilter,
            JwtAuthenticationEntryPoint entryPoint,
            SecurityProperties properties) {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http
                    .csrf().disable()
                    .cors().and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .exceptionHandling().authenticationEntryPoint(entryPoint).and()
                    .authorizeRequests()
                        .antMatchers(properties.getWhitelistUrls()).permitAll()
                        .anyRequest().authenticated().and()
                    .addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
            }
        };
    }

    @Bean
    @ConditionalOnProperty(prefix = "commons.security", name = "xss.enabled", matchIfMissing = true)
    public XssFilter xssFilter() {
        return new XssFilter();
    }

    @Bean
    @ConditionalOnProperty(prefix = "commons.security", name = "repeatable-filter-enabled", matchIfMissing = false)
    public RepeatableFilter repeatableFilter() {
        return new RepeatableFilter();
    }

    @Bean
    @ConditionalOnProperty(prefix = "commons.security", name = "permission-enabled", matchIfMissing = false)
    public PermissionAspect permissionAspect() {
        return new PermissionAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public DataScopeAspect dataScopeAspect() {
        return new DataScopeAspect();
    }
}
