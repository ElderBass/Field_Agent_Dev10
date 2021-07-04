package learn.field_agent.domain;

import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.SecurityClearance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityClearanceService {

    private final SecurityClearanceRepository repository;

    public SecurityClearanceService(SecurityClearanceRepository repository) { this.repository = repository; }

    public List<SecurityClearance> findAll() { return repository.findAll(); }

    public SecurityClearance findById(int scId) { return repository.findById(scId); }

    public Result<SecurityClearance> add(SecurityClearance sc) {
        Result<SecurityClearance> result = validate(sc);
        if (!result.isSuccess()) {
            return result;
        }
        if (sc.getSecurityClearanceId() != 0) {
            result.addMessage("securityClearanceId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        sc = repository.add(sc);
        result.setPayload(sc);
        return result;
    }

    public Result<SecurityClearance> update(SecurityClearance sc) {
        Result<SecurityClearance> result = validate(sc);
        if (!result.isSuccess()) {
            return result;
        }
        if (sc.getSecurityClearanceId() <= 0) {
            result.addMessage("Security Clearance ID should be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(sc)) {
            String msg = String.format("Security Clearance ID: %s, not found", sc.getSecurityClearanceId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<SecurityClearance> deleteById(int scId) {
        String message =  repository.deleteById(scId);
        Result<SecurityClearance> result = new Result();
        if (message.equals("Deleted Successfully.")) {
            return result;
        } else if (message.equals("Could not find Security Clearance with ID " + scId)){
            result.addMessage(message, ResultType.NOT_FOUND);
        } else {
            result.addMessage(message, ResultType.INTERNAL_SERVER);
        }
        return result;
    }

    // VALIDATION METHODS
    // ===================================================================================================

    private Result<SecurityClearance> validate(SecurityClearance sc) {
        Result<SecurityClearance> result = new Result<>();

        if (sc == null) {
            result.addMessage("Security Clearance cannot be null", ResultType.INVALID);
            return result;
        }

        if (sc.getName() == null || sc.getName().trim().length() == 0) {
            result.addMessage("Name is required", ResultType.INVALID);
        }

        List<SecurityClearance> all = findAll();
        for (SecurityClearance sec : all) {
            if (sec.getName().equals(sc.getName()) && sec.getSecurityClearanceId() != sc.getSecurityClearanceId()) {
                result.addMessage("Security Clearance name already in use.", ResultType.INVALID);
            }
        }
        return result;
    }
}
