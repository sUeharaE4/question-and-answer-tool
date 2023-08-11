package com.qa.tool.usecase;

import com.qa.common_libs.dto.qa.QADTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ToolQAUseCase {

    public QADTO createQA(Long toolId, String question, String answer);

    public List<QADTO> createQAs(Long toolId, List<String> questions, List<String> answers);

    public Page<QADTO> listToolQAs(Long toolId, Pageable page);

    public Page<QADTO> findByToolIds(List<Long> ids, Pageable page);
}
