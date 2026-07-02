package cn.yusiwen.spring.commons.security.core.aspect;

import cn.yusiwen.spring.commons.core.exception.PermissionException;
import cn.yusiwen.spring.commons.security.core.annotation.Permission;
import cn.yusiwen.spring.commons.security.core.enums.LogicTypeEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
public class PermissionAspect {

    private static final Logger log = LoggerFactory.getLogger(PermissionAspect.class);

    @Around("@annotation(permission)")
    public Object around(ProceedingJoinPoint point, Permission permission) throws Throwable {
        String[] required = permission.value();
        if (required.length == 0) {
            return point.proceed();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.warn("No authentication found, access denied");
            throw new PermissionException(403, "Access denied");
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> userRoles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        List<String> requiredRoles = Arrays.asList(required);
        boolean hasPermission;

        if (permission.logicType() == LogicTypeEnum.AND) {
            hasPermission = userRoles.containsAll(requiredRoles);
        } else {
            hasPermission = requiredRoles.stream().anyMatch(userRoles::contains);
        }

        if (!hasPermission) {
            log.warn("User lacks required permission: {} (logic={})", required, permission.logicType());
            throw new PermissionException(403, "Access denied");
        }

        return point.proceed();
    }
}
