package Parser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;



/**
 * This program converts the content of a WikiMedia site (UTF-8)
 * to a formatted Java properties file.
 * This program works independently of the default locale 
 * and charset being used by the underlying operating system.
 * 
 * @author: Josef Ziegler, Nick Eyring
 *
 */

public class FileDecoder {
    /**
     * @param args[0] the input file name.
     * @param args[1] the output file name.
     *
     */

    
    public static void main(String[] args) {
	try {
	    FileInputStream fis = new FileInputStream(args[0]);
	    byte[] wikiContents = new byte[fis.available()];
	    fis.read(wikiContents, 0, wikiContents.length);
	    String wikiString = new String(wikiContents, "UTF-8");
	    
	    /* Implement the Formatting used for a Java property file.
	     *
	     * Converted in the following way:
	     * 
	     * Wiki Content:
	     * <translate>
	     * <!--T:GeckoFrame.jMenuItem8.text-->
	     * Create Subcircuit
	     * </translate>
	     *
	     * Java properties file (.properties):
	     * GeckoFrame.jMenuItem8.text = Create Subcircuit
	     *
	     */
	    
	    String Output = decodeString(wikiString);

	    byte[] convertedContent = Output.getBytes("ISO-8859-1");
	    FileOutputStream fos = new FileOutputStream(args[1]);
	    fos.write(convertedContent);
	    fos.close();
	} catch(Exception e) {
	    e.printStackTrace();
	}
    }
    
    private static String decodeString(String wikiString) {
        
        String[] lines = wikiString.split(System.getProperty("line.separator")); 
	    String Output = "";
	    // depending on the system used line.separator could be sth other than "\n"
	    String lineSeparator = System.getProperty("line.separator");
	    boolean foundTag = false;
	    for (int i = 0; i < lines.length; i++){ // run through all lines
		if (lines[i].indexOf("<translate>") != -1 || 
		    lines[i].indexOf("</translate>")!= -1) {
		    // if the current line contains a <translate> or </translate> tag
		    continue;
		}
		if (lines[i].indexOf("<!--T:") != -1) {
		    Output += lines[i].substring(lines[i].indexOf("<!--T:") + 6, 
						 lines[i].indexOf("-->")) 
			+ "=";
		    foundTag = true;
		}
		else if (foundTag) {
		    Output += lines[i] + lineSeparator;
		    foundTag = false;
		    
		}
	    }
            
            return Output;
    }
    
    public static String getProperties(String lang) throws Exception {
        
        String content = Bot.PubBot.getWikiPage(lang);
        
        Properties prop = new Properties();
        
        // depending on the system used line.separator could be sth other than "\n"
       // String lineSeparator = System.getProperty("line.separator");
        
        String[] lines = content.split("\n");
        
        //String[] lines = content.split(System.getProperty("line.separator")); 
        
        /*
        for(int i=0; i < 3 ; i++) {
        
        System.out.println(lines[i]);
        
        }
        */
        
         boolean foundTag = false;
         
         String key = "";
         String value = "";
         String fileLocation = "";
         
         try {
         
         for (int i = 0; i < lines.length; i++){ // run through all lines
		if (lines[i].indexOf("<translate>") != -1 || 
		    lines[i].indexOf("</translate>")!= -1) {
		    // if the current line contains a <translate> or </translate> tag
		    continue;
		}
		if (lines[i].indexOf("<!--T:") != -1) {
                    
                    key = lines[i].substring(lines[i].indexOf("<!--T:") + 6, 
						 lines[i].indexOf("-->"));  
                    
                   
		    foundTag = true;
		}
		else if (foundTag) {
                    
                    value = lines[i];
                    
		    foundTag = false;
                    
                    prop.setProperty(key, value);
		    
		}
	    }
         
         fileLocation = "src\\GeckoProperties\\Nice_" + lang.substring(0,2).toUpperCase() + ".properties";
         
         prop.store(new FileOutputStream(fileLocation), null);
         
         
         } catch (IOException ex) {
             
             ex.printStackTrace();
             
         }
     
        return fileLocation;
        
    }
    
}
