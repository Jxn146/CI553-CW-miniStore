package clients.backDoor;

import middle.MiddleFactory;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 */

public class BackDoorView implements Observer
{
  private static final String RESTOCK  = "Add";
  private static final String CLEAR    = "Clear";
  private static final String QUERY    = "Query";
 
  private static final int H = 350;       // Height of window pixels
  private static final int W = 430;       // Width  of window pixels

  private final JLabel      pageTitle  = new JLabel();
  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextField  theInputNo = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtClear = new JButton( CLEAR );
  private final JButton     theBtRStock = new JButton( RESTOCK );
  private final JButton     theBtQuery = new JButton( QUERY );
  
  private StockReadWriter theStock     = null;
  private BackDoorController cont= null;
  
  private static final Color DARK_PINK = new Color(245, 66, 147);
  private static final Color LIGHT_PURPLE = new Color(206, 174, 214);
  private static final Color PURPLE = new Color(89, 15, 107);

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  public BackDoorView(  RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                             // 
    {      
      theStock = mf.makeStockReadWriter();          // Database access
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
    cp.setBackground(new Color(130, 62, 25)); // Light gray background
    
    Font f = new Font("Rockwell",Font.PLAIN,14);  // Font f is
    theAction.setFont(f); //applying font to theAction
    theOutput.setFont(f); //applying font to theOutput
    
    theAction.setForeground(LIGHT_PURPLE);        //setting text color for the action label
    //theInput.setForeground(Color.GREEN);       //setting text color for the input text field
    theOutput.setForeground(PURPLE);    //setting text color for the output text area


    pageTitle.setBounds( 110, 10 , 270, 40 );       
    pageTitle.setFont(new Font("Algerian", Font.BOLD, 18)); //increase font size and customize font style (eg. bold, rockwell)
    pageTitle.setForeground(new Color(237, 192, 225));
    pageTitle.setText( "<html>Staff check and<br> manage stock<html>" );                        
    cp.add( pageTitle );
    
    theBtQuery.setBounds( 16, 25+60*1, 80, 40 );    // Buy button 
    theBtQuery.setFont(new Font("Georgia", Font.PLAIN, 12));
    theBtQuery.setBackground(DARK_PINK);
    theBtQuery.addActionListener(                   // Call back code
      e -> cont.doQuery( theInput.getText() ) );
    cp.add( theBtQuery );                           //  Add to canvas

    theBtRStock.setBounds( 16, 25+60*2, 80, 40 );   // Check Button
    theBtRStock.setFont(new Font("Georgia", Font.PLAIN, 12));
    theBtRStock.setBackground(DARK_PINK);
    theBtRStock.addActionListener(                  // Call back code
      e -> cont.doRStock( theInput.getText(),
                          theInputNo.getText() ) );
    cp.add( theBtRStock );                          //  Add to canvas

    theBtClear.setBounds( 16, 25+60*3, 80, 40 );    // Buy button 
    theBtClear.setFont(new Font("Georgia", Font.PLAIN, 12));
    theBtClear.setBackground(DARK_PINK);
    theBtClear.addActionListener(                   // Call back code
      e -> cont.doClear() );
    cp.add( theBtClear );                           //  Add to canvas

 
    theAction.setBounds( 110, 48 , 270, 20 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 110, 70, 120, 40 );         // Input Area
    theInput.setText("");                           // Blank
    cp.add( theInput );                             //  Add to canvas
    
    theInputNo.setBounds( 260, 70, 120, 40 );       // Input Area
    theInputNo.setText("0");                        // 0
    cp.add( theInputNo );                           //  Add to canvas

    theSP.setBounds( 110, 120, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
    theInput.requestFocus();                        // Focus is here
  }
  
  public void setController( BackDoorController c )
  {
    cont = c;
  }

  /**
   * Update the view, called by notifyObservers(theAction) in model,
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  @Override
  public void update( Observable modelC, Object arg )  
  {
    BackDoorModel model  = (BackDoorModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    
    theOutput.setText( model.getBasket().getDetails() );
    theInput.requestFocus();
  }

}