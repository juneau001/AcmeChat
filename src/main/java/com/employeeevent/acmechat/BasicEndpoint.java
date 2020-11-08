/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.employeeevent.acmechat;

import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 *
 * @author Juneau
 */
@ServerEndpoint(value = "/basicEndpoint")
public class BasicEndpoint {
    
    @OnMessage
    public void onMessage(Session session, String message){
        try {
            session.getBasicRemote().sendText("Sending from server endpoint: " + message);
      } catch (IOException ex) {
          ex.printStackTrace();
      }
        
    }
}
