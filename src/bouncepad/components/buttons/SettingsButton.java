package bouncepad.components.buttons;

import bouncepad.screens.BouncePadSettingsScreen;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;

public class SettingsButton extends CircularButton {

	private String m_defaultImg = "/resources/settings.png";
	private String m_hoverImg = "/resources/settingsHover.png";
	private String m_clickImg = "/resources/settingsClick.png";
	
	private BouncePadSettingsScreen m_settingsScreen;
	
	/** SettingsButton
	 * 
	 * The button used to open and connect to the settingsScreen object
	 * 
	 * @param settingsScreen - the window for the settings
	 */
	public SettingsButton(BouncePadSettingsScreen settingsScreen) {
		handleEvents();
		m_settingsScreen = settingsScreen;
	}

	/** handleEvents
	 * 
	 * simulate the way a button is pressed by changing the image depending on the event
	 * as well as interacting with the settings window
	 */
	@Override
	protected void handleEvents() {
		final Image defaultImage = new Image(m_defaultImg);
		final Image hoverImage = new Image(m_hoverImg);
		final Image clickImage = new Image(m_clickImg);
		
		// set the image
		m_background.setFill(new ImagePattern(defaultImage, 0, 0, 1, 1, true));
		
		// when the mouse enters
		m_background.addEventHandler(MouseEvent.MOUSE_ENTERED, 
			    new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent e) {
	        	if (mouseClick) {
	        		m_background.setFill(new ImagePattern(clickImage, 0, 0, 1, 1, true));
	        		mouseEnter = true;
	        	} else {
	        		m_background.setFill(new ImagePattern(hoverImage, 0, 0, 1, 1, true));
	        		mouseEnter = true;
	        	}
	        }
		});
		
		// when the mouse exits
		m_background.addEventHandler(MouseEvent.MOUSE_EXITED, 
			    new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent e) {
	        	m_background.setFill(new ImagePattern(defaultImage, 0, 0, 1, 1, true));
	        	mouseEnter = false;	        	
	        }
		});
		
		// when the mouse button is clicked
		m_background.addEventHandler(MouseEvent.MOUSE_PRESSED,
				new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	m_background.setFill(new ImagePattern(clickImage, 0, 0, 1, 1, true));
        		mouseClick = true;
            }
        }); 
		
		// when the mouse button is released
		m_background.addEventHandler(MouseEvent.MOUSE_RELEASED,
				new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	// if the mouse is still inside of the circle AND the button is released, show the settings window
            	if(mouseEnter) {
            		m_background.setFill(new ImagePattern(hoverImage, 0, 0, 1, 1, true));
            		m_settingsScreen.showWindow();
            	} else {
            		m_background.setFill(new ImagePattern(defaultImage, 0, 0, 1, 1, true));
            	}
            	mouseClick = false;
            }
        }); 
		
	}
	
}
