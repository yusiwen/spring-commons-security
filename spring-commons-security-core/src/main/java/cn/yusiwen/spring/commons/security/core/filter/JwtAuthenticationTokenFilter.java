package cn.yusiwen.spring.commons.security.core.filter;

import cn.yusiwen.spring.commons.security.core.config.SecurityProperties;
import cn.yusiwen.spring.commons.security.core.jwt.JwtPayLoad;
import cn.yusiwen.spring.commons.security.core.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    private final JwtTokenUtil jwtTokenUtil;
    private final String tokenHeader;
    private final String tokenPrefix;

    public JwtAuthenticationTokenFilter(JwtTokenUtil jwtTokenUtil, SecurityProperties properties) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.tokenHeader = properties.getTokenHeader();
        this.tokenPrefix = properties.getTokenPrefix();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenPrefix)) {
            String token = authHeader.substring(tokenPrefix.length());
            try {
                JwtPayLoad payLoad = jwtTokenUtil.parseToken(token);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(payLoad, null, Collections.emptyList());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                log.debug("Failed to authenticate via JWT: {}", e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }
}
