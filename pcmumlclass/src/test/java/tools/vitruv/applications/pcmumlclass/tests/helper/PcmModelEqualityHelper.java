package tools.vitruv.applications.pcmumlclass.tests.helper;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;

/**
 * This class can be used to compare two PCM Models for structural equality. In
 * difference to the standard {EcoreUtil.EqualityHelper} it ignores the id value
 * and compares for primitive data types only the type value.
 */
public class PcmModelEqualityHelper extends IgnoreFeaturesEqualityHelper {
	private static final long serialVersionUID = 1712828566739779682L;

	public PcmModelEqualityHelper() {
		super(Set.of("id"));
	}

	@Override
	protected boolean haveEqualFeature(EObject eObject1, EObject eObject2, EStructuralFeature feature) {
		if (eObject1.eGet(feature) instanceof PrimitiveDataType
				&& eObject2.eGet(feature) instanceof PrimitiveDataType) {
			return ((PrimitiveDataType)eObject1.eGet(feature)).getType() == ((PrimitiveDataType)eObject2.eGet(feature)).getType();
		}

		return super.haveEqualFeature(eObject1, eObject2, feature);
	}
}
