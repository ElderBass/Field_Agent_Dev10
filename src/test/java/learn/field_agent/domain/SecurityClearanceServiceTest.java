package learn.field_agent.domain;

import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityClearanceServiceTest {


    @MockBean
    SecurityClearanceRepository repository;

    // 3. The mock PetRepository is injected into PetService
    // and PetService is injected into PetServiceTest.
    @Autowired
    SecurityClearanceService service;

    @Test
    void shouldAdd() {

        SecurityClearance scIn = new SecurityClearance(0, "I mean it's kinda secret...?");
        SecurityClearance scOut = new SecurityClearance(1, "I mean it's kinda secret...?");

        when(repository.add(scIn)).thenReturn(scOut);

        Result<SecurityClearance> result = service.add(scIn);

        // Assert
        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(scOut, result.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        Result<SecurityClearance> result = service.add(null);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddInvalid() {
        SecurityClearance sc = new SecurityClearance(0, null);
        Result<SecurityClearance> result = service.add(sc);

        assertEquals(ResultType.INVALID, result.getType());

        sc.setName("");

        result = service.add(sc);

        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldUpdate() {
        SecurityClearance sc = new SecurityClearance(1, "Victoria's Secret");

        when(repository.update(sc)).thenReturn(true);

        Result<SecurityClearance> actual = service.update(sc);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateInvalid() {
        SecurityClearance sc = new SecurityClearance(1, "");

        Result<SecurityClearance> actual = service.update(sc);
        assertEquals(ResultType.INVALID, actual.getType());

        sc.setName("Top Secret");
        sc.setSecurityClearanceId(0);
        actual = service.update(sc);
        assertEquals(ResultType.INVALID, actual.getType());

        sc.setName(null);
        sc.setSecurityClearanceId(1);
        actual = service.update(sc);
        assertEquals(ResultType.INVALID, actual.getType());
    }

}