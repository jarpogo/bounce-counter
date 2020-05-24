package bouncepad.screens;

import java.util.HashMap;
import javax.comm.CommPortIdentifier;
import bouncepad.BouncePadController;
import bouncepad.components.Status.StatusType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BouncePadSettingsScreen extends Stage {

	private Stage m_stage;
	private Scene m_scene;
	private Button m_connBtn;
	private Button m_disconnBtn;
	private ComboBox<String> m_commBox;
	private BouncePadController m_controller;
	    
	/** BouncePadSettingsScreen
	 * 
	 * create the settings window for the application
	 * 
     * @param controller - link to major components (LEDs, windows, etc)
     */
    public BouncePadSettingsScreen (BouncePadController controller) 
	{
    	m_controller = controller;
		
		initComboBox();
		initButtons();
        initWinLayout();        
    }

    /** showWindow
     * 
     * allow other objects to control when to show the window
     */
    public void showWindow()
    {
    	m_stage.show();
    }
    
    /** initWinLayout
     * 
     * initialize the settings window layout
     */
    private void initWinLayout() {
        /*********************************************
         * LAYOUT
         *********************************************/
        HBox contentBox = new HBox();
        contentBox.setId("contentBox");
        contentBox.getChildren().addAll(m_commBox, m_connBtn, m_disconnBtn);
        
        /*********************************************
         * WINDOW SETUP
         *********************************************/
        // create border for orientation
		BorderPane border = new BorderPane();
        border.setTop(contentBox);

        // create new scene to set window size and style
        m_scene = new Scene(border, 300, 60);
        m_scene.getStylesheets().add("/css/settingsPage.css"); 
        
        // initialize the window
    	m_stage = new Stage();
        m_stage.initModality(Modality.APPLICATION_MODAL);
        m_stage.setTitle("COM Settings");
        m_stage.setScene(m_scene); 
        m_stage.setResizable(false);
    }

    /** initComboBox
     * 
     * initialize the comobBox with available values from the controller serial comm
     */
    private void initComboBox() {
        HashMap<String,CommPortIdentifier> commMap = m_controller.getSerialComm().getPortList();
        m_commBox = new ComboBox<String>();
        
        // populate the comboBox with the hashmap
        for (String key : commMap.keySet()) {
        	m_commBox.getItems().add(key);
        }               
    }
    
    /** initButtons
     * 
     * initialize the connect and disconnect buttons
     * create the OnAction event handlers for when the buttons are pressed
     */
    private void initButtons() {
    	// create connect button
    	m_connBtn = new Button("Connect");
    	m_connBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		// connect the controller serial com port to the comboBox port value
        		m_controller.getConnLed().setStatus(m_controller.getSerialComm().connect(m_commBox.getValue()));
        	}
        });
    	
    	// create disconnect button
    	m_disconnBtn = new Button("Disconnect");
    	m_disconnBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		// disconnect the serial com port and set the status to BAD
        		m_controller.getSerialComm().disconnect();
        		m_controller.getConnLed().setStatus(StatusType.BAD);
        	}
        });
    }
}
