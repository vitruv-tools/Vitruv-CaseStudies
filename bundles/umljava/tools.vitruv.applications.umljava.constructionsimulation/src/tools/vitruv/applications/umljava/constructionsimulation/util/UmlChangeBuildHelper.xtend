package tools.vitruv.applications.umljava.constructionsimulation.util

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import tools.vitruv.extensions.constructionsimulation.traversal.util.ChangeBuildHelper;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.root.RootFactory;

class UmlChangeBuildHelper extends ChangeBuildHelper {

    def static EChange createChangeFromModel(Model model) {
        val change = RootFactory.eINSTANCE.createInsertRootEObject();
        change.setNewValue(model);
        return change;
    }
    
    def static EChange createChangeFromClass(Class source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }
    
    def static EChange createChangeFromInterface(Interface source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }
    
    def static EChange createChangeFromProperty(Property source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }
    
    def static EChange createChangeFromOperation(Operation source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }
    
    def static EChange createChangeFromParameter(Parameter source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }
    
    def static EChange createChangeFromGeneralization(Generalization source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }
    
    def static EChange createChangeFromInterfaceRealization(InterfaceRealization source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }
    
    def static EChange createChangeFromDataType(DataType source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }
    
    def static EChange createChangeFromPrimitiveType(PrimitiveType source) {
        return createSingleAddNonRootEObjectInListChange(source);
    }

}
