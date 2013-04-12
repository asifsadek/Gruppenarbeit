/*
 * -This class defines two HashMaps needed for assigning keys to values and
 * values to keys for a specific language.
 * -A new instance of this class needs to be defined for each language used.
 */
package i18n;

import java.util.HashMap;

/**
 *
 * @author Josef Ziegler, Nicholas Eyring
 */
public class DoubleMap {
    
    // maps keys to values, e.g. "GeckoFrame.jMenuItem17.text" to "Export"
    public HashMap<String,String> KeytoValue;
    // maps values to keys, e.g. "Export" to "GeckoFrame.jMenuItem17.text"
    public HashMap<String,String> ValuetoKey;
    
    // constructor
    public DoubleMap() {
        KeytoValue = new HashMap<String,String>();
        ValuetoKey = new HashMap<String,String>();
    }
   
}