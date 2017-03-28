package tools.vitruv.applications.umljava.constructionsimulation.traversal

import org.eclipse.emf.common.util.EList;
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
import tools.vitruv.applications.umljava.constructionsimulation.util.UmlChangeBuildHelper;
import tools.vitruv.extensions.constructionsimulation.traversal.EMFTraversalStrategy;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.change.description.VitruviusChangeFactory;
import tools.vitruv.framework.util.datatypes.VURI;
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.common.util.BasicEList

public class UmlTraversalStrategy extends EMFTraversalStrategy {
    protected EList<VitruviusChange> changeList;
    protected VURI vuri;
    
    def public EList<VitruviusChange> traverse(Model entity, URI uri, EList<VitruviusChange> existingChanges)
            throws UnsupportedOperationException {
        vuri = VURI.getInstance(uri);
        changeList = new BasicEList<VitruviusChange>();
        traverseModel(entity);
        traversePackagedElements(entity);
        return changeList;
    }
    
    /**
     * 
     * @param model Das Uml-RootModel
     */
    def private void traverseModel(Model model) {
        val systemChange = UmlChangeBuildHelper.createChangeFromModel(model);
        this.addChange(VitruviusChangeFactory.getInstance().createConcreteChange(systemChange, this.vuri), this.changeList);
    }
    
    def private void traversePackagedElements(Model model) {
        val packagedElements = model.getPackagedElements();
        //traversePrimitiveType(packagedElements.filter(PrimitiveType).toList);
        traverseInterface(packagedElements.filter(Interface).toList);
        traverseClass(packagedElements.filter(Class).toList);
    }
    
    def private void traversePrimitiveType(List<PrimitiveType> source) {
        for (PrimitiveType p : source) {
            val change = UmlChangeBuildHelper.createChangeFromPrimitiveType(p);
            this.addChange(VitruviusChangeFactory.getInstance().createConcreteChange(change, vuri), changeList);
        }
    }
    
    def private void traverseDataType(List<DataType> source) {
        for (DataType d : source) {
            val change = UmlChangeBuildHelper.createChangeFromDataType(d);
            this.addChange(VitruviusChangeFactory.getInstance().createConcreteChange(change, vuri), changeList);
        }
    }
    
    def private void traverseClass(List<Class> source) {
        for (Class c : source) {
            val change = UmlChangeBuildHelper.createChangeFromClass(c);
            this.addChange(VitruviusChangeFactory.getInstance().createConcreteChange(change, vuri), changeList);

            for (Property attr : c.getOwnedAttributes()) {
                traverseAttribute(attr);
            }
            for (Operation op : c.getOwnedOperations()) {
                traverseOperation(op);
            }
            for (Generalization g : c.getGeneralizations()) {
                traverseGeneralization(g);
            }
            for (InterfaceRealization r : c.getInterfaceRealizations()) {
                traverseInterfaceRealization(r);
            }
        }
    }
    
    def private void traverseInterface(List<Interface> source) {
        for (Interface i : source) {
            val change = UmlChangeBuildHelper.createChangeFromInterface(i);
            this.addChange(VitruviusChangeFactory.getInstance().createConcreteChange(change, vuri), changeList);
            for (Property attr : i.getOwnedAttributes()) {
                traverseAttribute(attr);
            }
            for (Operation op : i.getOwnedOperations()) {
                traverseOperation(op);
            }
            for (Generalization g : i.getGeneralizations()) {
                traverseGeneralization(g);
            }
        }
    }
    
    def private void traverseAttribute(Property source) {
        val change = UmlChangeBuildHelper.createChangeFromProperty(source);
        this.addChange(VitruviusChangeFactory.getInstance().createConcreteChange(change, vuri), changeList);
    }
    def private void traverseOperation(Operation source) {
        val change = UmlChangeBuildHelper.createChangeFromOperation(source);
        this.addChange(VitruviusChangeFactory.getInstance().createConcreteChange(change, vuri), changeList);
        for (Parameter p : source.getOwnedParameters()) {
            traverseParameter(p);
        }
    }
    
    def private void traverseParameter(Parameter source) {
        val change = UmlChangeBuildHelper.createChangeFromParameter(source);
        this.addChange(VitruviusChangeFactory.getInstance().createConcreteChange(change, vuri), changeList);
    }
    
    def private void traverseGeneralization(Generalization source) {
        val change = UmlChangeBuildHelper.createChangeFromGeneralization(source);
        this.addChange(VitruviusChangeFactory.getInstance().createConcreteChange(change, vuri), changeList);
    }
    
    def private void traverseInterfaceRealization(InterfaceRealization source) {
        val change = UmlChangeBuildHelper.createChangeFromInterfaceRealization(source);
        this.addChange(VitruviusChangeFactory.getInstance().createConcreteChange(change, vuri), changeList);
    }
}
