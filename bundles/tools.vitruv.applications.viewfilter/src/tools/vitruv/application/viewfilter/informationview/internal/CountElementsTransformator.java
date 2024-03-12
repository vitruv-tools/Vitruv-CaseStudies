package tools.vitruv.application.viewfilter.informationview.internal;


import java.util.List;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.applications.viewfilter.helpers.ViewFilterHelper;
import tools.vitruv.views.viewfilter.infostructure.model.infostructuremodel.InfostructuremodelFactory;
import tools.vitruv.views.viewfilter.infostructure.model.infostructuremodel.SingleInformation;

/**
 * Transforms models into an information. Instances of this class can be used to count elements of a certain 
 * type in a given set of models. Class uses {@link takeElementIntoAccount()} to determine whether a model-object is 
 * supposed to be counted (increase the counter by 1) or not. Class returns the determined information as 
 * an instance of {@link SingleInformation}. It therefor uses the {@link getTitle()} method to determine the title
 * for the {@link SingleInformation}. Implementations of this abstract class therefor must implement those methods 
 * according to what is supposed to be counted.
 */
public abstract class CountElementsTransformator implements InformationViewTransformator {
	
	@Override
	public SingleInformation transform(List<EObject> roots) {
		SingleInformation createSingleInformation = InfostructuremodelFactory.eINSTANCE.createSingleInformation();
		createSingleInformation.setTitle(getTitle());
		int count = 0;
		
		for (EObject root : roots) {
			List<EObject> allElements = ViewFilterHelper.convertTreeIterator2List(root.eAllContents());
			
			for (EObject element : allElements) { 
				if (takeElementIntoAccount(element)) {
					count++;
				}
			}
		}
		
		createSingleInformation.setValue(count);
		return createSingleInformation;
	}
	
	
	/**
	 * Method determines whether a model-object is supposed to be counted (if the counter is supposed 
	 * to be increased for this object). If the given object is supposed to be counted, which means it is taken into 
	 * account, this method returns true. If not, it returns false.
	 * 
	 * @param object The object for which the method is supposed to determine whether it is supposed to be taken into account
	 * @return true if the object is supposed to be taken into account
	 */
	protected abstract boolean takeElementIntoAccount(EObject object);
	
	/**
	 * Method returns the title which the {@link SingleInformation} object which this {@link CountElementsTransformator}
	 * instance returns, is supposed to have.
	 * 
	 * @return The title of {@link SingleInformation} objects which this {@link CountElementsTransformator} returns
	 */
	protected abstract String getTitle();
	
	
}
