package bouncepad.comms;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;
import java.util.UUID;

import javafx.application.Platform;
//import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import bouncepad.BouncePadController;
//import bouncepad.components.StatusLED;
import bouncepad.components.Status.StatusType;

public class BouncePadSerialComm extends Stage implements SerialPortEventListener {

	private Enumeration<CommPortIdentifier> m_portList;
	private HashMap<String,CommPortIdentifier> m_commMap;
	private InputStream m_inputStream;
    private SerialPort m_serialPort;
    private volatile boolean m_running = false;
    private Integer m_counter = 0;
    private String m_id;
    private BouncePadController m_controller;
    
    /** BouncePadSerialComm
     * 
     * Serial Communication object to select, connect, and disconnect
	 * to a specific COM port that is available
	 * 
     * @param controller - link to major components (LEDs, windows, etc)
     */
	public BouncePadSerialComm (BouncePadController controller) {
		// create a random ID to link the COM listener to this exxact instance
		m_id = UUID.randomUUID().toString();
		
		// list of all available COM ports
		m_portList = CommPortIdentifier.getPortIdentifiers();
		
		// hashmap of COM port names matching their respective CommPortIdentifiers
		m_commMap = new HashMap<String,CommPortIdentifier>();
		
		m_controller = controller;
	}

	/** reset
	 * 
	 * used to reset the counter back to Zero
	 */
    public void reset() {
    	m_counter = 0;
    }
    
    /** getPortList
     * 
     * populate the hashmap with available COM ports
     * return the hashmap for external use
     * 
     * @return hashmap containing all available COM ports
     */
	public HashMap<String,CommPortIdentifier> getPortList() {
		while (m_portList.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier) m_portList.nextElement();
			
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				m_commMap.put(portId.getName(),portId);
			}			
		}
		
		return m_commMap;
	}
	
	/** connect
	 * 
	 * Check if the port desired is owned
	 * If not, open the port with the unique ID
	 * 
	 * @param commPort - the desired port to connect to
	 * @return StatusType feedback on if the connection was successful or not
	 */
	public StatusType connect(String commPort) {
		// if no port selected, report BAD
		if (commPort == null) {
			return StatusType.BAD;
		}
		
		StatusType result;
		CommPortIdentifier selectedPort = m_commMap.get(commPort); 
		
		// if owned, check if this ID is the owner
		if (selectedPort.isCurrentlyOwned()) {
			if (selectedPort.getCurrentOwner().equals(m_id)) {
				result = StatusType.GOOD;
			} else {
				System.out.println("Different Owner");
				result = StatusType.BAD;
			}
		// if not owned, claim ownership
		} else {
			try {
				// try to open the port, timeout after 3 seconds
				m_serialPort = (SerialPort) selectedPort.open(m_id, 3000);
			} catch (PortInUseException e) {
				e.printStackTrace();
				result = StatusType.BAD;
			}
			result = StatusType.GOOD;
		}

		// begin reading from the serial port
        serialRead();
        
		return result;
	}
	
	/** disconnect
	 * 
	 * remove the listener from the serial port and close the port
	 */
	public void disconnect() {
		if (m_serialPort != null) {
			try {
				m_serialPort.removeEventListener();
			} catch (IllegalStateException e) {
				// ignore
			}
			m_serialPort.close();
		}
	}
	
	/** isRunning
	 * 
	 * determine if the counter is running
	 * 
	 * @return boolean 
	 */
	public boolean isRunning() {
		return m_running;
	}
	
	/** start
	 * 
	 * set m_running to true to notify the port listener to begin counting
	 */
	public void start() {
		m_running = true;
	}
	
	/** stop
	 * 
	 * set m_running to false to notify the port listener to stop counting
	 */
	public void stop() {
		m_running = false;
	}
	
	/** serialRead
	 * 
	 * open the input stream, add the event listener, and set the serial port parameters
	 */
	public void serialRead() {	
		
        try {
            m_inputStream = m_serialPort.getInputStream();
            m_serialPort.addEventListener(this);
            
            // make sure that the port is notified when data is received
	        m_serialPort.notifyOnDataAvailable(true);
        
            m_serialPort.setSerialPortParams(9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            
        } catch (IOException | TooManyListenersException | UnsupportedCommOperationException e) {
        	System.out.println(e);
        }
	}
	
	/** serialEvent
	 * 
	 * event handler for incoming serial port events
	 * When data is available, process accordingly
	 */
	@Override
	public void serialEvent(SerialPortEvent event) {
        switch(event.getEventType()) {
        case SerialPortEvent.DATA_AVAILABLE:
            try {
            	// read the complete stream of data until the carriage return
            	while (m_inputStream.read() != 10){
            		// blink the dataLED to show data received
            		m_controller.getDataLed().setStatus(StatusType.DATA_TRANSFER);
                }
            	// if the app is set to start, begin counting
                if (m_running) {
	                m_counter++;
	                
	                // update the countLbl on the mainWindow
	                // needs to be indirect since serialEvents are not JavaFX applications
	                Platform.runLater(new Runnable() {
						@Override
						public void run() {
							m_controller.getCountLbl().setText(m_counter.toString());
						}
					});
					
	                // print out the time and bounce number
	                // TODO: write this information to a log file
	                System.out.println("Time: " + Calendar.getInstance() + " | Bounces: " + m_counter);
                }
                // turn the dataLED off to finalize the 'blink' animation
                m_controller.getDataLed().setStatus(StatusType.NEUTRAL);
            } catch (IOException e) {
            	System.out.println(e);
            }
            break;
        }
	}
}
