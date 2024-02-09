package graphics;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import services.DatabaseConnectionService;
import services.GamerService;
import services.FetchGamers;
import services.InsertGame_GamepieceService;
import services.InsertGamerService;
import services.LoginService;
import services.RegisterService;

public class Frame extends JFrame {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String[] queryOpts = {"Gamers"};
	private static DefaultTableModel tableModel;
	// buttons and other inputs
	private JPanel buttonPanel;
	private JButton goButton;
	private JButton clearButton;
	//temp button
	private JButton addButton;
	private JButton getgameButton;
	private JButton getgamepieceButton;
	private JButton getmatchButton;
	private JButton addGamePieceButton;
	private JButton submitButton;
	private JTable displayTable;
	private JComboBox<String> dropDown;
	private GamerService gamerService;
	private InsertGame_GamepieceService insertGamepieceService;
	private InsertGamerService insertGamerService;
	private DatabaseConnectionService connection;
	private JScrollPane tablePane;
	private JLabel label0;
	private JLabel label1;
	private JLabel label2;
	private JTextField text1;
	private JTextField text2;
	private JLabel spanLabel0 = new JLabel("-                                                                                                                                                                                                                                                    -");
	private JLabel spanLabel1 = new JLabel("-                                                                                                                                                                                                                                                    -");
	private JLabel spanLabel2 = new JLabel("-                                                                                                                                                                                                                                                    -");
	private JLabel spanLabel3 = new JLabel("-                                                                                                                                                                                                                                                    -");
	private JLabel spanLabel4 = new JLabel("-                                                                                                                                                                                                                                                    -");
	private JLabel spanLabel5 = new JLabel("-                                                                                                                                                                                                                                                    -");
	private JFrame frame = this;
	private JButton loginButton;
	private JButton registerButton;
	private LoginService loginService;
	private RegisterService registerService;
	
	public Frame() {
		tableModel = new DefaultTableModel();
		this.buttonPanel = new JPanel();
		this.goButton = new JButton("Get All Gamers");
		this.getgameButton = new JButton("Get All Game Names");
		this.getgamepieceButton = new JButton("Get All Game Pieces");
		this.getmatchButton = new JButton("Get All Matches");
		this.addButton = new JButton("Insert a Custom Gamer");
		this.addGamePieceButton = new JButton("Insert a Custom Gamepiece");
		label0 = new JLabel("Output");
		label0.setText("Welcome to the Gamer Interface! If you haven't already, please log in to ensure a secure environment!");
		label1 = new JLabel("Test");
		label1.setText("Label Text");
		label2 = new JLabel("Test");
		label2.setText("Label Text");
		this.text1 = new JTextField(72);
		this.text2 = new JTextField(72);
		
		this.displayTable = new JTable(tableModel);
		this.dropDown = new JComboBox<String>(queryOpts);
		this.connection = new DatabaseConnectionService("golem.csse.rose-hulman.edu", 
				"s1g7");
//		*
//		*
//		*
//		*
//		*
//		*
//		* Insert Credential here
		this.connection.connect("yangw2","IUUser");
		this.gamerService = new GamerService(this.connection);
		this.insertGamepieceService = new InsertGame_GamepieceService(connection);
		this.insertGamerService = new InsertGamerService(connection);
		this.loginService = new LoginService(connection);
		this.registerService = new RegisterService(connection);
		this.submitButton = new JButton("Submit");
		this.clearButton = new JButton("Clear");
		this.loginButton = new JButton("                         Login                         ");
		this.registerButton = new JButton("                         Register                         ");
		
//		this.tablePane = new JScrollPane(this.displayTable);
//		this.displayTable.setFillsViewportHeight(true);
//		this.add(tablePane, BorderLayout.CENTER);
		prepare();
		this.setVisible(true);
	}
	public void reset() {
		label0.setText("");
		text1.setText("");
		text2.setText("");
		buttonPanel.remove(label0);
		buttonPanel.remove(label1);
		buttonPanel.remove(spanLabel2);
		buttonPanel.remove(spanLabel1);
		buttonPanel.remove(text1);
		buttonPanel.remove(label2);
		buttonPanel.remove(text2);
		buttonPanel.remove(spanLabel4);
		buttonPanel.remove(submitButton);
		buttonPanel.remove(clearButton);
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}
	public void prepareBasicText() {
		reset();
		buttonPanel.add(spanLabel1);
		buttonPanel.add(label1);
		buttonPanel.add(text1);
		buttonPanel.add(spanLabel2);
	}
	private void prepare() {
		this.setTitle("Gamer Database Access");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Frame.WIDTH, Frame.HEIGHT);
		// https://stackoverflow.com/questions/32467246/how-to-start-something-on-a-new-line-in-java-swing
        add(buttonPanel, BorderLayout.CENTER);
		this.buttonPanel.add(loginButton);
		this.buttonPanel.add(registerButton);
		this.buttonPanel.add(spanLabel5);
		this.buttonPanel.add(this.goButton);
		this.buttonPanel.add(this.getgameButton);
		this.buttonPanel.add(this.getgamepieceButton);
		this.buttonPanel.add(this.getmatchButton);
		this.buttonPanel.add(spanLabel0);
		this.buttonPanel.add(this.addButton);
		this.buttonPanel.add(this.addGamePieceButton);
		this.buttonPanel.add(spanLabel3);
		this.buttonPanel.add(label0);
		text1.setHorizontalAlignment(JTextField.CENTER);
		text2.setHorizontalAlignment(JTextField.CENTER);
		// https://stackoverflow.com/questions/15507639/how-do-i-center-a-jtextfield
		
		class AddListener implements ActionListener{
			private InsertGamerService InsertGamerService;

			public AddListener(InsertGamerService insertGamerService) {
				this.InsertGamerService = insertGamerService;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// add a new gamer (pre-defined, eventually should take from textboxes)
				prepareBasicText();
				label1.setText("Input new Gamer Name");
				frame.invalidate();
				frame.validate();
				frame.repaint();
				buttonPanel.add(submitButton);
				buttonPanel.add(clearButton);
				//Scanner in = new Scanner(System.in);
				//String gamepieceName = in.next();
				submitButton.addActionListener(new SubmitListener());
				clearButton.addActionListener(new ClearListener());
				//this.InsertGame_GamepieceService.insertOneGamePiece(gamepieceName);
			}
			class SubmitListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					// used https://www.geeksforgeeks.org/java-swing-jtextfield/
					String gamepieceName = text1.getText();
					InsertGamerService.insertOneGamePiece(gamepieceName);
					reset();
					buttonPanel.add(label0);
					label0.setText("Add successful!");
					frame.invalidate();
					frame.validate();
					frame.repaint();
				}
				
			}
			class ClearListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					text1.setText("");
				}
				
			}
			
		}
		class GetGameListener implements ActionListener{
			private GamerService gamerService;
			private JTable table;
			private DefaultTableModel tableModel;
			public GetGameListener(GamerService gamerS) {
				this.gamerService = gamerS;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// add a new gamer (pre-defined, eventually should take from textboxes)
				reset();
				buttonPanel.add(label0);
				String superString = "";
				ArrayList<String> strings = this.gamerService.getAllGameNames();
				superString = strings.toString();
				label0.setText(superString);
			}
			
		}
		class GetGamePieceListener implements ActionListener{
			private GamerService gamerService;
			private JTable table;
			private DefaultTableModel tableModel;
			public GetGamePieceListener(GamerService gamerS) {
				this.gamerService = gamerS;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// add a new gamer (pre-defined, eventually should take from textboxes)
				reset();
				buttonPanel.add(label0);
				String superString = "";
				ArrayList<String> strings = this.gamerService.getAllGamePieces();
				superString = strings.toString();
				label0.setText(superString);
			}
			
		}
		class GetMatchListener implements ActionListener{
			private GamerService gamerService;
			private JTable table;
			private DefaultTableModel tableModel;
			public GetMatchListener(GamerService gamerS) {
				this.gamerService = gamerS;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// add a new gamer (pre-defined, eventually should take from textboxes)
				reset();
				buttonPanel.add(label0);
				String superString = "";
				ArrayList<String> strings = this.gamerService.getAllMatchRecords("1/9/2023", "MTG", "w");
				superString = strings.toString();
				label0.setText(superString);
			}
			
		}
		
		class AddGamePiecesListener implements ActionListener{
			private InsertGame_GamepieceService InsertGame_GamepieceService;

			public AddGamePiecesListener(InsertGame_GamepieceService service) {
				this.InsertGame_GamepieceService = service;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// add a new gamer (pre-defined, eventually should take from textboxes)
				prepareBasicText();
				label1.setText("Input new Gamepiece Name");
				frame.invalidate();
				frame.validate();
				frame.repaint();
				buttonPanel.add(submitButton);
				buttonPanel.add(clearButton);
				//Scanner in = new Scanner(System.in);
				//String gamepieceName = in.next();
				submitButton.addActionListener(new SubmitListener());
				clearButton.addActionListener(new ClearListener());
				//this.InsertGame_GamepieceService.insertOneGamePiece(gamepieceName);
			}
			class SubmitListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					// used https://www.geeksforgeeks.org/java-swing-jtextfield/
					String gamepieceName = text1.getText();
					InsertGame_GamepieceService.insertOneGamePiece(gamepieceName);
					reset();
					buttonPanel.add(label0);
					label0.setText("The piece has been successfully added!");
					frame.invalidate();
					frame.validate();
					frame.repaint();
				}
				
			}
			class ClearListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					text1.setText("");
				}
				
			}
			
		}

		class LoginListener implements ActionListener{
			private LoginService loginService;

			public LoginListener(LoginService service) {
				this.loginService = service;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// add a new gamer (pre-defined, eventually should take from textboxes)
				prepareBasicText();
				label1.setText("Please Enter Your Username");
				buttonPanel.add(label2);
				label2.setText("Please Enter Your Password");
				buttonPanel.add(text2);
				buttonPanel.add(spanLabel4);
				buttonPanel.add(submitButton);
				buttonPanel.add(clearButton);
				frame.invalidate();
				frame.validate();
				frame.repaint();
				//Scanner in = new Scanner(System.in);
				//String gamepieceName = in.next();
				submitButton.addActionListener(new SubmitListener());
				clearButton.addActionListener(new ClearListener());
				//this.InsertGame_GamepieceService.insertOneGamePiece(gamepieceName);
			}
			class SubmitListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					// used https://www.geeksforgeeks.org/java-swing-jtextfield/
					String gamepieceName = text1.getText();
					loginService.insertOneGamePiece(gamepieceName);
					reset();
					buttonPanel.add(label0);
					label0.setText("Thank you for logging in, " + gamepieceName + "!");
					frame.invalidate();
					frame.validate();
					frame.repaint();
				}
				
			}
			class ClearListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					text1.setText("");
					text2.setText("");
				}
				
			}
			
		}
		
		this.setResizable(true);
		this.add(buttonPanel, BorderLayout.CENTER);
		class GoListener implements ActionListener{
			private JComboBox<String> dropDown;
			private GamerService gamerService;
			private JTable table;
			private DefaultTableModel tableModel;
			public GoListener(JComboBox<String> dropDown, GamerService gamerS, JTable table, DefaultTableModel model) {
				this.dropDown = dropDown;
				this.gamerService = gamerS;
				this.table = table;
				this.tableModel = model;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = (String) this.dropDown.getSelectedItem();
				if(selected.equals("Gamers")) {
					//System.out.println("gameing time");
					ArrayList<FetchGamers> fetched = this.gamerService.getGamersTable(); //this will return [some kind of] table with the gamers and their info
					//update <the table> 
					//TODO: make the table
//					this.table.removeAll();
//					this.tableModel.addColumn("username");
//					this.tableModel.addColumn("name");
//					this.tableModel.addColumn("email");
//					this.tableModel.addColumn("dob");
					//System.out.println(fetched.get(0).name);
					ArrayList<String> usernames = new ArrayList<String>();
					for(FetchGamers gamer : fetched) {
						//this.tableModel.addRow(new Object[] {gamer.username, gamer.name, gamer.email, gamer.dob});
						System.out.println("username: " + gamer.username + " | name: " + gamer.name + " | email: " + gamer.email + " | DOB: " + gamer.dob);
						usernames.add(gamer.username);
					}
					label0.setText(usernames.toString());
//					this.table.update(getGraphics());
				}
			}
		}
		
		class RegisterListener implements ActionListener{
			private RegisterService registerService;

			public RegisterListener(RegisterService service) {
				this.registerService = service;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// add a new gamer (pre-defined, eventually should take from textboxes)
				prepareBasicText();
				label1.setText("Please Enter Your Username");
				buttonPanel.add(label2);
				label2.setText("Please Enter Your Password");
				buttonPanel.add(text2);
				buttonPanel.add(spanLabel4);
				buttonPanel.add(submitButton);
				buttonPanel.add(clearButton);
				frame.invalidate();
				frame.validate();
				frame.repaint();
				//Scanner in = new Scanner(System.in);
				//String gamepieceName = in.next();
				submitButton.addActionListener(new SubmitListener());
				clearButton.addActionListener(new ClearListener());
				//this.InsertGame_GamepieceService.insertOneGamePiece(gamepieceName);
			}
			class SubmitListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					// used https://www.geeksforgeeks.org/java-swing-jtextfield/
					String gamepieceName = text1.getText();
					registerService.insertOneGamePiece(gamepieceName);
					reset();
					buttonPanel.add(label0);
					label0.setText("Thank you for registering, " + gamepieceName + "!");
					frame.invalidate();
					frame.validate();
					frame.repaint();
				}
				
			}
			class ClearListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					text1.setText("");
					text2.setText("");
				}
				
			}
			
		}
		this.goButton.addActionListener(new GoListener(this.dropDown, this.gamerService, this.displayTable, tableModel));
		//temp button listener
		this.addButton.addActionListener(new AddListener(insertGamerService));
		this.getgameButton.addActionListener(new GetGameListener(gamerService));
		this.getgamepieceButton.addActionListener(new GetGamePieceListener(gamerService));
		this.getmatchButton.addActionListener(new GetMatchListener(gamerService));
		this.addGamePieceButton.addActionListener(new AddGamePiecesListener(insertGamepieceService));
		this.loginButton.addActionListener(new LoginListener(loginService));
		this.registerButton.addActionListener(new RegisterListener(registerService));
	}
	
	public void run() {
		
	}
}
