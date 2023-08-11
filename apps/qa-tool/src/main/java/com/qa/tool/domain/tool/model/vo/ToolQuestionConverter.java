package com.qa.tool.domain.tool.model.vo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class ToolQuestionConverter implements AttributeConverter<ToolQuestionValue, String> {

    @Override
    public ToolQuestionValue convertToEntityAttribute(String attribute) {
        return Objects.nonNull(attribute) ? new ToolQuestionValue(attribute) : null;
    }

    @Override
    public String convertToDatabaseColumn(ToolQuestionValue dbData) {
        return Objects.nonNull(dbData) ? dbData.toString() : null;
    }


}
