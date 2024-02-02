package graphics;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import services.DatabaseConnectionService;
import services.GamerService;
import services.FetchGamers;

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
	private JTable displayTable;
	private JComboBox<String> dropDown;
	private GamerService gamerService;
	private DatabaseConnectionService connection;
	private JScrollPane tablePane;

	public Frame() {
		tableModel = new DefaultTableModel();
		this.buttonPanel = new JPanel();
		this.goButton = new JButton("get gamer");
		this.clearButton = new JButton("clear");
		
		//temp add new button
		this.addButton = new JButton("Add Gamer");
		this.getgameButton = new JButton("Get All Game Names");
		this.getgamepieceButton = new JButton("Get All Game Pieces");
		this.getmatchButton = new JButton("Get All Matches Today");
		
		this.displayTable = new JTable(tableModel);
		this.dropDown = new JComboBox<String>(queryOpts);
		this.connection = new DatabaseConnectionService("golem.csse.rose-hulman.edu", "s1g7");
		this.connection.connect("","");
		this.gamerService = new GamerService(this.connection);
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
		this.buttonPanel.add(this.goButton);
		//add temp button
		this.buttonPanel.add(this.addButton);
		this.buttonPanel.add(this.getgameButton);
		this.buttonPanel.add(this.getgamepieceButton);
		this.buttonPanel.add(this.getmatchButton);
		
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
				this.gamerService.insertOneGamer("unique", "Cool Name", "James.Jones@domain.com", "01-01-1999");
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
				this.gamerService.insertOneGamer("unique", "Cool Name", "James.Jones@domain.com", "01-01-1999");
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
		this.getgameButton.addActionListener(new AddListener(gamerService));
		this.getgamepieceButton.addActionListener(new AddListener(gamerService));
		this.getmatchButton.addActionListener(new AddListener(gamerService));
	}
	
	public void run() {
		
	}
}
