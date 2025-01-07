package clients.packing;

import clients.Main;

import clients.packing.PackingModel;
import clients.packing.PackingView;
import middle.MiddleFactory;
/**
 * The Packing Controller
 */

public class PackingController 
{
  private PackingModel model = null;
  private PackingView  view  = null;
   Main mainApp = new Main();  // Reference to Main class to trigger the Goodbye screen
   private MiddleFactory middleFactory;

  /**
   * Constructor
   * @param model The model 
   * @param view  The view from which the interaction came
   */
  public PackingController( PackingModel model, PackingView view )
  {
    this.view  = view;
    this.model = model;
  }

  /**
   * Picked interaction from view
   */
  public void doPacked()
  {
    model.doPacked();
    mainApp.closeAllClients(); //close beginMainApp screen
    mainApp.showGoodbyeScreen(middleFactory);//show Goodbye screen when packed button is clicked
  }
  
}

