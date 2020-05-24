package bouncepad.components.buttons;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class CircularButton {

	protected final int RADIUS = 15;
	protected final double RADIUS_PERCENT = 0.1;
	
	protected Group m_icon = new Group();
	protected Circle m_base;
	protected Circle m_border;
	protected Circle m_background;
	
	// these booleans are used to help track what state the mouse is in
	// to help simulate a typical Java button
	protected boolean mouseEnter = false;
	protected boolean mouseClick = false;
	
	/** CircularButton
	 * 
	 * create a circle object that simulates the actions of a button
	 */
	public CircularButton() {
		m_border = new Circle(RADIUS, Color.BLACK);
		m_base = new Circle(RADIUS-(RADIUS*RADIUS_PERCENT), Color.GRAY);
		m_background = new Circle(RADIUS-(RADIUS*RADIUS_PERCENT), Color.WHITE);
				
		m_icon.getChildren().addAll(m_border, m_base, m_background);
	}
	
	/** setSize
	 * 
	 * set the size of the icon
	 * 
	 * @param rad - an int representing the new size of the icon
	 */
	public void setSize(int rad) {
		m_border.setRadius(rad);
		m_base.setRadius(rad-(rad*RADIUS_PERCENT));
		m_background.setRadius(rad-(rad*RADIUS_PERCENT));
	}
	
	/** getButton
	 * 
	 * return the group m_icon to be used in other scnenes
	 * 
	 * @return - the group icon
	 */
	public Group getButton() {
		return m_icon;
	}
	
	/** showButton
	 * 
	 * allow the button to become visible
	 */
	public void showButton() {
		m_icon.visibleProperty().set(true);
	}
	
	/** hideButton
	 * 
	 * hide the button from the scene 
	 */
	public void hideButton() {
		m_icon.visibleProperty().set(false);
	}
	
	/** handleEvents
	 * 
	 * all subclasses must implement this method
	 * each button will have different actions depending on the events
	 */
	abstract void handleEvents();
}
