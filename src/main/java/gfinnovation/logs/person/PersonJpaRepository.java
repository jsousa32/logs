package gfinnovation.logs.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonJpaRepository extends JpaRepository<PersonJpaEntity, UUID> {
}
