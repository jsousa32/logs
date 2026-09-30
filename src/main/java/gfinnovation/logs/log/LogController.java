package gfinnovation.logs.log;

import gfinnovation.logs._shared.CheckChangesObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping(path = "api/v1/logs")
public class LogController {

    private final LogJpaRepository logJpaRepository;

    public LogController(final LogJpaRepository aLogJpaRepository) {
        logJpaRepository = aLogJpaRepository;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        final var pageRequest = PageRequest.of(0, 5, Sort.by(DESC, "createdAt"));
        final var logs = logJpaRepository.findAll(pageRequest);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("{id}/details")
    public ResponseEntity<?> findById(
            @PathVariable UUID id
    ) {
        final var log = logJpaRepository.findById(id).get();
        final var changes = CheckChangesObject.getChanges(log.getFrom(), log.getTo());
        return ResponseEntity.ok(changes);
    }
}
