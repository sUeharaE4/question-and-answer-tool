package com.qa.tool.domain.tool.model;

import com.qa.common_libs.dto.qa.QADTO;
import com.qa.common_libs.exception.ValidateException;
import com.qa.tool.domain.tool.model.vo.ToolAnswerValue;
import com.qa.tool.domain.tool.model.vo.ToolQuestionValue;

public class ToolQAEntityConverter {

    private ToolQAEntityConverter() {}

    public static QADTO toDTO(ToolQAEntity entity) {
        return new QADTO(entity.getId(), entity.getToolId(), entity.getQuestion().getValue(),
                entity.getAnswer().getValue(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public static ToolQAEntity toEntity(QADTO dto) throws ValidateException {
        ToolQuestionValue questionValue = new ToolQuestionValue(dto.getQuestion());
        ToolAnswerValue answerValue = new ToolAnswerValue(dto.getAnswer());
        return new ToolQAEntity(dto.getId(), dto.getToolId(), questionValue, answerValue,
                dto.getCreatedAt(), dto.getUpdatedAt());
    }

}
