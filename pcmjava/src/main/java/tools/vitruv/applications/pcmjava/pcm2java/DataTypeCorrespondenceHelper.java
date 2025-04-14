package tools.vitruv.applications.pcmjava.pcm2java;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil;
import org.apache.log4j.Logger;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypesFactory;
import org.palladiosimulator.pcm.repository.*;

import tools.vitruv.applications.util.temporary.java.JavaModificationUtil;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;

public final class DataTypeCorrespondenceHelper {

    private static final Logger logger = Logger.getLogger(DataTypeCorrespondenceHelper.class);

    private DataTypeCorrespondenceHelper() {
        // Prevent instantiation
    }

    /**
     * Returns a JaMoPP type for a PCM primitive data type.
     *
     * @param primitiveDataType The PCM primitive type.
     * @return The corresponding JaMoPP type, or null if unsupported.
     */
    public static Type getJaMoPPTypeForPrimitive(final PrimitiveDataType primitiveDataType) {
        if (primitiveDataType == null || primitiveDataType.getType() == null) {
            logger.warn("PrimitiveDataType or its type is null.");
            return null;
        }

        switch (primitiveDataType.getType()) {
            case BOOL:
                return TypesFactory.eINSTANCE.createBoolean();
            case BYTE:
                return TypesFactory.eINSTANCE.createByte();
            case CHAR:
                return TypesFactory.eINSTANCE.createChar();
            case DOUBLE:
                return TypesFactory.eINSTANCE.createDouble();
            case INT:
                return TypesFactory.eINSTANCE.createInt();
            case LONG:
                return TypesFactory.eINSTANCE.createLong();
            case STRING:
                var stringClass = ClassifiersFactory.eINSTANCE.createClass();
                stringClass.setName("String");
                return stringClass;
            default:
                logger.warn("Unsupported PrimitiveTypeEnum: " + primitiveDataType.getType());
                return null;
        }
    }

    /**
     * Retrieves the JaMoPP type reference for a given PCM data type.
     *
     * @param dataType      The PCM data type.
     * @param correspondence The correspondence model view.
     * @return A JaMoPP TypeReference, or an empty reference as fallback.
     */
    public static TypeReference getCorrespondingTypeReference(
            final DataType dataType,
            final EditableCorrespondenceModelView<?> correspondence) {

        if (dataType == null) {
            return TypesFactory.eINSTANCE.createVoid();
        }

        Type type = getCorrespondingType(dataType, correspondence);

        if (type instanceof TypeReference) {
            return (TypeReference) type;
        }

        if (type instanceof ConcreteClassifier) {
            return JavaModificationUtil.createNamespaceClassifierReference((ConcreteClassifier) type);
        }

        logger.warn("Could not convert DataType to TypeReference: " + type);
        return TypesFactory.eINSTANCE.createClassifierReference(); // Empty fallback
    }

    /**
     * Returns a JaMoPP type corresponding to a PCM DataType.
     *
     * @param dataType       The PCM data type.
     * @param correspondence The correspondence model view.
     * @return A JaMoPP type or null.
     */
    public static Type getCorrespondingType(
            final DataType dataType,
            final EditableCorrespondenceModelView<?> correspondence) {

        if (dataType instanceof CollectionDataType) {
            return getClassifierFromCorrespondence((CollectionDataType) dataType, correspondence);
        } else if (dataType instanceof CompositeDataType) {
            return getClassifierFromCorrespondence((CompositeDataType) dataType, correspondence);
        } else if (dataType instanceof PrimitiveDataType) {
            return getJaMoPPTypeForPrimitive((PrimitiveDataType) dataType);
        } else {
            logger.warn("Unhandled DataType: " + dataType.getClass().getName());
            return null;
        }
    }

    /**
     * Gets a corresponding classifier for a CollectionDataType from the correspondence model.
     */
    private static Type getClassifierFromCorrespondence(
            final CollectionDataType dataType,
            final EditableCorrespondenceModelView<?> correspondence) {

        return getSingleClassifier(dataType, correspondence);
    }

    /**
     * Gets a corresponding classifier for a CompositeDataType from the correspondence model.
     */
    private static Type getClassifierFromCorrespondence(
            final CompositeDataType dataType,
            final EditableCorrespondenceModelView<?> correspondence) {

        return getSingleClassifier(dataType, correspondence);
    }

    /**
     * Retrieves exactly one corresponding classifier or logs a warning.
     */
    private static Type getSingleClassifier(
            final DataType dataType,
            final EditableCorrespondenceModelView<?> correspondence) {

        var classifiers = Iterables.filter(
                correspondence.getCorrespondingEObjects(dataType),
                ConcreteClassifier.class
        );

        if (!classifiers.iterator().hasNext()) {
            logger.warn("No corresponding classifier found");
            return null;
        }

        try {
            return IterableUtil.claimOne(classifiers);
        } catch (IllegalStateException e) {
            logger.warn("Expected exactly one classifier but found: " + Iterables.size(classifiers), e);
            return null;
        }
    }
}
