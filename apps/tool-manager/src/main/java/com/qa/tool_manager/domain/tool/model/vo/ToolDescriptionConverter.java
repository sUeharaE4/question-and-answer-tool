package com.qa.tool_manager.domain.tool.model.vo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class ToolDescriptionConverter implements AttributeConverter<ToolDescriptionValue, String> {

    @Override
    public ToolDescriptionValue convertToEntityAttribute(String attribute) {
        return Objects.nonNull(attribute) ? new ToolDescriptionValue(attribute) : null;
    }

    @Override
    public String convertToDatabaseColumn(ToolDescriptionValue dbData) {
        return Objects.nonNull(dbData) ? dbData.toString() : null;
    }


}
