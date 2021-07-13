package learn.field_agent.domain;

import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Alias;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AliasServiceTest {


    @Autowired
    AliasService service;

    @MockBean
    AliasRepository repository;

    @Test
    void shouldFindAll() {

        List<Alias> all = new ArrayList<>();
        Alias arianna = new Alias(1, "Arianna Martell", "Willful, proud but not overtly arrogant, delicate but strong.", 1);
        Alias tissa = new Alias(2, "Tissa de Vries", "Perfectionist, meticulous, curt but elegant in all things, sharply intelligent.", 1);
        Alias rob = new Alias(3,"Rob Stark", "Composed, strategic and calculated but falls prey to emotions at times, honorable and capable.", 2);
        Alias triss = new Alias(4, "Triss Merigold", "Youthful, spunky, capricious, determined, competent but can get in over her head.", 3);
        all.add(arianna);
        all.add(tissa);
        all.add(rob);
        all.add(triss);

        when(repository.findAll()).thenReturn(all);

        all = service.findAll();

        assertEquals(4, all.size());
    }

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

    // TODO so when I make HTTP requests to add a duplicate name with null persona, I get the proper 400 error
    // but this test will not for the life of me conduct findAll in the validateDuplicate method, so this fails every time
    // I've debugged this many times but it just skips findAll every time for seemingly no reason

    @Test
    void shouldNotAddDuplicateWithNullPersona() {
        Alias aliasIn = new Alias(0, "Arianna Martell", null, 4);
        Alias aliasOut = new Alias(5, "Arianna Martell", null, 4);

        when(repository.add(aliasIn)).thenReturn(aliasOut);

        Result result = service.add(aliasIn);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldAddDuplicateNameWithDifferentPersona() {
        Alias alias = new Alias(0, "Arianna Martell", "A princess from House Martell of Dorne.", 5);
        Result result = service.add(alias);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldAdd() {
        Alias aliasIn = makeAlias();
        aliasIn.setAliasId(0);
        Alias aliasOut = makeAlias();
        aliasOut.setAliasId(4);

        when(repository.add(aliasIn)).thenReturn(aliasOut);

        Result<Alias> result = service.add(aliasIn);
        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(aliasOut, result.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        Alias alias = makeAlias();
        Result<Alias> actual = service.update(alias);
        assertEquals(ResultType.INVALID, actual.getType());

        alias = makeAlias();
        alias.setAliasId(4);
        alias.setName("");
        actual = service.update(alias);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        Alias alias = makeAlias();
        alias.setAliasId(4);

        when(repository.update(alias)).thenReturn(true);

        Result<Alias> actual = service.update(alias);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    private Alias makeAlias() {
        return new Alias(0, "Don't Worry About It", "Totally Not Suspicious.", 5);
    }

}