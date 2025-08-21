package tools.vitruv.applications.demo.performance.configs;

import tools.vitruv.applications.demo.FamiliesPersonsVsumWrapper;
import tools.vitruv.framework.remote.server.VirtualModelInitializer;
import tools.vitruv.framework.vsum.VirtualModel;

public class MeasuredVsumInitializer implements VirtualModelInitializer {
    @Override
    public VirtualModel init() {
        FamiliesPersonsVsumWrapper vsumWrapper = new FamiliesPersonsVsumWrapper();
        try {
            vsumWrapper.initialize();
        } catch (Exception ex) {
            throw new RuntimeException("Could not initialize the VSUM.", ex);
        }
        return vsumWrapper.getVsum();
    }
}
