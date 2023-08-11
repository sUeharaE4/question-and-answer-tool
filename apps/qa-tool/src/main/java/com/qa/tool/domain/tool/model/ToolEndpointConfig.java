package com.qa.tool.domain.tool.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ToolEndpointConfig {

    @Value("${app.endpoints.find_by_tool_id_fmt}")
    private String findByToolIdFmt;

    public String toFindByToolIdURL(Long toolId) {
        return String.format(findByToolIdFmt, toolId);
    }
}
