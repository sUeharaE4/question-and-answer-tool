package com.qa.tool.presentation;

import com.qa.common_libs.dto.qa.QACreateRequest;
import com.qa.common_libs.dto.qa.QADTO;
import com.qa.tool.usecase.ToolQAUseCase;
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
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/api/v1.0/qa/tools/{toolId}")
@RequiredArgsConstructor
public class ToolQAController {

    private final ToolQAUseCase qaUseCase;

    @GetMapping
    public ResponseEntity<Page<QADTO>> listToolQAs(@PathVariable Long toolId, Pageable pageable) {
        Page<QADTO> res = qaUseCase.listToolQAs(toolId, pageable);
        return new ResponseEntity<Page<QADTO>>(res, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<QADTO>> searchToolQA(@PathVariable Long toolId,
            @RequestParam(name = "ids", required = true) List<Long> ids, Pageable pageable) {
        Page<QADTO> res = qaUseCase.findByToolIds(ids, pageable);
        return new ResponseEntity<Page<QADTO>>(res, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<QADTO> createQA(@PathVariable Long toolId,
            @RequestBody QACreateRequest req) {
        QADTO dto = qaUseCase.createQA(toolId, req.getQuestion(), req.getAnswer());
        return new ResponseEntity<QADTO>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<QADTO>> bulkCreateQA(@PathVariable Long toolId,
            @RequestBody List<QACreateRequest> req) {
        List<String> questions = req.stream().map(QACreateRequest::getQuestion).toList();
        List<String> answers = req.stream().map(QACreateRequest::getAnswer).toList();
        return new ResponseEntity<>(qaUseCase.createQAs(toolId, questions, answers),
                HttpStatus.CREATED);
    }
}
