package tools.vitruv.applications.cbs.commonalities.tests.cbs.java;

import static tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaModelHelper.newCompilationUnit;

import java.util.List;
import java.util.function.Supplier;

import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypesFactory;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.CompositeDataTypeTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;
import tools.vitruv.applications.util.temporary.java.JavaModificationUtil;

public class JavaCompositeDataTypeTestModels extends JavaTestModelsBase implements CompositeDataTypeTest.DomainModels {

    private static Class newJavaCompositeDataTypeClass() {
        Class compositeDataTypeClass = ClassifiersFactory.eINSTANCE.createClass();
        compositeDataTypeClass.setName(COMPOSITE_DATATYPE_1_NAME);
        compositeDataTypeClass.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPublic());
        return compositeDataTypeClass;
    }

    private static Class newJavaCompositeDataTypeClass(String name, Field... elementFields) {
        Class compositeDataTypeClass = newJavaCompositeDataTypeClass();
        compositeDataTypeClass.setName(name);
        compositeDataTypeClass.getMembers().addAll(List.of(elementFields));
        return compositeDataTypeClass;
    }

    private static Field newJavaElementField(String name, TypeReference typeReference) {
        Field elementField = MembersFactory.eINSTANCE.createField();
        elementField.getAnnotationsAndModifiers().add(ModifiersFactory.eINSTANCE.createPrivate());
        elementField.setName(name);
        elementField.setTypeReference(typeReference);
        return elementField;
    }

    public JavaCompositeDataTypeTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    /**
     * The repository with a compilation unit in the datatypes package for the
     * given class.
     */
    private DomainModel repositoryWithDatatype(Supplier<Class> datatypeClassCreator) {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Class datatypeClass = datatypeClassCreator.get();
            CompilationUnit compilationUnit = newCompilationUnit(javaRepositoryModel.getDatatypesPackage(),
                    datatypeClass);
            return javaRepositoryModel.getRootObjectsWith(compilationUnit);
        });
    }

    // Empty CompositeDataType

    @Override
    public DomainModel emptyCompositeDataTypeCreation() {
        return repositoryWithDatatype(JavaCompositeDataTypeTestModels::newJavaCompositeDataTypeClass);
    }

    // Primitive inner elements

    @Override
    public DomainModel compositeDataTypeWithBooleanElementCreation() {
        return repositoryWithDatatype(() -> newJavaCompositeDataTypeClass(COMPOSITE_DATATYPE_1_NAME,
                newJavaElementField(BOOLEAN_ELEMENT_NAME, TypesFactory.eINSTANCE.createBoolean())));
    }

    @Override
    public DomainModel compositeDataTypeWithIntegerElementCreation() {
        return repositoryWithDatatype(() -> newJavaCompositeDataTypeClass(COMPOSITE_DATATYPE_1_NAME,
                newJavaElementField(INTEGER_ELEMENT_NAME, TypesFactory.eINSTANCE.createInt())));
    }

    @Override
    public DomainModel compositeDataTypeWithDoubleElementCreation() {
        return repositoryWithDatatype(() -> newJavaCompositeDataTypeClass(COMPOSITE_DATATYPE_1_NAME,
                newJavaElementField(DOUBLE_ELEMENT_NAME, TypesFactory.eINSTANCE.createDouble())));
    }

    @Override
    public DomainModel compositeDataTypeWithStringElementCreation() {
        return repositoryWithDatatype(() -> newJavaCompositeDataTypeClass(COMPOSITE_DATATYPE_1_NAME,
                newJavaElementField(STRING_ELEMENT_NAME, referenceJamoppType(String.class))));
    }

    @Override
    public DomainModel compositeDataTypeWithWithMultiplePrimitiveElementsCreation() {
        return repositoryWithDatatype(() -> newJavaCompositeDataTypeClass(COMPOSITE_DATATYPE_1_NAME,
                newJavaElementField(BOOLEAN_ELEMENT_NAME, TypesFactory.eINSTANCE.createBoolean()),
                newJavaElementField(INTEGER_ELEMENT_NAME, TypesFactory.eINSTANCE.createInt()),
                newJavaElementField(DOUBLE_ELEMENT_NAME, TypesFactory.eINSTANCE.createDouble()),
                newJavaElementField(STRING_ELEMENT_NAME, referenceJamoppType(String.class))));
    }

    // Multiple CompositeDataTypes

    @Override
    public DomainModel multipleCompositeDataTypesWithPrimitiveElementsCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Class datatype1Class = newJavaCompositeDataTypeClass(COMPOSITE_DATATYPE_1_NAME,
                    newJavaElementField(BOOLEAN_ELEMENT_NAME, TypesFactory.eINSTANCE.createBoolean()));
            CompilationUnit compilationUnit1 = newCompilationUnit(javaRepositoryModel.getDatatypesPackage(),
                    datatype1Class);
            Class datatype2Class = newJavaCompositeDataTypeClass(COMPOSITE_DATATYPE_2_NAME,
                    newJavaElementField(INTEGER_ELEMENT_NAME, TypesFactory.eINSTANCE.createInt()));
            CompilationUnit compilationUnit2 = newCompilationUnit(javaRepositoryModel.getDatatypesPackage(),
                    datatype2Class);
            return javaRepositoryModel.getRootObjectsWith(compilationUnit1, compilationUnit2);
        });
    }

    @Override
    public DomainModel compositeDataTypeWithCompositeElementsCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            Class datatype1Class = newJavaCompositeDataTypeClass(COMPOSITE_DATATYPE_1_NAME,
                    newJavaElementField(INTEGER_ELEMENT_NAME, TypesFactory.eINSTANCE.createInt()));
            CompilationUnit compilationUnit1 = newCompilationUnit(javaRepositoryModel.getDatatypesPackage(),
                    datatype1Class);
            Class datatype2Class = newJavaCompositeDataTypeClass(COMPOSITE_DATATYPE_2_NAME,
                    newJavaElementField(COMPOSITE_ELEMENT_1_NAME,
                            JavaModificationUtil.createNamespaceClassifierReference(datatype1Class)),
                    newJavaElementField(COMPOSITE_ELEMENT_2_NAME,
                            JavaModificationUtil.createNamespaceClassifierReference(datatype1Class)));
            CompilationUnit compilationUnit2 = newCompilationUnit(javaRepositoryModel.getDatatypesPackage(),
                    datatype2Class);
            return javaRepositoryModel.getRootObjectsWith(compilationUnit1, compilationUnit2);
        });
    }
}
