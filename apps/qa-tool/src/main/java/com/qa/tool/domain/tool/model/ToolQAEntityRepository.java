package com.qa.tool.domain.tool.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ToolQAEntityRepository
        extends JpaRepository<ToolQAEntity, Long>, JpaSpecificationExecutor<ToolQAEntity> {

    Page<ToolQAEntity> findByToolId(Long toolId, Pageable pageable);

    Page<ToolQAEntity> findByIdIn(List<Long> ids, Pageable pageable);

    @Query(value = "SELECT DISTINCT tool_id FROM qa", nativeQuery = true)
    List<Long> findDistinctToolId();
}
