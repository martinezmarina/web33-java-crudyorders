package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agents")
public class AgentController {
    @Autowired
    private AgentService agentService;

    // http://localhost:2019/agents/agent/9
    @GetMapping(value = "/agent/{id}", produces = {"application/json"})
    public ResponseEntity<?> findAgentById(@PathVariable long id) {
        Agent a = agentService.findAgentById(id);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    //http://localhost:2019/agents/unassigned/8
    //http://localhost:2019/agents/unassigned/16
//    @DeleteMapping(value = "/unassigned/{agentcode}")
//    public ResponseEntity<?> deleteAgentById(@PathVariable long id) {
//        agentService.deleteAgentById(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
