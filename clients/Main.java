package clients;

import clients.backDoor.BackDoorController;
import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;
import clients.cashier.BetterCashierModel;
import clients.cashier.CashierController;
import clients.cashier.CashierModel;
import clients.cashier.CashierView;
import clients.customer.CustomerController;
import clients.customer.CustomerModel;
import clients.customer.CustomerView;
import clients.packing.PackingController;
import clients.packing.PackingModel;
import clients.packing.PackingView;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;
import javax.swing.*;
import java.awt.*;

/**
 * Starts all the clients (user interface)  as a single application.
 * Good for testing the system using a single application.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 * @author  Shine University of Brighton
 * @version year-2024
 */

public class Main
{
	
	// Add references to the JFrames as member variables
	  private JFrame customerWindow;
	  private JFrame cashierWindow;
	  private JFrame packingWindow;
	  private JFrame backDoorWindow;
	  
  public static void main (String args[])
  {
    new Main().begin();
  }

  /**
   * Starts the system (Non distributed)
   */
  public static final Color PURPLE = new Color(133, 61, 77); 
  public static final Color PINK = new Color(191, 147, 161); 
  
  public void begin()
  {
    //DEBUG.set(true); /* Lots of debug info */
    MiddleFactory mlf = new LocalMiddleFactory();  // Direct access
    
    //show the Welcome screen first
    showWelcomeScreen(mlf);
  }
 //displays the Welcome screen and handles the transition to the main application.
  public void showWelcomeScreen(MiddleFactory mlf)
  {
    JFrame welcomeWindow = new JFrame();
    welcomeWindow.setTitle("Welcome Screen");
    welcomeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    welcomeWindow.setSize(800, 600);
    welcomeWindow.setLocationRelativeTo(null); // Center the window on screen
    

    JPanel panel = new JPanel(null);
    panel.setLayout(null); // Disable layout manager
    welcomeWindow.add(panel);
    placeWelcomeComponents(panel, mlf, welcomeWindow);

    welcomeWindow.setVisible(true); // Show welcome screen
  }
 //adds components to the Welcome screen.
  private void placeWelcomeComponents(JPanel panel, MiddleFactory mlf, JFrame welcomeWindow)
  {
	int panelWidth = 800; // Set to frame's width
	int panelHeight = 600; // Set to frame's height

    
    JLabel welcomeLabel = new JLabel("Welcome to the Ministore :)");
    welcomeLabel.setForeground(Color.BLACK); // Change text color to dark blue
    welcomeLabel.setFont(new Font("Algerian", Font.BOLD, 24)); // Change font style and size
    int labelWidth = 380; // Approximate width of the label
    int labelHeight = 40; // Approximate height of the label
    int labelX = (panelWidth - labelWidth) / 2;
    int labelY = (panelHeight - labelHeight) / 2 - 100; // Slight upward offset
    welcomeLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
    panel.setBackground(PURPLE);
    panel.add(welcomeLabel);

    JButton startButton = new JButton("Start Purchase");
    startButton.setFont(new Font("Algerian", Font.BOLD, 24)); // Change font style and size
    startButton.setForeground(PURPLE);
    startButton.setBackground(PINK);
    int buttonWidth = 280; // Approximate width of the button
    int buttonHeight = 50; // Approximate height of the button
    int buttonX = (panelWidth - buttonWidth) / 2;
    int buttonY = labelY + 80; // Position slightly below the label
    startButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
    startButton.addActionListener(e -> {
      welcomeWindow.setVisible(false); // Hide welcome screen
      beginMainApp(mlf); // Start the main application
    });
    panel.add(startButton);
  }
  
  //displays the Goodbye screen and handles the exit action.
  public void showGoodbyeScreen(MiddleFactory mlf)
  {
    JFrame goodbyeWindow = new JFrame();
    goodbyeWindow.setTitle("Goodbye Screen");
    goodbyeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    goodbyeWindow.setSize(800, 600);
    goodbyeWindow.setLocationRelativeTo(null); // Center the window on screen

    JPanel panel = new JPanel(null);
    goodbyeWindow.add(panel);
    placeGoodbyeComponents(panel, goodbyeWindow, mlf);

    goodbyeWindow.setVisible(true); // Show goodbye screen
  }
   //adds components to the Goodbye screen.
  private void placeGoodbyeComponents(JPanel panel, JFrame goodbyeWindow, MiddleFactory mlf)
  {
	int panelWidth = 800; // Set to frame's width
	int panelHeight = 600; // Set to frame's height
    
    JLabel goodbyeLabel = new JLabel("Thank you for shopping!");
    goodbyeLabel.setForeground(Color.BLACK); // Change text color to dark blue
    goodbyeLabel.setFont(new Font("Algerian", Font.BOLD, 24)); // Change font style and size
    int labelWidth = 400; // Approximate width of the label
    int labelHeight = 40; // Approximate height of the label
    int labelX = (panelWidth - labelWidth) / 2;
    int labelY = (panelHeight - labelHeight) / 2 - 100; // Slight upward offset
    goodbyeLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
    panel.setBackground(PURPLE);
    panel.add(goodbyeLabel);

    JButton exitButton = new JButton("Exit");
    exitButton.setFont(new Font("Algerian", Font.BOLD, 24)); // Change font style and size
    exitButton.setForeground(PURPLE);
    exitButton.setBackground(PINK);
    int buttonWidth = 130; // Approximate width of the button
    int buttonHeight = 50; // Approximate height of the button
    int buttonX = (panelWidth - buttonWidth) / 2;
    int buttonY = labelY + 80; // Position slightly below the label
    exitButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
    exitButton.addActionListener(e -> System.exit(0)); // Exit the application
    panel.add(exitButton);
    
    JButton backButton = new JButton("Back");//button to switch to back to the previous screen
    backButton.setFont(new Font("Algerian", Font.BOLD, 24)); // Change font style and size
    backButton.setForeground(PURPLE);
    backButton.setBackground(PINK);
    backButton.setBounds(buttonX - 150, buttonY, buttonWidth, buttonHeight); //positioned to the left of the Exit button
    backButton.addActionListener(e -> {
        goodbyeWindow.setVisible(false); // Hide screen
        beginMainApp(mlf); // Start the main application
      });
    panel.add(backButton);
  }
  
  public void beginMainApp(MiddleFactory mlf)
  {
    // Start all the client GUIs
    startCustomerGUI_MVC(mlf);
    startCashierGUI_MVC(mlf);
    startCashierGUI_MVC(mlf);
    startPackingGUI_MVC(mlf);
    startBackDoorGUI_MVC(mlf);
    startBackDoorGUI_MVC(mlf);
  }
  
//Start the Customer client, -search product
  public JFrame startCustomerGUI_MVC(MiddleFactory mlf)
  {
    JFrame window = new JFrame();
    window.setTitle("Customer Client MVC");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Dimension pos = PosOnScrn.getPos();
    
    CustomerModel model = new CustomerModel(mlf);
    CustomerView view = new CustomerView(window, mlf, pos.width, pos.height);
    CustomerController cont = new CustomerController(model, view);
    view.setController(cont);
    view.setModel(model);
    
    model.addListener(view);
    window.setVisible(true); // Start Screen
    return window; // Return the JFrame reference
  }
  
  //Start the cashier client - customer check stock, buy product
   
  public JFrame startCashierGUI_MVC(MiddleFactory mlf)
  {
    JFrame window = new JFrame();
    window.setTitle("Cashier Client MVC");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Dimension pos = PosOnScrn.getPos();
    
    BetterCashierModel model = new BetterCashierModel(mlf);
    CashierView view = new CashierView(window, mlf, pos.width, pos.height);
    CashierController cont = new CashierController(model, view);
    view.setController(cont);
    
    model.addObserver(view);
    window.setVisible(true); // Make window visible
    model.askForUpdate(); // Initial display
    return window; // Return the JFrame reference
  }

  //Start the Packing client - for warehouse staff to pack the bought order for customer, one order at a time
 
  public JFrame startPackingGUI_MVC(MiddleFactory mlf)
  {
    JFrame window = new JFrame();
    window.setTitle("Packing Client MVC");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Dimension pos = PosOnScrn.getPos();
    
    PackingModel model = new PackingModel(mlf);
    PackingView view = new PackingView(window, mlf, pos.width, pos.height);
    PackingController cont = new PackingController(model, view);
    view.setController(cont);
    
    model.addObserver(view);
    window.setVisible(true); // Make window visible
    return window; // Return the JFrame reference
  }

  public JFrame startBackDoorGUI_MVC(MiddleFactory mlf)
  {
    JFrame window = new JFrame();
    window.setTitle("BackDoor Client MVC");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Dimension pos = PosOnScrn.getPos();
    
    BackDoorModel model = new BackDoorModel(mlf);
    BackDoorView view = new BackDoorView(window, mlf, pos.width, pos.height);
    BackDoorController cont = new BackDoorController(model, view);
    view.setController(cont);
    
    model.addObserver(view);
    window.setVisible(true); // Make window visible
    return window; // Return the JFrame reference
  }
  
//Function to close all the opened client windows (JFrames)
 public void closeAllClients() {
   if (customerWindow != null) {
     customerWindow.setVisible(false); // Close the Customer client window
   }
   if (cashierWindow != null) {
     cashierWindow.setVisible(false); // Close the Cashier client window
   }
   if (packingWindow != null) {
     packingWindow.setVisible(false); // Close the Packing client window
   }
   if (backDoorWindow != null) {
     backDoorWindow.setVisible(false); // Close the BackDoor client window
   }
 }
  
  
}