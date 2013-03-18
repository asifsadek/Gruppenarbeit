/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;


//import net.sourceforge.jwbf.bots.MediaWikiBotImpl;
//import net.sourceforge.jwbf.bots.MediaWikiBot;
import net.sourceforge.jwbf.contentRep.mw.SimpleArticle;
//import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
//import net.sourceforge.jwbf.core.contentRep.SimpleArticle;


import hybrid.MediaWikiBot;


/**
 *
 * @author Nick Eyring
 */
public class PubBot {
    
    public static void main(String[] args) throws Exception {
        
       org.apache.log4j.BasicConfigurator.configure(); // configure log4j
         
       MediaWikiBot b = PubBot();
        
       //String s = getWikiPage("Espagnol");
        
       //System.out.println(s);
    }
    
    
    private static MediaWikiBot PubBot() throws Exception {
        
        MediaWikiBot b = new MediaWikiBot("http://localhost/wiki/");
        
        b.login("Nick", "hellowiki");
               
        if (!b.isLoggedIn()) {
            
            System.out.println("Can't log in!!!!");
                    
        }
        
        if (b.isLoggedIn()) {
            
            System.out.println("Logged in :)");            
            
        }
        
        return b;
        
    }
    
    public static String getWikiPage(String page) throws Exception {
        
        MediaWikiBot b = PubBot();
        
       SimpleArticle sa = new SimpleArticle(b.readContent(page));
       
       //sa.addText("Yea baby!!!");
       
       //b.writeContent(sa);
              
        return sa.getText();  
        
        //return null;
        
    } 

}
