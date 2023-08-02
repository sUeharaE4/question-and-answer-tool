package com.qa.tool_manager.domain.tool.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolEntityRepository
        extends JpaRepository<ToolEntity, Long>, JpaSpecificationExecutor<ToolEntity> {

    Page<ToolEntity> findAll(Pageable pageable);
}
