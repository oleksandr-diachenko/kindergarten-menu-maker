package com.epam.kindergartermenumaker.web.dialect;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Set;

import static java.util.Collections.singleton;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/13/2020
 **/
@Component
public class NumberDialect extends AbstractDialect implements IExpressionObjectDialect {

    NumberDialect() {
        super("Number dialect");
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {

            @Override
            public Set<String> getAllExpressionObjectNames() {
                return singleton("number");
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                return new NumberUtils();
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return true;
            }
        };
    }
}
