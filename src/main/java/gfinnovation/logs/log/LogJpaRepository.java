package gfinnovation.logs.log;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogJpaRepository extends JpaRepository<LogJpaEntity, UUID> {
}
