package com.qa.tool_manager.domain.tool.model.vo;

import com.qa.common_libs.exception.ValidateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

public class ToolNameValueTest {

    private static final int MAX_LENGTH = 64;

    @Test
    public void testInvalidEmpty() throws Exception {
        Assertions.assertThrows(ValidateException.class, () -> {
            new ToolNameValue("");
        });
    }

    @Test
    public void testInvalidNull() throws Exception {
        Assertions.assertThrows(ValidateException.class, () -> {
            new ToolNameValue(null);
        });
    }

    @Test
    public void testInvalidOverLength() throws Exception {
        Assertions.assertThrows(ValidateException.class, () -> {
            new ToolNameValue(RandomStringUtils.random(MAX_LENGTH + 1, true, true));
        });
    }

    @Test
    public void testSuccess() throws Exception {
        Assertions.assertDoesNotThrow(() -> {
            String name = RandomStringUtils.random(MAX_LENGTH - 1, true, true);
            name = name + "あ";
            new ToolNameValue(name);
        });
    }
}
