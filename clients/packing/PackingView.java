package clients.packing;

import catalogue.Basket;
import middle.MiddleFactory;
import middle.OrderProcessing;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Packing view.

 */

public class PackingView implements Observer
{
  private static final String PACKED = "<html>Packed and<br>Ready!<html>";

  private static final int H = 350;       // Height of window pixels
  private static final int W = 430;       // Width  of window pixels

  private final JLabel      pageTitle  = new JLabel();
  private final JLabel      theAction  = new JLabel();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtPack= new JButton( PACKED );
 
  private OrderProcessing theOrder     = null;
  
  private PackingController cont= null;
  
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
  public PackingView(  RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                           // 
    {      
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
    cp.setBackground(new Color(130, 62, 25)); // Light gray background


    Font f = new Font("Rockwell",Font.PLAIN,14);  // Font f is
    theAction.setFont(f); //applying font to theAction
    theOutput.setFont(f); //applying font to theOutput
    
    theAction.setForeground(LIGHT_PURPLE);        //setting text color for the action label
    //theInput.setForeground(Color.GREEN);       //setting text color for the input text field
    theOutput.setForeground(PURPLE);    //setting text color for the output text area

    
    pageTitle.setBounds( 110, 10 , 270, 20 );       
    pageTitle.setFont(new Font("Algerian", Font.BOLD, 18)); //increase font size and customize font style (eg. bold, rockwell)
    pageTitle.setForeground(new Color(237, 192, 225));
    pageTitle.setText( "Packaging Zone" );                        
    cp.add( pageTitle );

    theBtPack.setBounds( 16, 25+60*1, 80, 40 );   // Check Button
    theBtPack.setFont(new Font("Georgia", Font.PLAIN, 12));
    theBtPack.setBackground(DARK_PINK);
    theBtPack.addActionListener(                   // Call back code
      e -> cont.doPacked() );
    cp.add( theBtPack );                          //  Add to canvas

    theAction.setBounds( 110, 25 , 270, 20 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    theSP.setBounds( 110, 55, 270, 205 );           // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
  }
  
  public void setController( PackingController c )
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
	  PackingModel model  = (PackingModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    
    Basket basket =  model.getBasket();
    if ( basket != null )
    {
      theOutput.setText( basket.getDetails() );
    } else {
      theOutput.setText("");
    }
  }

}

