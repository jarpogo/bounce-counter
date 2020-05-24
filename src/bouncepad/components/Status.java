package bouncepad.components;

public class Status {
	
	/** StatusType
	 * 
	 * custom status enum to assist in handling various object events
	 */
	public enum StatusType {
	    NEUTRAL, GOOD, DATA_TRANSFER, BAD
	}
	StatusType m_type;
}
