package cn.yusiwen.spring.commons.security.core.annotation;

import cn.yusiwen.spring.commons.security.core.enums.LogicTypeEnum;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Permission {

    String[] value() default {};

    LogicTypeEnum logicType() default LogicTypeEnum.OR;
}
