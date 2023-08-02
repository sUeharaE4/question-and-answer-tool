package com.qa.tool_manager.usecase;

import com.qa.common_libs.dto.tool.ToolDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ToolUseCase {

    public ToolDTO createTool(String name, String description);

    public Page<ToolDTO> listTools(Pageable page);
}
