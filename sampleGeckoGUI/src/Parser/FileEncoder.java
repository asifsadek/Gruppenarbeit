package Parser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This program converts a Java properties file (latin-1) to 
 * UTF-8, formatted in such a way, that it can be copied into
 * a Wiki page.
 * This program works independently of the default locale 
 * and charset being used by the underlying operating system.
 * 
 * @author: Josef Ziegler
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
	    if (args.length != 2)
		throw new IOException("Wrong number of arguments specified");
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
	     * GeckoFrame.jMenuItem8.text =
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
                System.out.println("at line:  " + i);
                if (parts.length > 1) {
                  Output += lineSeparator + parts[0] + " =" + 
                      lineSeparator + "<translate>" + lineSeparator + 
                      "<!--T:" + parts[0] + "-->" + lineSeparator +  
                      parts[1];
                  for (int j = 2; j < parts.length; j++)
                    Output += parts[j];
                  Output += lineSeparator + "</translate>" + lineSeparator;
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
}
