package tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.traversal

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Model;
import tools.vitruv.extensions.constructionsimulation.traversal.EMFTraversalStrategy;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.util.datatypes.VURI;
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.common.util.BasicEList

public class UmlComponentTraversalStrategy extends EMFTraversalStrategy {
	
    protected EList<VitruviusChange> changeList
    protected VURI vuri
    
    def public EList<VitruviusChange> traverse(Model model, URI uri, EList<VitruviusChange> existingChanges) throws UnsupportedOperationException {
    	
    	if (existingChanges !== null) {
			throw new UnsupportedOperationException("Traversal cannot be placed on existing changes")
		}
				
        vuri = VURI.getInstance(uri);
        
        //final EList<DataType> dataTypes = model.
        //final EList<Component> components = model.
		//final EList<Interface> interfaces = model. 
        
        //this.traverseModel(model);
        //this.traverseDataTypes(dataTypes);
        //this.traverseComponents(components);
        //this.traverseInterfaces(interfaces);

        changeList = new BasicEList<VitruviusChange>()//Test
        return changeList        
        //return this.changeList
    }
    
}
		