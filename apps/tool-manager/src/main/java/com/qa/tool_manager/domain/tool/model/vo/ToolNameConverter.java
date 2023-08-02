package com.qa.tool_manager.domain.tool.model.vo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class ToolNameConverter implements AttributeConverter<ToolNameValue, String> {

    @Override
    public ToolNameValue convertToEntityAttribute(String attribute) {
        return Objects.nonNull(attribute) ? new ToolNameValue(attribute) : null;
    }

    @Override
    public String convertToDatabaseColumn(ToolNameValue dbData) {
        return Objects.nonNull(dbData) ? dbData.toString() : null;
    }


}
