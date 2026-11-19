package tools.vitruv.applications.cbs.commonalities.tests.util.common;

public final class NamingUtil {

    private NamingUtil() {
    }

    /**
     * Returns the given name with its first character converted to lower case,
     * or the name itself if it is <code>null</code> or empty.
     */
    public static String toFirstLower(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }
}
