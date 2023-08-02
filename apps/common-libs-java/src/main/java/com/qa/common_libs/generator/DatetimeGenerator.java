package com.qa.common_libs.generator;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class DatetimeGenerator {

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
