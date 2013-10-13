package ee.ut.math.tvt.flux;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.*;        

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class IntroUI extends JFrame implements MouseMotionListener, MouseListener{
	
	private static final long serialVersionUID = 8821652971254981654L;
	
	private static final int borderRoundArc = 65;
	private int width;
	private int height;
	private ImagePanel imp;
	private Point cursorPositionOnMouseDown;
	
	Properties appProp;
	Properties versProp;
	
	private ClassLoader loader;
	private Logger logger;
	
	public IntroUI() {
		loader = this.getClass().getClassLoader();
		
		// Initialize and configure logger.
		Configurator.initialize("IntroUI", loader, loader.getResource("log4j2.xml").toString());
		logger = LogManager.getLogger();
		

		
		// Load background and set window size.
		setTitle("flux");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setIconImage(imIcon);
		
		Image bg = null;
		try {
			bg = ImageIO.read(loader.getResourceAsStream("solidFluxUI.png"));
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		width = bg.getWidth(null);
		height = bg.getHeight(null);
		setSize(width, height);
		setResizable(false);
		setUndecorated(true);
		
		RoundRectangle2D rr = new RoundRectangle2D.Double(0, 0, width, height, borderRoundArc, borderRoundArc);
		setShape(rr);
		
		imp = new ImagePanel(bg);
		getLayeredPane().add(imp, new Integer(Integer.MIN_VALUE));
		((JPanel) getContentPane()).setOpaque(false);
		imp.setBounds(getX(), getY(), getWidth(), getHeight());
		
		addMouseMotionListener(this);
		addMouseListener(this);
		
		appProp = new Properties();
		versProp = new Properties();
		
		try {
			appProp.load(loader.getResourceAsStream("application.properties"));
			versProp.load(loader.getResourceAsStream("version.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JPanel p = new JPanel();
		p.setLayout(null);
		p.setOpaque(false);
		
		JLabel teamName = new JLabel("Team name: " + appProp.getProperty("teamName"));
		JLabel teamLeader = new JLabel("Team leader: " + appProp.getProperty("teamLeader"));
		JLabel member1 = new JLabel(appProp.getProperty("teamMembers").split(", ")[0]);
		JLabel member2 = new JLabel(appProp.getProperty("teamMembers").split(", ")[1]);
		JLabel member3 = new JLabel(appProp.getProperty("teamMembers").split(", ")[2]);
		JLabel member4 = new JLabel(appProp.getProperty("teamMembers").split(", ")[3]);
		JLabel version = new JLabel(versProp.getProperty("build.number"));
		
		p.add(teamName);
		p.add(teamLeader);
		p.add(version);
		
		Dimension size = teamName.getPreferredSize();
		teamName.setBounds(600, 5,size.width, size.height);
		size = teamLeader.getPreferredSize();
		teamLeader.setBounds(600 , 20,size.width, size.height);
		version.setBounds(600, 35,size.width, size.height);

		p.add(member1);
		
		member1.setFont(new Font("Open Sans", Font.BOLD, 18));
		
		p.add(member2);
		p.add(member3);
		p.add(member4);
		
		size = member1.getPreferredSize();
		member1.setBounds(80, 240,size.width, size.height);
		size = member2.getPreferredSize();
		member2.setBounds(80, 270,size.width, size.height);
		size = member3.getPreferredSize();
		member3.setBounds(80, 300,size.width, size.height);
		size = member4.getPreferredSize();
		member4.setBounds(80, 330,size.width, size.height);
		
	
		add(p);
		
		setVisible(true);
		logger.info("Intro window opened.");
	}

	private class ImagePanel extends JRootPane {
		private static final long serialVersionUID = 3287387287842282253L;
		private Image im;
		public ImagePanel(Image im) {
			super();
			this.im = im;
		}

		@Override
		protected void paintComponent(Graphics g) {
			/*((Graphics2D)g).setComposite(AlphaComposite.Clear);
			g.fillRect(0, 0, getWidth(), getHeight());
			((Graphics2D)g).setComposite(AlphaComposite.Src);*/
			
			//g.drawImage(im, 0, 0, null);
			g.drawImage(im, 0, 0, getWidth(), getHeight(), 0, 0, im.getWidth(null), im.getHeight(null), null);
			g.dispose();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		Point newCursorPosition = e.getPoint();
		int dX = newCursorPosition.x - cursorPositionOnMouseDown.x;
		int dY = newCursorPosition.y - cursorPositionOnMouseDown.y;
		Point frameLocation = getLocation();
		setLocation(frameLocation.x + dX, frameLocation.y + dY);
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		int screenX = e.getXOnScreen();
	    int screenY = e.getYOnScreen();
	    System.out.println("Click coordinates: " + screenX + "," + screenY);
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		cursorPositionOnMouseDown = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
