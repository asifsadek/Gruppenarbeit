/*
 * -Download bot class.  This bot is used to download current translations
 * off of the Wiki database.  It has its own dedicated login on the Wiki:
 * USER = "DLbot", PASSWORD = "download"
 */
package bot;

import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import translationtoolbox.TranslationDialog;
import i18n.DoubleMap;
import gecko.LangInit;

/**
 *
 * @author Nicholas Eyring
 */
public class DLbot {
   
    /*
     * Creates a new bot with DLbot credentials, logs in and returns it.
     * -Throws an exception if communication with the wiki failed.
     */
    private static MediaWikiBot initBot() throws Exception {
            org.apache.log4j.BasicConfigurator.configure(); // configure log4j
            MediaWikiBot b = new MediaWikiBot("http://localhost/wiki/");
            b.login("DLbot", "download");
            return b;        
    }
    
    /*
     * Downloads all current translations for the chosen language (other than
     * English) off of the Wiki database.
     */
    public static DoubleMap getTranslations() {
        try {
            MediaWikiBot b = initBot();
            SimpleArticle sa = b.readData("Gecko/" + LangInit.language.substring(0, 2).toLowerCase());
            String wikiCode = sa.getText();
            DoubleMap dm = getDoubleMap(wikiCode);           
            return dm;
        } catch(Exception e) {
            new TranslationDialog("Failed to communicate with database!!","Please select English or establish connection.").setVisible(true);
            // Do something?
            return null;
        }
    }
    
    /*
     * This method parses wikiCode to key-value pairs which get stored in a 
     * DoubleMap and returned.
     */
    private static DoubleMap getDoubleMap(String wikiCode) {
        DoubleMap dm = new DoubleMap(); // create the DoubleMap
        String[] lines = wikiCode.split("\n");
        // iterate through all lines
        for (int i=0; i<lines.length; i=i+2) {
            String key  = lines[i].substring(0, lines[i].length()-3);
            String value = lines[i+1];
            dm.KeytoValue.put(key, value);
            dm.ValuetoKey.put(value, key);            
        }       
        return dm;
    }
}
