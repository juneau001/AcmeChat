/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.employeeevent.acmechat;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Juneau
 */
public class TestBasicWebSocket {
    
    public static void main(String[] args){
        try {
            // open websocket
            final BasicClient clientEndPoint = new BasicClient(
                    new URI("ws://localhost:8080/AcmeChat/basicEndpoint"));

            // add listener
            clientEndPoint.addMessageHandler(new BasicClient.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println(message);
                }
            });

            // send message to websocket
            clientEndPoint.sendMessage("Message sent from client!");

            // wait 5 seconds for messages from websocket
            Thread.sleep(5000);

        } catch (InterruptedException|URISyntaxException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        }
    }
}
