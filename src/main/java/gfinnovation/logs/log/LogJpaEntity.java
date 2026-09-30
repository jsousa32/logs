package gfinnovation.logs.log;

import gfinnovation.logs._shared.AuditingJpaEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Entity(name = "Log")
@Table(name = "tb_logs")
public class LogJpaEntity extends AuditingJpaEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Enumerated(STRING)
    @Column(name = "action", nullable = false, updatable = false)
    private Action action;

    @Column(name = "from_snapshot", updatable = false, columnDefinition = "TEXT")
    private String from;

    @Column(name = "to_snapshot", updatable = false, columnDefinition = "TEXT")
    private String to;

    @Column(name = "has_change", nullable = false, updatable = false)
    private boolean hasChange;

    protected LogJpaEntity() {
    }

    private LogJpaEntity(
            final LocalDateTime aCreatedAt,
            final LocalDateTime aUpdatedAt,
            final LocalDateTime aDisabledAt,
            final boolean aDisabled,
            final UUID aId,
            final UUID aUserId,
            final Action aAction,
            final String aFrom,
            final String aTo,
            final boolean aHasChange
    ) {
        super(
                aCreatedAt,
                aUpdatedAt,
                aDisabledAt,
                aDisabled
        );
        id = aId;
        userId = aUserId;
        action = aAction;
        from = aFrom;
        to = aTo;
        hasChange = aHasChange;
    }

    public static LogJpaEntity newLog(
            final Action aAction,
            final String aFrom,
            final String aTo,
            final boolean aHasChange
    ) {
        final var now = LocalDateTime.now();
        final var id = UUID.randomUUID();
        final var userId = UUID.randomUUID();

        return new LogJpaEntity(
                now,
                now,
                null,
                false,
                id,
                userId,
                aAction,
                aFrom,
                aTo,
                aHasChange
        );
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public Action getAction() {
        return action;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public boolean isHasChange() {
        return hasChange;
    }
}
