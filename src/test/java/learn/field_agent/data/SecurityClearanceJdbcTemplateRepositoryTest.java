package learn.field_agent.data;

import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityClearanceJdbcTemplateRepositoryTest {

    @Autowired
    SecurityClearanceJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindById() {
        SecurityClearance secret = new SecurityClearance(1, "Secret");
        SecurityClearance topSecret = new SecurityClearance(2, "Top Secret");

        SecurityClearance actual = repository.findById(1);
        assertEquals(secret, actual);

        actual = repository.findById(2);
        assertEquals(topSecret, actual);

        actual = repository.findById(80);
        assertEquals(null, actual);
    }

    @Test
    void shouldFindAll() {
        List<SecurityClearance> all = repository.findAll();

        assertNotNull(all);
        assertTrue(all.size() >= 2);

        SecurityClearance expected = new SecurityClearance();
        expected.setSecurityClearanceId(1);
        expected.setName("Secret");

        assertTrue(all.contains(expected)
                && all.stream().anyMatch(i -> i.getSecurityClearanceId() == 2));
    }

    @Test
    void shouldAdd() {
        SecurityClearance sc = new SecurityClearance(3, "Sort of Secret");
        SecurityClearance added = repository.add(sc);

        assertEquals(3, repository.findAll().size());

        assertEquals("Sort of Secret", repository.findById(3).getName());
    }

    @Test
    void shouldUpdateExisting() {
        SecurityClearance sc = new SecurityClearance();
        sc.setSecurityClearanceId(2);
        sc.setName("Semi-Secret");

        assertTrue(repository.update(sc));
        assertEquals(sc, repository.findById(2));
    }

    @Test
    void shouldNotUpdateMissing() {
        SecurityClearance sc = new SecurityClearance();
        sc.setSecurityClearanceId(20000);
        sc.setName("Super Secret");

        assertFalse(repository.update(sc));
    }

    // TODO this is where this gets tricky - need to run a check to see if this SecurityClearance is already in use

    @Test
    void shouldDelete() {
        SecurityClearance sc = new SecurityClearance(3, "Sort of Secret");
        SecurityClearance added = repository.add(sc);

        assertEquals(3, repository.findAll().size());

        boolean isDeleted = repository.deleteById(3);
        assertTrue(isDeleted);
    }

    @Test
    void shouldNotDeleteMissing() {
        assertFalse(repository.deleteById(2000));
    }

    @Test
    void shouldNotDeleteClearanceInUse() {
        assertFalse(repository.deleteById(1));
    }
}