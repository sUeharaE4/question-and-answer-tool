package com.qa.common_libs.dto.tool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolCreateRequest {

    private String name;
    private String description;
}
