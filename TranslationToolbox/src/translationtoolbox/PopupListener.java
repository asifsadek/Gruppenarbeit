/*
 * -Pop-up mouse listener class.
 * 
 */
package translationtoolbox;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;


/**
 *
 * @author Nick Eyring
 */
public class PopupListener implements MouseListener {
    
    @Override
    public void mousePressed(MouseEvent evt) {
        listenForPopup(evt);
    }
    
    public void listenForPopup(MouseEvent evt) {        
        if (evt.isControlDown() && evt.isShiftDown()) {
            new TranslationPopup(evt).setVisible(true); // open the pop-up            
        }
    }
    
    @Override
    public void mouseExited(MouseEvent evt) {}
    @Override
    public void mouseEntered(MouseEvent evt) {}
    @Override
    public void mouseReleased(MouseEvent evt) {}
    @Override
    public void mouseClicked(MouseEvent evt) {}
}
