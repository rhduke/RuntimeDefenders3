package mvcV4;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class userManualInterface extends JFrame{
	
	JButton button1;
	String outputString = "";
	
	// A Tree contains nodes that can contain other nodes
	
	JTree theTree;
	
	// If a node holds other nodes it is called a parent node
	// The nodes inside of a parent node are children nodes
	// Nodes on the same level are called siblings
	
	DefaultMutableTreeNode gettingStarted, print, email, emails;
	
	DefaultMutableTreeNode fileSystem = new DefaultMutableTreeNode("User Manual");
	private static JFrame frame = new JFrame("My Own Tree V2");
	private static JLabel browsePicture;
	private static JLabel convertPicture;
	private static JLabel editPicture;
	private static JLabel savePicture;
	private static JLabel gettingStartedStep1_0;
	private static JLabel gettingStartedStep1_1;
	private static JLabel gettingStartedStep2_0;
	private static JLabel gettingStartedStep3_0;
	private static JLabel gettingStartedStep4_0;
	private static JLabel printStep1_0;
	private static JLabel printStep2_0;
	private static JLabel emailStep1_0;
	private static JLabel emailStep2_0;
	private static JLabel emailStep3_0;
	private static JLabel emailStep4_0;
	private static JPanel informationPanel;
	private static JScrollPane scrollBox2;
	private static GridBagConstraints c = new GridBagConstraints();
	public userManualInterface(){
		frame.setSize(700,500);
		
		frame.setLocationRelativeTo(null);
		
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		
		JPanel thePanel = new JPanel(new GridBagLayout());
		informationPanel = new JPanel(new GridBagLayout());
		frame.getContentPane().add(thePanel, BorderLayout.NORTH);
		// Create the JTree by passing it the top tree node
		
		theTree = new JTree(fileSystem);
		
		// Makes sure only one item can be selected at a time
		// By default you can make multiple selections
		
		theTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		// Only show 8 rows of the tree at a time
		
		theTree.setVisibleRowCount(8);
		
		// Add the items to the tree documents, work, games
		// We first add the documents parent node
		
		gettingStarted = addAFile("Getting Started", fileSystem);
		
		// Now we add children nodes to the documents parent node
		
		addAFile("Step 1 - Selecting a File", gettingStarted);
		addAFile("Step 2 - Convert File", gettingStarted);
		addAFile("Step 3 - Editing File", gettingStarted);
		addAFile("Step 4 - Saving File", gettingStarted);
		
		
		// Create the work node and its children
		
		print = addAFile("Printer", fileSystem);
		addAFile("How to Print: Step 1", print);
		addAFile("How to Print: Step 2", print);
		
		// Create the games node and its children
		
		email = addAFile("Email", fileSystem);
		addAFile("Step 1 - Click Email", email);
		addAFile("Step 2 - Login", email);
		addAFile("Step 3 - Fill in Fields", email);
		addAFile("Step 4 (optional) - Add Emails from Address Book", email);
		
		// Put the tree in a scroll component
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				gettingStartedClicked(evt);
			}
		}
		);
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				howToPrintClicked(evt);
			}
		}
		);
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				howToEmailClicked(evt);
			}
		}
		);
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				step1Pressed(evt);
			}
		}
		);
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				step2Pressed(evt);
			}
		}
		);
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				step3Pressed(evt);
			}
		}
		);
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				step4Pressed(evt);
			}
		}
		);
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				print1Pressed(evt);
			}
		}
		);
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				print2Pressed(evt);
			}
		}
		);
	
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				email1Pressed(evt);
			}
		}
		);
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				email2Pressed(evt);
			}
		}
		);
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				email3Pressed(evt);
			}
		}
		);
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				email4Pressed(evt);
			}
		}
		);
		
		JScrollPane scrollBox = new JScrollPane(theTree);
		// Set the size for the JScrollPane so that everything fits
		
		Dimension d = scrollBox.getPreferredSize();
		d.width = 200;
		d.height = 350;
		scrollBox.setPreferredSize(d);
		
		c.insets = new Insets(0,0,0,0);
		c.gridx = 0;
		c.gridy = 0;
		thePanel.add(scrollBox, c);
		
		
		
		
		
		
		
		ImageIcon Icon = new ImageIcon("res/userManualImages/UM.jpg");
		File imageFile = new File("res/userManualImages/UM.jpg");
		
		BufferedImage image;
		BufferedImage resizedImage = null;
		try {
			image = ImageIO.read(imageFile);
			resizedImage = resize(image, 360,340);
			Icon = new ImageIcon(resizedImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		browsePicture = new JLabel();
		browsePicture.setIcon(Icon);
		
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0,0,60,0);
		informationPanel.add(browsePicture, c);
		
		scrollBox2 = new JScrollPane(informationPanel);
		scrollBox2.getVerticalScrollBar().setUnitIncrement(16);
		// Set the size for the JScrollPane so that everything fits
		
		Dimension d2 = scrollBox2.getPreferredSize();
		d2.width = 400;
		d2.height = 350;
		scrollBox2.setPreferredSize(d2);
		c.insets = new Insets(0,0,0,0);
		c.gridx = 0;
		c.gridy = 0;
		
		container.add(thePanel);
		container.add(scrollBox2);
		frame.add(container);
		frame.pack();
		frame.setVisible(true);
	}
	private void gettingStartedClicked(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("Getting Started") ) {
	    	informationPanel.removeAll();
	    	scrollBox2.getViewport().setViewPosition(new Point(0,0));
	        ImageIcon Icon = new ImageIcon("res/userManualImages/browseUM.jpg");
			File imageFile = new File("res/userManualImages/browseUM.jpg");
			BufferedImage image;
			BufferedImage resizedImage = null;
			try {
				image = ImageIO.read(imageFile);
				resizedImage = resize(image, 250,250);
				Icon = new ImageIcon(resizedImage);
			} catch (IOException e) {
				e.printStackTrace();
			}
			browsePicture.setIcon(Icon);
			c.gridx = 1;
			c.gridy = 1;
			c.insets = new Insets(0,0,60,0);
			informationPanel.add(browsePicture, c);
			
			gettingStartedStep1_0 = new JLabel("<html>Step 1:<br>Select a file by clicking the \"Browse Computer\" button<br>located at the top left corner of the program.<html>");
			c.insets = new Insets(10,10,10,10);
			c.gridx = 1;
			c.gridy = 0;
			informationPanel.add(gettingStartedStep1_0, c);
			
			gettingStartedStep2_0 = new JLabel("<html>Step 2:<br>Convert the file by clicking the \"Convert\" button.<html>");
			c.gridx = 1;
			c.gridy = 2;
			c.insets = new Insets(-60,10,10,50);
			informationPanel.add(gettingStartedStep2_0, c);
			
			//Adding in the convert picture.
			ImageIcon Icon2 = new ImageIcon("res/userManualImages/convertUM.jpg");
			File imageFile2 = new File("res/userManualImages/convertUM.jpg");
			BufferedImage image2;
			BufferedImage resizedImage2 = null;
			try {
				image2 = ImageIO.read(imageFile2);
				resizedImage2 = resize(image2, 250,250);
				Icon2 = new ImageIcon(resizedImage2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			convertPicture = new JLabel();
			convertPicture.setIcon(Icon2);
			c.gridx = 1;
			c.gridy = 3;
			c.insets = new Insets(0,0,0,0);
			informationPanel.add(convertPicture, c);
			
			gettingStartedStep3_0 = new JLabel("<html>Step 3:<br>Edit certain fields such as the title or spacing<br>by clicking the edit button or moving the slider.<html>");
			c.gridx = 1;
			c.gridy = 4;
			c.insets = new Insets(10,10,10,50);
			informationPanel.add(gettingStartedStep3_0, c);
			
			//Adding in the edit picture.
			ImageIcon Icon3 = new ImageIcon("res/userManualImages/editUM.jpg");
			File imageFile3 = new File("res/userManualImages/editUM.jpg");
			BufferedImage image3;
			BufferedImage resizedImage3 = null;
			try {
				image3 = ImageIO.read(imageFile3);
				resizedImage3 = resize(image3, 250,250);
				Icon3 = new ImageIcon(resizedImage3);
			} catch (IOException e) {
				e.printStackTrace();
			}
			editPicture = new JLabel();
			editPicture.setIcon(Icon3);
			c.gridx = 1;
			c.gridy = 5;
			c.insets = new Insets(0,0,100,0);
			informationPanel.add(editPicture, c);
			
			gettingStartedStep4_0 = new JLabel("<html>Step 4:<br>Save the file by clicking the \"Save As\" button.<html>");
			c.gridx = 1;
			c.gridy = 5;
			c.insets = new Insets(210,10,10,50);
			informationPanel.add(gettingStartedStep4_0, c);
			
			//Adding in the save picture.
			ImageIcon Icon4 = new ImageIcon("res/userManualImages/saveUM.jpg");
			File imageFile4 = new File("res/userManualImages/saveUM.jpg");
			BufferedImage image4;
			BufferedImage resizedImage4 = null;
			try {
				image4 = ImageIO.read(imageFile4);
				resizedImage4 = resize(image4, 250,250);
				Icon4 = new ImageIcon(resizedImage4);
			} catch (IOException e) {
				e.printStackTrace();
			}
			savePicture = new JLabel();
			savePicture.setIcon(Icon4);
			c.gridx = 1;
			c.gridy = 7;
			c.insets = new Insets(-50,0,20,0);
			informationPanel.add(savePicture, c);
			
			scrollBox2.revalidate();
			frame.revalidate();
			frame.getContentPane().repaint();
	    }
		
	}
	
	private void howToPrintClicked(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("Printer") ) {
	    	informationPanel.removeAll();
	    	scrollBox2.getViewport().setViewPosition(new Point(0,0));
	        ImageIcon Icon = new ImageIcon("res/userManualImages/print1UM.jpg");
			File imageFile = new File("res/userManualImages/print1UM.jpg");
			BufferedImage image;
			BufferedImage resizedImage = null;
			try {
				image = ImageIO.read(imageFile);
				resizedImage = resize(image, 250,250);
				Icon = new ImageIcon(resizedImage);
			} catch (IOException e) {
				e.printStackTrace();
			}
			browsePicture.setIcon(Icon);
			c.gridx = 1;
			c.gridy = 1;
			c.insets = new Insets(0,0,0,0);
			informationPanel.add(browsePicture, c);
			
			printStep1_0 = new JLabel("<html>Step 1:<br>Select the \"print\" tab located on the top left corner<br> of the menu.<html>");
			c.insets = new Insets(10,10,10,35);
			c.gridx = 1;
			c.gridy = 0;
			informationPanel.add(printStep1_0, c);
			
			printStep2_0 = new JLabel("<html>Step 2:<br>A window will appear, and the only thing left to do<br>is to select the printer you would like to print to.<html>");
			c.gridx = 1;
			c.gridy = 2;
			c.insets = new Insets(30,10,10,50);
			informationPanel.add(printStep2_0, c);
			
			//Adding in the convert picture.
			ImageIcon Icon2 = new ImageIcon("res/userManualImages/print2UM.jpg");
			File imageFile2 = new File("res/userManualImages/print2UM.jpg");
			BufferedImage image2;
			BufferedImage resizedImage2 = null;
			try {
				image2 = ImageIO.read(imageFile2);
				resizedImage2 = resize(image2, 250,250);
				Icon2 = new ImageIcon(resizedImage2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			JLabel convertPicture = new JLabel();
			convertPicture.setIcon(Icon2);
			c.gridx = 1;
			c.gridy = 3;
			c.insets = new Insets(0,0,0,0);
			informationPanel.add(convertPicture, c);
			
			scrollBox2.revalidate();
			frame.revalidate();
			frame.getContentPane().repaint();
	    }
		
	}
	
	private void howToEmailClicked(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("Email") ) {
	    	informationPanel.removeAll();
	    	scrollBox2.getViewport().setViewPosition(new Point(0,0));
	        ImageIcon Icon = new ImageIcon("res/userManualImages/email0UM.jpg");
			File imageFile = new File("res/userManualImages/email0UM.jpg");
			BufferedImage image;
			BufferedImage resizedImage = null;
			try {
				image = ImageIO.read(imageFile);
				resizedImage = resize(image, 250,250);
				Icon = new ImageIcon(resizedImage);
			} catch (IOException e) {
				e.printStackTrace();
			}
			browsePicture.setIcon(Icon);
			c.gridx = 1;
			c.gridy = 1;
			c.insets = new Insets(0,0,0,0);
			informationPanel.add(browsePicture, c);
			
			emailStep1_0 = new JLabel("<html>Step 1:<br>Select the \"email\" tab located on the top left corner<br> of the menu.<html>");
			c.insets = new Insets(10,10,10,35);
			c.gridx = 1;
			c.gridy = 0;
			informationPanel.add(emailStep1_0, c);
			
			emailStep2_0 = new JLabel("<html>Step 2:<br>Login using your gmail account or if you don't have one<br>then click the \"Don't have gmail?\" box.<html>");
			c.gridx = 1;
			c.gridy = 2;
			c.insets = new Insets(30, 40,10,50);
			informationPanel.add(emailStep2_0, c);
			
			//Adding in the convert picture.
			ImageIcon Icon2 = new ImageIcon("res/userManualImages/email1UM.jpg");
			File imageFile2 = new File("res/userManualImages/email1UM.jpg");
			BufferedImage image2;
			BufferedImage resizedImage2 = null;
			try {
				image2 = ImageIO.read(imageFile2);
				resizedImage2 = resize(image2, 300,250);
				Icon2 = new ImageIcon(resizedImage2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			JLabel convertPicture = new JLabel();
			convertPicture.setIcon(Icon2);
			c.gridx = 1;
			c.gridy = 3;
			c.insets = new Insets(0,0,0,0);
			informationPanel.add(convertPicture, c);
			
			emailStep3_0 = new JLabel("<html>Step 3:<br>Fill in the fields as specified by the program.<html>");
			c.gridx = 1;
			c.gridy = 4;
			c.insets = new Insets(30,-15,10,50);
			informationPanel.add(emailStep3_0, c);
			
			//Adding in the convert picture.
			ImageIcon Icon3 = new ImageIcon("res/userManualImages/email2UM.jpg");
			File imageFile3 = new File("res/userManualImages/email2UM.jpg");
			BufferedImage image3;
			BufferedImage resizedImage3 = null;
			try {
				image3 = ImageIO.read(imageFile3);
				resizedImage3 = resize(image3, 250,250);
				Icon3 = new ImageIcon(resizedImage3);
			} catch (IOException e) {
				e.printStackTrace();
			}
			JLabel convertPicture2 = new JLabel();
			convertPicture2.setIcon(Icon3);
			c.gridx = 1;
			c.gridy = 5;
			c.insets = new Insets(0,0,0,0);
			informationPanel.add(convertPicture2, c);
			
			emailStep4_0 = new JLabel("<html>Step 4:<br>Click \"Contacts\" if you wish to add contacts from your<br>address book. Click save when done.<html>");
			c.gridx = 1;
			c.gridy = 6;
			c.insets = new Insets(30,30,10,50);
			informationPanel.add(emailStep4_0, c);
			
			//Adding in the convert picture.
			ImageIcon Icon4 = new ImageIcon("res/userManualImages/email3UM.jpg");
			File imageFile4 = new File("res/userManualImages/email3UM.jpg");
			BufferedImage image4;
			BufferedImage resizedImage4 = null;
			try {
				image4 = ImageIO.read(imageFile4);
				resizedImage4 = resize(image4, 250,250);
				Icon4 = new ImageIcon(resizedImage4);
			} catch (IOException e) {
				e.printStackTrace();
			}
			JLabel convertPicture3 = new JLabel();
			convertPicture3.setIcon(Icon4);
			c.gridx = 1;
			c.gridy = 7;
			c.insets = new Insets(0,0,0,0);
			informationPanel.add(convertPicture3, c);
			
			scrollBox2.revalidate();
			frame.revalidate();
			frame.getContentPane().repaint();
	    }
		
	}
	
	private void step1Pressed(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("Step 1 - Selecting a File") ) {
	        scrollBox2.getViewport().setViewPosition(new Point(0,0));
	    }
	}
	
	private void step2Pressed(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("Step 2 - Convert File") ) {
	        scrollBox2.getViewport().setViewPosition(new Point(0,320));
	    }
	}
	
	private void step3Pressed(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("Step 3 - Editing File") ) {
	        scrollBox2.getViewport().setViewPosition(new Point(0,630));
	    }
	}
	
	private void step4Pressed(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("Step 4 - Saving File") ) {
	        scrollBox2.getViewport().setViewPosition(new Point(0,970));
	    }
	}
	
	private void print1Pressed(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("How to Print: Step 1") ) {
	        scrollBox2.getViewport().setViewPosition(new Point(0,0));
	    }
	}
	
	private void print2Pressed(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("How to Print: Step 2") ) {
	        scrollBox2.getViewport().setViewPosition(new Point(0,970));
	    }
	}
	
	private void email1Pressed(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("Step 1 - Click Email") ) {
	        scrollBox2.getViewport().setViewPosition(new Point(0,0));
	    }
	}
	
	private void email2Pressed(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("Step 2 - Login") ) {
	        scrollBox2.getViewport().setViewPosition(new Point(0,340));
	    }
	}
	
	private void email3Pressed(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("Step 3 - Fill in Fields") ) {
	        scrollBox2.getViewport().setViewPosition(new Point(0,670));
	    }
	}
	
	private void email4Pressed(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("Step 4 (optional) - Add Emails from Address Book") ) {
	        scrollBox2.getViewport().setViewPosition(new Point(0,1070));
	    }
	}
	
	public static BufferedImage resize(BufferedImage image, int width, int height) {
	    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) bi.createGraphics();
	    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	    g2d.drawImage(image, 0, 0, width, height, null);
	    g2d.dispose();
	    return bi;
	}
	private DefaultMutableTreeNode addAFile(String fileName, DefaultMutableTreeNode parentFolder){
		
		// Creates a new node for the tree
		DefaultMutableTreeNode newFile = new DefaultMutableTreeNode(fileName);
		
		// Add attaches a name to the node
		
		parentFolder.add(newFile);
		
		// return the new node
		
		return newFile;
		
	}
}

