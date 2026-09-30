package gfinnovation.logs.person;

import gfinnovation.logs._shared.AuditingJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "Person")
@Table(name = "tb_person")
public class PersonJpaEntity extends AuditingJpaEntity {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    protected PersonJpaEntity() {
    }

    private PersonJpaEntity(
            final LocalDateTime aCreatedAt,
            final LocalDateTime aUpdatedAt,
            final LocalDateTime aDisabledAt,
            final boolean aDisabled,
            final UUID aId,
            final String aName,
            final String aEmail,
            final String aTelephone
    ) {
        super(
                aCreatedAt,
                aUpdatedAt,
                aDisabledAt,
                aDisabled
        );
        id = aId;
        name = aName;
        email = aEmail;
        telephone = aTelephone;
    }

    public static PersonJpaEntity newPerson(
            final String aName,
            final String aEmail,
            final String aTelephone
    ) {
        final var now = LocalDateTime.now();
        final var id = UUID.randomUUID();

        return new PersonJpaEntity(
                now,
                now,
                null,
                false,
                id,
                aName,
                aEmail,
                aTelephone
        );
    }

    public PersonJpaEntity update(
            final String aName,
            final String aEmail,
            final String aTelephone
    ) {
        updatedAt = LocalDateTime.now();
        name = aName;
        email = aEmail;
        telephone = aTelephone;
        return this;
    }

    public PersonJpaEntity disable() {
        final var now = LocalDateTime.now();
        updatedAt = now;
        disabledAt = now;
        disabled = false;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }
}
