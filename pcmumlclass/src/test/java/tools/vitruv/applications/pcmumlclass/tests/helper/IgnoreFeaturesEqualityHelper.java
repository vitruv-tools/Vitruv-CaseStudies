package tools.vitruv.applications.pcmumlclass.tests.helper;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class IgnoreFeaturesEqualityHelper extends EcoreUtil.EqualityHelper {
	private static final long serialVersionUID = 1L;
	private final Set<String> ignoredFeatureNames;
	
	
	public IgnoreFeaturesEqualityHelper(Set<String> ignoredFeatureNames) {
		this.ignoredFeatureNames = ignoredFeatureNames;
	}
	
    @Override
    protected boolean haveEqualFeature(EObject eObject1, EObject eObject2, EStructuralFeature feature) {
    	if(ignoredFeatureNames.contains(feature.getName())) {
    		return true;
    	}
    	
    	return super.haveEqualFeature(eObject1, eObject2, feature);
    }
}
