package com.qa.tool_manager.domain.tool.model.vo;

import com.qa.common_libs.validator.VOValidator;
import com.qa.common_libs.vo.ValueObject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class ToolDescriptionValue implements ValueObject {

    @Getter
    @NotEmpty(message = "Tool description is required.")
    @Size(max = 1024, message = "Task description should not be longer than {max}.")
    private String value;

    public ToolDescriptionValue(String value) {
        this.value = value;
        VOValidator.validate(this);
    }

    @Override
    public String toString() {
        return value;
    }
}
