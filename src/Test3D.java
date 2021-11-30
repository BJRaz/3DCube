/**
 * AWT Sample application
 *
 * @@author Brian 
 * @@version 1.00 04/05/12
 */
public class Test3D {
    
    public static void main(String[] args) {
        // Create application frame.
        Test3DFrame frame = new Test3DFrame(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
        
        // Show frame
        frame.setVisible(true);
        frame.render();
        
    }
}

