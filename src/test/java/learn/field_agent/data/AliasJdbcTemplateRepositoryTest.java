package learn.field_agent.data;

import learn.field_agent.models.Alias;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AliasJdbcTemplateRepositoryTest {

    private final int NEXT_ID = 4;

    @Autowired
    AliasJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Alias> all = repository.findAll();

        assertTrue(all.size() >= 3);
    }

    @Test
    void shouldAdd() {
       Alias alias = makeAlias();

        Alias result = repository.add(alias);

        assertEquals(NEXT_ID, result.getAliasId());
    }

    @Test
    void shouldAddNullPersona() {
        Alias alias = makeAlias();
        alias.setPersona(null);
        Alias result = repository.add(alias);

        assertEquals(NEXT_ID + 1, result.getAliasId());
    }

    @Test
    void shouldUpdate() {
        Alias alias = makeAlias();
        alias.setAliasId(2);

        assertTrue(repository.update(alias));
    }

    @Test
    void shouldNotUpdateNonexistent() {
        Alias alias = makeAlias();
        alias.setAliasId(2000);

        assertFalse(repository.update(alias));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(3));
    }

    private Alias makeAlias() {
        Alias alias = new Alias();
        alias.setName("[Inconspicuous Name]");
        alias.setPersona("Totally just a regular person. Not suspicious at all.");
        alias.setAgentId(4);
        return alias;
    }
}