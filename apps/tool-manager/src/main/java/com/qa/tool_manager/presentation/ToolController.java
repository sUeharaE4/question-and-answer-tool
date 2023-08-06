package com.qa.tool_manager.presentation;

import com.qa.common_libs.dto.tool.ToolCreateRequest;
import com.qa.common_libs.dto.tool.ToolDTO;
import com.qa.common_libs.exception.ResourceNotFoundException;
import com.qa.tool_manager.usecase.ToolUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1.0/tools")
@RequiredArgsConstructor
public class ToolController {

    private final ToolUseCase toolUseCase;

    @GetMapping
    public ResponseEntity<Page<ToolDTO>> listTools(Pageable pageable) {
        Page<ToolDTO> res = toolUseCase.listTools(pageable);
        return new ResponseEntity<Page<ToolDTO>>(res, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ToolDTO> createTool(@RequestBody ToolCreateRequest req) {
        ToolDTO dto = toolUseCase.createTool(req.getName(), req.getDescription());
        return new ResponseEntity<ToolDTO>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolDTO> findToolById(@PathVariable("id") Long id)
            throws ResourceNotFoundException {
        return new ResponseEntity<ToolDTO>(toolUseCase.findById(id), HttpStatus.OK);
    }
}
