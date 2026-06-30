package cn.yusiwen.spring.commons.security.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.regex.Pattern;

public class XssFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(XssFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    public static class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

        private static final Pattern[] XSS_PATTERNS = {
                Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
                Pattern.compile("src[\r\n]*=[\r\n]*'(.*?)'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
                Pattern.compile("src[\r\n]*=[\r\n]*\"(.*?)\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
                Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
                Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
                Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
                Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
                Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
                Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
                Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE)
        };

        public XssHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String[] getParameterValues(String parameter) {
            String[] values = super.getParameterValues(parameter);
            if (values == null) return null;
            int count = values.length;
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
                encodedValues[i] = stripXss(values[i]);
            }
            return encodedValues;
        }

        @Override
        public String getParameter(String parameter) {
            String value = super.getParameter(parameter);
            return value != null ? stripXss(value) : null;
        }

        @Override
        public String getHeader(String name) {
            String value = super.getHeader(name);
            return value != null ? stripXss(value) : null;
        }

        private String stripXss(String value) {
            if (value == null) return null;
            String result = value;
            for (Pattern pattern : XSS_PATTERNS) {
                result = pattern.matcher(result).replaceAll("");
            }
            return result;
        }
    }
}
