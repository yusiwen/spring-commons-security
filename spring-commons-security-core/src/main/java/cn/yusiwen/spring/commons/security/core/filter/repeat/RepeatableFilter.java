package cn.yusiwen.spring.commons.security.core.filter.repeat;

import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RepeatableFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            String contentType = request.getContentType();
            if (contentType != null && (contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)
                    || contentType.startsWith(MediaType.APPLICATION_JSON_UTF8_VALUE))) {
                requestWrapper = new RepeatedlyRequestWrapper((HttpServletRequest) request, response);
            }
        }
        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {
    }
}
