/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.employeeevent.acmechat;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

/**
 *
 * @author Juneau
 */
@Named
@ApplicationScoped
public class ChatSessionController implements java.io.Serializable {
    
    private Map<String, String> users = null;
    
    public ChatSessionController(){}
    
    @PostConstruct
    public void init(){
         users = new HashMap<>();
    }

    /**
     * @return the users
     */
    public Map<String, String> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(Map<String, String> users) {
        this.users = users;
    }
    
}
