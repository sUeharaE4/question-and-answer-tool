package com.qa.tool.domain.tool.model.vo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class ToolAnswerConverter implements AttributeConverter<ToolAnswerValue, String> {

    @Override
    public ToolAnswerValue convertToEntityAttribute(String attribute) {
        return Objects.nonNull(attribute) ? new ToolAnswerValue(attribute) : null;
    }

    @Override
    public String convertToDatabaseColumn(ToolAnswerValue dbData) {
        return Objects.nonNull(dbData) ? dbData.toString() : null;
    }


}
