/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telegramapi;

import APIopenstreetmap.MyXMLOperations;
import APIopenstreetmap.SearchResults;
import Liberiatelegram.LibreriaTelegram;
import Liberiatelegram.UserTelegram;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

/**
 *
 * @author guepe
 */
public class ThreadRicezione extends Thread {
    
    LibreriaTelegram n;
    UserTelegram utente;
    MyXMLOperations openstreet;
    String[] campi;
    List<String> linee;

    SearchResults sr = null;

    public ThreadRicezione(FileCSV fileUsers) {
        this.n = new LibreriaTelegram();
        this.utente=new UserTelegram();
        this.openstreet = new MyXMLOperations();
        this.campi = new String[MIN_PRIORITY];
        this.linee = linee;
    }

    @Override
    public void run() {
        
        while (true) {
            String all = n.getUpdates();
            JSONObject json = new JSONObject(all);
            JSONArray jArray = null;
            jArray=json.getJSONArray("result");
            
            
            System.out.println("n messaggi:"+jArray.length());
            
            
            for (int i = 0; i < jArray.length(); i++) {
                utente = new UserTelegram();


                utente.setUpdate_id(Integer.parseInt(jArray.getJSONObject(i).get("update_id").toString()));
                utente.setMessage_id(Integer.parseInt(jArray.getJSONObject(i).getJSONObject("message").get("message_id").toString()));
                utente.setChatId(Integer.parseInt(jArray.getJSONObject(i).getJSONObject("message").getJSONObject("chat").get("id").toString()));
                utente.setFirst_name(jArray.getJSONObject(i).getJSONObject("message").getJSONObject("chat").get("first_name").toString());
                utente.setText(jArray.getJSONObject(i).getJSONObject("message").get("text").toString());
                System.out.println("dati-->"+utente.toString());
                
                
                if (utente.getText().startsWith("/citta")) {
                    try {
                        sr = openstreet.searchPlace(utente.getText().substring(7));
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(ThreadRicezione.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SAXException ex) {
                        Logger.getLogger(ThreadRicezione.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ThreadRicezione.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        n.sendMessage("fare /citta 'nome citta'", utente.getChatId());
                    } catch (IOException ex) {
                        Logger.getLogger(ThreadRicezione.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println("modifico offset");
                try {
                    n.setOffset(utente.getUpdate_id() + 1);//per non rileggere gli stessi messaggi
                } catch (IOException ex) {
                    Logger.getLogger(ThreadRicezione.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            
            

            try {
                Thread.sleep(3000);//getupdates ogni 3s
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadRicezione.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
