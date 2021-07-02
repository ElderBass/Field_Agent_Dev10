package learn.field_agent.models;

public class Alias {

    private int aliasId;
    private String name;
    private String persona;
    private Agent agent;

    public Alias() {}

    public Alias(int aliasId, String name, String persona, Agent agent) {
        this.aliasId = aliasId;
        this.name = name;
        this.persona = persona;
        this.agent = agent;
    }

    public int getAliasId() {
        return aliasId;
    }

    public void setAliasId(int aliasId) {
        this.aliasId = aliasId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}