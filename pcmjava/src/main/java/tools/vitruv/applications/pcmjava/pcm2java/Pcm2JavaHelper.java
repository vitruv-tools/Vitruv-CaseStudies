package tools.vitruv.applications.pcmjava.pcm2java;

import java.util.Optional;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypesFactory;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import tools.vitruv.applications.util.temporary.java.JavaModificationUtil;

public final class Pcm2JavaHelper {

    /**
     * Creates a Java type reference for a given PCM DataType. If the type is primitive, it will be mapped using JaMoPP.
     * If it is not primitive, a corresponding Java class must be provided.
     *
     * @param originalDataType the PCM DataType
     * @param correspondingJavaClassIfExisting an optional Java class corresponding to the PCM DataType
     * @return a Java TypeReference representing the type
     * @throws IllegalArgumentException if the datatype is not primitive and no Java class is provided
     */
    public static TypeReference createTypeReference(
            final DataType originalDataType,
            final Optional<Class> correspondingJavaClassIfExisting) {

        if (originalDataType == null) {
            return TypesFactory.eINSTANCE.createVoid();
        }

        TypeReference innerDataTypeReference;

        if (originalDataType instanceof PrimitiveDataType) {
            Type type = EcoreUtil.copy(
                    DataTypeCorrespondenceHelper.getJaMoPPTypeForPrimitive((PrimitiveDataType) originalDataType)
            );

            if (type instanceof TypeReference) {
                innerDataTypeReference = (TypeReference) type;
            } else if (type instanceof ConcreteClassifier) {
                innerDataTypeReference = JavaModificationUtil.createNamespaceClassifierReference((ConcreteClassifier) type);
            } else {
                throw new IllegalStateException("Unsupported type returned for primitive data type.");
            }

        } else if (correspondingJavaClassIfExisting.isPresent()) {
            innerDataTypeReference = JavaModificationUtil.createNamespaceClassifierReference(
                    correspondingJavaClassIfExisting.get()
            );
        } else {
            throw new IllegalArgumentException(
                    "Either the DataType must be primitive or a corresponding Java class must be specified."
            );
        }

        return innerDataTypeReference;
    }

    // Private constructor to prevent instantiation
    private Pcm2JavaHelper() {
    }
}
