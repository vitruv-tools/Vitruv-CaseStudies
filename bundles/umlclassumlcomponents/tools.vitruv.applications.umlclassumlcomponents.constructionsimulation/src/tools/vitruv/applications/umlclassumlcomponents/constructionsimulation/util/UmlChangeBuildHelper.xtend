package tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.util

import org.eclipse.uml2.uml.Model
import tools.vitruv.extensions.constructionsimulation.traversal.util.ChangeBuildHelper
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.root.RootFactory

class UmlChangeBuildHelper extends ChangeBuildHelper {

    def static EChange createChangeFromModel(Model model) {
        val change = RootFactory.eINSTANCE.createInsertRootEObject()
        change.setNewValue(model)
        return change
    }


}