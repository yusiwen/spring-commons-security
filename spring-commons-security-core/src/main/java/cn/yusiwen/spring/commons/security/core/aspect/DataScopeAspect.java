package cn.yusiwen.spring.commons.security.core.aspect;

import cn.yusiwen.spring.commons.security.core.annotation.DataScope;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class DataScopeAspect {

    private static final Logger log = LoggerFactory.getLogger(DataScopeAspect.class);

    @Around("@annotation(dataScope)")
    public Object around(ProceedingJoinPoint point, DataScope dataScope) throws Throwable {
        log.debug("Data scope check for method: {}", point.getSignature().getName());
        return point.proceed();
    }
}
