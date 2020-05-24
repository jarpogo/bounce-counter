package bouncepad.components.buttons;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import bouncepad.comms.BouncePadSerialComm;

public class StartButton extends CircularButton {

	private String m_startImg = "/resources/start.png";
	
	private BouncePadSerialComm m_serialComm;
	private StopButton m_stopBtn;
	
	/** StartButton
	 * 
	 * The button used to start the serialComm counter
	 * 
	 * @param serialComm - the serial communication object to track the number of bounces
	 */
	public StartButton(BouncePadSerialComm serialComm) {
		handleEvents();
		setSize(100);
		m_serialComm = serialComm;
	}
	
	/** setButton
	 * 
	 * The start button will swap positions with the stop button when clicked
	 * This is meant to unite the start button with the stop button
	 * 
	 * @param stopBtn - stop button to link with
	 */
	public void setButton(StopButton stopBtn) {
		m_stopBtn = stopBtn;
	}
	
	/** handleEvents
	 * 
	 * simulate the way a button is pressed by changing the image depending on the event
	 * as well as interacting with the settings window
	 */
	@Override
	protected void handleEvents() {
		Image defaultImage = new Image(m_startImg);
		
		// set the image
		m_background.setFill(new ImagePattern(defaultImage, 0, 0, 1, 1, true));
		
		// when the mouse enters
		m_background.addEventHandler(MouseEvent.MOUSE_ENTERED, 
			    new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent e) {
	        	if (mouseClick) {
	        		m_base.setFill(Color.GRAY);
	        		mouseEnter = true;
	        	} else {
	        		m_base.setFill(Color.LIGHTGRAY);
	        		mouseEnter = true;
	        	}
	        }
		});
		
		// when the mouse exits
		m_background.addEventHandler(MouseEvent.MOUSE_EXITED, 
			    new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent e) {
	        	m_base.setFill(Color.DARKGRAY);
	        	mouseEnter = false;	        	
	        }
		});
		
		// when the mouse button is pressed
		m_background.addEventHandler(MouseEvent.MOUSE_PRESSED,
				new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
	        	m_base.setFill(Color.GRAY);
        		mouseClick = true;
            }
        }); 
		
		// when the mouse button is released
		m_background.addEventHandler(MouseEvent.MOUSE_RELEASED,
				new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	// if the mouse is still inside of the circle AND the button is released, begin the counter
            	if(mouseEnter) {
            		System.out.println("START");
    	        	m_base.setFill(Color.LIGHTGRAY);
    	        	
    	        	// reset the counter to zero and begin counting when bounces are detected
    	        	m_serialComm.reset();
            		m_serialComm.start();
            		
            		// hide the start button and show the stop button
            		hideButton();
            		m_stopBtn.showButton();
            	} else {
	        		m_base.setFill(Color.DARKGRAY);
            	}
            	mouseClick = false;
            }
        }); 
	}
}
