/*This application was created by Ofir Aghai & Vidran Abdovich , 22/4/2014
 * as part of a Graphics programming course.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class main {
//	static List<Line> lines_L;
//	static List<Circle> circle_L;
//	static List<Poligon> poligon_L;
//	static List<Bezier> bezier_L;
	static List<Shape> shapeList;
	
	static List <String>lines = null;
	
	static int numOfPoints=0;
	static List<Point> linePoints=new ArrayList<>();
	static List<Point> circlePoints=new ArrayList<>();
	static List<Point> polygonPoints=new ArrayList<>();
	static List<Point> bezierPoints=new ArrayList<>();
	
	static myJPanel pane;
	static int poligon_vertex =0;
	static Point pointPressed =null;
	static Point pointRelease =null;
	static int lastDrag_x=0;
	static int lastDrag_y=0;
	static int shape = 1;		// 1=Line   2=Circle   3=Polygon 4=Bezier curve
	static Color color = Color.black;

	public static void main(String[] args) {
        final int width = 900;
        final int height = 500;

        /* java window - the container managed the frame */
        JFrame frame = new JFrame("Direct draw demo");       
        
        /* specific frame */
        pane = new myJPanel(width, height);
        
        //******************************************************************

        frame.add(pane);
        // to show at least the panel data
        frame.pack();
        /* Resizability */
        frame.setResizable(true);     
        /* close all app in close button */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      	//Create the menu bar.
        JMenuBar menuBar = new JMenuBar();
        
        //File Menu
        JMenu fileMenu = new JMenu ("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenuItem openFile = new JMenuItem("Open file..");
        //objectItem_line.setMnemonic(KeyEvent.VK_F1);
        openFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Component c = null;
				String pathString = promptForFile(c);
				Path path = Paths.get(pathString);

				try {
					//parse can open .TXT format that saved in type UTF-8
					lines = Files.readAllLines(path, StandardCharsets.US_ASCII);
					parseLines_ToObjects(lines);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}

			private void parseLines_ToObjects(List<String> lines) {
				//run on every line
				for (String line : lines){
					String numOfObject_string = line.substring(0, 1);  	//take the first character
					line = line.substring(2);			//reduce the line
					
					int objectNumber = Integer.parseInt(numOfObject_string);
					Object ob;
					switch (objectNumber){
					case 1: 
						ob = new Line();
						
						char[] charArray = line.toCharArray();
						for(int i=0; i<charArray.length; i++){
							if(!charArray.equals(',')){
								
							}
						}
						break;
					case 2:
						ob = new Circle();
						break;
					case 3:
						ob = new Poligon();
						break;
					case 4:
						ob = new Bezier();
						break;
						default:
							System.out.println("line error.");
							break;
					}
				}
			}
		});
        fileMenu.add(openFile);
        
        // Clear Screen button
        JMenuItem menu_clearScreen = new JMenuItem("Clear Screen");
        menu_clearScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.fillCanvas(Color.white);
			}
		});

        // Objects Menu
        JMenu objectsMenu = new JMenu ("Objects");
        objectsMenu.setMnemonic(KeyEvent.VK_O);
        JMenuItem objectItem_line = new JMenuItem("Line");
        objectItem_line.setMnemonic(KeyEvent.VK_F1);
        objectItem_line.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shape=1;
			}
		});
        JMenuItem objectItem_circle = new JMenuItem("Circle");
        objectItem_circle.setMnemonic(KeyEvent.VK_F2);
        objectItem_circle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shape=2;
			}
		});
        JMenuItem objectItem_poligon = new JMenuItem("Poligon");
        objectItem_poligon.setMnemonic(KeyEvent.VK_F3);
        objectItem_poligon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shape=3;

				//custom title, custom icon
				String result = JOptionPane.showInputDialog("Enter poligon vertex:");
				poligon_vertex= Integer.parseInt(result);
			}
		});

        JMenuItem objectItem_bezier = new JMenuItem("Bezier Curve");
        objectItem_bezier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shape=4;
			}
		});
        objectsMenu.add(objectItem_line);
        objectsMenu.add(objectItem_circle);
        objectsMenu.add(objectItem_poligon);
        objectsMenu.add(objectItem_bezier);
      //************************************************
        
        // Color Menu
        JMenu colorMenu = new JMenu("Color");
        JMenuItem colorMenu_black = new JMenuItem("");
        colorMenu_black.setPreferredSize(new Dimension(40, 20));
        colorMenu_black.setBackground(Color.BLACK);
        colorMenu_black.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = Color.BLACK;
			}
		});
        JMenuItem colorMenu_green = new JMenuItem("");
        colorMenu_green.setPreferredSize(new Dimension(40, 20));
        colorMenu_green.setBackground(Color.GREEN);
        colorMenu_green.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = Color.GREEN;
			}
		});
        JMenuItem colorMenu_blue = new JMenuItem("");
        colorMenu_blue.setPreferredSize(new Dimension(40, 20));
        colorMenu_blue.setBackground(Color.BLUE);
        colorMenu_blue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = Color.BLUE;
			}
		});
        JMenuItem colorMenu_white = new JMenuItem("");
        colorMenu_white.setPreferredSize(new Dimension(40, 20));
        colorMenu_white.setBackground(Color.WHITE);
        colorMenu_white.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = Color.WHITE;
			}
		});
        JMenuItem colorMenu_yellow = new JMenuItem("");
        colorMenu_yellow.setPreferredSize(new Dimension(40, 20));
        colorMenu_yellow.setBackground(Color.YELLOW);
        colorMenu_yellow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = Color.YELLOW;
			}
		});
        JMenuItem colorMenu_red = new JMenuItem("");
        colorMenu_red.setPreferredSize(new Dimension(40, 20));
        colorMenu_red.setBackground(Color.RED);
        colorMenu_red.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = Color.RED;
			}
		});
        //Build the first menu.
        colorMenu.add(colorMenu_black);
        colorMenu.add(colorMenu_red);
        colorMenu.add(colorMenu_green);
        colorMenu.add(colorMenu_blue);
        colorMenu.add(colorMenu_white);
        colorMenu.add(colorMenu_yellow);
        //*********************************************
        
        menuBar.add(fileMenu);
        menuBar.add(objectsMenu);
        menuBar.add(colorMenu);
        menuBar.add(menu_clearScreen);

        pane.setLayout(new BorderLayout());
        frame.add(menuBar, BorderLayout.NORTH);
        /**End menu's*******************************************************/
        
        // visibility 
        frame.setVisible(true);
          
        // Mouse Listeners 
        frame.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e1) {
				// TODO Auto-generated method stub
				System.out.println("release"+e1.getPoint());
				pointRelease= new Point( e1.getPoint() );
				// Fix point
				pointRelease.x-=8;
				pointRelease.y-=53;

				switch (shape){
				// Draw a line
				case 1: 
					linePoints.add(new Point((int)pointPressed.getX(), (int)pointPressed.getY()));	//start
					linePoints.add(new Point((int)lastDrag_x,(int)lastDrag_y));	//end
					
					pane.drawLine(color, linePoints);
					linePoints.clear(); 
					break;
				//Draw a circle
				case 2:
					//calculate radius
					circlePoints.add(new Point(pointPressed.x, pointPressed.y));
					int radius = pane.calculateRadius(pointPressed.x, pointPressed.y, pointRelease.x, pointRelease.y);
					
					pane.drawCircle(color, circlePoints, radius);
					circlePoints.clear();
					break;
				//Draw a polygon
				case 3:
					polygonPoints.add(new Point(pointPressed.x,pointPressed.y));
					polygonPoints.add(new Point(pointRelease.x,pointRelease.y));
					
					pane.regularPolygon(color ,polygonPoints, poligon_vertex);
					polygonPoints.clear();
					break;
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("pressed"+e.getPoint());
				pointPressed= new Point(e.getPoint() );
				// Fix point
				pointPressed.x-=8;
				pointPressed.y-=53;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				System.out.println("exited");

			}
			@Override
			public void mouseEntered(MouseEvent e) {				
				System.out.println("entered");

			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if ((shape==4) && (numOfPoints<4)){ //shape 4 = bezier curve
					pane.putSuperPixel(e.getX(),e.getY(),color);	//put big pixel
					numOfPoints++;									//counter
					bezierPoints.add(new Point(e.getX(),e.getY()));	//save point to list
					if (numOfPoints==4) {
						pane.drawBezierCurve(color,bezierPoints);	//draw
						bezierPoints=new ArrayList<>();	//init	why not use .clear() ??
						numOfPoints=0;					//init
					}
				}

				System.out.println("clicked");
			}
		});
        
        // mouse motion 
        frame.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				System.out.println("moved");
				System.out.println( "("+e.getPoint().getX()+" , "+e.getPoint().getY()+")");
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				System.out.println("dragged");
				lastDrag_x= (int) e.getPoint().getX()-8;
				lastDrag_y= (int) e.getPoint().getY()-53;
			}
		});
        
    }

	public static String promptForFile( Component parent )
	{
	    JFileChooser fc = new JFileChooser();
	    fc.setFileSelectionMode( JFileChooser.FILES_ONLY );
	
	    if( fc.showOpenDialog( parent ) == JFileChooser.APPROVE_OPTION )
	    {
	        return fc.getSelectedFile().getAbsolutePath();
	    }
	
	    return null;
	}
}