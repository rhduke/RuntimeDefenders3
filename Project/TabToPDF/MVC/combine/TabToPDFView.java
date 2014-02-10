package combine;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

//import ReadAndDisplayUserManual;   //text.ReadAndDisplayUserManual;

import com.alee.laf.WebLookAndFeel;
import com.itextpdf.text.DocumentException;

// DONE should createPreview be run at Select File button, or should there be a seperate preview button, or at convert button?
// DONE need to find a way to update preview image when button pressed
public class TabToPDFView {

	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;

	private static File fileToRead;
	public static String logString = "";

	protected static File previewImage; // had to change to public
	protected static File imageFile;

	private static java.awt.Color TRANSPARENT = new java.awt.Color(0, 0, 0, 0);
	private static JTextArea log;
	private static JButton selectButton;
	private static JButton convertButton;
	private static JTextPane topBox;
	private static Font buttonFont = new Font("SANS_SERIF", Font.BOLD, 25);

	public static JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The first menu");
		menuBar.add(menu);

		// Build second menu in the menu bar.
		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
				"Contain Elements pertaining to Edit");
		menuBar.add(menu);

		// Build third menu in the menu bar.
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
				"Contain elements to help the user");
		menuBar.add(menu);

		menuItem = new JMenuItem("User Manual");
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// OPEN USER MANUAL

				logString += "Opening User Manual...\n";
				updateLog();

				//ReadAndDisplayUserManual. CHANGED
				read();

				if (/*ReadAndDisplayUserManual.*/worked()) { //CHANGED
					logString += "User manual was opened.\n";
					updateLog();
				} else {
					logString += "Eek! User manual failed to open.\n";
					updateLog();
				}

			}

		});
		menu.add(menuItem);

		return menuBar;
	}

	
	
	//added readanddisplayusermanual here
	public static void openPDF() { //opens any pdf from desktop
	if (Desktop.isDesktopSupported()) {
	    try {
	        File myFile = new File(TabToPDFModel.PDF_FILENAME);
	        Desktop.getDesktop().open(myFile);
	    } catch (IOException ex) {
	        // no application registered for PDFs
	    }
	}
	}
	//alt open pdf
	/*public static void openPDF() {
	File file = new File("/outputfiles/musicPDF");
    if (file.toString().endsWith(".pdf")) 
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
    else {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);
}
	}*/ // WINDOWS ONLY!!!
	
	protected String message = "";
	protected static boolean worked;

	public static void read() {

		worked = false;

		/*
		 * Read in the user manual text and display in a dialog option pane.
		 */
		//TabToPDFView.openPDF(); DOESN'T WORK
		
		File userman = new File("res/userman");// OLD CHANGED
		//File userman = new File("/outputfiles/musicPDF.pdf"); //NEW
		//openPDF();
		String message = ""; //changed log string NOT!!!
		try {
			Scanner sc = new Scanner(userman);
			while (sc.hasNext()) {
				message += sc.nextLine() + "\n";
			}

			worked = true;

			sc.close();

			JOptionPane.showMessageDialog(new JFrame(), message);

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	protected static boolean worked() {
		return worked;
	}
	
	// end add of readanddisplayusermanual
	
	
	
	/**
	 * Method that creates the buttons
	 * 
	 * @param panel
	 */
	public static void addButtons(Container panel) {

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;

		selectButton = new JButton("Select Input File to Convert"); //changed this to be more descriptive
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// OPEN JFILESELECTOR

				logString += "Selecting a file...\n";
				updateLog();
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select PDF to convert");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					fileToRead = chooser.getSelectedFile();
					logString += "File " + chooser.getSelectedFile()
							+ " selected.\n";
					updateLog();
					
					TabToPDFModel.createPreview();
					

				} else {
					//
					logString += "Oops! Something went wrong when selecting file...\n";
					updateLog();
				}
			}
		});
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 0, 0, 0);
		c.ipady = 40;
		panel.add(selectButton, c);

		selectButton.setFont(buttonFont);

		convertButton = new JButton("Convert Input to PDF"); //changed this to be more descriptive
		convertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// DO CONVERT
				logString += "Attempting to convert file...\n";

				// need to send this to controller which sends this to model!!!

				TabToPDFModel test = new TabToPDFModel();

				try {
					test.createPDF(TabToPDFModel.PDF_FILENAME);
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Successfully converted "
						+ TabToPDFModel.INPUT_FILENAME + " to "
						+ TabToPDFModel.PDF_FILENAME + "!");

				updateLog();
				
				openPDF(); //added opening of PDF

			}
		});
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5, 0, 0, 0);
		c.ipady = 40;
		panel.add(convertButton, c);
		
		convertButton.setFont(buttonFont);
		
		
		//print button stuff
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(5, 0, 0, 0);
		c.ipady = 40;
		JButton printButton; // this needs to go to the top with private static
		printButton = new JButton("Print PDF");
		panel.add(printButton, c); // print button added
		printButton.setFont(buttonFont);
		//delete and move somewhere more relevant
		

		

	}

	/**
	 * Method that creates the top panel.
	 * 
	 * @param panel
	 */
	
	public static void addTopBox(Container panel) {

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        



        topBox = new JTextPane();
        topBox.setEditable(false);
        topBox.setPreferredSize(new Dimension(550, 250));
        topBox.setMinimumSize(new Dimension(550, 250));
        topBox.setBackground(TRANSPARENT);
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs , StyleConstants.ALIGN_CENTER);
        topBox.setParagraphAttributes(attribs,true);
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(topBox);
        
        if(imageFile == null){
                imageFile = new File("res/nopreview.gif");
        }
        
        try {
             BufferedImage image = ImageIO.read(imageFile);
             ImageIcon preview = new ImageIcon(image);
             topBox.insertIcon(preview);
            } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
    }
    
    public static void updateTopBox(){
    	
        try {
        	topBox.setText("");
        	
            BufferedImage image = ImageIO.read(imageFile);
            ImageIcon preview = new ImageIcon(image);
            
            topBox.insertIcon(preview);
            topBox.repaint();
           } catch (IOException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
           }
    }
	/*
	public static void addTopBox(Container panel, File f) {

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JTextPane topBox = new JTextPane();
		topBox.setEditable(false);
		topBox.setPreferredSize(new Dimension(550, 250));
		topBox.setMinimumSize(new Dimension(550, 250));
		topBox.setBackground(TRANSPARENT);
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
		topBox.setParagraphAttributes(attribs, true);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(topBox);

		if (f == null) {
			f = new File("res/nopreview.gif"); // "outputfiles/musicIMG.png");
												// // res folder necessary?
		}

		try {
			BufferedImage image = ImageIO.read(f);
			ImageIcon preview = new ImageIcon(image);
			topBox.insertIcon(preview);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

*/
	/**
	 * Method that creates the log box.
	 * 
	 * @param panel
	 */
	public static void addLogBox(Container panel) {

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel logLabel = new JLabel("Log:");
		c.gridx = 0;
		c.gridy = 1;
		panel.add(logLabel, c);

		log = new JTextArea(10, 30);
		log.setEditable(false);
		log.setLineWrap(true);
		log.setBackground(TRANSPARENT);

		JScrollPane scrollPane = new JScrollPane(log);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBackground(TRANSPARENT);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 2;
		panel.add(scrollPane, c);

	}

	/**
	 * Updates the log box with current activity.
	 */
	private static void updateLog() {
		log.append(logString);
		logString = "";
	}

	private static void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

			// WebLookAndFeel.class.getCanonicalName ()
			// "com.seaglasslookandfeel.SeaGlassLookAndFeel"
			// "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
		} catch (Exception exc) {

		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	public static void createAndShowGUI() {

		setLookAndFeel();

		// Create and set up the window.
		JFrame frame = new JFrame("Tab to PDF"); //changed this to be more appropriate
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setSize(700, 500);
		frame.setResizable(false);

		// Set up the content pane.
		JPanel finalPanel = new JPanel(new BorderLayout());
		// Setup sub panels
		JPanel buttonPanel = new JPanel();
		JPanel logPanel = new JPanel();
		JPanel topPanel = new JPanel();
		buttonPanel.setLocation(0, frame.getHeight());

		addButtons(buttonPanel);
		addLogBox(logPanel);
		addTopBox(topPanel); //previewImage);

		finalPanel.add(topPanel, BorderLayout.PAGE_START);
		finalPanel.add(logPanel, BorderLayout.EAST);
		finalPanel.add(buttonPanel, BorderLayout.WEST);

		frame.setJMenuBar(createMenuBar());
		frame.add(finalPanel);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String args[]) {

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}

}
