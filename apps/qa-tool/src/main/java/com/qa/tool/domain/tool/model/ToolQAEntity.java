package com.qa.tool.domain.tool.model;

import com.qa.common_libs.entity.AbstractTimeContainEntity;
import com.qa.tool.domain.tool.model.vo.ToolAnswerConverter;
import com.qa.tool.domain.tool.model.vo.ToolAnswerValue;
import com.qa.tool.domain.tool.model.vo.ToolQuestionConverter;
import com.qa.tool.domain.tool.model.vo.ToolQuestionValue;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.convert.ReadingConverter;
import java.time.LocalDateTime;

@Getter
@Setter
@ReadingConverter
@Entity
@Table(name = "qa")
public class ToolQAEntity extends AbstractTimeContainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tool_id")
    private Long toolId;

    @Convert(converter = ToolQuestionConverter.class)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private ToolQuestionValue question;

    @Convert(converter = ToolAnswerConverter.class)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private ToolAnswerValue answer;

    protected ToolQAEntity(Long toolId, ToolQuestionValue question, ToolAnswerValue answer) {
        super();
        this.toolId = toolId;
        this.question = question;
        this.answer = answer;
    }

    /**
     * Create empty object.
     *
     * @deprecated No args constructor of this class is exist for SpringBoot libs.
     */
    @Deprecated(since = "Default constructor is only allowed for Spring JPA")
    public ToolQAEntity() {}

    public ToolQAEntity(Long id, Long toolId, ToolQuestionValue question, ToolAnswerValue answer,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

}
