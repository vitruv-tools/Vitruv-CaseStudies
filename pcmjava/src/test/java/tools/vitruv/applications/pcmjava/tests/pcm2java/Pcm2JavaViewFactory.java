package tools.vitruv.applications.pcmjava.tests.pcm2java;

import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.testutils.integration.TestViewFactory;
import tools.vitruv.framework.vsum.VirtualModel;

public class Pcm2JavaViewFactory extends TestViewFactory {

    public Pcm2JavaViewFactory(VirtualModel virtualModel) {
        super(virtualModel);
    }

    private View createJavaView() {
        return createViewOfElements("Java", Set.of(Package.class, CompilationUnit.class));
    }

    private View createPcmView() {
        return createViewOfElements("PCM", Set.of(Repository.class, System.class));
    }

    public void changeJavaView(Procedure1<? super View> modelModification) throws Exception {
        try {
            changeViewRecordingChanges(this.createJavaView(), (CommittableView arg0) -> {
                modelModification.apply(arg0);
            });
        } catch (Exception e) {
            throw e;
        }

    }

    public void changePcmView(Procedure1<? super View> modelModification) throws Exception {
        try {
            this.changeViewRecordingChanges(this.createPcmView(), (CommittableView arg0) -> {
                modelModification.apply(arg0);
            });
        } catch (Exception e) {
            throw e;
        }
    }

    public void validateJavaView(Procedure1<? super View> viewValidation) throws Exception {
        try {
            this.validateView(this.createJavaView(), (View arg0) -> {
                viewValidation.apply(arg0);
            });
        } catch (Exception e) {
            throw e;
        }
    }

    public void validatePcmView(Procedure1<? super View> viewValidation) throws Exception {
        try {
            this.validateView(this.createPcmView(), new Consumer<View>() {
                public void accept(View arg0) {
                    viewValidation.apply(arg0);
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }
}