package com.qa.tool.domain.tool.model;

import com.qa.common_libs.dto.tool.ToolDTO;
import com.qa.common_libs.exception.ResourceNotFoundException;
import com.qa.common_libs.exception.ValidateException;
import com.qa.tool.domain.tool.model.vo.ToolAnswerValue;
import com.qa.tool.domain.tool.model.vo.ToolQuestionValue;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ToolQAService {

    private final ToolQAEntityRepository qaRepo;
    private final ToolEndpointConfig toolEndpointConfig;
    private final RestTemplate restTemplate;

    @Cacheable("is-exist-tool")
    public boolean isExistTool(Long toolId) {
        List<Long> toolIds = qaRepo.findDistinctToolId();
        if (toolIds.contains(toolId)) {
            return true;
        }
        try {
            return restTemplate.exchange(toolEndpointConfig.toFindByToolIdURL(toolId),
                    HttpMethod.GET, null, ToolDTO.class).getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    public Page<ToolQAEntity> listAllQA(Long toolId, Pageable pageable) {
        if (!isExistTool(toolId)) {
            throw new ResourceNotFoundException("toolId",
                    String.format("specified id does not exist: %d", toolId));
        }
        return qaRepo.findByToolId(toolId, pageable);
    }

    public ToolQAEntity findById(Long id) throws ResourceNotFoundException {
        return qaRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("id", "Specified id does not exist."));
    }

    public Page<ToolQAEntity> findByIds(List<Long> ids, Pageable pageable) {
        return qaRepo.findByIdIn(ids, pageable);
    }

    public ToolQAEntity create(Long toolId, String question, String answer)
            throws ValidateException {
        return qaRepo.save(makeEntityObj(toolId, question, answer));
    }

    public List<ToolQAEntity> bulkInsert(Long toolId, List<String> questions, List<String> answers)
            throws ValidateException {
        if (questions.size() != answers.size()) {
            throw new ValidateException(List.of("the size of input list is not matched"));
        }
        ArrayList<ToolQAEntity> entities = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            entities.add(makeEntityObj(toolId, questions.get(i), answers.get(i)));
        }
        return qaRepo.saveAll(entities);
    }

    public ToolQAEntity update(Long id, String question, String answer)
            throws ResourceNotFoundException, ValidateException {
        ToolQAEntity entity = findById(id);
        ToolQuestionValue questionValue =
                Objects.nonNull(question) ? new ToolQuestionValue(question) : entity.getQuestion();
        ToolAnswerValue answerValue =
                Objects.nonNull(answer) ? new ToolAnswerValue(answer) : entity.getAnswer();
        entity.setQuestion(questionValue);
        entity.setAnswer(answerValue);
        return qaRepo.save(entity);
    }

    public void delete(Long id) throws ResourceNotFoundException {
        ToolQAEntity entity = findById(id);
        qaRepo.delete(entity);
    }

    private ToolQAEntity makeEntityObj(Long toolId, String question, String answer)
            throws ValidateException {
        ToolQuestionValue questionValue = new ToolQuestionValue(question);
        ToolAnswerValue answerValue = new ToolAnswerValue(answer);
        return new ToolQAEntity(toolId, questionValue, answerValue);
    }

}
