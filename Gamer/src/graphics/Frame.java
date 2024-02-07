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
	private DatabaseConnectionService connection;
	private JScrollPane tablePane;
	private JLabel label1;
	private JTextField text1;
	private JLabel spanLabel1 = new JLabel("-                                                                                                                                                                                                                                                    -");
	private JLabel spanLabel2 = new JLabel("-                                                                                                                                                                                                                                                    -");
	private JFrame frame = this;
	
	public Frame() {
		tableModel = new DefaultTableModel();
		this.buttonPanel = new JPanel();
		this.goButton = new JButton("get gamer");
		
		//temp add new button
		this.addButton = new JButton("Add Gamer");
		this.getgameButton = new JButton("Get All Game Names");
		this.getgamepieceButton = new JButton("Get All Game Pieces");
		this.getmatchButton = new JButton("Get All Matches Today");
		this.addGamePieceButton = new JButton("Insert One Gamepiece");
		label1 = new JLabel("Test");
		label1.setText("Label Text");
		this.text1 = new JTextField(72);
		
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
		this.submitButton = new JButton("Submit");
		this.clearButton = new JButton("Clear");
		
//		this.tablePane = new JScrollPane(this.displayTable);
//		this.displayTable.setFillsViewportHeight(true);
//		this.add(tablePane, BorderLayout.CENTER);
		prepare();
		this.setVisible(true);
	}
	
	private void prepare() {
		this.setTitle("Gamer Database Access");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Frame.WIDTH, Frame.HEIGHT);
		// https://stackoverflow.com/questions/32467246/how-to-start-something-on-a-new-line-in-java-swing
        add(buttonPanel, BorderLayout.CENTER);
		this.buttonPanel.add(this.goButton);
		//add temp button
		this.buttonPanel.add(this.addButton);
		this.buttonPanel.add(this.getgameButton);
		this.buttonPanel.add(this.getgamepieceButton);
		this.buttonPanel.add(this.getmatchButton);
		this.buttonPanel.add(this.addGamePieceButton);
		this.buttonPanel.add(spanLabel1);
		this.buttonPanel.add(label1);
		text1.setHorizontalAlignment(JTextField.CENTER);
		this.buttonPanel.add(this.text1);
		this.buttonPanel.add(spanLabel2);
		// https://stackoverflow.com/questions/15507639/how-do-i-center-a-jtextfield
		
		class AddListener implements ActionListener{
			private GamerService gamerService;
			private JTable table;
			private DefaultTableModel tableModel;
			public AddListener(GamerService gamerS) {
				this.gamerService = gamerS;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// add a new gamer (pre-defined, eventually should take from textboxes)
				this.gamerService.insertOneGamer("uniqued", "Cool Name", "James.Jones@domain.com", "01-01-1999");
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
				this.gamerService.getAllGameNames();
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
				this.gamerService.getAllGamePieces();
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
				this.gamerService.getAllMatchRecords("1/9/2023", "MTG", "w");
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
					text1.setText("");
					buttonPanel.remove(submitButton);
					buttonPanel.remove(clearButton);
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
					for(FetchGamers gamer : fetched) {
						//this.tableModel.addRow(new Object[] {gamer.username, gamer.name, gamer.email, gamer.dob});
						System.out.println("username: " + gamer.username + " | name: " + gamer.name + " | email: " + gamer.email + " | DOB: " + gamer.dob);
					}
//					this.table.update(getGraphics());
				}
			}
		}
		this.goButton.addActionListener(new GoListener(this.dropDown, this.gamerService, this.displayTable, tableModel));
		//temp button listener
		this.addButton.addActionListener(new AddListener(gamerService));
		this.getgameButton.addActionListener(new GetGameListener(gamerService));
		this.getgamepieceButton.addActionListener(new GetGamePieceListener(gamerService));
		this.getmatchButton.addActionListener(new GetMatchListener(gamerService));
		this.addGamePieceButton.addActionListener(new AddGamePiecesListener(insertGamepieceService));
	}
	
	public void run() {
		
	}
}
