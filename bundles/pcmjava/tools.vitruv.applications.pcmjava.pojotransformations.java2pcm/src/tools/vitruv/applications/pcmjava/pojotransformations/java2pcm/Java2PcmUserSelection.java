package tools.vitruv.applications.pcmjava.pojotransformations.java2pcm;

/**
 * Possible selection the user can select during user interaction.
 * @author thomcz
 *
 */
public enum Java2PcmUserSelection {
	SELECT_BASIC_COMPONENT(0, "create Basic Component"),
	SELECT_COMPOSITE_COMPONENT(1, "create Composite Component"),
	SELECT_SYSTEM(2, "create System"),
	SELECT_COMPOSITE_DATA_TYPE(0, "create composite Data Type"),
	SELECT_COLLECTION_DATA_TYPE(1, "create collection data type"),
	SELECT_CREATE_INTERFACE_NOT_IN_CONTRACTS(0, "create interface"),
	SELECT_DONT_CREATE_INTERFACE_NOT_IN_CONTRACTS(1, "dont't create interface"),
	SELECT_NOTHING_DECIDE_LATER(-1, "do nothing")
	;
	
	private int selection;
	private String message;
	
	private Java2PcmUserSelection(int selection, String message) {
		this.selection = selection;
		this.message = message;
	}
	
	/**
	 * Returns selection as number. 
	 * @return selection as number.
	 */
	public int getSelection() {
		return selection;
	}
	
	/**
	 * Returns message of selection.
	 * @return message of selection.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Returns message of selection.
	 * @return message of selection.
	 */
	public String toString() {
		return message;
	}
}
