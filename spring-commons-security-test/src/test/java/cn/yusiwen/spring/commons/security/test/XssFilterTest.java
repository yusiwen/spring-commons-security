package cn.yusiwen.spring.commons.security.test;

import cn.yusiwen.spring.commons.security.core.filter.XssFilter;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

class XssFilterTest {

    @Test
    void stripScriptTagFromParameter() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name", "<script>alert('xss')</script>");

        MockHttpServletResponse response = new MockHttpServletResponse();
        new XssFilter().doFilter(request, response, (servletRequest, servletResponse) -> {
            String value = servletRequest.getParameter("name");
            assertThat(value).doesNotContain("<script>");
            assertThat(value).doesNotContain("alert");
        });
    }

    @Test
    void safeStringPassesThrough() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name", "hello world");

        MockHttpServletResponse response = new MockHttpServletResponse();
        new XssFilter().doFilter(request, response, (servletRequest, servletResponse) -> {
            assertThat(servletRequest.getParameter("name")).isEqualTo("hello world");
        });
    }

    @Test
    void stripJavascriptFromHeader() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Referer", "javascript:alert(1)");

        MockHttpServletResponse response = new MockHttpServletResponse();
        new XssFilter().doFilter(request, response, (servletRequest, servletResponse) -> {
            assertThat(((HttpServletRequest) servletRequest).getHeader("Referer")).doesNotContain("javascript");
        });
    }
}
