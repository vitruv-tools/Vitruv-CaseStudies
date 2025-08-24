package tools.vitruv.applications.demo.performance.common;

public final class EnvUtility {
    public static final String ENV_KEY_VITRUV_PERF_SERVER_PORT = "VITRUV_PERF_SERVER_PORT";

    public static final String ENV_KEY_VITRUV_PERF_VITRUV_SERVER_PORT = "VITRUV_PERF_SERVER_PORT";

    public static final String ENV_KEY_VITRUV_PERF_WORK_DIR = "VITRUV_PERF_WORK_DIR";

    public static final String ENV_KEY_VITRUV_PERF_TLS_PWD = "VITRUV_PERF_TLS_PWD";

    public static final String ENV_KEY_VITRUV_WORKER_OIDC_PORT = "VITRUV_WORKER_OIDC_PORT";

    public static final String ENV_KEY_VITRUV_CONTROLLER_REMOTE_WORKER_URI = "VITRUV_CONTROLLER_REMOTE_WORKER_URI";

    private EnvUtility() {}

    public static String asString(String envKey) {
        return System.getenv(envKey);
    }

    public static String asString(String envKey, String defaultValue) {
        var value = asString(envKey);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }

    public static boolean asBoolean(String envKey) {
        var valueString = asString(envKey);
        return Boolean.parseBoolean(valueString);
    }

    public static int asInt(String envKey, int defaultValue) {
        var valueString = asString(envKey);
        try {
            return Integer.parseInt(valueString);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }
}
