/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
//import net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version;


/**
 *
 * @author Nick Eyrings
 */
public class PubBot {
    
    public static void main(String[] args) throws Exception {
        
        org.apache.log4j.BasicConfigurator.configure(); // configure log4j
               
        String s = getWikiPage("Espagnol");
        
        System.out.println(s);
        
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
