package com.qa.common_libs.entity;

import java.time.LocalDateTime;

public interface Timestamps<T> {

    T getId();

    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime createdAt);

    LocalDateTime getUpdatedAt();

    void setUpdatedAt(LocalDateTime updatedAt);

}
