/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

import net.sourceforge.jwbf.bots.MediaWikiBot;
import net.sourceforge.jwbf.contentRep.SimpleArticle;


/**
 *
 * @author Nick Eyring
 */
public class PubBot {
    
    public static void main(String[] args) throws Exception {
        
        org.apache.log4j.BasicConfigurator.configure();
         
        MediaWikiBot b = PubBot();
        
    }
    
    
    private static MediaWikiBot PubBot() throws Exception {
        
        MediaWikiBot b = new MediaWikiBot("http://localhost/wiki/");
        
        b.login("Bot", "login");
        
        if (!b.isLoggedIn()) {
            
            System.out.println("Can't log in!!!!");
            
        }
        
        return b;
        
    }
    
    public static String getWikiPage(String page) throws Exception {
        
        MediaWikiBot b = PubBot();
        
        SimpleArticle sa = new SimpleArticle(b.readContent(page));
        
        return sa.getText();   
        
    }
    
    
    
    
    
    
}
