package gfinnovation.logs.person;

import java.time.LocalDateTime;
import java.util.UUID;

public record PersonSnapshot(

        boolean disabled,

        LocalDateTime disabledAt,

        UUID id,

        String name,

        String email,

        String telephone
) {

    public static PersonSnapshot from(
            final PersonJpaEntity aEntity
    ) {
        return new PersonSnapshot(
                aEntity.isDisabled(),
                aEntity.getDisabledAt(),
                aEntity.getId(),
                aEntity.getName(),
                aEntity.getEmail(),
                aEntity.getTelephone()
        );
    }
}
