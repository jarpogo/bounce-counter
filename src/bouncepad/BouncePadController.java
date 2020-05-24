package bouncepad;

import javafx.scene.control.Label;
import bouncepad.comms.BouncePadSerialComm;
import bouncepad.components.StatusLED;
import bouncepad.screens.BouncePadMainScreen;
import bouncepad.screens.BouncePadSettingsScreen;

public class BouncePadController {

	private BouncePadMainScreen m_mainWin;
	private BouncePadSettingsScreen m_settingsWin;
	private BouncePadSerialComm m_serialComm;
	private StatusLED m_connLed;
	private StatusLED m_dataLed;
	private Label m_countLbl;
	
	/** BouncePadController
	 * 
	 * Has access to all of the major components
	 * Used as a gateway to give access to the major objects in the BouncePadApp
	 * 
	 * @param mainWin - main window object
	 * @param settingsWin - settings window object
	 * @param serialComm - serial communication object
	 * @param connLed - connection light object
	 * @param dataLed - data transfer light object
	 * @param countLbl - counter object
	 */
	public BouncePadController(BouncePadMainScreen mainWin, BouncePadSettingsScreen settingsWin, BouncePadSerialComm serialComm, StatusLED connLed, StatusLED dataLed, Label countLbl) {
		m_mainWin = mainWin;
		m_settingsWin = settingsWin;
		m_serialComm = serialComm;
		m_connLed = connLed;
		m_dataLed = dataLed;
		m_countLbl = countLbl;
	}
	
	/*************************************
	 * GETTERS
	 *************************************/
	public BouncePadMainScreen getMainWin() {
		return m_mainWin;
	}
	
	public BouncePadSettingsScreen getSettingsWin() {
		return m_settingsWin;
	}
	
	public BouncePadSerialComm getSerialComm() {
		return m_serialComm;
	}
	
	public StatusLED getConnLed() {
		return m_connLed;
	}
	
	public StatusLED getDataLed() {
		return m_dataLed;
	}
	
	public Label getCountLbl() {
		return m_countLbl;
	}
	
	/*************************************
	 * SETTERS
	 *************************************/
	public void setMainWin(BouncePadMainScreen mainWin) {
		m_mainWin = mainWin;
	}
	
	public void setSettingsWin(BouncePadSettingsScreen settingsWin) {
		m_settingsWin = settingsWin;
	}
	
	public void setSerialComm(BouncePadSerialComm serialComm) {
		m_serialComm = serialComm;
	}
	
	public void setConnLed(StatusLED connLed) {
		m_connLed = connLed;
	}
	
	public void setDataLed(StatusLED dataLed) {
		m_dataLed = dataLed;
	}
	
	public void setCountLbl(Label countLbl) {
		m_countLbl = countLbl;
	}
	
}
