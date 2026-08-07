package tools.vitruv.applications.demo.performance.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class IpUtil {
    private IpUtil() {}

    public static String getHostIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
