package bouncepad.screens;

import bouncepad.BouncePadController;
import bouncepad.components.buttons.SettingsButton;
import bouncepad.components.buttons.StartButton;
import bouncepad.components.buttons.StopButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BouncePadMainScreen extends Stage {

	private Stage m_stage;
	private Scene m_scene;
	private BouncePadController m_controller;
	
	/** BouncePadMainScreen
	 * 
	 * create the main window for the application
	 * 
     * @param controller - link to major components (LEDs, windows, etc)
     */
	public BouncePadMainScreen(BouncePadController controller) {
		
		m_controller = controller;
		
		initWinLayout();
	}
	
    /** initWinLayout
     * 
     * initialize the settings window layout
     */
	public void initWinLayout() {
		/*********************************************
		 * LOGOS
		 *********************************************/
        Circle logo = new Circle(110);
        logo.setId("btacLogo");
        logo.setFill(new ImagePattern(new Image("/resources/btacLogo.png"), 0, 0, 1, 1, true));
        
        
        /*********************************************
         * BUTTONS
         *********************************************/
        SettingsButton m_settingsBtn = new SettingsButton(m_controller.getSettingsWin());
        StartButton startBtn = new StartButton(m_controller.getSerialComm());
        StopButton stopBtn = new StopButton(m_controller.getSerialComm());
        
        startBtn.setButton(stopBtn);
        stopBtn.setButton(startBtn);
        
        
        /*********************************************
         * LABELS
         *********************************************/
        Label title = new Label("World Record Bounce Counter");
        title.setId("titleLabel");
        
		Label connLbl = new Label("CONNECTION ");
        connLbl.setId("statusLabel");
                
        Label dataLbl = new Label("DATA ");
        dataLbl.setId("statusLabel");
    
        Label oldRecord = new Label("Previous Record: 70,076");
        oldRecord.setId("contentLabel");
        
        Label newRecord = new Label("Current Attempt: ");
        newRecord.setId("contentLabel");
        
        m_controller.getCountLbl().setText("0");
        m_controller.getCountLbl().setId("contentLabel");
        
        Label btacTitle = new Label("Bounce to a Cure");
        btacTitle.setId("btacTitle");
        
        Label btacDetail = new Label("Visit www.bouncetoacure.com to donate to the Scleroderma Research Foundation");
        btacDetail.setId("btacDetail");
        
        
        /*********************************************
         * LAYOUT
         *********************************************/
        // header layout
		HBox connBox = new HBox();
		connBox.setId("statusBox");
        HBox.setHgrow(connBox, Priority.ALWAYS);
        connBox.getChildren().addAll(connLbl, m_controller.getConnLed().getLed());

        HBox dataBox = new HBox();
		dataBox.setId("statusBox");
        dataBox.getChildren().addAll(dataLbl, m_controller.getDataLed().getLed());
        
        HBox titleBox = new HBox();
        titleBox.setId("titleBox");
        titleBox.getChildren().addAll(title,connBox);
                
        VBox headerBox = new VBox();
        headerBox.setId("header");
        headerBox.getChildren().addAll(titleBox, dataBox, m_settingsBtn.getButton());
                
        // content layout
        StackPane btnPane = new StackPane();
        btnPane.getChildren().addAll(stopBtn.getButton(), startBtn.getButton());
         
        HBox curRecord = new HBox();
        curRecord.getChildren().addAll(newRecord, m_controller.getCountLbl());
		        
		VBox recordBox = new VBox();
		recordBox.setId("recordBox");
		recordBox.getChildren().addAll(oldRecord, curRecord);      
		        
        HBox contentBox = new HBox();
        contentBox.setId("content");
        contentBox.getChildren().addAll(btnPane, recordBox);
        
        // footer layout
        VBox btacBox = new VBox();
        btacBox.setAlignment(Pos.BOTTOM_CENTER);
        btacBox.setSpacing(20);
        btacBox.setPadding(new Insets(0, 0, 20, 10));
        btacBox.getChildren().addAll(btacTitle, btacDetail);
        
        BorderPane footer = new BorderPane();
        footer.setId("footer");       
        footer.setLeft(logo);
        footer.setCenter(btacBox);
        
        
        /*********************************************
         * WINDOW SETUP
         *********************************************/
        // create border for orientation
        BorderPane border = new BorderPane();
        border.setCenter(contentBox);
        border.setBottom(footer);
        border.setTop(headerBox);
        
        // create new scene to set window size and style
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        m_scene = new Scene(border, screenBounds.getWidth(), screenBounds.getHeight());
        m_scene.getStylesheets().add("/css/mainPage.css"); 
        
        // initialize the window
        m_stage = new Stage();
        m_stage.setTitle("Pogo Bounce Counter");
        m_stage.setScene(m_scene);

        m_stage.show();
	}
}
