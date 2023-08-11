package com.qa.common_libs.dto.qa;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class QADTO {

    private Long id;
    @JsonProperty("tool_id")
    private Long toolId;
    private String question;
    private String answer;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @Deprecated(since = "Used by auto")
    public QADTO() {}
}
