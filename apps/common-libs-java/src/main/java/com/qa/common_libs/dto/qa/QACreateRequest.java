package com.qa.common_libs.dto.qa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QACreateRequest {

    private String question;
    private String answer;
}
