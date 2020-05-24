package bouncepad.components;

import bouncepad.components.Status.StatusType;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

public class StatusLED {

	private double LED_BASE_RADIUS = 15;
	private double LED_LIGHT_RADIUS = 13;
	private double FOCAL_DISTANCE = .7;
	private double COLOR_STOP = .5;
	
	private Group m_led = new Group();
	private RadialGradient m_gradient;
	private Circle m_LedBase;
	private Circle m_ledLight;
	
	/** StatusLED
	 * 
	 * LED object used to monitor status
	 */
	public StatusLED() 
	{
		// create the circles that form an LED
		m_LedBase = new Circle(LED_BASE_RADIUS, Color.BLACK);
		m_ledLight = new Circle(LED_LIGHT_RADIUS);
		
		// create the gradient of the LED
		m_gradient = new RadialGradient(330,
				FOCAL_DISTANCE,
	            0,
	            0,
	            LED_LIGHT_RADIUS,
	            false,
	            CycleMethod.NO_CYCLE,
	            new Stop(0, Color.WHITE),
	            new Stop(COLOR_STOP, Color.GRAY),
	            new Stop(1, Color.DARKGRAY));
		m_ledLight.setFill(m_gradient);
				
		// add both portions to the group m_led
		m_led.getChildren().addAll(m_LedBase, m_ledLight);
	}
	
	/** getLed
	 * 
	 * return the LED so it can be added to various scenes
	 * 
	 * @return Group - the LED group
	 */
	public Group getLed()
	{
		return m_led;
	}
	
	/** setStatus
	 * 
	 * change the color of the LED based on the status received
	 * 
	 * @param status - the enum status value
	 */
	public void setStatus(StatusType status) 
	{
		switch(status) 
		{
			// create a gray LED
			case NEUTRAL:
				m_gradient = new RadialGradient(330,
						FOCAL_DISTANCE,
			            0,
			            0,
			            LED_LIGHT_RADIUS,
			            false,
			            CycleMethod.NO_CYCLE,
			            new Stop(0, Color.WHITE),
			            new Stop(COLOR_STOP, Color.GRAY),
			            new Stop(1, Color.DARKGRAY));
				break;
			// create a green LED
			case GOOD:
				m_gradient = new RadialGradient(330,
						FOCAL_DISTANCE,
			            0,
			            0,
			            LED_LIGHT_RADIUS,
			            false,
			            CycleMethod.NO_CYCLE,
			            new Stop(0, Color.WHITE),
			            new Stop(COLOR_STOP, Color.GREENYELLOW),
			            new Stop(1, Color.DARKGREEN));
				break;
			// create an orange LED
			case DATA_TRANSFER:
				m_gradient = new RadialGradient(330,
						FOCAL_DISTANCE,
			            0,
			            0,
			            LED_LIGHT_RADIUS,
			            false,
			            CycleMethod.NO_CYCLE,
			            new Stop(0, Color.WHITE),
			            new Stop(COLOR_STOP, Color.ORANGE),
			            new Stop(1, Color.ORANGERED));
				break;
			// create a red LED
			case BAD:
				m_gradient = new RadialGradient(330,
						FOCAL_DISTANCE,
			            0,
			            0,
			            LED_LIGHT_RADIUS,
			            false,
			            CycleMethod.NO_CYCLE,
			            new Stop(0, Color.WHITE),
			            new Stop(COLOR_STOP, Color.INDIANRED),
			            new Stop(1, Color.DARKRED));
				break;
		}
		
		// apply the new gradient to the m_ledLight circle
		m_ledLight.setFill(m_gradient);
		
	}
}
