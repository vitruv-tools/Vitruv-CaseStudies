package tools.vitruv.applications.cbs.commonalities.oo;

/**
 * Shared representation of all known visibility levels.
 * <p>
 * Note: The Java metamodel does not have a dedicated visibility modifier for
 * 'package-private'.
 * <p>
 * This enum is not part of any domain and can therefore not be used as
 * attribute type directly. Instead commonalities store Visibility values via
 * their String representation.
 */
// TODO Xtend does not support enums with members yet.
public enum Visibility {

	PUBLIC,
	PROTECTED,
	PACKAGE,
	PRIVATE;

	/**
	 * Gets the {@link Visibility} for the given name.
	 * 
	 * @param name
	 *        the name
	 * @return the visibility, or <code>null</code> if the given name is
	 *         <code>null</code> or empty
	 * @throws IllegalArgumentException
	 *         if the name does not match any visibility
	 */
	public static Visibility byName(String name) {
		if (name == null || name.isEmpty()) {
			return null;
		} else {
			return valueOf(name);
		}
	}
}
