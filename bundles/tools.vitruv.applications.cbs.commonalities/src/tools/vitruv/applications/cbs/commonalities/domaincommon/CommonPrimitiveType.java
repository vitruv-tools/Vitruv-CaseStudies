package tools.vitruv.applications.cbs.commonalities.domaincommon;

/**
 * Shared representation of all common primitive types.
 * <p>
 * This represents only the common subset of primitive types that are currently
 * supported.
 */
// TODO Xtend does not support enums with members yet.
public enum CommonPrimitiveType {

	BOOLEAN,
	INTEGER,
	DOUBLE,
	STRING;

	/**
	 * Gets the {@link CommonPrimitiveType} for the given name.
	 * 
	 * @param name
	 * 		the name
	 * @return the primitive type, or <code>null</code> if the given name is
	 * 		<code>null</code> or empty
	 * @throws IllegalArgumentException
	 *         if the name does not match any primitive type
	 */
	public static CommonPrimitiveType byName(String name) {
		if (name == null || name.isEmpty()) {
			return null;
		} else {
			return valueOf(name);
		}
	}
}
