/*
 * -Class containing main method.
 * -Running this class creates a new Gecko imitation GUI with translation pop-up
 * and translation toolbox functionality on the "Datei" menu and the
 * "Translation Tools" menu item.
 * -Pop-up triggering command is CTRL+SHIFT+press-mouse
 * 
 */
package translationtoolbox;

import gecko.GeckoGUI;

/**
 *
 * @author Nick Eyring
 */
public class TranslationToolbox {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Start a new Gecko GUI
        new GeckoGUI().setVisible(true);
        
    }
}
