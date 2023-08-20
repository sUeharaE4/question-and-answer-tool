package com.qa.tool.usecase.impl;

import com.qa.common_libs.dto.qa.QADTO;
import com.qa.common_libs.exception.ResourceNotFoundException;
import com.qa.tool.domain.queue.config.AmazonSQSProperties;
import com.qa.tool.domain.queue.model.AmazonSQSService;
import com.qa.tool.domain.tool.model.ToolQAEntity;
import com.qa.tool.domain.tool.model.ToolQAEntityConverter;
import com.qa.tool.domain.tool.model.ToolQAService;
import com.qa.tool.usecase.ToolQAUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ToolQAUseCaseImpl implements ToolQAUseCase {

    private final ToolQAService qaService;
    private final AmazonSQSService sqsService;
    private final AmazonSQSProperties sqsProperties;

    @Override
    public QADTO createQA(Long toolId, String question, String answer)
            throws ResourceNotFoundException {
        validateToolIsExist(toolId);
        ToolQAEntity createdEntity = qaService.create(toolId, question, answer);
        sendSqs(toolId, List.of(createdEntity.getId()));
        return ToolQAEntityConverter.toDTO(createdEntity);
    }

    @Override
    public List<QADTO> createQAs(Long toolId, List<String> questions, List<String> answers) {
        validateToolIsExist(toolId);
        List<ToolQAEntity> createdEntities = qaService.bulkInsert(toolId, questions, answers);
        sendSqs(toolId, createdEntities.stream().map(ToolQAEntity::getId).toList());
        return createdEntities.stream().map(ToolQAEntityConverter::toDTO).toList();
    }

    @Override
    public Page<QADTO> listToolQAs(Long toolId, Pageable page) {
        Page<ToolQAEntity> pages = qaService.listAllQA(toolId, page);
        List<QADTO> dtos = pages.getContent().stream().map(ToolQAEntityConverter::toDTO).toList();
        return new PageImpl<>(dtos, page, pages.getTotalElements());
    }

    @Override
    public Page<QADTO> findByToolIds(List<Long> ids, Pageable page) {
        Page<ToolQAEntity> pages = qaService.findByIds(ids, page);
        List<QADTO> dtos = pages.getContent().stream().map(ToolQAEntityConverter::toDTO).toList();
        return new PageImpl<>(dtos, page, pages.getTotalElements());
    }

    private void validateToolIsExist(Long toolId) throws ResourceNotFoundException {
        if (qaService.isExistTool(toolId)) {
            return;
        }
        throw new ResourceNotFoundException("toolId",
                String.format("Specified id is not exist: %d", toolId));
    }

    private void sendSqs(Long toolId, List<Long> upsertIds) {
        sqsService.sendMessage(sqsProperties.getQueueName(), toolId, upsertIds);
    }

}
