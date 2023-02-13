package org.dyndns.tfud;

import java.awt.*;
import java.awt.event.*;

/**
 * Sample application using Frame.
 *
 * @@author Brian
 * @@version 1.00 04/05/12
 */
public class Frame3D extends Frame {

    private final int i = 60;
    private final int x[] = {i, i, i, i, -i, -i, -i, -i};
    private final int y[] = {i, i, -i, -i, i, i, -i, -i};
    private final int z[] = {-i, i, -i, i, -i, i, -i, i};

    
    private static final double SCALE = 1;
    private static final int SPEED = 131;
    private final CubeModel model;
    
    /**
     * The constructor.
     */
    /**
     * The constructor.
     *
     * @param anglex
     * @param angley
     * @param anglez
     */
    public Frame3D(double anglex, double angley, double anglez) {
        model = new CubeModel();
        
        init(anglez, anglex, angley);

    }

    /**
     *
     * @param anglez1
     * @param anglex1
     * @param angley1
     * @throws HeadlessException
     */
    private void init(double anglez1, double anglex1, double angley1) throws HeadlessException {
        model.anglez = anglez1;
        model.anglex = anglex1;
        model.angley = angley1;
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu();
        MenuItem menuFileExit = new MenuItem();
        menuFile.setLabel("File");
        menuFileExit.setLabel("Exit");
        // Add action listener.for the menu button
        menuFileExit.addActionListener((ActionEvent e) -> {
            Frame3D.this.windowClosed();
        });
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);
        setTitle("3D");
        // setMenuBar(menuBar);
        setSize(new Dimension(400, 400));
        // Add window listener.
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Frame3D.this.windowClosed();
            }
        }
        );

        setBackground(Color.DARK_GRAY);
    }

    /**
     * Shutdown procedure when run as an application.
     */
    protected void windowClosed() {

        // TODO: Check if it is save to close the application
        // Exit application.
        System.exit(0);
    }

    class CubeModel {
        
        public double anglez, anglex, angley;
        
        int offset = 200;

        /*g2.setPaint(Color.GRAY);
    	g2.drawLine(200,0,200,400);
    	g2.drawLine(0,200,400,200);
         */
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
        
        Point points[];
        
        
        public CubeModel() {
            points = new Point[8];
        }

        public void setPivot(Pivot p) {
            pivx = p.x;
            pivy = p.y;
            pivz = p.z;
        }
        
        public Pivot getPivot() {
            return new Pivot(pivx, pivy, pivz);
        }
        
        public int getOffset() {
            return offset;
        }
        
        
        
        public Point[] calculate() {
            double _scale = SCALE / camz;

            

            for (int k = 0; k < 8; k++) {

                int xd = x[k] - pivx;
                int yd = y[k] - pivy;
                int zd = z[k] - pivz;

                zx = (xd * Math.cos(Math.toRadians(anglez))) - (yd * Math.sin(Math.toRadians(anglez))) - xd;
                zy = (xd * Math.sin(Math.toRadians(anglez))) + (yd * Math.cos(Math.toRadians(anglez))) - yd;

                yx = ((xd + zx) * Math.cos(Math.toRadians(angley))) - (zd * Math.sin(Math.toRadians(angley))) - (xd + zx);
                yz = ((xd + zx) * Math.sin(Math.toRadians(angley))) + (zd * Math.cos(Math.toRadians(angley))) - zd;

                xy = ((yd + zy) * Math.cos(Math.toRadians(anglex))) - ((zd + yz) * Math.sin(Math.toRadians(anglex))) - (yd + zy);
                xz = ((yd + zy) * Math.sin(Math.toRadians(anglex))) + ((zd + yz) * Math.cos(Math.toRadians(anglex))) - (zd + yz);

                double xrotoffset = yx + zx;
                double yrotoffset = zy + xy;
                double zrotoffset = xz + yz;

                /**
                 * 3D calc
                 *
                 */
                double z1 = (z[k] + zrotoffset + camz);
                double x1 = (((x[k] + xrotoffset + camx) / z1) / _scale) + movex;
                double y1 = (((y[k] + yrotoffset + camy) / z1) / _scale) + movey;

                //z1 = (z[k] + zrotoffset + camz);
                points[k] = new Point((int) x1 + offset, (int) y1 + offset);
                /*
    		g2.drawString(x[i] + ", " + y[i] + ", " + z[i] + "|", 100, (i*40)+100);
    		
    		g2.drawString("HER: " + xrotoffset + " " + yrotoffset + " " + zrotoffset, 100, ((i*20)+100));
    		g2.drawOval((int)((x[i] + xrotoffset)/SCALE)+offset, (int)((y[i] + yrotoffset)/SCALE)+offset, 1, 1);
    		g2.drawOval((int)x1+offset, (int)y1+offset, 1, 1);
                 */
            }
            return points;
        }
    }

    /**
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        /**
         * DRAW PIVOT
         */
        Pivot pv = model.getPivot();
        g2.setPaint(Color.YELLOW);
        g2.drawOval(pv.x + model.getOffset(), pv.y + model.getOffset(), 5, 5);
        //g2.drawString("Pivot(" + (pivx) + "," + (pivy) + ")", pivx+offset, pivy+offset);

        g2.setPaint(Color.WHITE);

        Point[] points = model.calculate();
        
        
        /**
         * DRAW BOX
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

    /**
     *
     */
    public void render() {

        DoWork();
    }

    /**
     *
     */
    private void DoWork() {
        repaint();
        try {
            Thread.sleep(SPEED);
        } catch (Exception e) {
            //
        }
        model.anglez += 6f;
        model.anglex += 8f;
        model.angley += 6f;

        DoWork();
    }

}
