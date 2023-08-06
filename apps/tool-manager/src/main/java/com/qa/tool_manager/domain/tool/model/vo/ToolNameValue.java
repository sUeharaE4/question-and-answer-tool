package com.qa.tool_manager.domain.tool.model.vo;

import com.qa.common_libs.validator.VOValidator;
import com.qa.common_libs.vo.ValueObject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class ToolNameValue implements ValueObject {

    @Getter
    @NotEmpty(message = "Tool name is required.")
    @Size(max = 64, message = "Task name should not be longer than {max}.")
    private String value;

    public ToolNameValue(String value) {
        this.value = value;
        VOValidator.validate(this);
    }

    @Override
    public String toString() {
        return value;
    }
}
