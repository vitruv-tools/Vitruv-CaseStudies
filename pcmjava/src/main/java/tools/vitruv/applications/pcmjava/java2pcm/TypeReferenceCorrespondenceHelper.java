package tools.vitruv.applications.pcmjava.java2pcm;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import java.util.Set;
import org.apache.log4j.Logger;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.types.Char;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.Int;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.util.temporary.pcm.PcmDataTypeUtil;
import tools.vitruv.applications.util.temporary.pcm.PrimitiveTypesRepositoryLoader;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.change.interaction.UserInteractor;

/**
 * Helper to map type References to PCM data types
 */
public final class TypeReferenceCorrespondenceHelper {
    private static final Logger logger = Logger.getLogger(TypeReferenceCorrespondenceHelper.class.getSimpleName());

    private static DataType claimPCMDataTypeForJaMoPPPrimitiveType(final PrimitiveType primitiveJavaType) {
        PrimitiveDataType result = null;
        if (primitiveJavaType instanceof org.emftext.language.java.types.Void) {
            result = null;
        } else if (primitiveJavaType instanceof org.emftext.language.java.types.Boolean) {
            result = PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes().get("BOOL");
        } else if (primitiveJavaType instanceof org.emftext.language.java.types.Byte) {
            result = PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes().get("BYTE");
        } else if (primitiveJavaType instanceof Char) {
            result = PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes().get("CHAR");
        } else if (primitiveJavaType instanceof org.emftext.language.java.types.Double) {
            result = PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes().get("DOUBLE");
        } else if (primitiveJavaType instanceof Int) {
            result = PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes().get("INT");
        } else if (primitiveJavaType instanceof org.emftext.language.java.types.Long) {
            result = PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes().get("INT");
        } else if (primitiveJavaType instanceof org.emftext.language.java.types.Float) {
            result = PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes().get("DOUBLE");
        } else if (primitiveJavaType instanceof org.emftext.language.java.types.Short) {
            result = PrimitiveTypesRepositoryLoader.getPrimitiveDataTypes().get("INT");
        } else {
            String typeName = primitiveJavaType.eClass().getName();
            throw new IllegalStateException("No PCM type for Java type '" + typeName + "' defined");
        }
        return result;
    }

    public static DataType getCorrespondingPCMDataTypeForTypeReference(final TypeReference typeReference, final EditableCorrespondenceModelView<?> correspondenceModel, final UserInteractor userInteractor, final Repository repo, final long arrayDimension) {
        DataType pcmDataType = TypeReferenceCorrespondenceHelper.getDataTypeFromTypeReference(typeReference, correspondenceModel, userInteractor, repo);
        if (arrayDimension > 0 && pcmDataType != null && repo != null) {
            String typeName = "List_" + PcmDataTypeUtil.getNameFromPCMDataType(pcmDataType);
            CollectionDataType collectionDataType = null;
            for (CollectionDataType it : Iterables.filter(repo.getDataTypes__Repository(), CollectionDataType.class)) {
                if (it.getEntityName().equals(typeName)) {
                    collectionDataType = it;
                    break;
                }
            }

            if (collectionDataType == null) {
                collectionDataType = RepositoryFactory.eINSTANCE.createCollectionDataType();
                collectionDataType.setInnerType_CollectionDataType(pcmDataType);
                collectionDataType.setEntityName(typeName);
                repo.getDataTypes__Repository().add(collectionDataType);
                if (!(pcmDataType instanceof PrimitiveDataType)) {
                    correspondenceModel.addCorrespondenceBetween(collectionDataType, pcmDataType, null);
                }
            }
            pcmDataType = collectionDataType;
        }
        return pcmDataType;
    }

    public static DataType getDataTypeFromTypeReference(final TypeReference typeReference, final EditableCorrespondenceModelView<?> correspondenceModel, final UserInteractor userInteractor, final Repository repo) {
        DataType type = null;
        if (typeReference instanceof PrimitiveType) {
            type = TypeReferenceCorrespondenceHelper.claimPCMDataTypeForJaMoPPPrimitiveType((PrimitiveType) typeReference);
        } else if (typeReference instanceof ClassifierReference) {
            type = TypeReferenceCorrespondenceHelper.getPCMDataTypeForClassifierReference((ClassifierReference) typeReference, correspondenceModel, userInteractor, repo);
        } else if (typeReference instanceof NamespaceClassifierReference) {
            type = TypeReferenceCorrespondenceHelper.getPCMDataTypeForNamespaceClassifierReference((NamespaceClassifierReference) typeReference, correspondenceModel, userInteractor, repo);
        }
        Preconditions.checkState(type != null, "Could not find a PCM data type for type reference %s", typeReference);
        return type;
    }

    private static DataType getPCMDataTypeForNamespaceClassifierReference(final NamespaceClassifierReference reference, final EditableCorrespondenceModelView<?> correspondenceModel, final UserInteractor userInteractor, final Repository repo) {
        if (reference.getClassifierReferences() != null && !reference.getClassifierReferences().isEmpty()) {
            for (final ClassifierReference classifierRef : reference.getClassifierReferences()) {
                if (classifierRef != null) {
                    return TypeReferenceCorrespondenceHelper.getPCMDataTypeForClassifierReference(classifierRef, correspondenceModel, userInteractor, repo);
                }
            }
        }
        logger.error("Could not find a PCM data type for namespace classifier reference " + reference + " Possible reason: no classifierReference found inside namespace classifier reference.");
        return null;
    }

    private static DataType getPCMDataTypeForClassifierReference(final ClassifierReference classifierReference, final EditableCorrespondenceModelView<?> correspondenceModel, final UserInteractor userInteractor, final Repository repo) {
        final Classifier classifier = classifierReference.getTarget();
        if (classifier != null) {
            if ("String".equalsIgnoreCase(classifier.getName())) {
                return PrimitiveTypesRepositoryLoader.getPrimitiveDataTypeString();
            }

            Set<DataType> dataTypes = null;
            try {
                dataTypes = IterableExtensions.toSet(Iterables.filter(correspondenceModel.getCorrespondingEObjects(classifier), DataType.class));
            } catch (final Throwable e) {
                logger.info("No correspondence found for classifier");
                return null;
            }

            if (dataTypes != null && !dataTypes.isEmpty()) {
                if (dataTypes.size() == 1) {
                    return dataTypes.iterator().next();
                } else {
                    logger.warn("More than one datatype correspondences found: returning the first");
                    return dataTypes.iterator().next();
                }
            }

            return TypeReferenceCorrespondenceHelper.createDataTypeForClassifier(classifier, correspondenceModel, userInteractor, repo);
        }
        return null;
    }

    private static DataType createDataTypeForClassifier(final Classifier classifier, final EditableCorrespondenceModelView<?> correspondenceModel, final UserInteractor userInteractor, final Repository repo) {
        if (classifier == null) {
            logger.warn("Classifier is null! Cannot create a data type for the classifier");
            return null;
        }
        Iterable<NamedElement> correspondingPCMEObjects = Iterables.filter(correspondenceModel.getCorrespondingEObjects(classifier), NamedElement.class);
        String correspondingWarning = "";
        if (correspondingPCMEObjects != null && !Iterables.isEmpty(correspondingPCMEObjects)) {
            correspondingWarning = "Warning: the classifier " + classifier.getName() + " already corresponds to the following PCM elements:";
            for (final NamedElement namedElement : correspondingPCMEObjects) {
                correspondingWarning += "\n" + namedElement.getClass().getSimpleName() + ": " + namedElement.getEntityName();
            }
        }

        CompositeDataType cdt = RepositoryFactory.eINSTANCE.createCompositeDataType();
        cdt.setEntityName(classifier.getName());
        cdt.setRepository__DataType(repo);
        correspondenceModel.addCorrespondenceBetween(cdt, classifier, null);
        return cdt;
    }

    private TypeReferenceCorrespondenceHelper() {
    }
}
