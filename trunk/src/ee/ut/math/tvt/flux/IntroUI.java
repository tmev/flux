package ee.ut.math.tvt.flux;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class IntroUI extends JFrame implements MouseMotionListener,
		MouseListener {

	private static final long serialVersionUID = 8821652971254981654L;

	private static final int borderRoundArc = 65;
	private int width;
	private int height;
	private ImagePanel imp;
	private Point cursorPositionOnMouseDown;
	private Font font;
	private Font font_40;
	private Font font_28;

	Properties appProp;
	Properties versProp;

	private ClassLoader loader;
	private Logger logger;

	public IntroUI() {
		loader = this.getClass().getClassLoader();

		// Initialize and configure logger.
		Configurator.initialize("IntroUI", loader,
				loader.getResource("log4j2.xml").toString());
		logger = LogManager.getLogger();

		appProp = new Properties();
		versProp = new Properties();

		try {
			appProp.load(loader.getResourceAsStream("application.properties"));
			versProp.load(loader.getResourceAsStream("version.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}


		setTitle("flux");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setIconImage(imIcon);

		// Load background and fonts, set window size and shape
		Image bg = null;
		try {
			bg = ImageIO.read(loader.getResourceAsStream(appProp
					.getProperty("teamLogo")));
		} catch (IOException e) {

			e.printStackTrace();
		}

		try {
			font = Font.createFont(Font.TRUETYPE_FONT,
					loader.getResourceAsStream("OpenSans-Light.ttf"));
		} catch (FontFormatException | IOException e1) {
			font = new Font("Sans", Font.PLAIN, 20);
		}
		font_40 = font.deriveFont((float) 40);
		font_28 = font.deriveFont((float) 28);

		width = bg.getWidth(null);
		height = bg.getHeight(null);
		setSize(width, height);
		setResizable(false);
		setUndecorated(true);

		RoundRectangle2D rr = new RoundRectangle2D.Double(0, 0, width, height,
				borderRoundArc, borderRoundArc);
		setShape(rr);

		imp = new ImagePanel(bg);
		getLayeredPane().add(imp, new Integer(Integer.MIN_VALUE));
		((JPanel) getContentPane()).setOpaque(false);
		imp.setBounds(getX(), getY(), getWidth(), getHeight());

		addMouseMotionListener(this);
		addMouseListener(this);

		// Add elements
		JPanel p = new JPanel();
		p.setLayout(null);
		p.setOpaque(false);

		JLabel teamName = new JLabel("Team name: "
				+ appProp.getProperty("teamName"));
		JLabel teamLeader = new JLabel("Team leader: "
				+ appProp.getProperty("teamLeader"));
		JLabel version = new JLabel(versProp.getProperty("build.number"));
		String[] members = appProp.getProperty("teamMembers").split(", ");

		p.add(teamName);
		p.add(teamLeader);
		p.add(version);

		Dimension size = teamName.getPreferredSize();
		// teamName.setBounds(600, 5,size.width, size.height);
		size = teamLeader.getPreferredSize();
		// teamLeader.setBounds(600 , 20,size.width, size.height);
		// size = version.getPreferredSize();
		version.setFont(font_40);
		version.setBounds(590, 40, 130, 50);

		JLabel member_label;
		for (int i = 0; i < members.length; i++) {
			member_label = new JLabel(members[i]);
			p.add(member_label);
			member_label.setFont(font_28);
			size = member_label.getPreferredSize();
			member_label.setBounds(80, 240 + 30 * i, size.width, size.height);
		}

		JButton exitButton = new JButton();

		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.setOpaque(false);
		p.add(exitButton);
		Image exitImage = null;
		try {
			exitImage = ImageIO.read(loader
					.getResourceAsStream("closeButton.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		exitButton.setIcon(new ImageIcon(exitImage));
		exitButton.addActionListener(new exitApp());
		exitButton.setBounds(755, 25, 12, 12);

		add(p);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);

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
			/*
			 * ((Graphics2D)g).setComposite(AlphaComposite.Clear); g.fillRect(0,
			 * 0, getWidth(), getHeight());
			 * ((Graphics2D)g).setComposite(AlphaComposite.Src);
			 */

			// g.drawImage(im, 0, 0, null);
			g.drawImage(im, 0, 0, getWidth(), getHeight(), 0, 0,
					im.getWidth(null), im.getHeight(null), null);
			g.dispose();
		}
	}

	static class exitApp implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
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
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int screenX = e.getXOnScreen();
		int screenY = e.getYOnScreen();
		logger.debug("Click coordinates: " + screenX + "," + screenY);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		cursorPositionOnMouseDown = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
