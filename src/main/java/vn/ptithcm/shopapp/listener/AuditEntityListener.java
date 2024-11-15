package vn.ptithcm.shopapp.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import vn.ptithcm.shopapp.model.entity.Base;
import vn.ptithcm.shopapp.util.SecurityUtil;

import java.time.Instant;

public class AuditEntityListener {

    @PrePersist
    public void handleBeforeInsert(Base entity) {
        String username = SecurityUtil.getCurrentUserLogin().isPresent() == true ?
                SecurityUtil.getCurrentUserLogin().get()
                : "";
        Instant createdAt = Instant.now();
        entity.setCreatedAt(createdAt);
        entity.setCreatedBy(username);
    }

    @PreUpdate
    public void handleBeforeUpdate(Base entity) {

        String username = SecurityUtil.getCurrentUserLogin().isPresent() == true ?
                SecurityUtil.getCurrentUserLogin().get()
                : "";
        Instant updatedAt = Instant.now();
        entity.setUpdatedAt(updatedAt);
        entity.setUpdatedBy(username);
    }
}
