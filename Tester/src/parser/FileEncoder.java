package Parser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * This program converts a Java properties file (latin-1) to 
 * UTF-8, formatted in such a way, that it can be copied into
 * a Wiki page.
 * This program works independently of the default locale 
 * and charset being used by the underlying operating system.
 * 
 * @author: Josef Ziegler, Nick Eyring
 *
 */

public class FileEncoder {
    /**
     * @param args[0] the input file name 
     * @param args[1] the output file name.
     *
     */

    
    public static void main(String[] args) {
	try {
	    FileInputStream fis = new FileInputStream(args[0]);
	    byte[] propertyContents = new byte[fis.available()];
	    fis.read(propertyContents, 0, propertyContents.length);
	    String propertyString = new String(propertyContents, "ISO-8859-1");
	    
	    /* Implement the Formatting used for the Wiki site
	     *
	     * Every line that contains an equality sign "="
	     * and an ending newline "\n" is formatted in the 
	     * following way:
	     * 
	     * Java properties file (.properties):
	     * GeckoFrame.jMenuItem8.text = Create Subcircuit
	     *
	     * Correponding output:
	     * <translate>
	     * <!--T:GeckoFrame.jMenuItem8.text-->
	     * Create Subcircuit
	     * </translate>
	     *
	     *
	     * Comments in the Java property file (beginning with '#') 
	     * are ignored.
	     * 
	     */
	    
	    String[] lines = propertyString.split(System.getProperty("line.separator")); 
	    String Output = "";
	    // depending on the system used line.separator could be sth other than "\n"
	    String lineSeparator = System.getProperty("line.separator");
	    
	    for (int i = 0; i < lines.length; i++){ // run through all lines
		if (lines[i].indexOf("#") != -1) { 
		    // the current line contains a comment
		    lines[i] = lines[i].substring(0, lines[i].indexOf("#"));
		}
		String[] parts = lines[i].split("=");
		if (parts.length > 2) {
		    throw new IOException("more than one equality sign appears on a line");
		}
		else if (parts.length == 2) {
		    // if there is exactly one equality sign
		    Output += lineSeparator + "<translate>" + lineSeparator + 
			"<!--T:" + parts[0] + "-->" + lineSeparator +  
			parts[1] + lineSeparator + "</translate>" + lineSeparator;
		}
	    }

	    byte[] convertedContent = Output.getBytes("UTF-8");	    
	    FileOutputStream fos = new FileOutputStream(args[1]);
	    fos.write(convertedContent);
	    fos.close();
	} catch(Exception e) {
	    e.printStackTrace();
	}
    }
    
    public static String encodeProperties(String lang) throws Exception {
        
        String output = "";
        
        try {
            
            File file = new File("src\\GeckoProperties\\Bundle_" + lang.substring(0,2).toUpperCase() + ".properties");
            
            FileInputStream fileInput = new FileInputStream(file);
            
            Properties prop = new Properties();
            
            prop.load(fileInput);
            
            fileInput.close();
            
            Enumeration enuKeys = prop.keys();
            
            String lineSeparator = System.getProperty("line.separator");
            
            while (enuKeys.hasMoreElements()) {
                
                String key = (String) enuKeys.nextElement();
                String value = prop.getProperty(key);
                
                output += "<translate>" + lineSeparator + 
			"<!--T:" + key + "-->" + lineSeparator +  
			value + lineSeparator + "</translate>" + lineSeparator;               
                
            }
            
        } catch (FileNotFoundException e) {
            
            e.printStackTrace();
            
        } catch (IOException e) {
            
            e.printStackTrace();
            
        }       
        
        return output;
    }
    
    
}
