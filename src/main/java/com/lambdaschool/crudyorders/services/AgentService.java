package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;

public interface AgentService {
    Agent findAgentById(long id);
    Agent save(Agent agent);
//    Agent deleteAgentById(long id);
}
