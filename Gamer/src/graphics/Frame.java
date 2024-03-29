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
import services.DeleteGameService;
import services.DeleteGamepieceService;
import services.DeleteGamerService;
import services.DeleteMatchService;
import services.GamerService;
import services.FetchGamers;
import services.InsertGame_GamepieceService;
import services.InsertGamerService;
import services.LoginService;
import services.PentaStringList;
import services.RegisterService;
import services.TwinStringList;
import services.UpdateGamepieceService;
import services.UpdateGamerService;

public class Frame extends JFrame {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String[] queryOpts = { "Gamers" };
	private static DefaultTableModel tableModel;
	// buttons and other inputs
	private JPanel buttonPanel;
	private JButton goButton;
	private JButton clearButton;
	// temp button
	private JButton addButton;
	private JButton getgameButton;
	private JButton getgamepieceButton;
	private JButton getmatchButton;
	private JButton addGamePieceButton;
	private JButton submitButton;
	private JButton deleteGameButton;
	private JButton deleteGamerButton;
	private JButton deleteGamePieceButton;
	private JButton deleteMatchButton;
	private JButton getLeaderboardButton;
	private JButton updateGamerButton;
	private JButton updateGameButton;
	private JButton updateGamePieceButton;
	
	// display table
	private JTable displayTable;
	private JComboBox<String> dropDown;
	
	// all services
	private GamerService gamerService;
	private InsertGame_GamepieceService insertGamepieceService;
	private InsertGamerService insertGamerService;
	private DeleteGamerService deleteGamerService;
	private DeleteGamepieceService deleteGamepieceService;
	private DeleteGameService deleteGameService;
	private DeleteMatchService deleteMatchService;
	
	private DatabaseConnectionService connection;
	private JScrollPane tablePane;
	private JLabel label0;
	private JLabel label1;
	private JLabel label2;
	private JTextField text1;
	private JTextField text2;
	private JLabel spanLabel0 = new JLabel(
			"-                                                                                                                                                                                                                                                    -");
	private JLabel spanLabel1 = new JLabel(
			"-                                                                                                                                                                                                                                                    -");
	private JLabel spanLabel2 = new JLabel(
			"-                                                                                                                                                                                                                                                    -");
	private JLabel spanLabel3 = new JLabel(
			"-                                                                                                                                                                                                                                                    -");
	private JLabel spanLabel4 = new JLabel(
			"-                                                                                                                                                                                                                                                    -");
	private JLabel spanLabel5 = new JLabel(
			"-                                                                                                                                                                                                                                                    -");
	private JLabel spanLabel6 = new JLabel(
			"-                                                                                                                                                                                                                                                    -");
	private JFrame frame = this;
	private UpdateGamepieceService updateGamepieceService;
	private UpdateGamerService updateGamerService;

	public Frame() {
		tableModel = new DefaultTableModel();
		this.buttonPanel = new JPanel();
		this.goButton = new JButton("Get All Gamer Info");
		this.getgameButton = new JButton("Get All Game Names");
		this.getgamepieceButton = new JButton("Get All Game Pieces");
		this.getmatchButton = new JButton("Get Matches from Username");
		this.addButton = new JButton("Insert a Gamer");
		this.addGamePieceButton = new JButton("Insert a Game Piece");
		this.deleteGamePieceButton = new JButton("Remove a Game Piece");
		getLeaderboardButton = new JButton("View the Leaderboard");
		updateGamerButton = new JButton("Update a Gamer");
		// updateGameButton = new JButton("Update a Game"); = not enough information
		updateGamePieceButton = new JButton("Update a Game Piece");
		
		label0 = new JLabel("Output");
		label0.setText("Welcome to the Gamer Interface!");
		label1 = new JLabel("Test");
		label1.setText("Label Text");
		label2 = new JLabel("Test");
		label2.setText("Label Text");
		this.text1 = new JTextField(72);
		this.text2 = new JTextField(72);

		this.displayTable = new JTable(tableModel);
		this.dropDown = new JComboBox<String>(queryOpts);
		this.connection = new DatabaseConnectionService("golem.csse.rose-hulman.edu", "s1g7");

//		* Insert Credential here
		this.connection.connect("GamePlayer404", "minecraftfortnitebattleparkour");
		this.gamerService = new GamerService(this.connection);
		this.insertGamepieceService = new InsertGame_GamepieceService(connection);
		this.insertGamerService = new InsertGamerService(connection);
		this.deleteGamerService = new DeleteGamerService(connection);
		this.deleteGamepieceService = new DeleteGamepieceService(connection);
		this.deleteGameService = new DeleteGameService(connection);
		this.deleteMatchService = new DeleteMatchService(connection);
		this.submitButton = new JButton("Submit");
		this.clearButton = new JButton("Clear");
		this.deleteGamerButton = new JButton("Remove one Gamer");
		this.deleteGameButton = new JButton("Remove one Game");
		this.deleteMatchButton = new JButton("Remove one Match");
		this.updateGamepieceService = new UpdateGamepieceService(connection);
		this.updateGamerService = new UpdateGamerService(connection);
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

	// display one line of text 
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
		this.buttonPanel.add(this.goButton);
		this.buttonPanel.add(this.getgameButton);
		this.buttonPanel.add(this.getgamepieceButton);
		this.buttonPanel.add(this.getmatchButton);
		this.buttonPanel.add(spanLabel0);
		this.buttonPanel.add(this.addButton);
		this.buttonPanel.add(getLeaderboardButton);
		this.buttonPanel.add(this.addGamePieceButton);
		this.buttonPanel.add(spanLabel5);
		this.buttonPanel.add(deleteGamerButton);
		this.buttonPanel.add(deleteGamePieceButton);
		this.buttonPanel.add(deleteGameButton);
		this.buttonPanel.add(deleteMatchButton);
		this.buttonPanel.add(spanLabel3);
		this.buttonPanel.add(updateGamerButton);
		this.buttonPanel.add(updateGamePieceButton);
		this.buttonPanel.add(updateGamerButton);
		this.buttonPanel.add(spanLabel6);
		this.buttonPanel.add(label0);
		text1.setHorizontalAlignment(JTextField.CENTER);
		text2.setHorizontalAlignment(JTextField.CENTER);
		// https://stackoverflow.com/questions/15507639/how-do-i-center-a-jtextfield

		
		class DeleteGamerListener implements ActionListener {
			private DeleteGamerService DeleteGamerService;

			public DeleteGamerListener(DeleteGamerService deleteGamerService) {
				this.DeleteGamerService = deleteGamerService;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				prepareBasicText();
				label1.setText("Input Gamer UserName for Deletion");
				frame.invalidate();
				frame.validate();
				frame.repaint();
				buttonPanel.add(submitButton);
				buttonPanel.add(clearButton);
				
				// clear all listenner
				ActionListener[] listeners = submitButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	submitButton.removeActionListener(listener);
		        }
		        listeners = clearButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	clearButton.removeActionListener(listener);
		        }
		   
				submitButton.addActionListener(new DeleteSubmitListener());
				clearButton.addActionListener(new ClearListener());
			}

			class DeleteSubmitListener implements ActionListener {
				public DeleteSubmitListener() {
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					// used https://www.geeksforgeeks.org/java-swing-jtextfield/
					String text = text1.getText();
					reset();
					buttonPanel.add(label0);
					if(DeleteGamerService.deleteGamer(text)) {
						label0.setText("<html>The deletion of Gamer \"" + text + "\"was successful!");
					}
					else {
						label0.setText("<html>Error: Gamer \"" + text + "\" was not found in the database.");
					}
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
		
		class DeleteGameListener implements ActionListener {
			private DeleteGameService DeleteGameService;

			public DeleteGameListener(DeleteGameService deleteGameService) {
				this.DeleteGameService = deleteGameService;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// add a new gamer (pre-defined, eventually should take from textboxes)
				// ** not properly implemented yet **
				reset();
				prepareBasicText();
				label1.setText("Input Game Name to delete");
				frame.invalidate();
				frame.validate();
				frame.repaint();
				buttonPanel.add(submitButton);
				buttonPanel.add(clearButton);
				
				// clear all listenner
				ActionListener[] listeners = submitButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	submitButton.removeActionListener(listener);
		        }
		        listeners = clearButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	clearButton.removeActionListener(listener);
		        }
				// Scanner in = new Scanner(System.in);
				// String gamepieceName = in.next();
				submitButton.addActionListener(new DeleteSubmitListener());
				clearButton.addActionListener(new ClearListener());
			}

			class DeleteSubmitListener implements ActionListener {
				public DeleteSubmitListener() {
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					// used https://www.geeksforgeeks.org/java-swing-jtextfield/
					String text = text1.getText();
					reset();
					buttonPanel.add(label0);
					if(DeleteGameService.deleteGamer(text)) {
						label0.setText("<html>The deletion of Game \"" + text + "\"was successful!");
					}
					else {
						label0.setText("<html>Error: Game \"" + text + "\" was not found in the database.");
					}
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
		class DeleteGamepieceListener implements ActionListener {
			private DeleteGamepieceService DeleteGamepieceService;

			public DeleteGamepieceListener(DeleteGamepieceService DeleteGamepieceService) {
				this.DeleteGamepieceService = DeleteGamepieceService;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// add a new gamer (pre-defined, eventually should take from textboxes)
				reset();
				prepareBasicText();
				label1.setText("Game Piece Name for Deletion"); //this needs to specify what game. TODO: fix this
				frame.invalidate();
				frame.validate();
				frame.repaint();
				buttonPanel.add(submitButton);
				buttonPanel.add(clearButton);
				
				// clear all listenner
				ActionListener[] listeners = submitButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	submitButton.removeActionListener(listener);
		        }
		        listeners = clearButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	clearButton.removeActionListener(listener);
		        }
				// Scanner in = new Scanner(System.in);
				// String gamepieceName = in.next();
				submitButton.addActionListener(new DeleteSubmitListener());
				clearButton.addActionListener(new ClearListener());
			}

			class DeleteSubmitListener implements ActionListener {
				public DeleteSubmitListener() {
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					// used https://www.geeksforgeeks.org/java-swing-jtextfield/
					String text = text1.getText();
					reset();
					buttonPanel.add(label0);
					if(DeleteGamepieceService.deleteGamer(text)) {
						label0.setText("<html>The deletion of Game Piece \"" + text + "\"was successful!<\\html>");
					}
					else {
						label0.setText("<html>Error: Game Piece \"" + text + "\" was not found in the database.");
					}
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
		class DeleteMatchListener implements ActionListener {
			private DeleteMatchService DeleteMatchService;

			public DeleteMatchListener(DeleteMatchService DeleteMatchService) {
				this.DeleteMatchService = DeleteMatchService;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// add a new gamer (pre-defined, eventually should take from textboxes)
				reset();
				prepareBasicText();
				label1.setText("Match Date for Deletion (MM-DD-YYYY)");
				frame.invalidate();
				frame.validate();
				frame.repaint();
				label2.setText("Match Location");
				buttonPanel.add(label2);
				buttonPanel.add(text2);
				buttonPanel.add(submitButton);
				buttonPanel.add(clearButton);
				
				// clear all listenner
				ActionListener[] listeners = submitButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	submitButton.removeActionListener(listener);
		        }
		        listeners = clearButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	clearButton.removeActionListener(listener);
		        }
				// Scanner in = new Scanner(System.in);
				// String gamepieceName = in.next();
				submitButton.addActionListener(new DeleteSubmitListener());
				clearButton.addActionListener(new ClearListener());
			}

			class DeleteSubmitListener implements ActionListener {
				public DeleteSubmitListener() {
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					// used https://www.geeksforgeeks.org/java-swing-jtextfield/
					String date = text1.getText();
					String location = text2.getText();
					reset();
					buttonPanel.add(label0);
					if(DeleteMatchService.deleteGamer(date, location)) {
						label0.setText("<html>The deletion of the Match on " + date + " at " + location + " was successful!<\\html>");
					}
					else {
						label0.setText("<html>Error: The Match on " + date + " at " + location + " was not found in the database.");
					}
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
		class AddListener implements ActionListener { //TODO: make more clear names
			private InsertGamerService InsertGamerService;

			public AddListener(InsertGamerService insertGamerService) {
				this.InsertGamerService = insertGamerService;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				prepareBasicText();
				label1.setText("Input new Gamer Name");
				frame.invalidate();
				frame.validate();
				frame.repaint();
				buttonPanel.add(submitButton);
				buttonPanel.add(clearButton);
				
				// clear all listenner
				ActionListener[] listeners = submitButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	submitButton.removeActionListener(listener);
		        }
		        listeners = clearButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	clearButton.removeActionListener(listener);
		        }
				// Scanner in = new Scanner(System.in);
				// String gamepieceName = in.next();
				submitButton.addActionListener(new SubmitListener());
				clearButton.addActionListener(new ClearListener());
				// this.InsertGame_GamepieceService.insertOneGamePiece(gamepieceName);
			}

			class SubmitListener implements ActionListener {
				private FetchGamers info;
				private int done;
				public SubmitListener() {
					info = new FetchGamers("", "", "", "");
					done = 0;
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					// used https://www.geeksforgeeks.org/java-swing-jtextfield/
					if(done == 0) {
					info.name = text1.getText();
					label1.setText("Hi "+ info.name + ", please type your desired username.");
					done = 1;
					text1.setText("");
					}else if(done == 1) {
						info.username = text1.getText();
						text1.setText("__-__-____");
						label1.setText(info.name + ", please now input your Date of Birth, as MM-DD-YYYY");
						done = 2;
						text1.setText("");
					}else if(done == 2) {
						info.dob = text1.getText();
						label1.setText("Finally, please type your email.");
						done = 3;
						text1.setText("");
					}else {
						info.email = text1.getText();
						InsertGamerService.insertGamer(info);
						reset();
						buttonPanel.add(label0);
						label0.setText("Add successful! Welcome to the system " + info.username);
						frame.invalidate();
						frame.validate();
						frame.repaint();
						done = 0;
						text1.setText("");
					}
				}

			}

			class ClearListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					text1.setText("");
				}

			}

		}
		class UpdateGamepieceListener implements ActionListener {
			private UpdateGamepieceService UpdateGamepieceService;

			public UpdateGamepieceListener(UpdateGamepieceService updateGamepieceService) {
				this.UpdateGamepieceService = updateGamepieceService;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// add a new gamer (pre-defined, eventually should take from textboxes)
				reset();
				prepareBasicText();
				label1.setText("Input Game Name");
				frame.invalidate();
				frame.validate();
				frame.repaint();
				buttonPanel.add(submitButton);
				buttonPanel.add(clearButton);

				// clear all listenner
				ActionListener[] listeners = submitButton.getActionListeners();
				for (ActionListener listener : listeners) {
					submitButton.removeActionListener(listener);
				}
				listeners = clearButton.getActionListeners();
				for (ActionListener listener : listeners) {
					clearButton.removeActionListener(listener);
				}
				// Scanner in = new Scanner(System.in);
				// String gamepieceName = in.next();
				submitButton.addActionListener(new SubmitListener(updateGamepieceService));
				clearButton.addActionListener(new ClearListener());
				// this.InsertGame_GamepieceService.insertOneGamePiece(gamepieceName);
			}

			class SubmitListener implements ActionListener {
				private int done;
				private int test;
				private ArrayList<String> adj;
				private ArrayList<String> val;
				private ArrayList<String> old;
				private String gameName;
				private String pieceName;

				public SubmitListener(UpdateGamepieceService updateGamepieceService) {
					done = 0;
					test = 0;
					val = new ArrayList<String>();
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					if (this.done == 0) {
						this.gameName = text1.getText();
						text1.setText("");
						done++;
						label1.setText("Input the name of the gamepiece you'd like to modify");
						this.adj = updateGamepieceService.getAdjectives(this.gameName);
					} else if (done == 1) {
						this.pieceName = text1.getText();
						text1.setText("");
						done++;
						label1.setText("Input new value for: " + adj.get(0));
						System.out.println(adj);
						old = updateGamepieceService.discribe(this.gameName, this.pieceName);
					} else if (done == 2) {
						String value = text1.getText();
						if (!value.equals("")) {
							val.add(value);
						} else {
							val.add(old.get(test));
						}
						label1.setText("Input new value for: " + adj.get(test));
						test++;
						text1.setText("");
						if(adj.size() == test) {
							for(int i = 0; i < adj.size(); i++) {
								updateGamepieceService.setDescription(gameName, pieceName, adj.get(i), val.get(i));
							}
						}
					}
				}
			}

			class ClearListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					text1.setText("");
				}

			}

		}
			class ClearListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					text1.setText("");
				}

			}
	
		class UpdateGamerListener implements ActionListener {
			private UpdateGamerService UpdateGamerService;
	
			public UpdateGamerListener(UpdateGamerService updateGamerService) {
				this.UpdateGamerService = updateGamerService;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				prepareBasicText();
				label1.setText("Please input your name");
				frame.invalidate();
				frame.validate();
				frame.repaint();
				buttonPanel.add(submitButton);
				buttonPanel.add(clearButton);
				
				// clear all listenner
				ActionListener[] listeners = submitButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	submitButton.removeActionListener(listener);
		        }
		        listeners = clearButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	clearButton.removeActionListener(listener);
		        }
				submitButton.addActionListener(new SubmitListener());
				clearButton.addActionListener(new ClearListener());
			}

			class SubmitListener implements ActionListener {
				private FetchGamers info;
				private int done;
				public SubmitListener() {
					info = new FetchGamers("", "", "", "");
					done = 0;
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					// used https://www.geeksforgeeks.org/java-swing-jtextfield/
					if(done == 0) {
					int test = UpdateGamerService.getGamer(text1.getText());
						if(test == 1) {
							info.name = text1.getText();
							label1.setText("Hi "+ info.name + ", please type your desired new username, or leave it blank to not change it.");
							done = 1;
							text1.setText("");
						}
						else if(test == 0) {
							done = 0;
							label1.setText("<html> The name you have entered is not in our system. <br>Please check your name for typos or create a new user.");
							text1.setText("");
							
						}
						else if(test == -1){
							done = 0;
							reset();
							label1.setText("There has been a mistake. Please reload this page.");
							buttonPanel.add(label1);
						}
					}
					else if(done == 1) {
						info.username = text1.getText();
						text1.setText("");
						label1.setText(info.name + ", please now input your new Date of Birth, as MM-DD-YYYY, or leave it blank to not change it.");
						done = 2;
						text1.setText("");
					}else if(done == 2) {
						info.dob = text1.getText();
						label1.setText("Finally, please type your new email, or leave it blank to not change it.");
						done = 3;
						text1.setText("");
					}else {
						info.email = text1.getText();
					UpdateGamerService.insertGamer(info);
					reset();
					buttonPanel.add(label0);
					label0.setText("Add successful! Welcome to the system " + info.username);
					frame.invalidate();
					frame.validate();
					frame.repaint();
					done = 0;
					text1.setText("");
					}
				}

			}

			class ClearListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					text1.setText("");
				}

			}

		}
		class GetGameListener implements ActionListener {
			private GamerService gamerService;
			// private JTable table;
			// private DefaultTableModel tableModel; 
			// not used code above.

			public GetGameListener(GamerService gamerS) {
				this.gamerService = gamerS;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				buttonPanel.add(label0);
				// String superString = "";
				ArrayList<String> strings = this.gamerService.getAllGameNames();
				StringBuilder bob = new StringBuilder();
				bob.append("<html>Game Name");
				for (String string : strings) {
					bob.append(string + "<br>");
				}
				label0.setText(bob.toString());
			}

		}
		class GetGamePieceListener implements ActionListener {
			private GamerService gamerService;
			private JTable table;
			private DefaultTableModel tableModel;

			public GetGamePieceListener(GamerService gamerS) {
				this.gamerService = gamerS;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				buttonPanel.add(label0);
				TwinStringList strings = this.gamerService.getAllGamePieces();
				StringBuilder bob = new StringBuilder();
				bob.append("<html>Game Name | Gamepiece Name<br>");
				for (int i = 0; i < strings.first.size(); i++) {
					bob.append(strings.second.get(i) + " | " + strings.first.get(i) + "<br>");
				}
				label0.setText(bob.toString());
			}

		}
		class GetMatchListener implements ActionListener {
			private GamerService gamerService;
			private JTable table;
			private DefaultTableModel tableModel;

			public GetMatchListener(GamerService gamerS) {
				this.gamerService = gamerS;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				prepareBasicText();
				label1.setText("Input Username to search by");
				frame.invalidate();
				frame.validate();
				frame.repaint();
				buttonPanel.add(submitButton);
				buttonPanel.add(clearButton);
				
				// clear all listenner
				ActionListener[] listeners = submitButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	submitButton.removeActionListener(listener);
		        }
		        listeners = clearButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	clearButton.removeActionListener(listener);
		        }
				submitButton.addActionListener(new SubmitListener(gamerService));
				clearButton.addActionListener(new ClearListener());
			}

			class SubmitListener implements ActionListener {
				private GamerService gamerService;

				public SubmitListener(GamerService gs) {
					this.gamerService = gs;
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					// used https://www.geeksforgeeks.org/java-swing-jtextfield/
					String gamepieceName = text1.getText();
					reset();
					buttonPanel.add(label0);
					PentaStringList strings = this.gamerService.getUserMatchRecords(gamepieceName);
					StringBuilder bob = new StringBuilder();
					bob.append("<html>Game Name | Gamepiece Name | date | result | score<br>");
					for (int i = 0; i < strings.first.size(); i++) {
						bob.append(strings.first.get(i) + " | " + strings.second.get(i) + " | " + strings.third.get(i)
								+ " | " + strings.fourth.get(i) + " | " + strings.fith.get(i) + "<br>");
					}
					label0.setText(bob.toString());
				}

			}

			class ClearListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					text1.setText("");
				}

			}

		}
		class GetLeaderboardListener implements ActionListener {
			private GamerService gamerService;
			private JTable table;
			private DefaultTableModel tableModel;

			public GetLeaderboardListener(GamerService gamerS) {
				this.gamerService = gamerS;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				buttonPanel.add(label0);
				PentaStringList strings = this.gamerService.getLeaderboard();
				StringBuilder bob = new StringBuilder();
				bob.append("<html>Game Name | Gamepiece Name | date | result | score<br>");
				for (int i = 0; i < strings.first.size(); i++) {
					bob.append(strings.first.get(i) + " | " + strings.second.get(i) + " | " + strings.third.get(i)
							+ " | " + strings.fourth.get(i) + " | " + strings.fith.get(i) + "<br>");
				}
				label0.setText(bob.toString());
			}

		}

		class AddGamePiecesListener implements ActionListener {
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
				
				// clear all listenner
				ActionListener[] listeners = submitButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	submitButton.removeActionListener(listener);
		        }
		        listeners = clearButton.getActionListeners();
		        for (ActionListener listener : listeners) {
		        	clearButton.removeActionListener(listener);
		        }
				// Scanner in = new Scanner(System.in);
				// String gamepieceName = in.next();
				submitButton.addActionListener(new SubmitListener());
				clearButton.addActionListener(new ClearListener());
				// this.InsertGame_GamepieceService.insertOneGamePiece(gamepieceName);
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
		

		this.setResizable(true);
		this.add(buttonPanel, BorderLayout.CENTER);
		class GoListener implements ActionListener {
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
				if (selected.equals("Gamers")) {
					// System.out.println("gameing time");
					ArrayList<FetchGamers> fetched = this.gamerService.getGamersTable(); // this will return [some kind
																							// of] table with the gamers
																							// and their info
					// update <the table>
					// TODO: make the table
//					this.table.removeAll();
//					this.tableModel.addColumn("username");
//					this.tableModel.addColumn("name");
//					this.tableModel.addColumn("email");
//					this.tableModel.addColumn("dob");
					// System.out.println(fetched.get(0).name);
					StringBuilder bob = new StringBuilder();
					bob.append("<html>UserName | Name | email | Birthday<br>");
					for (FetchGamers gamer : fetched) {
						// this.tableModel.addRow(new Object[] {gamer.username, gamer.name, gamer.email,
						// gamer.dob});
						bob.append(gamer.username + " | " + gamer.name + " | "
								+ gamer.email + " | " + gamer.dob + "<br>");
				
					}
				
					
					
					reset();
					buttonPanel.add(label0);
					label0.setText(bob.toString());
					frame.invalidate();
					frame.validate();
					frame.repaint();
//					this.table.update(getGraphics());
					
				}
			}
		}
		this.goButton.addActionListener(new GoListener(this.dropDown, this.gamerService, this.displayTable, tableModel));
		this.addButton.addActionListener(new AddListener(insertGamerService));
		this.getgameButton.addActionListener(new GetGameListener(gamerService));
		this.getgamepieceButton.addActionListener(new GetGamePieceListener(gamerService));
		this.getmatchButton.addActionListener(new GetMatchListener(gamerService));
		this.addGamePieceButton.addActionListener(new AddGamePiecesListener(insertGamepieceService));
		this.deleteGamerButton.addActionListener(new DeleteGamerListener(deleteGamerService));
		this.deleteGamePieceButton.addActionListener(new DeleteGamepieceListener(deleteGamepieceService));
		this.deleteGameButton.addActionListener(new DeleteGameListener(deleteGameService));
		this.deleteMatchButton.addActionListener(new DeleteMatchListener(deleteMatchService));
		this.updateGamePieceButton.addActionListener(new UpdateGamepieceListener(updateGamepieceService));
		this.updateGamerButton.addActionListener(new UpdateGamerListener(updateGamerService));
		this.getLeaderboardButton.addActionListener(new GetLeaderboardListener(gamerService));
	}

	public void run() {

	}
}
