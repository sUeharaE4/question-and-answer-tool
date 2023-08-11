package com.qa.tool.domain.tool.model.vo;

import com.qa.common_libs.exception.ValidateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToolQuestionValueTest {

    @Test
    public void testInvalidEmpty() throws Exception {
        Assertions.assertThrows(ValidateException.class, () -> {
            new ToolQuestionValue("");
        });
    }

    @Test
    public void testInvalidNull() throws Exception {
        Assertions.assertThrows(ValidateException.class, () -> {
            new ToolQuestionValue(null);
        });
    }

    @Test
    public void testSuccess() throws Exception {
        Assertions.assertDoesNotThrow(() -> {
            String answer = """
                    Question can contain multi line text like this.
                    日本語もOK。
                    """;
            new ToolQuestionValue(answer);
        });
    }
}
