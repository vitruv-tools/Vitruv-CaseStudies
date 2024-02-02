package tools.vitruv.application.viewfilter.transformation;

import com.niklas.niklasproject.niklasdomain.NiklasdomainFactory;
import com.niklas.niklasproject.niklasdomain.SingleInformation;

import tools.vitruv.applications.viewfilter.helpers.ViewFilterHelper;

import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;

//TODO nbr add javadoc
public abstract class CountElementsTransformator extends InformationViewTransformator {
	

	public SingleInformation transform(EObject root) {
		if (!(root instanceof Model)) {
			//not an Uml model
			return null;
		}
		//TODO nbruening hier noch generischer machen
		SingleInformation createSingleInformation = NiklasdomainFactory.eINSTANCE.createSingleInformation();
		createSingleInformation.setTitle(getTitle());
		
		List<EObject> allElements = ViewFilterHelper.convertTreeIterator2List(root.eAllContents());
		
		int count = 0;
		
		for (EObject element : allElements) { 
			if (takeElementIntoAccount(element)) {
				count++;
			}
		}
		
		createSingleInformation.setValue(count);
		return createSingleInformation;
	}
	
	
	protected abstract boolean takeElementIntoAccount(EObject object);
	
	protected abstract String getTitle();
	
	
}
