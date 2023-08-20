package com.qa.tool.domain.queue.model.vo;

import com.qa.common_libs.validator.VOValidator;
import com.qa.common_libs.vo.ValueObject;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import java.util.List;

public class UpsertIdList implements ValueObject {

    @Getter
    @NotEmpty(message = "List of upsert id is required.")
    private List<Long> value;

    public UpsertIdList(List<Long> value) {
        this.value = value;
        VOValidator.validate(this);
    }

    @Override
    public String toString() {
        return "Upsert list: " + String.join(", ", value.stream().map(String::valueOf).toList());
    }

    public String toSQSMessage(Long toolId) {
        String upsertIds = String.join(",", value.stream().map(String::valueOf).toList());
        return String.format("{\"tool_id\":%d,\"upsert_ids\":[%s]}", toolId, upsertIds);
    }

}
