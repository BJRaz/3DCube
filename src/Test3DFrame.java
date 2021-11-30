import java.awt.*;
import java.awt.event.*;

/**
 * Sample application using Frame.
 *
 * @@author 
 * @@version 1.00 04/05/12
 */
public class Test3DFrame extends Frame {
    
    
    private int i = 60;
    private int x[] = {i, i, i, i, -i, -i, -i, -i};
    private int y[] = {i, i, -i, -i, i, i, -i, -i};
    private int z[] = {-i, i, -i, i, -i, i, -i, i};
    
    private double scale = 1.0;
    
    /**
     * The constructor.
     */  
     private double anglez, anglex, angley;
     
     private static int speed = 50;
     
     public Test3DFrame(double anglex, double angley, double anglez) {
           
        this.anglez = anglez; 
        this.anglex = anglex;  
        this.angley = angley;
                 
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu();
        MenuItem menuFileExit = new MenuItem();
        
        menuFile.setLabel("File");
        menuFileExit.setLabel("Exit");
        
        // Add action listener.for the menu button
        menuFileExit.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Test3DFrame.this.windowClosed();
                }
            }
        ); 
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);
        
        setTitle("3D");
       // setMenuBar(menuBar);
        setSize(new Dimension(400, 400));
        
        // Add window listener.
        this.addWindowListener
        (
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    Test3DFrame.this.windowClosed();
                }
            }
        );  
        
        /**
         *
         */
         
        setBackground(Color.RED);
        
       	
    }
    
    
    /**
     * Shutdown procedure when run as an application.
     */
    protected void windowClosed() {
    	
    	// TODO: Check if it is save to close the application
    	
        // Exit application.
        System.exit(0);
    }
    
    
    public void paint(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	
    	int offset = 200;
    	
    	/*g2.setPaint(Color.GRAY);
    	g2.drawLine(200,0,200,400);
    	g2.drawLine(0,200,400,200);
    	*/
    	
    	
    	scale = 0.9;
    	
    	int camx = 0;
    	int camy = 0;
    	int camz = 200;
    	
    	int pivx = 0;
    	int pivy = 0;
    	int pivz = 0;
    
    	int movex = 0;
    	int movey = 0;
    	int movez = 0;
    	
    	double zx = 0;
    	double zy = 0;
    	double yx = 0;
    	double yz = 0;
    	double xy = 0;
    	double xz = 0;
    	
    	   
    	scale = scale / camz;   
    	    
     
    	Point points[] = new Point[8];   
    	
    	/**
    	 *	DRAW PIVOT
    	 */
    	g2.setPaint(Color.YELLOW); 
    	g2.drawOval(pivx+offset, pivy+offset, 5, 5);
    	//g2.drawString("Pivot(" + (pivx) + "," + (pivy) + ")", pivx+offset, pivy+offset);
    	 
    	 
    	g2.setPaint(Color.WHITE);    	
    	for(int i = 0;i < 8;i++) {
    		
    		int xd = x[i] - pivx;
    		int yd = y[i] - pivy;
    		int zd = z[i] - pivz;
    		
    		zx = (xd * Math.cos(Math.toRadians(anglez))) - (yd * Math.sin(Math.toRadians(anglez))) - xd;
    		zy = (xd * Math.sin(Math.toRadians(anglez))) + (yd * Math.cos(Math.toRadians(anglez))) - yd;
    		
    		yx = ((xd+zx) * Math.cos(Math.toRadians(angley))) - (zd * Math.sin(Math.toRadians(angley))) - (xd+zx);
    		yz = ((xd+zx) * Math.sin(Math.toRadians(angley))) + (zd * Math.cos(Math.toRadians(angley))) - zd;
    		
    		xy = ((yd+zy) * Math.cos(Math.toRadians(anglex))) - ((zd+yz) * Math.sin(Math.toRadians(anglex))) - (yd+zy);
    		xz = ((yd+zy) * Math.sin(Math.toRadians(anglex))) + ((zd+yz) * Math.cos(Math.toRadians(anglex))) - (zd+yz);
    		
    		
    		double xrotoffset = yx + zx;
    		double yrotoffset = zy + xy;
    		double zrotoffset = xz + yz;
    		
    		/**
    		 *	3D calc
    		 **/
    		 
    		double z1 = (z[i] + zrotoffset + camz);
    		double x1 = (((x[i] + xrotoffset + camx) / z1) / scale) + movex;
    		double y1 = (((y[i] + yrotoffset + camy) / z1) / scale) + movey;
    		
    		z1 = (z[i] + zrotoffset + camz);
    		
    		points[i] = new Point((int)x1+offset, (int)y1+offset);
    		
    		//g2.drawString(x[i] + ", " + y[i] + ", " + z[i] + "|", 100, (i*40)+100);
    		
    		//g2.drawString("HER: " + xrotoffset + " " + yrotoffset + " " + zrotoffset, 100, ((i*20)+100));
    		//g2.drawOval((int)((x[i] + xrotoffset)/scale)+offset, (int)((y[i] + yrotoffset)/scale)+offset, 1, 1);
    		//g2.drawOval((int)x1+offset, (int)y1+offset, 1, 1);
    		
    	}
    	
    	/**
    	 *   DRAW BOX
    	 */
    	
    	g2.drawLine(points[0].x, points[0].y, points[1].x, points[1].y);
    	g2.drawLine(points[0].x, points[0].y, points[4].x, points[4].y);
    	g2.drawLine(points[0].x, points[0].y, points[2].x, points[2].y);
    	g2.drawLine(points[2].x, points[2].y, points[3].x, points[3].y);
    	g2.drawLine(points[2].x, points[2].y, points[6].x, points[6].y);
    	g2.drawLine(points[3].x, points[3].y, points[1].x, points[1].y);
    	g2.drawLine(points[3].x, points[3].y, points[7].x, points[7].y);
    	g2.drawLine(points[7].x, points[7].y, points[5].x, points[5].y);
    	g2.drawLine(points[7].x, points[7].y, points[6].x, points[6].y);
    	g2.drawLine(points[1].x, points[1].y, points[5].x, points[5].y);
    	g2.drawLine(points[5].x, points[5].y, points[4].x, points[4].y);
    	g2.drawLine(points[4].x, points[4].y, points[6].x, points[6].y);
    	
    }
    
   
   	public void render() {
   		
   		
   		repaint();
   		try {
   			Thread.sleep(speed);
   		} catch(Exception e){
   			//
   		}
   		this.anglez += 6f;
   		this.anglex += 8f;
   		this.angley += 6f;
   	
   		render();
   	}
   
}

