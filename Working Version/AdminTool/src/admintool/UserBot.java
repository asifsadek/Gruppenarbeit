/*
 * -User Bot class
 */
package admintool;

import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;

import java.util.HashMap;

/**
 *
 * @author Nick Eyring
 */
public class UserBot {
        
    private MediaWikiBot b;
    public static String user;
    public static String pword;
    
    public UserBot(String username, String password) {
        user = username;
        pword = password;
        login(user,pword);
    }
    
    private void login(String username, String password) {
        try {
            org.apache.log4j.BasicConfigurator.configure(); // configure log4j
            b = new MediaWikiBot("http://localhost/wiki/");
            b.login(username, password);
            user = username;
            pword = password;
        } catch (Exception e) {
            e.printStackTrace();
            new Dialog().setVisible(true);
        }
    }
    
    public boolean isLoggedIn() {
        if (b.isLoggedIn()) {
            return true;
        } else {
            return false;
        }
    }
    
    public String getCode(String page) {
        try {
            if (!isLoggedIn()) {
                login(user,pword);
            }
            SimpleArticle sa = b.readData(page);
            String wikiCode = sa.getText();
            return wikiCode;
        } catch (Exception e) {
            e.printStackTrace();
            new Dialog().setVisible(true);
            return "";
        }
    }
    
}
