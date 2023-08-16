package com.qa.common_libs.dto.tool;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ToolDTO {

    private Long id;
    private String name;
    private String description;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    /**
     * Create empty object.
     *
     * @deprecated No args constructor of this class is exist for SpringBoot libs.
     */
    @Deprecated(since = "Used by auto")
    public ToolDTO() {}
}
