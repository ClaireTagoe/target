import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListModel;

/**
 * Java Application for Target Hackathon
 * User enters product url to create shopping list and check if products are on sale. Application will alert users when a product goes on sale.
 * @author emilypantuso
 *
 */
public class TargetProjGUI extends JFrame {

	public static void main(String[] args){
		TargetProjGUI frame = new TargetProjGUI();
		//frame.setLayout(new BorderLayout());
		frame.setVisible(true);
	}

	private final int FRAME_HEIGHT = 400;
	private final int FRAME_WIDTH = 600;
	private ArrayList<String> products = new ArrayList<String>();
	private JPanel userData = new JPanel();
	private JPanel productListArea = new JPanel();
	private JPanel footer = new JPanel();
	private JLabel hello = new JLabel("Hello and welcome to your Target List!");
	private JLabel urlLabel = new JLabel("Enter Product URL: ");
	private JButton addButton = new JButton("Add");
	static JTextArea urlLink = new JTextArea(1,20);
	static JList productList;

	/**
	 * Constructor.
	 */
	public TargetProjGUI(){

		this.setSize(FRAME_HEIGHT,FRAME_WIDTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("My Target List");

		create();

	}
	/**
	 * Local inner class for chess board buttons
	 */
	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// UPDATE - try/catch needed for HTTPGetRequest and API
			try {
				refresh(urlLink.getText());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	
	/*
	 * Creates new frame
	 */
	public void create(){

		//userData components
		userData.setLayout(new GridLayout(4, 1));
		hello.setPreferredSize(new Dimension(20,10));
		urlLink.setSize(30, 10);
		ButtonListener bl = new ButtonListener();
		addButton.addActionListener(bl);
		addButton.setSize(10, 50);
		userData.add(hello);
		userData.add(urlLabel);
		userData.add(urlLink);
		userData.add(addButton);	


		//making it pretty
		userData.setPreferredSize(new Dimension(100,100));
		productListArea.setPreferredSize(new Dimension(100, 200));
		footer.setPreferredSize(new Dimension(100, 100));
		userData.setBackground(Color.gray);
		productListArea.setBackground(Color.DARK_GRAY);


		//UPDATE populate productList


		//JList component populating and adding to productList
		productList = new JList(products.toArray());
		productList.setPreferredSize(new Dimension(200,100));
		productListArea.add(productList);
		

		//add components to frame
		this.add(productListArea, BorderLayout.CENTER);
		this.add(userData, BorderLayout.NORTH);
		this.add(footer, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	/*
	 * Refreshes the product list, called every time a new product is added
	 */
	public void refresh(String url) throws Exception{
		productListArea.remove(productList);
		
		// Retrieves ID
		String productID = getProductID(url);

		products.add(productID);
		productList = new JList(products.toArray());
		productList.setPreferredSize(new Dimension(200,100));
		productListArea.add(productList);
		this.setVisible(true);
		
		// Use API to get prices and determine if item is on sale
		ProductInfo info = new ProductInfo();
		String theJSON = info.fetchDetails(productID);
	}
	
	/*
	 * Retrieves 8-digit product id from the URL
	 * Assumes that a valid Target URL is given
	 */
	private String getProductID(String url) {
		Matcher m = Pattern.compile("(?<!\\d)\\d{8}(?!\\d)").matcher(url);
		m.find();
		return m.group();
	}
	
	
}