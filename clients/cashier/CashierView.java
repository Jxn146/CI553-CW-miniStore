package clients.cashier;

import catalogue.Basket;
import clients.customer.NameToNumber;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;


/**
 * View of the model 
 */
public class CashierView implements Observer
{
  private static final int H = 350;       // Height of window pixels
  private static final int W = 430;       // Width  of window pixels
  
  private static final String CHECK  = "<html>Check -><br>Availability<html>";
  private static final String BUY    = "<html>Buy -><br>Product<html>";
  private static final String CLEAR  = "Clear";     ////
  private static final String BOUGHT = "<html>Navigate<br>to Packaging<html>";
 
  
  private final JLabel      pageTitle  = new JLabel();
  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextField  buyQuantity   = new JTextField(); //////////
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtCheck = new JButton( CHECK );
  private final JButton     theBtBuy   = new JButton( BUY );
  private final JButton theBtRemoveLast = new JButton("Remove Last");
  private final JButton     theBtClear   = new JButton( CLEAR );
  private final JButton     theBtBought= new JButton( BOUGHT );
  

  private StockReadWriter theStock     = null;
  private OrderProcessing theOrder     = null;
  private CashierController cont       = null;
  
  private static final Color DARK_PINK = new Color(245, 66, 147);
  private static final Color LIGHT_PURPLE = new Color(206, 174, 214);
  private static final Color PURPLE = new Color(89, 15, 107);

  
  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-coordinate of position of window on screen 
   * @param y     y-coordinate of position of window on screen  
   */
          
  public CashierView(  RootPaneContainer rpc,  MiddleFactory mf, int x, int y  )
  {
    try                                           // 
    {      
      theStock = mf.makeStockReadWriter();        // Database access
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );
    // set the background color of the content pane
    cp.setBackground(new Color(37, 107, 122)); // Light gray background


    Font f = new Font("Rockwell",Font.PLAIN,14);  // Font f is
    theAction.setFont(f); //applying font to theAction
    theInput.setFont(f);  //applying font to theInput
    theOutput.setFont(f); //applying font to theOutput
    
    theAction.setForeground(LIGHT_PURPLE);        //setting text color for the action label
    //theInput.setForeground(Color.GREEN);       //setting text color for the input text field
    theOutput.setForeground(PURPLE);    //setting text color for the output text area

    pageTitle.setBounds( 130, 10 , 270, 20 );       
    pageTitle.setText("Ready to Go?" ); 
    pageTitle.setFont(new Font("Algerian", Font.BOLD, 18)); //increase font size and customize font style (eg. bold, rockwell)
    pageTitle.setForeground(new Color(237, 192, 225));
    cp.add( pageTitle );  
    
    theBtCheck.setBounds( 16, 25+60*0, 100, 40 );    // Check Button
    theBtCheck.setFont(new Font("Georgia", Font.PLAIN, 12));
    theBtCheck.setBackground(DARK_PINK);
    theBtCheck.addActionListener (e -> {
        String input = theInput.getText().trim();//retrieve the user input and remove leading/trailing spaces
        NameToNumber nameToNumber = new NameToNumber(); //an instance of NameToNumber for product lookup
        String productNumber = nameToNumber.getNumberByName(nameToNumber, input); //to perform case-insensitive lookup to find the corresponding product number
        if (productNumber == null) {//if the input is already a product number, use it directly
            productNumber = input; //use input as the product number if no match by name
        } try {
            int quantity = Integer.parseInt(buyQuantity.getText()); //get the quantity from the input field
            cont.doCheck(productNumber, quantity); //passes the resolved product number and quantity to the controller
        } catch (NumberFormatException ex) {
            theAction.setText("Invalid quantity entered!");//display an error message if the quantity is invalid
        }
         });
    cp.add( theBtCheck );                           //  Add to canvas

    theBtBuy.setBounds( 16, 25+60*1, 80, 40 );      // Buy button 
    theBtBuy.setFont(new Font("Georgia", Font.PLAIN, 12));
    theBtBuy.setBackground(DARK_PINK);
    theBtBuy.addActionListener(                     // Call back code
      e -> cont.doBuy() );
    cp.add( theBtBuy );                             //  Add to canvas
    
//    theBtRemoveLast.setBounds( 16, 25 + 60 * 4, 100, 40 );  // Adjust placement to fit below existing buttons
//    theBtRemoveLast.setFont(new Font("Georgia", Font.PLAIN, 12));
//    theBtRemoveLast.setBackground(DARK_PINK);
//    theBtRemoveLast.addActionListener(e -> cont.doRemoveLast());  // Call back to controller's method
//    cp.add(theBtRemoveLast);  // Add to the content pane

    theBtBought.setBounds( 16, 25+60*3 , 100, 40);   // Bought Button
    theBtBought.setFont(new Font("Georgia", Font.PLAIN, 12));
    theBtBought.setBackground(DARK_PINK);
    theBtBought.addActionListener(                  // Call back code
      e -> cont.doBought() );
    cp.add( theBtBought );                          //  Add to canvas

    theAction.setBounds( 130, 25 , 270, 20 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas
    
    buyQuantity.setBounds( 320, 50, 80, 40 );         // Input Area
    buyQuantity.setText("1");                           // Blank
    cp.add( buyQuantity );                             //  Add to canvas
    
    theBtClear.setBounds(16, 25 + 60 * 2, 80, 40);     // Clear Button
    theBtClear.setFont(new Font("Georgia", Font.PLAIN, 12));
    theBtClear.setBackground(DARK_PINK);
    theBtClear.addActionListener(e -> cont.doClearTexts());                     // Call back code
    cp.add(theBtClear);                               // Add to canvas

    theInput.setBounds( 130, 50, 170, 40 );         // Input Area
    theInput.setText("");                           // Blank
    cp.add( theInput );                             //  Add to canvas

    theSP.setBounds( 130, 100, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
    theInput.requestFocus();                        // Focus is here
  }
  
  public void clearTexts() 
  {
  	theInput.setText("");
  	buyQuantity.setText("1");
  	theOutput.setText("");
  	theAction.setText("Welcome :)");
  	theInput.requestFocus();
  }
  
  /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */

  public void setController( CashierController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  @Override
  public void update( Observable modelC, Object arg )
  {
    CashierModel model  = (CashierModel) modelC;
    String      message = (String) arg;
    theAction.setText( message );
    Basket basket = model.getBasket();
    if ( basket == null )
      theOutput.setText( "Customers order details" );
    else
      theOutput.setText( basket.getDetails() );
    
    theInput.requestFocus();               // Focus is here
    
    if(message.equals("!!! Not in stock:(") || message.contains("Purchased ")) {
    	buyQuantity.setText("1");
    }
  }
}
