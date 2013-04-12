/*
 * -Download bot class.  This bot is used to download current translations
 * off of the Wiki database.  It has its own dedicated login on the Wiki:
 * USER = "DLbot", PASSWORD = "download"
 */
package admintool;

import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;

/**
 *
 * @author Nicholas Eyring
 */
public class DLbot {
    
    private static String wikiCode;
   
    /*
     * Creates a new bot with DLbot credentials, logs in and returns it.
     * -Throws an exception if communication with the wiki failed.
     */
    private static MediaWikiBot initBot() throws Exception {
            org.apache.log4j.BasicConfigurator.configure(); // configure log4j
            MediaWikiBot b = new MediaWikiBot("http://localhost/wiki/");
            b.login(UserBot.user, UserBot.pword);
            return b;        
    }
    
    /*
     * Downloads all current translations for the chosen language (other than
     * English) off of the Wiki database.
     */
    public static DoubleMap getTranslations(String language) {
        try {
            MediaWikiBot b = initBot();
            SimpleArticle sa = b.readData("GeckoGUI/" + language.substring(0, 2).toLowerCase());
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
     * -Removes the given (toRemove) suggestion from the suggestions page dedicated to
     * "language".
     * -Returns string array of current suggestions for the given key.
     */
    public static String[] removeSuggestion(String language, String key, String toRemove) {
        try {
            MediaWikiBot b = initBot();
            SimpleArticle sa = b.readData("Suggestions_" + language.substring(0,2).toLowerCase());
            String code = sa.getText();
            String[] split = code.split(key + " =");
            String[] lines = split[1].split("\n");
            String newSuggestions = "";
            String oldSuggestions = "";
            String[] suggestions = new String[100];
            // iterate through lines
            for (int j=1; j<lines.length; j++) {
                if(!lines[j].contains("end-") && j<=100) {
                    oldSuggestions += (lines[j] + "\n");
                    if (!lines[j].equals(toRemove)) {
                       newSuggestions += (lines[j] + "\n");// updated suggestions
                       suggestions[j-1] = lines[j];
                    }                        
                } else {
                    break;
                }
            }
            code = code.replace(oldSuggestions, newSuggestions);
            sa.setText(code);
            wikiCode = code;
            b.writeContent(sa);
            return suggestions;
        } catch (Exception e) {
            new TranslationDialog("Failed to communicate with database!!","Please select English or establish connection.").setVisible(true);
            // Do something?
            return null;
        }        
    }
    
    /*
     * Overwrites current translation with "translation" parameter.  
     */
    public static void validateTranslation(String language, String key, String translation) {
        try {
            MediaWikiBot b = initBot();
            SimpleArticle sa = new SimpleArticle();
            sa.setTitle("Translations:GeckoGUI/" + key + "/" + language.substring(0, 2).toLowerCase());
            sa.setText(translation);
            b.writeContent(sa);
        } catch(Exception e) {
            new TranslationDialog("Failed to communicate with database!!","Please select English or establish connection.").setVisible(true);
            // Do something?
        }
    }
    
    /*
     * Returns private class variable "wikiCode" which gets written in the
     * removeSuggestion method.
     */
    public static String getWikiCode(){
        return wikiCode;
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
