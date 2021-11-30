package org.dyndns.tfud.TestApps;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.dyndns.tfud.Test3DFrame;

/**
 * AWT Sample application
 *
 * @@author Brian 
 * @@version 1.00 04/05/12
 */
public class Test3D {
    
    public static void main(String[] args) {
        // Create application frame.
        try 
        {
            if(args.length < 3)
                throw new Exception("no enough parameters");
            Test3DFrame frame = new Test3DFrame(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));

            // Show frame
            frame.setVisible(true);
            frame.render();

        } catch (Exception ex) {
            Logger.getLogger(Test3D.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}

