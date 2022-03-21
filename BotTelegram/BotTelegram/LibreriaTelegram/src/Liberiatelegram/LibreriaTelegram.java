/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Liberiatelegram;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author guepe
 */
public class LibreriaTelegram {

    private static BufferedReader in;
    String Nomee = "Pubblicita5B_Ginisi";
    String Chiave = "5220261632:AAHbTs8U1peYQ7Dc3aCV_0E33hGcKmsJ9K8";
    int offset;

    public void LibreriaTelegram() {
    }

    public String getNomee() {
        return Nomee;
    }

    public String getChiave() {
        return Chiave;
    }

    public String getUpdates() {
        
        String allJSON="";
        try {
            URL url = new URL("https://api.telegram.org/bot" + Chiave + "/getUpdates?offset="+offset);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = in.readLine()) != null) {
                allJSON += line+"\n";
            }
            in.close();
            System.out.println("https://api.telegram.org/bot"+Chiave+"/getUpdates?offset="+offset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allJSON;
    }

    public void sendMessage(String message) throws MalformedURLException, IOException {
        URL url = new URL("https://api.telegram.org/bot" + getChiave() + "/sendMessage?chat_id=" + "&text=" + message);
        URLConnection con = url.openConnection();
        InputStream send = new BufferedInputStream(con.getInputStream());
    }
    public void setOffset(int  offset) throws MalformedURLException, IOException
    {
        this.offset=offset;
        URL url = new URL("https://api.telegram.org/bot" + Chiave + "/getUpdates?offset="+offset);
        System.out.println("nuovo url:"+url);
        URLConnection con = url.openConnection();
        InputStream invia = new BufferedInputStream(con.getInputStream());
    }
}
