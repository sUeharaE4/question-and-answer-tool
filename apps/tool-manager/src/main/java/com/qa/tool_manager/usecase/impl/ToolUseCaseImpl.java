package com.qa.tool_manager.usecase.impl;

import com.qa.common_libs.dto.tool.ToolDTO;
import com.qa.tool_manager.domain.tool.model.ToolEntity;
import com.qa.tool_manager.domain.tool.model.ToolEntityConverter;
import com.qa.tool_manager.domain.tool.model.ToolService;
import com.qa.tool_manager.usecase.ToolUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ToolUseCaseImpl implements ToolUseCase {

    private final ToolService toolService;

    @Override
    public ToolDTO createTool(String name, String description) {
        return ToolEntityConverter.toDTO(toolService.create(name, description));
    }

    @Override
    public Page<ToolDTO> listTools(Pageable page) {
        Page<ToolEntity> pages = toolService.listAll(page);
        List<ToolDTO> dtos = pages.getContent().stream().map(ToolEntityConverter::toDTO).toList();
        return new PageImpl<>(dtos, page, pages.getTotalElements());
    }

}
