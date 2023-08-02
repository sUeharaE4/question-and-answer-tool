package com.qa.tool_manager.domain.tool.model;

import com.qa.common_libs.exception.ResourceNotFoundException;
import com.qa.common_libs.exception.ValidateException;
import com.qa.tool_manager.domain.tool.model.vo.ToolDescriptionValue;
import com.qa.tool_manager.domain.tool.model.vo.ToolNameValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ToolService {

    private final ToolEntityRepository toolRepo;

    public Page<ToolEntity> listAll(Pageable pageable) {
        return toolRepo.findAll(pageable);
    }

    public ToolEntity findById(Long id) throws ResourceNotFoundException {
        return toolRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("id", "Specified id does not exist."));
    }

    public ToolEntity create(String name, String description) throws ValidateException {
        return toolRepo.save(makeEntityObj(name, description));
    }

    public List<ToolEntity> bulkInsert(List<String> names, List<String> descriptions)
            throws ValidateException {
        if (names.size() != descriptions.size()) {
            throw new ValidateException(List.of("the size of input list is not matched"));
        }
        ArrayList<ToolEntity> entities = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            entities.add(makeEntityObj(names.get(i), descriptions.get(i)));
        }
        return toolRepo.saveAll(entities);
    }


    public ToolEntity update(Long id, String name, String description)
            throws ResourceNotFoundException, ValidateException {
        ToolEntity entity = findById(id);
        ToolNameValue nameValue =
                Objects.nonNull(name) ? new ToolNameValue(name) : entity.getName();
        ToolDescriptionValue descriptionValue =
                Objects.nonNull(description) ? new ToolDescriptionValue(description)
                        : entity.getDescription();
        entity.setName(nameValue);
        entity.setDescription(descriptionValue);
        return toolRepo.save(entity);
    }

    public void delete(Long id) throws ResourceNotFoundException {
        ToolEntity entity = findById(id);
        toolRepo.delete(entity);
    }

    private ToolEntity makeEntityObj(String name, String description) throws ValidateException {
        ToolNameValue nameValue = new ToolNameValue(name);
        ToolDescriptionValue descriptionValue = new ToolDescriptionValue(description);
        return new ToolEntity(nameValue, descriptionValue);
    }

}
