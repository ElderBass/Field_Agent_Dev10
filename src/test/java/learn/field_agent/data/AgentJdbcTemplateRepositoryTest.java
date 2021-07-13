package learn.field_agent.data;

import learn.field_agent.models.Agent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AgentJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 9;

    @Autowired
    AgentJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Agent> agents = repository.findAll();
        assertNotNull(agents);

        // can't predict order
        // if delete is first, we're down to 7
        // if add is first, we may go as high as 10
        assertTrue(agents.size() >= 7 && agents.size() <= 10);
    }

    @Test
    void shouldFindHazel() {
        Agent hazel = repository.findById(1);
        assertEquals(1, hazel.getAgentId());
        assertEquals("Hazel", hazel.getFirstName());
        assertEquals(2, hazel.getAgencies().size());

        // NEW STUFF ALERT
        // This fails when I run all tests in project, saying Hazel has 0 aliases despite me never deleting those
        // aliases anywhere in the project, but when I run all tests in this file it passes. I've debugged this
        // several times but am failing to see why it won't retrieve the aliases.
        // It will always return them when I run the API request as well. At a total loss.
        assertEquals(2, hazel.getAliases().size());
    }
//
//    @Test
//    void shouldFindHazelAliases() {
//        Agent hazel = repository.findById(1);
//        assertEquals(2, hazel.getAliases().size());
//    }

    @Test
    void shouldAdd() {
        // all fields
        Agent agent = makeAgent();
        Agent actual = repository.add(agent);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getAgentId());

        // null dob
        agent = makeAgent();
        agent.setDob(null);
        actual = repository.add(agent);
        assertNotNull(actual);
        assertEquals(NEXT_ID + 1, actual.getAgentId());
    }

    @Test
    void shouldUpdate() {
        Agent agent = makeAgent();
        agent.setAgentId(3);
        assertTrue(repository.update(agent));
        agent.setAgentId(13);
        assertFalse(repository.update(agent));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }

    private Agent makeAgent() {
        Agent agent = new Agent();
        agent.setFirstName("Test");
        agent.setLastName("Last Name");
        agent.setDob(LocalDate.of(1985, 8, 15));
        agent.setHeightInInches(66);
        return agent;
    }
}