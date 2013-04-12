/*
 * -Upload bot class.  This bot is used to change specific translations locally 
 * and on the Wiki database.  It has its own dedicated login on the Wiki:
 * USER = "UPbot", PASSWORD = "upload"
 */
package bot;

import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import translationtoolbox.TranslationDialog;
import gecko.LangInit;

/**
 *
 * @author Nicholas Eyring
 */
public class UPbot {
    
    /*
     * Creates a new bot with UPbot credentials, logs in and returns it.
     * -Throws an exception if communication with the wiki failed.
     */
    private static MediaWikiBot initBot() throws Exception {
            org.apache.log4j.BasicConfigurator.configure(); // configure log4j
            MediaWikiBot b = new MediaWikiBot("http://localhost/wiki/");
            b.login("UPbot", "upload");
            return b;        
    }
    
    /*
     * Changes a translation on the Wiki database to the 
     * newTranslation parameter given its key.
     */
    public static void addTranslationSuggestion(String key, String newTranslation) {
        try {
            // make the change on the Wiki database
            MediaWikiBot b = initBot();
            SimpleArticle sa = b.readData("Suggestions_" + LangInit.language.substring(0, 2).toLowerCase());
            String wikiCode = sa.getText();
            wikiCode = wikiCode.replace("end-" + key, newTranslation + "\nend-" + key);
            sa.setText(wikiCode);
            b.writeContent(sa);
        } catch(Exception e) {
            new TranslationDialog("Failed to communicate with database!!","   Please re-establish connection and try again.").setVisible(true);
            // make the change locally
            //LangInit.transMap.KeytoValue.put(key, newTranslation);
            //LangInit.transMap.ValuetoKey.put(newTranslation, key);
        }       
    }
    
}
