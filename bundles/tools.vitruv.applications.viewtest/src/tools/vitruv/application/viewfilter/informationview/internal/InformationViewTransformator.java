package tools.vitruv.application.viewfilter.informationview.internal;

import org.eclipse.emf.ecore.EObject;

import com.niklas.niklasproject.niklasdomain.SingleInformation;

/**
 * Transforms a given model into a {@link SingleInformation}. A transformation could be 
 * counting the elements of a certain type in the given model for instance. 
 * The actual transformation depends on the overwriting implementation.}
 */
public interface InformationViewTransformator {

	/**
	 * Method transforms the given model (represented by its root) into a {@link SingleInformation}.
	 * The transformation corresponds to the calculation of the actual information, which will than 
	 * be represented by the {@link SingleInformation} instance which the method returns.
	 * A transformation could be counting the elements of a certain type in the given model for instance.
	 * The actual transformation depends on the actual implementation of this method
	 * 
	 * @param root The root element of the model which is supposed to be transformed
	 * @return The information which has been extracted from the given model
	 */
	public abstract SingleInformation transform(EObject root);
	
}
