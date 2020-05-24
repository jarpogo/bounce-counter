package bouncepad;
 
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import bouncepad.comms.BouncePadSerialComm;
import bouncepad.components.StatusLED;
import bouncepad.screens.BouncePadMainScreen;
import bouncepad.screens.BouncePadSettingsScreen;
 
public class BouncePadApp extends Application {

	private BouncePadController m_controller;
	private BouncePadSerialComm m_serialComm;
	private BouncePadSettingsScreen m_settingsWindow;
	private BouncePadMainScreen m_mainWindow;
	          
    public static void main(String[] args) {
    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	// initialize main components shared across multiple windows
    	Label cntLab = new Label();
    	StatusLED connLed = new StatusLED();
    	StatusLED dataLed = new StatusLED();
    	
    	// initialize the controller
    	m_controller = new BouncePadController(m_mainWindow, m_settingsWindow, m_serialComm, connLed, dataLed, cntLab);
    	
    	// initialize the major objects 
        m_controller.setSerialComm(new BouncePadSerialComm(m_controller));
    	m_controller.setSettingsWin(new BouncePadSettingsScreen(m_controller));    	
    	m_controller.setMainWin(new BouncePadMainScreen(m_controller));     
    	
    }

    @Override
    public void stop() {
    	// disconnect serial port to prevent it from locking
    	m_controller.getSerialComm().disconnect();
    	
    	System.out.println("Shutting down");
    }
}