package com.qa.tool.domain.tool.model.vo;

import com.qa.common_libs.validator.VOValidator;
import com.qa.common_libs.vo.ValueObject;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

public class ToolAnswerValue implements ValueObject {

    @Getter
    @NotEmpty(message = "Tool answer is required.")
    private String value;

    public ToolAnswerValue(String value) {
        this.value = value;
        VOValidator.validate(this);
    }

    @Override
    public String toString() {
        return value;
    }
}
