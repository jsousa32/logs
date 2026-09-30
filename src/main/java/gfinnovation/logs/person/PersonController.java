package gfinnovation.logs.person;

import gfinnovation.logs.log.Action;
import gfinnovation.logs.log.LogJpaEntity;
import gfinnovation.logs._shared.CheckChangesObject;
import gfinnovation.logs.log.LogJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/person")
public class PersonController {

    private final ObjectMapper om = new ObjectMapper();

    private static UUID ID = null;

    private final LogJpaRepository logJpaRepository;

    private final PersonJpaRepository personJpaRepository;

    public PersonController(
            final LogJpaRepository aLogJpaRepository,
            final PersonJpaRepository aPersonJpaRepository
    ) {
        logJpaRepository = aLogJpaRepository;
        personJpaRepository = aPersonJpaRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save() {
        ID = saveInDB().getId();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("without")
    @Transactional
    public ResponseEntity<?> updateWithoutChanges() {
        final var person = personJpaRepository.findById(ID).get();
        final var from = om.writeValueAsString(PersonSnapshot.from(person));
        final var to = om.writeValueAsString(PersonSnapshot.from(person));
        final var log = LogJpaEntity.newLog(Action.UPDATE_PERSON, from, to, CheckChangesObject.hasChanges(from, to));
        personJpaRepository.save(person);
        logJpaRepository.save(log);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping
    @Transactional
    public ResponseEntity<?> update() {
        final var person = personJpaRepository.findById(ID).get();
        final var from = om.writeValueAsString(PersonSnapshot.from(person));
        final var actualPerson = person.update("lucas", "lucas@gmail.com", person.getTelephone());
        final var to = om.writeValueAsString(PersonSnapshot.from(actualPerson));
        final var log = LogJpaEntity.newLog(Action.UPDATE_PERSON, from, to, CheckChangesObject.hasChanges(from, to));
        personJpaRepository.save(actualPerson);
        logJpaRepository.save(log);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private PersonJpaEntity saveInDB() {
        final var person = PersonController.generate();
        final var to = om.writeValueAsString(PersonSnapshot.from(person));
        logJpaRepository.save(LogJpaEntity.newLog(Action.CREATED_PERSON, null, to, false));
        return personJpaRepository.save(person);
    }


    private static PersonJpaEntity generate() {
        return PersonJpaEntity.newPerson(
                "João",
                "joao@gmail.com",
                "12345678901"
        );
    }
}
