package learn.field_agent.domain;


import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Alias;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AliasService {

    private final AliasRepository repository;

    public AliasService(AliasRepository repository) {
        this.repository = repository;
    }

    public List<Alias> findAll() { return repository.findAll(); }

    public Result<Alias> add(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() != 0) {
            result.addMessage("Alias ID cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        alias = repository.add(alias);
        result.setPayload(alias);
        return result;
    }

    public Result<Alias> update(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() <= 0) {
            result.addMessage("Alias ID must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(alias)) {
            String msg = String.format("Alias ID: %s, not found", alias.getAliasId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int aliasId) {
        return repository.deleteById(aliasId);
    }

    private Result<Alias> validate(Alias alias) {
        Result<Alias> result = new Result<>();

        if (alias == null) {
            result.addMessage("Alias cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(alias.getName())) {
            result.addMessage("Alias name cannot be null.", ResultType.INVALID);
        }

        result = validateDuplicate(alias, result);

        String agentIdStr = "" + alias.getAgentId();
        if (agentIdStr.length() == 0) {
            result.addMessage("Alias must have an Agent ID.", ResultType.INVALID);
        }

        return result;
    }

    private Result<Alias> validateDuplicate(Alias alias, Result result) {
        List<Alias> all = repository.findAll();
        for (Alias a : all) {
            if (a.getName().equals(alias.getName()) && (Validations.isNullOrBlank(alias.getPersona()))) {
                result.addMessage("Persona required for duplicate alias names.", ResultType.INVALID);
            }
        }
        return result;
    }
}
