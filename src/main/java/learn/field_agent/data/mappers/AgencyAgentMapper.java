package learn.field_agent.data.mappers;

import learn.field_agent.models.AgencyAgent;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgencyAgentMapper implements RowMapper<AgencyAgent> {

    @Override
    public AgencyAgent mapRow(ResultSet resultSet, int i) throws SQLException {

        AgencyAgent agencyAgent = new AgencyAgent();
        agencyAgent.setAgencyId(resultSet.getInt("agency_id"));
        agencyAgent.setIdentifier(resultSet.getString("identifier"));
        agencyAgent.setActivationDate(resultSet.getDate("activation_date").toLocalDate());
        agencyAgent.setActive(resultSet.getBoolean("is_active"));

        // TODO this isn't working and I need to take a break but there's just a mismatch between the securityClearance field
        // in agencyAgent and the security_clearance_id field in the schema
        // i feel like it's looking for the whole thing instead of id. Or something like that

        // this is mapping an entire SC object to this row
        SecurityClearanceMapper securityClearanceMapper = new SecurityClearanceMapper();
        agencyAgent.setSecurityClearance(securityClearanceMapper.mapRow(resultSet, i));

        AgentMapper agentMapper = new AgentMapper();
        agencyAgent.setAgent(agentMapper.mapRow(resultSet, i));

        return agencyAgent;
    }
}
