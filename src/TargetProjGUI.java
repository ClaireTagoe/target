//package src;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.OverlayLayout;
import javax.swing.border.Border;

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
	private final int FRAME_WIDTH = 700;
	private ArrayList<String> products = new ArrayList<String>();
	private JPanel userData = new JPanel();
	private JPanel productListArea = new JPanel();
	private JPanel header = new JPanel();
	private JLabel hello = new JLabel("Welcome to Sale Sniper!");
	private JLabel catchyPhrase = new JLabel("Let us help you target the right sale products!");
	private JLabel urlLabel = new JLabel("Enter Product URL: ");
	private JButton addButton = new JButton("Add");
	static JTextArea urlLink = new JTextArea(1,20);
	static JList productList;
	private JLabel logo = new JLabel();
	private JLabel bg = new JLabel();
	private ArrayList<ArrayList<String>> productsToStore = new ArrayList<ArrayList<String>>();

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

		ImageIcon image = new ImageIcon(new ImageIcon("logo.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		ImageIcon store = new ImageIcon(new ImageIcon("store.jpg").getImage().getScaledInstance(400, 150, Image.SCALE_DEFAULT));

		//userData components
		header.setLayout(new GridLayout(3,1));
		logo.setHorizontalAlignment(JLabel.CENTER);
		hello.setHorizontalAlignment(JLabel.CENTER);
		catchyPhrase.setFont(new Font("Arial", Font.ITALIC, 12));
		catchyPhrase.setHorizontalAlignment(JLabel.CENTER);
		userData.setLayout(new GridLayout(4, 1));
		//hello.setPreferredSize(new Dimension(20,10));
		//urlLink.setSize(30, 10);
		ButtonListener bl = new ButtonListener();
		addButton.addActionListener(bl);
		//addButton.setSize(10, 50);
		userData.add(hello);
		urlLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		userData.add(urlLabel);
		userData.add(urlLink);
		userData.add(addButton);	
		logo.setIcon(image);
		header.add(logo);
		hello.setFont(new Font("Arial", Font.BOLD, 18));
		header.add(hello);
		header.add(catchyPhrase);
		bg.setIcon(store);

		//making it pretty
//		userData.setPreferredSize(new Dimension(100,100));
		//productListArea.setLayout(new OverlayLayout(productListArea));
		productListArea.setPreferredSize(new Dimension(100, 400));
//		header.setPreferredSize(new Dimension(100, 100));
		userData.setBackground(Color.gray);
		//productListArea.setBackground(Color.RED);


		//UPDATE populate productList


		//JList component populating and adding to productList
		productList = new JList(products.toArray());
		productList.setPreferredSize(new Dimension(350,250));
		productListArea.add(productList);
		productListArea.add(bg);


		//add components to frame
		this.add(productListArea, BorderLayout.SOUTH);
		this.add(userData, BorderLayout.CENTER);
		this.add(header, BorderLayout.NORTH);

		this.setVisible(true);
	}

	/*
	 * Refreshes the product list, called every time a new product is added
	 */
	public void refresh(String url) throws Exception{
		productListArea.remove(productList);
		productListArea.remove(bg);
		
		// Retrieves ID
		String productID = getProductID(url);
		
		// Use API to get prices and determine if item is on sale
		ProductInfo info = new ProductInfo();
		
		// Add item information to store
		ArrayList<String> item = info.fetchDetails(productID);
		
		productsToStore.add(item);
		
		
		products.add(item.get(2) + " " + item.get(1) + " --> " + item.get(0) + " Discount: " + info.calculate_discount(item.get(0), item.get(1)) + "%");
			
		
		productList = new JList(products.toArray());
		productList.setPreferredSize(new Dimension(350,250));
		productListArea.add(productList);
		this.setVisible(true);
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