package bouncepad.components.buttons;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import bouncepad.comms.BouncePadSerialComm;

public class StopButton extends CircularButton {

	private String m_stopImg = "/resources/stop.png";
	private BouncePadSerialComm m_serialComm;
	private StartButton m_startBtn;
	
	/** StopButton
	 * 
	 * The button used to stop the serialComm counter
	 * 
	 * @param serialComm - the serial communication object to track the number of bounces
	 */
	public StopButton(BouncePadSerialComm serialComm) {
		handleEvents();
		setSize(100);
		m_serialComm = serialComm;
	}
	
	/** setButton
	 * 
	 * The stop button will swap positions with the start button when clicked
	 * This is meant to unite the stop button with the start button
	 * 
	 * @param startBtn - start button to link with
	 */
	public void setButton(StartButton startBtn) {
		m_startBtn = startBtn;
	}
	
	/** handleEvents
	 * 
	 * simulate the way a button is pressed by changing the image depending on the event
	 * as well as interacting with the settings window
	 */
	@Override
	protected void handleEvents() {
		Image defaultImage = new Image(m_stopImg);
		
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
            	// if the mouse is still inside of the circle AND the button is released, stop counting
            	if(mouseEnter) {
    	        	System.out.println("STOP");
    	        	m_base.setFill(Color.LIGHTGRAY);
    	        	
    	        	// stop the serial comm from counting
            		m_serialComm.stop();
            		
            		// hide the stop button and show the start button
            		hideButton();
            		m_startBtn.showButton();
            	} else {
	        		m_base.setFill(Color.DARKGRAY);
            	}
            	mouseClick = false;
            }
        }); 
	}
}
