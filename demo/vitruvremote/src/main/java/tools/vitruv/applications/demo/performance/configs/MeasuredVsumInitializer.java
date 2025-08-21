package tools.vitruv.applications.demo.performance.configs;

import java.nio.file.Path;

import tools.vitruv.applications.demo.FamiliesPersonsVsumWrapper;
import tools.vitruv.framework.remote.server.VirtualModelInitializer;
import tools.vitruv.framework.vsum.VirtualModel;

public class MeasuredVsumInitializer implements VirtualModelInitializer {
    private Path vsumDir;

    public MeasuredVsumInitializer() {}

    public MeasuredVsumInitializer(Path vsumDir) {
        this.vsumDir = vsumDir;
    }

    @Override
    public VirtualModel init() {
        FamiliesPersonsVsumWrapper vsumWrapper = new FamiliesPersonsVsumWrapper();
        try {
            if (this.vsumDir != null) {
                vsumWrapper.initialize(this.vsumDir);
            } else {
                vsumWrapper.initialize();
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not initialize the VSUM.", ex);
        }
        return vsumWrapper.getVsum();
    }
}
