package learn.field_agent.domain;

import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Alias;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AliasServiceTest {


    @Autowired
    AliasService service;

    @MockBean
    AliasRepository repository;

    @Test
    void shouldNotAddInvalid() {
        Alias alias = null;
        Result<Alias> result = service.add(alias);

        assertFalse(result.isSuccess());

        alias = makeAlias();
        alias.setName(null);

        result = service.add(alias);
        assertFalse(result.isSuccess());

        alias = makeAlias();
        alias.setName("   ");
        
        result = service.add(alias);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldAdd() {
        Alias alias = makeAlias();

        Result<Alias> result = service.add(alias);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        Alias alias = makeAlias();
        Result<Alias> actual = service.update(alias);
        assertEquals(ResultType.INVALID, actual.getType());

        alias = makeAlias();
        alias.setAliasId(1);
        alias.setName("");
        actual = service.update(alias);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        Alias alias = makeAlias();
        alias.setAliasId(1);

        when(repository.update(alias)).thenReturn(true);

        Result<Alias> actual = service.update(alias);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    private Alias makeAlias() {
        Alias alias = new Alias();
        alias.setName("[Inconspicuous Name]");
        alias.setPersona("Totally just a regular person. Not suspicious at all.");
        alias.setAgentId(1);
        return alias;
    }

}