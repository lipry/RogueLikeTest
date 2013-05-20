/**
 * @author Mika Oksanen
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GameGUI extends JFrame implements Serializable {

	// Fields

	private Game controller;
	private Adventurer a;
	private ArrayList<Monster> monsters;
	private int monstersAmount;
	private boolean equipSuccesfull;
	private boolean isInvOpen = true;
	private boolean destroying = false;
	private boolean shiftIsPressed = false;
	private boolean keyDown = false;
	private boolean gamePause;
	private String dungeon;
	
	/**
	 * GUI consists from these elements mainContainer JPanel, all other elements
	 * are added into this dialogBox JPanel, dialog box which will be placed on
	 * top of the window? gameWindow JPanel, includes dungeon and all the actors
	 * infoBox JPanel, dungeon level, character level, hp etc. Bottom of the
	 * window
	 */
	private JPanel mainContainer;
	private JPanel dialogBoxOne;
	private JPanel dialogBoxTwo;
	private JPanel gameWindow;
	private JPanel inventoryPanel;
	private JPanel infoBoxPlayer;
	private JPanel infoBoxMonster;
	private JPanel inventoryBox;
	private JTextArea gameArea; // Shows the entire dungeon and actors
	private JLabel dialogLabelOne; // Shows row 1 of dialog
	private JLabel dialogLabelTwo; // Shows row 2 of dialog
	private JLabel infoLabelPlayer; // Shows players stats
	private JLabel infoLabelMonster; // Shows stats of the closest monter
	private JLabel inventoryLabel; // Shows equipped items and dungeon level
	private JTextArea inventoryText; // Text that shows inventory
	private GridBagConstraints c; // Constraints for the layout

	// pause menu buttons.
	private JButton cont;
	private JButton restrt;
	private JButton ext;
	private JButton restrtNewDungeon;
	private JButton buttonLoad;
	private JButton buttonSave;

	ActionListener continueListener;
	ActionListener restartListener;
	ActionListener saveButtonListener;
	ActionListener loadButtonListener;
	ActionListener exitListener;
	ActionListener restarNewDungeonListener;

	/*
	 *  Start Menu items, this are all the items and keylistener used to display many menus
	 */
	private JPanel menuPanel; //panel that contains all the items to display
	private JTextArea titleArea;
	private JFormattedTextField centerArea;
	private JButton newGame; //new Game button at the starting menu
	private JButton continueGame; // continue button at the starting menu
	private JTextArea imageArea; //area used to display the dragon image
	private JButton startGame;
	private JLabel insertName;
	ActionListener newGameListener;
	ActionListener continueGameListener;
	ActionListener startGameListener; // button "Next" at the player input name screen
	private JButton okButton; // button at the story screen
	ActionListener okButtonListener;
	private JButton loadGameButton; // button displayed at the load screen
	ActionListener loadGameButtonListener;

	// Death Panel
	private JButton restart = new JButton("Restart");
	private JButton exit = new JButton("Exit");
	private JButton loadGame = new JButton("Load Game");
	private JPanel gameOverPanel;
	private JTextArea skullArea;
	private JTextArea ticketArea;
	ActionListener restartGame;
	
	

	//Final Win Panel
	private JPanel winPanel;
	private JTextArea winGameArea;
	private JButton yesWinButton; //button to go to the main menu after saving the sister
	private JButton noWinButton; //button to exit the game after saving the sister	
	ActionListener yesWinButtonListener;
	ActionListener noWinButtonListener;
	
	//Partial Win Panel (the panel displayed after reaching level 25 and defeating the boss)
	private JPanel partialWinPanel;
	private JTextArea partialWinArea;
	private JButton letsGoButton;
	private JButton killSisterButton;
	ActionListener letsGoButtonListener;
	ActionListener killSisterButtonListener;

    //Credits Panel (displayed after winning the game)
	private JPanel creditsPanel;
	private JTextArea creditsArea;
	private JButton creditsExit;
	
	// Constructor

	public GameGUI() {
		installGUI();
		changeMenuColor(Color.white, Color.black); //this sets the colors of the starting menu and adds all the keylisteners
		changeColor(Color.white, Color.black);
	}

	// Methods
	/**
	 * This methods change the color of the components
	 * 
	 * @param policy
	 *            Color of the Foreground of all components
	 * @param landscape
	 *            Color of the BackGround of all components
	 */

	public void changeColor(Color policy, Color landscape) {
		this.mainContainer.setBackground(landscape);
		this.mainContainer.setForeground(policy);
		this.mainContainer.setOpaque(true);
		this.dialogBoxOne.setBackground(landscape);
		this.dialogBoxOne.setForeground(policy);
		this.dialogBoxOne.setOpaque(true);
		this.dialogBoxTwo.setBackground(landscape);
		this.dialogBoxTwo.setForeground(policy);
		this.dialogBoxTwo.setOpaque(true);
		this.gameWindow.setBackground(landscape);
		this.gameWindow.setForeground(policy);
		this.gameWindow.setOpaque(true);
		this.inventoryPanel.setBackground(landscape);
		this.inventoryPanel.setForeground(policy);
		this.inventoryPanel.setOpaque(true);
		this.infoBoxPlayer.setBackground(landscape);
		this.infoBoxPlayer.setForeground(policy);
		this.infoBoxPlayer.setOpaque(true);
		this.infoBoxMonster.setBackground(landscape);
		this.infoBoxMonster.setForeground(policy);
		this.infoBoxMonster.setOpaque(true);
		this.inventoryBox.setBackground(landscape);
		this.inventoryBox.setForeground(policy);
		this.inventoryBox.setOpaque(true);
		this.gameArea.setBackground(landscape);
		this.gameArea.setForeground(policy);
		this.gameArea.setOpaque(true);
		this.dialogLabelOne.setBackground(landscape);
		this.dialogLabelOne.setForeground(policy);
		this.dialogLabelOne.setOpaque(true);
		this.dialogLabelTwo.setBackground(landscape);
		this.dialogLabelTwo.setForeground(policy);
		this.dialogLabelTwo.setOpaque(true);
		this.infoLabelPlayer.setBackground(landscape);
		this.infoLabelPlayer.setForeground(policy);
		this.infoLabelPlayer.setOpaque(true);
		this.infoLabelMonster.setBackground(landscape);
		this.infoLabelMonster.setForeground(policy);
		this.infoLabelMonster.setOpaque(true);
		this.inventoryLabel.setBackground(landscape);
		this.inventoryLabel.setForeground(policy);
		this.inventoryLabel.setOpaque(true);
		this.inventoryText.setBackground(landscape);
		this.inventoryText.setForeground(policy);
		this.inventoryText.setOpaque(true);
		
		this.ticketArea.setBackground(landscape);
		this.ticketArea.setForeground(policy);
		this.ticketArea.setOpaque(true);

	}

	public void installGUI() {

		setTitle("Roguelike");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gamePause = false;

		/*
		 * buttons listeners
		 */

		continueListener = new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				setContentPane(menuPanel);
				gamePause = !gamePause;
				refreshScreen2();
				requestFocus();
			}
		};

		restartListener = new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				Box[][] currentBoxes = controller.returnDungeon().getBoxes();
				gamePause = !gamePause;

				// recreate adventure
				currentBoxes[a.getPosX()][a.getPosY()].setVisible(false);
				currentBoxes[a.getPosX()][a.getPosY()].setActor(null);
				currentBoxes[a.getPosX()][a.getPosY()].setWalked(false);
				currentBoxes[a.getPosX()][a.getPosY()].setWalkable(true);

				Adventurer a1 = new Adventurer("Default",
						controller.returnDungeon());
				Adventurer.expToNextLevel = 10;
				controller.setAdventurer(a1);
				registerAdventurer(a1);

				// recreate monsters
				for (int i = 0; i < monsters.size(); i++) {
					currentBoxes[monsters.get(i).getPosX()][monsters.get(i)
							.getPosY()].setVisible(false);
					currentBoxes[monsters.get(i).getPosX()][monsters.get(i)
							.getPosY()].setActor(null);
					currentBoxes[monsters.get(i).getPosX()][monsters.get(i)
							.getPosY()].setWalked(false);
					currentBoxes[monsters.get(i).getPosX()][monsters.get(i)
							.getPosY()].setWalkable(true);
				}
				controller.createMonsters();
				registerMonster(controller.getMonsters());

				// set all rooms to not walked status and all corridors to not
				// visible.
				Dungeon d = controller.getArrayDungeon()[controller
						.getPosDungeon()];
				Room rm[] = d.getRooms();
				for (int i = 0; i < rm.length; i++) {
					rm[i].setWalked(false);

				}

				for (int j = 0; j < currentBoxes[0].length; j++) {
					for (int i = 0; i < currentBoxes.length; i++) {
						if (currentBoxes[i][j] != null
								&& currentBoxes[i][j].isVisible())
							currentBoxes[i][j].setVisible(false);
					}
				}

				requestFocus();
				refreshScreen();

			}
		};
		
		//Game Over Panel

		gameOverPanel = new JPanel();
		gameOverPanel.setPreferredSize(new Dimension(1280, 720));
		gameOverPanel.setLayout(null);
		
		skullArea = new JTextArea("");
        ticketArea = new JTextArea("");
        
		gameOverPanel.add(skullArea);
        gameOverPanel.add(ticketArea);
        gameOverPanel.add(restart);
        gameOverPanel.add(loadGame);
        gameOverPanel.add(exit);

		skullArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        ticketArea.setFont(new Font("Monospaced", Font.PLAIN, 14));



	    

        gameOverPanel.setLayout(null);
 

        gameOverPanel.add(skullArea);

		
		
		// Win Panel settings
		winPanel=new JPanel();
		winPanel.setPreferredSize(new Dimension(1280,720));
		winGameArea= new JTextArea("");
		winGameArea.setFont(new Font("Monoscaped", Font.PLAIN, 12));
		yesWinButton=new JButton("Main Menu");
		noWinButton=new JButton("Exit");
		winPanel.setLayout(null);
		winPanel.add(winGameArea);
		winPanel.add(yesWinButton);
		winPanel.add(noWinButton);
		yesWinButton.setBounds(575, 611, 130, 30);
		noWinButton.setBounds(575,650,130,30);
		winGameArea.setBounds(300,10,670,560);
		
		
		//Partial win panel settings
		partialWinPanel= new JPanel();
		partialWinPanel.setPreferredSize(new Dimension(1280,720));
		partialWinArea=new JTextArea("");
		partialWinArea.setFont(new Font("Monoscaped", Font.PLAIN,12));
		letsGoButton=new JButton("I'll save her!");
		letsGoButton.setBounds(575,650,130,30);
		killSisterButton=new JButton("No, let her die!");
		killSisterButton.setBounds(575,670,130,30);
		partialWinPanel.setLayout(null);
		partialWinPanel.add(partialWinArea);
		partialWinPanel.add(letsGoButton);
		partialWinArea.setBounds(380,50,670,560);
		partialWinPanel.add(killSisterButton);
		
		//Credits panel setting
		creditsPanel=new JPanel();
		creditsPanel.setPreferredSize(new Dimension(1280,720));
		creditsPanel.setLayout(null);
		creditsArea=new JTextArea("");
		creditsArea.setFont(new Font("Monoscaped", Font.PLAIN,12));
		creditsExit=new JButton("Exit");
		creditsPanel.add(creditsArea);
		creditsPanel.add(creditsExit);
		creditsExit.setBounds(575,670,130,30);
		
		/**
		 * Content and settings of the starting menu panel
		 */
		

        
		menuPanel = new JPanel();
		titleArea = new JTextArea(
				"   ________            _       __           __    __        __  ___           __  \n"
						+ "  /_  __/ /_  ___     | |     / /___  _____/ /___/ /____   /  |/  /___  _____/ /_ \n"
						+ "   / / / __ |/ _ |    | | /| / / __ |/ ___/ / __  / ___/  / /|_/ / __ |/ ___/ __/ \n"
						+ "  / / / / / /  __/    | |/ |/ / /_/ / /  / / /_/ (__  )  / /  / / /_/ (__  ) /_   \n"
						+ " /_/ /_/ /_/|___/     |__/|__/|____/_/  /_/|__,_/____/  /_/  /_/|____/____/|__/   \n"
						+ "                                                                                  \n"
						+ "     ______      _         ____                  __     ______                \n"
						+ "    / ____/___  (_)____   / __ |__  _____  _____/ /_   / ____/   _____  _____ \n"
						+ "   / __/ / __ |/ / ___/  / / / / / / / _ |/ ___/ __/  / __/ | | / / _ |/ ___/ \n"
						+ "  / /___/ /_/ / / /__   / /_/ / /_/ /  __(__  ) /_   / /___ | |/ /  __/ /     \n"
						+ " /_____/ .___/_/|___/   |___|_|__,_/|___/____/|__/  /_____/ |___/|___/_/      \n"
						+ "      /_/                                                                     \n");
		centerArea = new JFormattedTextField("");

		imageArea = new JTextArea(
				"                                                                    ____________\n"
						+ "                                              (`-..________....---''  ____..._.-`\n"
						+ "                                               \\\\`._______.._,.---'''     ,'\n"
						+ "                                               ; )`.      __..-'`-.      /\n"
						+ "                                              / /     _.-' _,.;;._ `-._,'\n"
						+ "                                             / /   ,-' _.-'  //   ``--._``._\n"
						+ "                                           ,','_.-' ,-' _.- (( =-    -. `-._`-._____\n"
						+ "                                         ,;.''__..-'   _..--.\\\\.--'````--.._``-.`-._`.\n"
						+ "                          _          |\\,' .-''        ```-'`---'`-...__,._  ``-.`-.`-.`.\n"
						+ "               _     _.-,'(__)\\__)\\-'' `     ___  .          `     \\      `--._\n"
						+ "              ,',)---' /|)          `     `      ``-.   `     /     /     `     `-.\n"
						+ "   ./         \\_____--.  '`  `               __..-.  \\     . (   < _...-----..._   `.\n"
						+ "  <_n_          \\_,--..__. \\\\ .-`.\\----'';``,..-.__ \\  \\      ,`_. `.,-'`--'`---''`.  )\n"
						+ "   'B\\)                 `.\\`.\\  `_.-..' ,'   _,-..'  /..,-''(, ,' ; ( _______`___..'__\n"
						+ "   /^>                          ((,(,__(    ((,(,__,'  ``'-- `'`.(\\  `.,..______   W&B\n"
						+ "  '  '                                                             ``--------..._``--.__\n");

		newGame = new JButton("New Game");
		continueGame = new JButton("Continue");
		startGame = new JButton("Next");
		insertName = new JLabel("Insert Player Name:");
		okButton = new JButton("Start Game");
		okButton.setBounds(200, 611, 100, 30);
		okButton.setVisible(false);
		loadGameButton = new JButton("Load Game");
		loadGameButton.setBounds(593, 611, 100, 30);
		loadGameButton.setVisible(false);

		centerArea.setColumns(20);
		menuPanel.setLayout(null);
		titleArea.setBounds(271, 20, 750, 255);
		menuPanel.add(okButton);

		/**
		 * Content and settings of the menu Panel
		 */
		menuPanel.setPreferredSize(new Dimension(1280, 720));
		menuPanel.add(titleArea);
		centerArea.setBounds(540, 401, 200, 30);
		menuPanel.add(centerArea);
		newGame.setBounds(593, 611, 100, 30);
		menuPanel.add(newGame);
		continueGame.setBounds(594, 651, 100, 30);
		menuPanel.add(continueGame);
		startGame.setBounds(593, 611, 100, 30);
		menuPanel.add(startGame);
		startGame.setVisible(false);
		menuPanel.add(loadGameButton);

		centerArea.setVisible(false);
		imageArea.setBounds(360, 300, 830, 300);
		imageArea.setEditable(false);
		imageArea.setCursor(null);
		imageArea.setOpaque(false);
		imageArea.setFocusable(false);
		imageArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		imageArea.setVisible(true);

		insertName.setBounds(380, 401, 200, 30);
		menuPanel.add(insertName);
		insertName.setVisible(false);
		menuPanel.add(imageArea);

		titleArea.setEditable(false);
		titleArea.setCursor(null);
		titleArea.setOpaque(false);
		titleArea.setFocusable(false);
		titleArea.setFont(new Font("Monospaced", Font.PLAIN, 15));

		centerArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		centerArea.setFocusable(true);
		centerArea.setEditable(true);

		// GAME MENU LISTENERS

		/**
		 * This displays the insert player menu screen after you push new game
		 */
		newGameListener = new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				imageArea.setVisible(false);
				centerArea.setVisible(true);
				newGame.setVisible(false);
				continueGame.setVisible(false);
				startGame.setVisible(true);
				insertName.setVisible(true);
			}
		};
		
		/**
		 * This button allows the player to continue after defeating the dragon and gives the player a key
		 */
		letsGoButtonListener = new ActionListener(){
			public void actionPerformed(ActionEvent e1){
				setContentPane(mainContainer);
				a.grabKey();
			}
		};
		
		/**
		 * This displays the continue game menu screen where you can insert a name to load a game
		 */
		
		continueGameListener = new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				imageArea.setVisible(false);
				okButton.setVisible(false);
				centerArea.setVisible(true);
				newGame.setVisible(false);
				continueGame.setVisible(false);
				insertName.setVisible(true);
				loadGameButton.setVisible(true);
				okButton.setVisible(false);

				setContentPane(menuPanel);
			}
		};

		/**
		 * This button allows you to load a game
		 */
		loadGameButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				controller.load(centerArea.getText());
				setContentPane(mainContainer);
				gamePause = false;
				refreshScreen2();
				requestFocus();
			}
		};
		
		
		/**
		 * Listener of the button at the win screen ("No, let her die!" button): closes the game
		 */
		noWinButtonListener=new ActionListener(){
			public void actionPerformed(ActionEvent e1){
				displayCredits();
			}
		};

		/**
		 * Listener of the "Main Menu" button at the win screen; goes back to the main menu
		 */
		yesWinButtonListener=new ActionListener(){
			public void actionPerformed(ActionEvent e1){
				displayStartMenu();
			}
		};
		
		
		
		// TODO finish save button listener and load button listener
		saveButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				gamePause = !gamePause;
				controller.save();
				refreshScreen();
				requestFocus();
			}
		};
		
		/**
		 * Button that sets the panel to receive the player's name from the user
		 */
		loadButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				
				setContentPane(menuPanel);
				imageArea.setVisible(false);
				centerArea.setVisible(true);
				insertName.setVisible(true);
				loadGameButton.setVisible(true);
				
			}
		};

		/**
		 * This button displays the prologue of the game
		 */
		startGameListener = new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				String text=new String(centerArea.getText());
				if (text.compareTo("") != 0) {
					a.setName(text);
				}
				else {
					a.setName("Adventurer");
				}
				displayPrologue();
			}
		};

		/**
		 * This is the button that actually shows you the game panel and allows
		 * you to play
		 */
		okButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				titleArea.setVisible(false);
				setContentPane(mainContainer);
			}
		};

		// action listener for RESTART with new dungeon button in menu.
		restarNewDungeonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				gamePause = !gamePause;
				GameGUI view = returnCurrentObject();
				controller = null;
				controller = new Game(view, "Default");
				view.registerController(controller);
				view.registerAdventurer(controller.returnAdventurer());
				view.registerMonster(controller.returnMonsters());

				requestFocus();
				refreshScreen();

			}
		};
		
		restartGame = new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				
				
				GameGUI view = returnCurrentObject();
				controller = null;
				controller = new Game(view, "Default");
				view.registerController(controller);
				view.registerAdventurer(controller.returnAdventurer());
				view.registerMonster(controller.returnMonsters());
				setContentPane(mainContainer);

				requestFocus();
				refreshScreen();
				Sound.setCurrentMusic(Sound.back);
				setContentPane(mainContainer);

			}
		};

		exitListener = new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				System.exit(0);
			}
		};

		/**
		 * All of the components of GUI
		 */

		// TODO Check the size of all containers
		mainContainer = new JPanel();
		dialogBoxOne = new JPanel();
		dialogBoxTwo = new JPanel();
		inventoryPanel = new JPanel();
		gameWindow = new JPanel();
		infoBoxPlayer = new JPanel();
		infoBoxMonster = new JPanel();
		inventoryBox = new JPanel();
		dialogLabelOne = new JLabel("Welcome to the game!");
		dialogLabelTwo = new JLabel("Press arrows to move!");
		gameArea = new JTextArea(dungeon);
		infoLabelPlayer = new JLabel();
		infoLabelMonster = new JLabel();
		inventoryLabel = new JLabel();
		inventoryText = new JTextArea();
		c = new GridBagConstraints();

		cont = new JButton("CONTINUE");
		buttonLoad = new JButton("LOAD");
		buttonSave = new JButton("SAVE");
		restrt = new JButton("RESTART");
		restrtNewDungeon = new JButton("RESTART IN NEW DUNGEON");
		ext = new JButton("EXIT");

		// adding action listeners to buttons.

		cont.addActionListener(continueListener);
		restrt.addActionListener(restartListener);
		ext.addActionListener(exitListener);
		restrtNewDungeon.addActionListener(restarNewDungeonListener);
		buttonLoad.addActionListener(loadButtonListener);
		buttonSave.addActionListener(saveButtonListener);

		/**
		 * Content and settings of main container
		 */

		mainContainer.setLayout(new GridBagLayout());
		mainContainer.setPreferredSize(new Dimension(1280, 720));
		c.gridx = 0; // X grid for dialogBoxOne
		c.gridy = 0; // Y grid for DialogBoxOne
		mainContainer.add(dialogBoxOne, c);
		c.gridx = 0; // X grid for dialogBoxTwo
		c.gridy = 1; // Y grid for dialogBoxTwo
		mainContainer.add(dialogBoxTwo, c);
		c.gridx = 0; // X grid for gameWindow
		c.gridy = 2; // Y grid for gameWindow
		mainContainer.add(gameWindow, c);
		c.gridx = 3; // X grid for inventoryPanel
		c.gridy = 2; // Y grid for inventoryPanel
		mainContainer.add(inventoryPanel, c);
		c.gridx = 0; // X grid for infoBoxPlayer
		c.gridy = 3; // Y grid for infoBoxPlayer
		mainContainer.add(infoBoxPlayer, c);
		c.gridx = 0; // X grid for inventoryBox
		c.gridy = 4; // Y grid for inventoryBox
		mainContainer.add(inventoryBox, c);
		c.gridx = 0; // X grid for infoBoxMonster
		c.gridy = 5; // Y grid for infoBoxMonster
		mainContainer.add(infoBoxMonster, c);

		mainContainer.add(cont);
		cont.setVisible(false);
		cont.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainContainer.add(restrt);
		restrt.setVisible(false);
		restrt.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainContainer.add(restrtNewDungeon);
		restrtNewDungeon.setVisible(false);

		restrtNewDungeon.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainContainer.add(buttonSave);
		buttonSave.setVisible(false);
		buttonSave.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainContainer.add(buttonLoad);
		buttonLoad.setVisible(false);

		buttonLoad.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainContainer.add(ext);
		ext.setVisible(false);
		ext.setAlignmentX(Component.CENTER_ALIGNMENT);

		/**
		 * Content and settings of dialog boxes and labels
		 */

		dialogBoxOne.add(dialogLabelOne);
		dialogBoxTwo.add(dialogLabelTwo);
		dialogBoxOne.setPreferredSize(new Dimension(1280, 40));
		dialogBoxTwo.setPreferredSize(new Dimension(1280, 40));

		/**
		 * Content and settings of game window and gameArea
		 */

		gameWindow.add(gameArea);
		gameWindow.setPreferredSize(new Dimension(980, 560));

		// TODO Determinate the final font and font size

		gameArea.setEditable(false);
		gameArea.setCursor(null);
		gameArea.setOpaque(false);
		gameArea.setFocusable(false);

		Toolkit t = this.getToolkit();
		Dimension d = t.getScreenSize();
		int h = d.height; 
		gameArea.setFont(new Font("Monospaced", Font.PLAIN, h/77));
		/**
		 * Content and settings of inventory
		 */

		inventoryPanel.add(inventoryText);
		inventoryPanel.setPreferredSize(new Dimension(300, 300));
		inventoryText.setEditable(false);
		inventoryText.setCursor(null);
		inventoryText.setOpaque(false);
		inventoryText.setFocusable(false);

		/**
		 * Content and settings for infoBoxPlayer
		 */

		infoBoxPlayer.add(infoLabelPlayer);
		infoBoxPlayer.setPreferredSize(new Dimension(1280, 40));

		/**
		 * Content and settings for infoBoxMonster
		 */

		infoBoxMonster.add(infoLabelMonster);
		infoBoxMonster.setPreferredSize(new Dimension(1280, 40));
		monstersAmount = 10;

		/**
		 * Content and settings of inventory box
		 */

		inventoryBox.add(inventoryLabel);
		inventoryBox.setPreferredSize(new Dimension(1280, 40));

		/**
         *
         */

		setContentPane(mainContainer);
		this.setFocusable(true);

		/**
		 * Listens to keyboard
		 */

		// TODO Shift+movement for fast movement
		this.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				;
				int keyCode;
				char keyChar;
				keyChar = e.getKeyChar();
				keyCode = e.getKeyCode();
				//System.out.println("You typed the key :"+keyCode+"\nWhich is on your system:"+keyChar);
				if (!gamePause) {
					if (keyCode == KeyEvent.VK_SHIFT) {
						shiftIsPressed = true;
					}
					if (!keyDown) {
						if (keyCode == KeyEvent.VK_SPACE) { // Key pressed =
															// space
							keyDown = true;
							refreshScreen();
						} else if (keyCode == KeyEvent.VK_LEFT
								|| keyCode == KeyEvent.VK_H) { // Key pressed =
																// left arrow or
																// H
							if (shiftIsPressed) {
								a.moveLeft();
							} else {
								keyDown = true;
								a.moveLeft();
							}
							refreshScreen();
						} else if (keyCode == KeyEvent.VK_UP
								|| keyCode == KeyEvent.VK_K) { // Key pressed =
																// up arrow or K
							if (shiftIsPressed) {
								a.moveUp();
							} else {
								keyDown = true;
								a.moveUp();
							}
							refreshScreen();
						} else if (keyCode == KeyEvent.VK_RIGHT
								|| keyCode == KeyEvent.VK_L) { // Key pressed =
																// right arrow
																// or L
							if (shiftIsPressed) {
								a.moveRight();
							} else {
								keyDown = true;
								a.moveRight();
							}
							refreshScreen();
						} else if (keyCode == KeyEvent.VK_DOWN
								|| keyCode == KeyEvent.VK_J) { // Key pressed =
																// down arrow or
																// J
							if (shiftIsPressed) {
								a.moveDown();
							} else {
								keyDown = true;
								a.moveDown();
							}
							refreshScreen();
						} else if (keyCode == 93 || keyCode == 33) { // CHEATCODE: view all rooms (PgUp or $ key)
							a.getDungeon().setAllVisible();
							System.out.println("View all rooms cheat!");
							refreshScreen();
						} else if (keyCode == 36 || keyCode == 92) { // CHEATCODE: dungeon level up (diagonal upleft arrow or ` key)
							controller.goUpDungeon();
							System.out.println("Dungeon level up cheat!");
							refreshScreen();
						} else if (keyCode == 127 || keyChar == 'ù') { // CHEATCODE: add exp (del or ù key) (keycode 222 for ù Mac)
							a.setExp(a.getExp() * 2 + 500);
							System.out.println("Player's experience cheat!");
							refreshScreen();
						} else if (keyCode == 91 || keyCode == 35) { // CHEATCODE: get dragon's key (end or ^ key)
							System.out.println("Grab dragon's key cheat!");
							a.grabKey();
							refreshScreen();
						} else if (keyCode == KeyEvent.VK_EQUALS || keyCode == 47) { // CHEATCODE: dungeon level -1 (end or ^ key)
							System.out.println("Dungeon level down cheat!");
							controller.goDownDungeon();
							refreshScreen();
						} else if (keyCode == KeyEvent.VK_B) { // SAVING GAME (B
																// key)
							controller.save();
							refreshScreen();
						} else if (keyCode == KeyEvent.VK_N) { // LOADING GAME
																// (N key)
							controller.load(controller.returnAdventurer()
									.getName());
							refreshScreen();
						} else if (keyCode == KeyEvent.VK_I) { // Key pressed =
																// I for
																// inventory
							openInventory();
						} else if (keyCode == KeyEvent.VK_D) { // Key pressed D
																// for
																// destroying
																// items
							setDestroy();
						} else if (keyCode == KeyEvent.VK_1 || keyChar == '&') { // Key pressed =
																// 1 for potions
							usePotion(1);
						} else if (keyCode == KeyEvent.VK_2 || keyChar == 'é') { // Key pressed =
																// 2 for
																// equipping
							equipItem(2);
						} else if (keyCode == KeyEvent.VK_3 || keyChar == '"') { // Key pressed =
																// 3 for
																// equipping
							equipItem(3);
						} else if (keyCode == KeyEvent.VK_4 || keyChar == '\'') { // Key pressed =
																// 4 for
																// equipping
							equipItem(4);
						} else if (keyCode == KeyEvent.VK_5 || keyChar == '(') { // Key pressed =
																// 5 for
																// equipping
							equipItem(5);
						} else if (keyCode == KeyEvent.VK_6 || keyChar == '-') { // Key pressed =
																// 6 for
																// equipping
							equipItem(6);
						} else if (keyCode == KeyEvent.VK_7 || keyChar == 'è') { // Key pressed =
																// 7 for
																// equipping
							equipItem(7);
						} else if (keyCode == KeyEvent.VK_8 || keyChar == '_') { // Key pressed =
																// 8 for
																// equipping
							equipItem(8);
						} else if (keyCode == KeyEvent.VK_9 || keyCode == 'à') { // Key pressed =
																// 9 for
																// equipping
							equipItem(9);
						} else if (keyChar == '<') { // Key pressed = < for
														// going down lvl
							if (controller.returnDungeon().getBoxes()[a
									.getPosX()][a.getPosY()].getRealRep() == '%') {
								if(a.hasKey() && controller.getPosDungeon()==0)
									displayWinMenu();
								
								controller.goDownDungeon();
								refreshScreen();
							}
						} else if (keyChar == '>') { // Key pressed = > for
														// going up lvl
							if (controller.returnDungeon().getBoxes()[a
									.getPosX()][a.getPosY()].getRealRep() == '%') {
								if(a.hasKey() && controller.getPosDungeon()==0){
									displayWinMenu();}
								else{
								controller.goUpDungeon();
								refreshScreen();
								}
							}
						}

					}
					if (keyCode == KeyEvent.VK_W) { // Key pressed = W for
													// unequip weapon
						a.unequip("Weapon");
						refreshInvText();
					}
					if (keyCode == KeyEvent.VK_A) { // Key pressed = A for
													// unequip armor
						a.unequip("Armor");
						refreshInvText();
					}
					refreshScreen2();

				}
				if (keyCode == KeyEvent.VK_P) { // Key pressed = P for
					// pause
					gamePause = !gamePause;
					refreshScreen2();
				}
			}

			public void keyReleased(KeyEvent e) {
				int keyCode;
				keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_SHIFT) {
					shiftIsPressed = false;
				}
				if (keyCode == KeyEvent.VK_K || keyCode == KeyEvent.VK_L
						|| keyCode == KeyEvent.VK_H) {
					keyDown = false;
				}
				if (keyCode == KeyEvent.VK_J || keyCode == KeyEvent.VK_DOWN
						|| keyCode == KeyEvent.VK_LEFT) {
					keyDown = false;
				}
				if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_UP) {
					keyDown = false;
				}
			}

			public void keyTyped(KeyEvent e) { // Do we need this? Can prolly
												// leave blank
			}
		});
		pack();
		setVisible(true);

	}

	/**
	 * This method introduces Game and GameGUI to each other
	 */

	public void registerController(Game controller) {
		this.controller = controller;
	}

	public Game getController() {
		return controller;
	}

	/**
	 * Registers the adventurer to GameGUI
	 * 
	 * @param a
	 *            our adventurer
	 */

	public void registerAdventurer(Adventurer a) {
		this.a = a;
	}

	/**
	 * This method introduces the monsters ArrayList for GameGUI
	 * 
	 * @param monsters
	 *            monsters of the current dungeon level
	 */

	public void registerMonster(ArrayList<Monster> monsters) {
		this.monsters = monsters;
	}

	/**
	 * Gets the nearest monster from the player if there is any
	 * 
	 * @return Nearest monster from the player's position
	 */
	public int nearestMonster() {
		int ax = Math.abs(this.a.getPosX());
		int ay = Math.abs(this.a.getPosY());
		int mx = 0, my = 0;
		double dist;
		int res = -1;

		// Distance formula : sqrt( (mx-ax)^2+(my-ay)^2 )
		Box[][] currentBoxes = controller.returnDungeon().getBoxes();
		if (this.monsters.size() > 0) {
			mx = this.monsters.get(0).getPosX();
			my = this.monsters.get(0).getPosY();
			dist = Math.sqrt((mx - ax) * (mx - ax) + (my - ay) * (my - ay));
			// System.out.println("Monster 0:"+currentBoxes[monsters.get(0).getPosX()][monsters.get(0).getPosY()].isVisible());
			if (currentBoxes[monsters.get(0).getPosX()][monsters.get(0)
					.getPosY()].isVisible())
				res = 0;
			for (int i = 1; i < monsters.size(); i++) {
				// System.out.println("Monster "+i+":"+currentBoxes[monsters.get(i).getPosX()][monsters.get(i).getPosY()].isVisible());
				if ((currentBoxes[monsters.get(i).getPosX()][monsters.get(i)
						.getPosY()].isVisible())
						&& dist > Math
								.sqrt((this.monsters.get(i).getPosX() - ax)
										* (this.monsters.get(i).getPosX() - ax)
										+ (this.monsters.get(i).getPosY() - ay)
										* (this.monsters.get(i).getPosY() - ay))) {
					res = i;
					dist = Math.sqrt((this.monsters.get(i).getPosX() - ax)
							* (this.monsters.get(i).getPosX() - ax)
							+ (this.monsters.get(i).getPosY() - ay)
							* (this.monsters.get(i).getPosY() - ay));
				}
			}
		}
		if (a.getFightStatus()) {
			for (int i = 0; i < monsters.size(); i++) {
				if (currentBoxes[monsters.get(i).getPosX()][monsters.get(i)
						.getPosY()].isVisible()
						&& a.getTarget() == monsters.get(i)) {
					res = i;
					break;
				}
			}
		}

		return res;
	}

	private void setPauseVisibility() {

		cont.setVisible(gamePause);
		ext.setVisible(gamePause);
		restrt.setVisible(gamePause);
		restrtNewDungeon.setVisible(gamePause);
		buttonLoad.setVisible(gamePause);
		buttonSave.setVisible(gamePause);

		gameWindow.setVisible(!gamePause);
		dialogBoxOne.setVisible(!gamePause);
		dialogBoxTwo.setVisible(!gamePause);
		dialogLabelOne.setVisible(!gamePause);
		dialogLabelTwo.setVisible(!gamePause);
		infoBoxMonster.setVisible(!gamePause);
		infoBoxPlayer.setVisible(!gamePause);
		infoLabelMonster.setVisible(!gamePause);
		infoLabelPlayer.setVisible(!gamePause);
		inventoryBox.setVisible(!gamePause);
		inventoryPanel.setVisible(!gamePause);
		inventoryText.setVisible(!gamePause);
	}

	/**
	 * Refreshes the GUI screen after every move/action the player makes.
	 */
	// TODO Add dialog label into refreshScreen
	public void refreshScreen() {
		if (!gamePause) {
			setPauseVisibility();

			if (this.a.isDead()) {
				// this.gameOverPanel.setVisible(true);
				displayDeathScreen();
				this.gameWindow.setVisible(false);
				Sound.back.stop();
				Sound.boss.stop();
				Sound.setCurrentMusic(Sound.end);
			} else {
				dungeon = controller.getDungeon(a);
				infoLabelPlayer.setText(controller.getAdventurer());
				inventoryLabel.setText(a.getEquipment() + "Dungeon level: "
						+ controller.getPosDungeon());
				if (isInvOpen && !destroying) {
					inventoryText.setText(a.printInventory());
					inventoryPanel.setVisible(true);
				} else if (isInvOpen) {
					inventoryText.setText(a.printDestroyMenu());
					inventoryPanel.setVisible(true);
				} else {
					inventoryPanel.setVisible(false);
				}
				if (this.monsters.size() > 0 && nearestMonster() != -1) {
					infoLabelMonster.setText(controller.getMonster(monsters
							.get(nearestMonster())));
				} else {
					infoLabelMonster.setText("No enemy in sight!");
				}
				gameArea.setText(dungeon);
				for (int i = 0; i < monsters.size(); i++)
					if (monsters.get(i).getLifePoints() > 0) {
						monsters.get(i).checkRange(a);
					} else if (monsters.get(i).getLifePoints() <= 0) {
						monsters.get(i).dropLoot();
						a.giveExp(monsters.get(i));
						monsters.get(i).isDead();
						monsters.remove(i);
						if(controller.getPosDungeon()==25)
							displayPartialWin();
					}
				Adventurer.checkExp(a);
			}
		} else {
			setPauseVisibility();
		}

		// Sounds
		Sound.setCurrentMusic(Sound.currentMusic);

	}

	public void refreshScreen2() {
		if (!gamePause) {
			setPauseVisibility();

			if (this.a.isDead()) {
				displayDeathScreen();
				// this.gameOverPanel.setVisible(true);
				this.gameWindow.setVisible(false);
				Sound.back.stop();
				Sound.boss.stop();
				Sound.end.play();
			} else {
				dungeon = controller.getDungeon(a);
				infoLabelPlayer.setText(controller.getAdventurer());
				inventoryLabel.setText(a.getEquipment() + "Dungeon level: "
						+ controller.getPosDungeon());
				if (isInvOpen && !destroying) {
					inventoryText.setText(a.printInventory());
					inventoryPanel.setVisible(true);
				} else if (isInvOpen) {
					inventoryText.setText(a.printDestroyMenu());
					inventoryPanel.setVisible(true);
				} else {
					inventoryPanel.setVisible(false);
				}
				if (this.monsters.size() > 0 && nearestMonster() != -1) {
					infoLabelMonster.setText(controller.getMonster(monsters
							.get(nearestMonster())));
				} else {
					infoLabelMonster.setText("No enemy in sight!");
				}
				gameArea.setText(dungeon);
				for (int i = 0; i < monsters.size(); i++) {
					if (monsters.get(i).getLifePoints() <= 0) {
						monsters.get(i).dropLoot();
						a.giveExp(monsters.get(i));
						monsters.get(i).isDead();
						monsters.remove(i);
						if(controller.getPosDungeon()==25)
							displayPartialWin();
					}
				}
				Adventurer.checkExp(a);
			}
		} else {
			setPauseVisibility();
		}

		// Sounds
		Sound.setCurrentMusic(Sound.currentMusic);

	}

	public void requestFocus() // request focus to main window to be able to use
								// KeyEventListener
	{
		this.requestFocusInWindow();
	}

	/**
	 * Opens the inventory/destroy menu next to game area
	 */

	private void openInventory() {
		if (isInvOpen && !destroying) {
			inventoryText.setText(a.printInventory());
			inventoryPanel.setVisible(false);
			isInvOpen = false;
		} else if (isInvOpen && destroying) {
			inventoryText.setText(a.printDestroyMenu());
			inventoryPanel.setVisible(false);
			isInvOpen = false;
		} else if (!isInvOpen && destroying) {
			inventoryText.setText(a.printDestroyMenu());
			inventoryPanel.setVisible(true);
			isInvOpen = true;
		} else {
			inventoryText.setText(a.printInventory());
			inventoryPanel.setVisible(true);
			isInvOpen = true;
		}
	}

	/**
	 * Changes the inventory text between inventory and destroy
	 */

	private void setDestroy() {
		if (isInvOpen && !destroying) {
			destroying = true;
			inventoryText.setText(a.printDestroyMenu());
		} else if (isInvOpen) {
			destroying = false;
			inventoryText.setText(a.printInventory());
		}
	}

	/**
	 * Uses an health potion
	 * 
	 * @param invNumber
	 *            is the slot in inventory where the potion is being used from
	 */

	private void usePotion(int invNumber) {
		if (isInvOpen && !destroying) {
			a.usePotion(invNumber);
			inventoryText.setText(a.printInventory());
			inventoryLabel.setText(a.getEquipment() + "Dungeon level: "
					+ controller.getPosDungeon());
			infoLabelPlayer.setText(controller.getAdventurer());
		}
		if (isInvOpen && destroying) {
			a.throwAway(invNumber);
			destroying = false;
			inventoryText.setText(a.printInventory());
		}
	}

	/**
	 * Equips an item from inventory
	 * 
	 * @param invNumber
	 *            is the slot in inventory where the item is being equipped from
	 */

	private void equipItem(int invNumber) {
		if (isInvOpen && !destroying) {
			equipSuccesfull = a.usePotion(invNumber);
			if (!equipSuccesfull)
				a.equip(invNumber);
			inventoryText.setText(a.printInventory());
			inventoryLabel.setText(a.getEquipment() + "Dungeon level: "
					+ controller.getPosDungeon());
			infoLabelPlayer.setText(controller.getAdventurer());
		}
		if (isInvOpen && destroying) {
			a.throwAway(invNumber);
			destroying = false;
			inventoryText.setText(a.printInventory());
		}
	}

	/**
	 * Refreshes the inventory text
	 */

	public void refreshInvText() {
		if (!destroying) {
			inventoryText.setText(a.printInventory());
		} else {
			inventoryText.setText(a.printDestroyMenu());
		}
	}

	/*
	 * return link to current object
	 */

	private GameGUI returnCurrentObject() {
		return this;
	}

	// START MENU METHODS

	/**
	 * Displays the starting menu panel
	 */
	public void displayStartMenu() {
		setContentPane(menuPanel);
	}

	
	/**
	 * Sets up the formatted text area for the player's name input
	 */
	public void askPlayer() {
		this.centerArea.setText("");
		this.centerArea.setEditable(true);
		this.centerArea.setFocusable(true);
	}

	/**
	 * Gets a string from the text area
	 * @return the string written in the text area at that moment
	 */
	public String getNameLabel() {
		String s = this.centerArea.getText();
		return s;
	}

	/**
	 * This method changes the color of the menu items and adds the keylisteners to buttons
	 * @param policy the wanted color for the foreground
	 * @param landscape the wanted color for the background
	 */
	public void changeMenuColor(Color policy, Color landscape) {

		this.menuPanel.setBackground(landscape);
		this.menuPanel.setForeground(policy);
		this.menuPanel.setOpaque(true);
		this.gameOverPanel.setBackground(landscape);
		this.gameOverPanel.setForeground(policy);
		this.gameOverPanel.setOpaque(true);
		this.imageArea.setBackground(landscape);
		this.imageArea.setForeground(policy);
		this.imageArea.setOpaque(true);
		this.titleArea.setBackground(landscape);
		this.titleArea.setForeground(policy);
		this.titleArea.setOpaque(true);
		this.menuPanel.setBackground(landscape);
		this.menuPanel.setForeground(policy);
		this.menuPanel.setOpaque(true);
		this.insertName.setBackground(landscape);
		this.insertName.setForeground(policy);
		this.insertName.setOpaque(true);
		this.insertName.setFont(new Font("Monospaced", Font.PLAIN, 14));
		this.skullArea.setBackground(landscape);
		this.skullArea.setForeground(policy);
		this.skullArea.setOpaque(true);
		this.winGameArea.setBackground(landscape);
		this.winGameArea.setForeground(policy);
		this.winGameArea.setOpaque(true);
		this.winPanel.setBackground(landscape);
		this.winGameArea.setFocusable(false);
		this.partialWinArea.setBackground(landscape);
		this.partialWinArea.setForeground(policy);
		this.partialWinArea.setOpaque(true);
		this.partialWinArea.setFocusable(false);
		this.partialWinPanel.setBackground(landscape);
		this.creditsPanel.setBackground(landscape);
		this.creditsArea.setBackground(landscape);
		this.creditsArea.setForeground(policy);
		this.creditsArea.setFocusable(false);
		
		newGame.addActionListener(newGameListener);
		startGame.addActionListener(startGameListener);
		okButton.addActionListener(okButtonListener);
		continueGame.addActionListener(continueGameListener);
		loadGameButton.addActionListener(loadGameButtonListener);
		noWinButton.addActionListener(noWinButtonListener);
		yesWinButton.addActionListener(yesWinButtonListener);
		letsGoButton.addActionListener(letsGoButtonListener);
		killSisterButton.addActionListener(noWinButtonListener);
		creditsExit.addActionListener(exitListener);
		}

	/**
	 * This method is used to display the prologue menu (story menu)
	 */
	public void displayPrologue() {
		okButton.setVisible(true);
		imageArea.setBounds(50, 30, 700, 400);
		titleArea.setBounds(470, 200, 900, 900);
		titleArea
				.setText("       .         .      /\\      .:  *       .          .              .\n"
						+ "                 *    .'  `.      .     .     *      .                  .\n"
						+ "  :             .    /      \\  _ .________________  .                    .\n"
						+ "       |            `.+-~~-+.'/.' `.^^^^^^^^\\~~~~~\\.                      .\n"
						+ " .    -*-   . .       |u--.|  /     \\~~~~~~~|~~~~~|\n"
						+ "       |              |   u|.'       `.' '  |' ' '|                        .\n"
						+ "    :            .    |.u-./ _..---.._ \\' ' | ' ' |\n"
						+ "   -*-            *   |    ~-|U U U U|-~____L_____L_                      .\n"
						+ "    :         .   .   |.-u.| |..---..|'//// ////// /\\       .            .\n"
						+ "          .  *        |u   | |       |// /// // ///==\\     / \\          .\n"
						+ " .          :         |.--u| |..---..|//////~\\////====\\   /   \\       .\n"
						+ "      .               | u  | |       |~~~~/\\u |~~|++++| .`+~~~+'  .\n"
						+ "                      |.-|~U~U~|---..|u u|u | |u ||||||   |  U|\n"
						+ "                   /~~~~/-\\---.'     |===|  |u|==|++++|   |   |\n"
						+ "          aaa      |===| _ | ||.---..|u u|u | |u ||HH||U~U~U~U~|        aa@@@@\n"
						+ "     aaa@@@@@@aa   |===|||||_||      |===|_.|u|_.|+HH+|_/_/_/_/aa    a@@@@@@@@\n"
						+ " aa@@@@@@@@@@@@@@a |~~|~~~~\\---/~-.._|--.---------.~~~`.__ _.@@@@@@a    ~~~~~~\n"
						+ "   ~~~~~~    ~~~    \\_\\\\ \\  \\/~ //\\  ~,~|  __   | |`.   :||  ~~~~\n"
						+ "                     a\\`| `   _//  | / _| || |  | `.'  ,''|     aa@@@@@@@a\n"
						+ " aaa   aaaa       a@@@@\\| \\  //'   |  // \\`| |  `.'  .' | |  aa@@@@@@@@@@@@@a\n"
						+ "@@@@@a@@@@@@a      ~~~~~ \\\\`//| | \\ \\//   \\`  .-'  .' | '/      ~~~~~~~  ~~\n"
						+ "@..W.&.B...@@@@a          \\// |.`  ` ' /~  :-'   .'|  '/~aa\n"
						+ "~~~~~~~ ~~~~~~         a@@@|   \\\\ |   // .'    .'| |  |@@@@@@a\n"
						+ "                    a@@@@@@@\\   | `| ''.'     .' | ' /@@@@@@@@@a\n");
		centerArea.setVisible(false);
		insertName.setVisible(false);
		titleArea.setVisible(true);
		startGame.setVisible(false);
		imageArea
				.setText("Hello "
						+ a.getName()
						+ "!\n"
						+ "Welcome to the rogue-like project, made by students of ERASMUS ViOpe program!\n"
						+ "Are you ready for this epic quest?\n"
						+ "Okay so, "
						+ a.getName()
						+ ", you're now a rogue.\n"
						+ "Not much of a rogue for instance, but you'll have to get some skills quickly,\n"
						+ "at least if you want to survive...\n\n"
						+ "The dragon captured your beloved little sister, "
						+ controller.getSisterName()
						+ ", several weeks ago,\n"
						+ "and you finally found the dungeon in which \nthis little basterd is hiding.\n\n"
						+ "You must reach the 25th level of the dungeon \nto beat  him and get her out of here.\n"
						+ "Do you think you can handle it ?\n");
		imageArea.setVisible(true);
		imageArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
	}

	
	/**
	 * This method is used to display the death screen
	 */
    public void displayDeathScreen(){
		    
		  	loadGame.setBounds(600, 450, 130, 30);
			restart.setBounds(600, 490, 130, 30);
			exit.setBounds(600, 530, 130, 30);
			
		    loadGame.setVisible(true);
		    restart.setVisible(true);
		    exit.setVisible(true);
	        
	        skullArea.setBounds(25, 100, 350, 500);
	        ticketArea.setBounds(450, 100, 800, 300);
	        
	        skullArea.setFocusable(false);
	        ticketArea.setFocusable(false);
	        ticketArea.setText("I don't really know where it hurts, but at least I know it hurts a lot. Too much maybe.\n"+
	        		"I'll give you a gift, as a thanks for playing:\n\n"+
	        		"+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+\n"+
	        		"|| (1)          OFFICIAL RIDE NOTE       ONE-WAY ||\n"+
	        		"||                      /\\/\\                     ||\n"+
	        		"||      #1       ___--- WMEQE ---___   (!)       ||\n"+
	        		"||                 -----____-----                ||\n"+
	        		"|| ONE-WAY     TICKET TO THE GRAVEYARD       (1) ||\n"+
	        		"+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+:+\n"
);
	        skullArea.setText("                      _,.---,---.,_\n"+ 
	                "                  ,;~'             '~;,\n"+ 
	                "                ,;                     ;,\n"+      
	                "               ;                         ;\n"+ 
	                "              ,'                          '\n"+
	                "             ,;                           ;\n"+
	                "             ; ;      .           .     ; ;\n"+
	                "             | ;   ______       ______  ; |\n"+
	                "             |  '/~'     ~' . '~     '~\\' |\n"+
	                "             |  ~  ,-~~~^~, | ,~^~~~-,  ~ |\n"+
	                "             |    |        }:{        |   |\n"+
	                "              |   l       / | \\       !  |\n"+
	                "              .~  (__,.--' .^. '--.,__)  ~.\n"+ 
	                "              |    ----;' / | \\ `;-----  |\n"+
	                "               \\__.       \\/^\\/       .__/\n"+  
	                "                V| \\                 / |V\n"+  
	                "                 | |T~\\___!___!___/~T| |\n"+  
	                "                 | |`IIII_I_I_I_IIII'| |\n"+  
	                "                 |  \\,III I I I III,/  |\n"+ 
	                "                  \\   `~~~~~~~~~~'    /\n"+
	                "                    \\   .       .   /\n"+
	                "                      \\.    ^    ./\n"+   
	                "                        ^~~~^~~~^\n");
	        skullArea.setVisible(true);
	        ticketArea.setVisible(true);
	        setContentPane(gameOverPanel);
	        restart.addActionListener(restartGame);
	        loadGame.addActionListener(continueGameListener);
	        exit.addActionListener(exitListener);
	        
	    }
	  


	/**
	 * Sets up and display the final winning screen 
	 */
	public void displayWinMenu(){
		setContentPane(winPanel);
		this.winGameArea.setText("   ___                                   /\\                                   ___\n"+   
				" /     \\                              ..(  )..                              /     \\\n"+ 
				"( *hug* )                            '  |\\/|  '                            ( *hug* )\n"+
				"|\\.___________________________________./    \\.___________________________________./|\n"+
				"|                                                                                  |\n"+
				"|                               Officially completed                               |\n"+
				"|    ________            _       __           __    __        __  ___           __ |\n"+
				"|   /_  __/ /_  ___     | |     / /___  _____/ /___/ /____   /  |/  /___  _____/ /_|\n"+
				"|    / / / __ \\/ _ \\    | | /| / / __ \\/ ___/ / __  / ___/  / /|_/ / __ \\/ ___/ __/|\n"+
				"|   / / / / / /  __/    | |/ |/ / /_/ / /  / / /_/ (__  )  / /  / / /_/ (__  ) /_  |\n"+
				"|  /_/ /_/ /_/\\___/     |__/|__/\\____/_/  /_/\\__,_/____/  /_/  /_/\\____/____/\\__/  |\n"+
				"|                                                                                  |\n"+
				"|      ______      _         ____                  __     ______                   |\n"+
				"|     / ____/___  (_)____   / __ \\__  _____  _____/ /_   / ____/   _____  _____    |\n"+
				"|    / __/ / __ \\/ / ___/  / / / / / / / _ \\/ ___/ __/  / __/ | | / / _ \\/ ___/    |\n"+
				"|   / /___/ /_/ / / /__   / /_/ / /_/ /  __(__  ) /_   / /___ | |/ /  __/ /        |\n"+
				"|  /_____/ .___/_/\\___/   \\___\\_\\__,_/\\___/____/\\__/  /_____/ |___/\\___/_/         |\n"+
				"|       /_/                                                                        |\n"+
				"|                                                                                  |\n"+
				"|                    You finally found your beloved little sister                  |\n"+
				"|                        She's weak but you saved her in time.                     |\n"+
				"|                             It's time to go back home                            |\n"+
				"|                                    It's over.                                    |\n"+
				"|  ___________________________________        ____________________________________ |\n"+
				"|/                                     \\    /                                     \\|\n"+
				"( *hug* )                               |/\\|                               ( *hug* )\n"+
				" \\ ___ /                             '..(  )..'                             \\ ___ /\n"+
				"                                         \\/\n");
		winGameArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
	}
	
	
	
	/**
	 * Sets up and display the partial win menu (the one displayed after the player kills the dragon)
	 */
	public void displayPartialWin(){
		setContentPane(partialWinPanel);
		this.partialWinArea.setText("You fought with all you strength, you're wounded and exhausted\n"+
				"     but the dragon is finally lying dead before you.\n\n"+
				"                        It's over.\n\n\n\n\n"+
				"                        Or is it...?\n\n"+
				"As you look around the room for your little sister you notice\n"+
				"                      that she's not there.\n"+
				"She must be somewhere in the dungeon waiting for her brother to\n"+
				"                        rescue her.\n"+
				"There's a key on the floor, use it to go through the dungeon again\n\n"+
				"             8 8 8 8                     ,ooo.\n"+
				"             8a8 8a8                    oP   ?b\n"+
				"            d888a888zzzzzzzzzzzzzzzzzzzz8     8b\n"+
				"             `''^'''                    ?o___oP'\n\n"+
				"           Decide what you want to do adventurer:\n"+
				"           do you really want to save your sister?\n");
		this.partialWinArea.setFont(new Font("Monospaced",Font.PLAIN,14));
		this.letsGoButton.setBounds(570,625,130,30);
		this.killSisterButton.setBounds(570,665,130,30);
	}
	
	
	/**
	 * Set up the credits panel and displays it
	 */
	public void displayCredits(){
		setContentPane(creditsPanel);
		creditsArea.setBounds(300,30,900,600);
		creditsArea.setFont(new Font("Monospaced",Font.PLAIN,14));
		creditsArea.setText("   ___                                   /\\                                   ___   \n"+
" /     \\                              ..(  )..                              /     \\\n"+
"( *hug* )                            '  |\\/|  '                            ( *hug* )\n"+
"|\\.___________________________________./    \\.___________________________________./|\n"+
"|                                                                                  |\n"+
"|                            Erasmus Project:Rogue-Like                            |\n"+
"|                        'The World's Most Epic Quest Ever'                        |\n"+
"|                                                                                  |\n"+
"|                                       Team                                       |\n"+
"|                                   Whisky & Beer                                  |\n"+
"|                                                                                  |\n"+
"|                              Jordan Becker (Lead developer)                      |\n"+
"|                           Robin Kebaili (aka Sound genius)                       |\n"+
"|                         Théo Gerriet (aka Corridor’s master)                     |\n"+
"|                            Vittorio Toma (aka Pasta lover)                       |\n"+
"|                         Francesco Merati (aka The short one)                     |\n"+
"|                               Marco Taddei (aka Bug ﬁnder)                       |\n"+
"|                      Anton Machkasov (aka From Russia with love)                 |\n"+
"|                            Mika Oksanen (aka The Local One)                      |\n"+
"|                                                                                  |\n"+
"|                                                                                  |\n"+
"|                                                                                  |\n"+
"|                                                                                  |\n"+
"|                           We hope you enjoyed the game                           |\n"+
"|                                Thanks for playing!                               |\n"+
"|  ___________________________________        _____________________________________|\n"+
"|/                                     \\    /                                     \\|\n"+
"( *hug* )                               |/\\|                               ( *hug* )\n"+
" \\ ___ /                             '..(  )..'                             \\ ___ / \n"+
"                                         \\/ \n");
	}
	
}
