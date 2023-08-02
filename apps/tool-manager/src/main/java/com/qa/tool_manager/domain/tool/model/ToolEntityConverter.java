package com.qa.tool_manager.domain.tool.model;

import com.qa.common_libs.dto.tool.ToolDTO;
import com.qa.common_libs.exception.ValidateException;
import com.qa.tool_manager.domain.tool.model.vo.ToolDescriptionValue;
import com.qa.tool_manager.domain.tool.model.vo.ToolNameValue;

public class ToolEntityConverter {

    private ToolEntityConverter() {}

    public static ToolDTO toDTO(ToolEntity entity) {
        return new ToolDTO(entity.getId(), entity.getName().getValue(),
                entity.getDescription().getValue(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public static ToolEntity toEntity(ToolDTO dto) throws ValidateException {
        ToolNameValue nameValue = new ToolNameValue(dto.getName());
        ToolDescriptionValue descriptionValue = new ToolDescriptionValue(dto.getDescription());
        return new ToolEntity(dto.getId(), nameValue, descriptionValue, dto.getCreatedAt(),
                dto.getUpdatedAt());
    }

}
