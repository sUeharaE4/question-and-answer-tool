package com.qa.tool_manager.domain.tool.model.vo;

import com.qa.common_libs.exception.ValidateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

public class ToolDescriptionValueTest {

    private static final int MAX_LENGTH = 1024;

    @Test
    public void testInvalidEmpty() throws Exception {
        Assertions.assertThrows(ValidateException.class, () -> {
            new ToolDescriptionValue("");
        });
    }

    @Test
    public void testInvalidNull() throws Exception {
        Assertions.assertThrows(ValidateException.class, () -> {
            new ToolDescriptionValue(null);
        });
    }

    @Test
    public void testInvalidOverLength() throws Exception {
        Assertions.assertThrows(ValidateException.class, () -> {
            new ToolDescriptionValue(RandomStringUtils.random(MAX_LENGTH + 1, true, true));
        });
    }

    @Test
    public void testSuccess() throws Exception {
        Assertions.assertDoesNotThrow(() -> {
            String description = RandomStringUtils.random(MAX_LENGTH - 1, true, true);
            description = description + "あ";
            new ToolDescriptionValue(description);
        });
    }
}
