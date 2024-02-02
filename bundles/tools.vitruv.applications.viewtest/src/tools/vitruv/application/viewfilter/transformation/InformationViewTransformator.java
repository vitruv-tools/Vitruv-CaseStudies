package tools.vitruv.application.viewfilter.transformation;

import org.eclipse.emf.ecore.EObject;

import com.niklas.niklasproject.niklasdomain.SingleInformation;

public abstract class InformationViewTransformator {

	public abstract SingleInformation transform(EObject root);
	
}
