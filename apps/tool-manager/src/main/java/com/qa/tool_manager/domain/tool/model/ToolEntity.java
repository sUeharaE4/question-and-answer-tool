package com.qa.tool_manager.domain.tool.model;

import com.qa.common_libs.entity.AbstractTimeContainEntity;
import com.qa.tool_manager.domain.tool.model.vo.ToolDescriptionConverter;
import com.qa.tool_manager.domain.tool.model.vo.ToolDescriptionValue;
import com.qa.tool_manager.domain.tool.model.vo.ToolNameConverter;
import com.qa.tool_manager.domain.tool.model.vo.ToolNameValue;
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
@Table(name = "tool")
public class ToolEntity extends AbstractTimeContainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = ToolNameConverter.class)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private ToolNameValue name;

    @Convert(converter = ToolDescriptionConverter.class)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private ToolDescriptionValue description;

    protected ToolEntity(ToolNameValue name, ToolDescriptionValue description) {
        super();
        this.name = name;
        this.description = description;
    }

    /**
     * Create empty object.
     *
     * @deprecated No args constructor of this class is exist for SpringBoot libs.
     */
    @Deprecated(since = "Default constructor is only allowed for Spring JPA")
    public ToolEntity() {}

    public ToolEntity(Long id, ToolNameValue name, ToolDescriptionValue description,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
