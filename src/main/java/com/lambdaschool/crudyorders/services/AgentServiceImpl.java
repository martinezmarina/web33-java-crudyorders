package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service(value = "agentService")
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentsRepository agentrepos;

    @Override
    public Agent findAgentById(long id) {
        return agentrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent " + id + " Not Found"));
    }
    @Override
    public Agent save(Agent agent) {
        return agentrepos.save(agent);
    }

//    @Override
//    public void delete(long id){
//        if(agentrepos.findById(id).isPresent() && agentrepos.findById(id)){
//            agentrepos.deleteById(id);
//        }else {
//            throw new EntityNotFoundException("Found A Customer For Agent " + id);
//        }
//    }
}
