package learn.field_agent.data;

import learn.field_agent.models.SecurityClearance;

import java.util.List;

public interface SecurityClearanceRepository {
    SecurityClearance findById(int securityClearanceId);
    List<SecurityClearance> findAll();
    SecurityClearance add(SecurityClearance sc);
    boolean update(SecurityClearance sc);
    boolean deleteById(int scId);
}
