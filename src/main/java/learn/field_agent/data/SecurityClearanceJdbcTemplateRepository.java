package learn.field_agent.data;

import learn.field_agent.data.mappers.SecurityClearanceMapper;
import learn.field_agent.models.SecurityClearance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class SecurityClearanceJdbcTemplateRepository implements SecurityClearanceRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SecurityClearanceMapper mapper = new SecurityClearanceMapper();

    public SecurityClearanceJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SecurityClearance findById(int securityClearanceId) {

        final String sql = "select security_clearance_id, name security_clearance_name "
                + "from security_clearance "
                + "where security_clearance_id = ?;";

        return jdbcTemplate.query(sql, new SecurityClearanceMapper(), securityClearanceId)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<SecurityClearance> findAll() {
        final String sql = "select security_clearance_id, name security_clearance_name from security_clearance";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public SecurityClearance add(SecurityClearance sc) {
        final String sql = "insert into security_clearance (security_clearance_name) values (?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, sc.getName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        sc.setSecurityClearanceId(keyHolder.getKey().intValue());
        return sc;
    }

    @Override
    public boolean update(SecurityClearance sc) {
        final String sql = "update security_clearance set "
                + "security_clearance_name  = ?, "
                + "where security_clearance_id = ?;";

        int rowsUpdated = jdbcTemplate.update(sql,
                sc.getName(), sc.getSecurityClearanceId());

        return rowsUpdated > 0;
    }

    @Override
    public boolean deleteById(int scId) {
        final String sql = "delete from security_clearance where security_clearance_id = ?;";
        return jdbcTemplate.update(sql, scId) > 0;
    }
}
