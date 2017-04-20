package tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.traversal

import org.eclipse.emf.common.util.EList
import org.eclipse.uml2.uml.Model
import tools.vitruv.extensions.constructionsimulation.traversal.EMFTraversalStrategy
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.change.recording.AtomicEMFChangeRecorder

public class UmlComponentTraversalStrategy extends EMFTraversalStrategy {
	
    protected EList<VitruviusChange> changeList
    protected VURI vuri
    
    def public EList<VitruviusChange> traverse(Model model, URI uri, EList<VitruviusChange> existingChanges) throws UnsupportedOperationException {
    	
    	if (existingChanges !== null) {
			throw new UnsupportedOperationException("Traversal cannot be placed on existing changes")
		}
				
        vuri = VURI.getInstance(uri)

		val resourceSet = new ResourceSetImpl
		val resource = resourceSet.createResource(uri)		
		
		val changeRecorder = new AtomicEMFChangeRecorder
		//changeRecorder.beginRecording(vuri, #{resource})
		changeRecorder.beginRecording(VURI.getInstance(resource), #{resource})
		
		//Simply add the existing model to the new resource:
		resource.contents.add(model)
		
		changeList = new BasicEList<VitruviusChange>()
		val changes = changeRecorder.endRecording
		changes.forEach[changeList.add(it)]
        return changeList     
           
    }
    
}
		