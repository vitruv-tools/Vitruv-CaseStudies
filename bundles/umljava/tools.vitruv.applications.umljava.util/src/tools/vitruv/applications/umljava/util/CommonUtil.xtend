package tools.vitruv.applications.umljava.util

/**
 * This class contains util functions that are not explicit attached to java or uml.
 * 
 * @author Fei
 */
class CommonUtil {
    private new() {}
    
    def static String firstLettertoUppercase(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1)
    }
    
    def static String firstLettertoLowercase(String s) {
        return Character.toLowerCase(s.charAt(0)) + s.substring(1)
    }
}