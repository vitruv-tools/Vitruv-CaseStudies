package tools.vitruv.applications.cbs.commonalities.tests.cbs.java;

import java.util.ArrayList;
import java.util.List;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.types.TypesFactory;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.CompositeDataTypeTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;
import tools.vitruv.applications.util.temporary.java.JavaModificationUtil;
import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.newCompilationUnit;
// import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.referenceJamoppType;

public class JavaCompositeDataTypeTestModels extends JavaTestModelsBase
        implements CompositeDataTypeTest.DomainModels {

    private static Class newJavaCompositeDataTypeClass() {
        Class compositeClass = ClassifiersFactory.eINSTANCE.createClass();
        compositeClass.setName(COMPOSITE_DATATYPE_1_NAME);
        compositeClass.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        return compositeClass;
    }

    private static Field newJavaElementField() {
        Field field = MembersFactory.eINSTANCE.createField();
        field.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPrivate());
        return field;
    }

    public JavaCompositeDataTypeTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel emptyCompositeDataTypeCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package datatypesPackage = javaRepositoryModel.getDatatypesPackage();

            Class datatypeClass = newJavaCompositeDataTypeClass();
            CompilationUnit compilationUnit = newCompilationUnit(datatypesPackage, datatypeClass);

            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit);
            return rootObjects;
        });
    }

    @Override
    public DomainModel compositeDataTypeWithBooleanElementCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package datatypesPackage = javaRepositoryModel.getDatatypesPackage();

            Class datatypeClass = newJavaCompositeDataTypeClass();
            Field field = newJavaElementField();
            field.setName(BOOLEAN_ELEMENT_NAME);
            field.setTypeReference(TypesFactory.eINSTANCE.createBoolean());
            datatypeClass.getMembers().add(field);

            CompilationUnit compilationUnit = newCompilationUnit(datatypesPackage, datatypeClass);

            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit);
            return rootObjects;
        });
    }

    // ... similar pattern for other primitive type methods ...

    @Override
    public DomainModel compositeDataTypeWithCompositeElementsCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package datatypesPackage = javaRepositoryModel.getDatatypesPackage();

            // First datatype with integer field
            Class datatype1Class = newJavaCompositeDataTypeClass();
            Field intField = newJavaElementField();
            intField.setName(INTEGER_ELEMENT_NAME);
            intField.setTypeReference(TypesFactory.eINSTANCE.createInt());
            datatype1Class.getMembers().add(intField);
            CompilationUnit compilationUnit1 = newCompilationUnit(datatypesPackage, datatype1Class);

            // Second datatype with composite fields
            Class datatype2Class = newJavaCompositeDataTypeClass();
            datatype2Class.setName(COMPOSITE_DATATYPE_2_NAME);

            Field compositeField1 = newJavaElementField();
            compositeField1.setName(COMPOSITE_ELEMENT_1_NAME);
            compositeField1.setTypeReference(
                    JavaModificationUtil.createNamespaceClassifierReference(datatype1Class));
            datatype2Class.getMembers().add(compositeField1);

            Field compositeField2 = newJavaElementField();
            compositeField2.setName(COMPOSITE_ELEMENT_2_NAME);
            compositeField2.setTypeReference(
                    JavaModificationUtil.createNamespaceClassifierReference(datatype1Class));
            datatype2Class.getMembers().add(compositeField2);

            CompilationUnit compilationUnit2 = newCompilationUnit(datatypesPackage, datatype2Class);
            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit1);
            rootObjects.add(compilationUnit2);
            return rootObjects;
        });
    }

    @Override
    public DomainModel compositeDataTypeWithIntegerElementCreation() {
        return newModel(() -> {
            // Create repository and get datatypes package
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package datatypesPackage = javaRepositoryModel.getDatatypesPackage();

            // Create datatype class with integer field
            Class datatypeClass = newJavaCompositeDataTypeClass();
            Field field = newJavaElementField();
            field.setName(COMPOSITE_DATATYPE_1_NAME);
            field.setTypeReference(TypesFactory.eINSTANCE.createInt());
            datatypeClass.getMembers().add(field);

            // Create compilation unit
            CompilationUnit compilationUnit = JavaModelHelper.newCompilationUnit(
                    datatypesPackage,
                    datatypeClass);

            // Combine root objects with new compilation unit
            List<EObject> allObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            allObjects.add(compilationUnit);
            return allObjects;
        });
    }

    @Override
    public DomainModel compositeDataTypeWithDoubleElementCreation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compositeDataTypeWithDoubleElementCreation'");
    }

    @Override
    public DomainModel compositeDataTypeWithStringElementCreation() {
        return newModel(() -> {
            // Create repository and package
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package datatypesPackage = javaRepositoryModel.getDatatypesPackage();

            // Create datatype class
            Class datatypeClass = newJavaCompositeDataTypeClass();

            // Create and add double field
            Field field = newJavaElementField();
            field.setName(DOUBLE_ELEMENT_NAME);
            field.setTypeReference(TypesFactory.eINSTANCE.createDouble());
            datatypeClass.getMembers().add(field);

            // Create compilation unit
            CompilationUnit compilationUnit = JavaModelHelper.newCompilationUnit(
                    datatypesPackage,
                    datatypeClass);

            // Combine and return root objects
            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit);
            return rootObjects;
        });
    }

    @Override
    public DomainModel compositeDataTypeWithWithMultiplePrimitiveElementsCreation() {
        return newModel(() -> {
            // Create repository and package
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package datatypesPackage = javaRepositoryModel.getDatatypesPackage();

            // Create composite datatype class
            Class datatypeClass = newJavaCompositeDataTypeClass();

            // Add boolean field
            Field booleanField = newJavaElementField();
            booleanField.setName(BOOLEAN_ELEMENT_NAME);
            booleanField.setTypeReference(TypesFactory.eINSTANCE.createBoolean());
            datatypeClass.getMembers().add(booleanField);

            // Add integer field
            Field integerField = newJavaElementField();
            integerField.setName(INTEGER_ELEMENT_NAME);
            integerField.setTypeReference(TypesFactory.eINSTANCE.createInt());
            datatypeClass.getMembers().add(integerField);

            // Add double field
            Field doubleField = newJavaElementField();
            doubleField.setName(DOUBLE_ELEMENT_NAME);
            doubleField.setTypeReference(TypesFactory.eINSTANCE.createDouble());
            datatypeClass.getMembers().add(doubleField);

            // Add string field
            Field stringField = newJavaElementField();
            stringField.setName(STRING_ELEMENT_NAME);
            stringField.setTypeReference(referenceJamoppType(String.class));
            datatypeClass.getMembers().add(stringField);

            // Create compilation unit
            CompilationUnit compilationUnit = JavaModelHelper.newCompilationUnit(
                    datatypesPackage,
                    datatypeClass);

            // Combine root objects
            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit);
            return rootObjects;
        });
    }

    @Override
    public DomainModel multipleCompositeDataTypesWithPrimitiveElementsCreation() {
        return newModel(() -> {
            // Create repository and package
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Package datatypesPackage = javaRepositoryModel.getDatatypesPackage();

            // Create first datatype with boolean field
            Class datatype1Class = newJavaCompositeDataTypeClass();
            Field booleanField = newJavaElementField();
            booleanField.setName(BOOLEAN_ELEMENT_NAME);
            booleanField.setTypeReference(TypesFactory.eINSTANCE.createBoolean());
            datatype1Class.getMembers().add(booleanField);
            CompilationUnit compilationUnit1 = JavaModelHelper.newCompilationUnit(
                    datatypesPackage,
                    datatype1Class);

            // Create second datatype with integer field
            Class datatype2Class = newJavaCompositeDataTypeClass();
            datatype2Class.setName(COMPOSITE_DATATYPE_2_NAME);
            Field integerField = newJavaElementField();
            integerField.setName(INTEGER_ELEMENT_NAME);
            integerField.setTypeReference(TypesFactory.eINSTANCE.createInt());
            datatype2Class.getMembers().add(integerField);
            CompilationUnit compilationUnit2 = JavaModelHelper.newCompilationUnit(
                    datatypesPackage,
                    datatype2Class);

            // Combine root objects with new compilation units
            List<EObject> rootObjects = new ArrayList<>(javaRepositoryModel.getRootObjects());
            rootObjects.add(compilationUnit1);
            rootObjects.add(compilationUnit2);
            return rootObjects;
        });
    }
}