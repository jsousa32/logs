package gfinnovation.logs._shared;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AuditingJpaEntity {

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    protected LocalDateTime updatedAt;

    @Column(name = "disabled_at")
    protected LocalDateTime disabledAt;

    @Column(name = "disabled", nullable = false)
    protected boolean disabled;

    protected AuditingJpaEntity() {
    }

    protected AuditingJpaEntity(
            final LocalDateTime aCreatedAt,
            final LocalDateTime aUpdatedAt,
            final LocalDateTime aDisabledAt,
            final boolean aDisabled
    ) {
        createdAt = aCreatedAt;
        updatedAt = aUpdatedAt;
        disabledAt = aDisabledAt;
        disabled = aDisabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDisabledAt() {
        return disabledAt;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
