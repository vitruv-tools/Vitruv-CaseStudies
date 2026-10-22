package tools.vitruv.applications.util.temporary.java;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage.Literals;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.types.Char;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.Double;
import org.emftext.language.java.types.Float;
import org.emftext.language.java.types.Int;
import org.emftext.language.java.types.Long;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.Short;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypesFactory;
import tools.vitruv.applications.util.temporary.other.CorrespondenceRetriever;
import tools.vitruv.applications.util.temporary.uml.UmlTypeUtil;
import tools.vitruv.change.interaction.UserInteractor;
import tools.vitruv.change.interaction.UserInteractionOptions.WindowModality;
import tools.vitruv.change.interaction.builder.MultipleChoiceSelectionInteractionBuilder;

@Utility
public final class UmlJavaTypePropagationHelper {
    private static final Logger logger = Logger.getLogger(UmlJavaTypePropagationHelper.class.getSimpleName());
    private static final List<Class<?>> supportedCollectionTypes =
            Collections.unmodifiableList(CollectionLiterals.<Class<?>>newArrayList(
                    ArrayList.class, LinkedList.class, HashSet.class
            ));
    public static Class<?> userSelectCollectionType(UserInteractor userInteractor) {
        String selectTypeMsg = "Select a Collection type for the association end";
        Functions.Function1<Class<?>, String> _function = (it) -> it.getName();
        int selectedType = (Integer)((MultipleChoiceSelectionInteractionBuilder.OptionalSteps)userInteractor.getSingleSelectionDialogBuilder().message("Select a Collection type for the association end").choices(ListExtensions.map(supportedCollectionTypes, _function)).windowModality(WindowModality.MODAL)).startInteraction();
        return (Class)supportedCollectionTypes.get(selectedType);
    }

    protected static Type _getUmlTypeFromReference(Void jRef, CorrespondenceRetriever correspondenceRetriever) {
        return null;
    }

    protected static Type _getUmlTypeFromReference(TypeReference jRef, CorrespondenceRetriever correspondenceRetriever) {
        return null;
    }

    protected static Type _getUmlTypeFromReference(org.emftext.language.java.types.PrimitiveType jRef, CorrespondenceRetriever correspondenceRetriever) {
        return UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.mapJavaPrimitiveTypeToUmlPrimitiveType(jRef, correspondenceRetriever);
    }

    protected static Type _getUmlTypeFromReference(ClassifierReference jRef, CorrespondenceRetriever correspondenceRetriever) {
        PrimitiveType umlPrimitive = UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.mapJavaPrimitiveTypeToUmlPrimitiveType(jRef, correspondenceRetriever);
        if (umlPrimitive != null) {
            return umlPrimitive;
        } else {
            Classifier classifier = JavaTypeUtil.getNormalizedClassifierFromTypeReference(jRef);
            if (classifier != null) {
                EObject _retrieveCorrespondingElement = correspondenceRetriever.retrieveCorrespondingElement(classifier, Type.class, (String)null);
                return (Type)_retrieveCorrespondingElement;
            } else {
                return null;
            }
        }
    }

    protected static Type _getUmlTypeFromReference(NamespaceClassifierReference jRef, CorrespondenceRetriever correspondenceRetriever) {
        Type _xifexpression = null;
        boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(jRef.getClassifierReferences());
        boolean _not = !_isNullOrEmpty;
        if (_not) {
            _xifexpression = getUmlTypeFromReference((TypeReference)IterableExtensions.head(jRef.getClassifierReferences()), correspondenceRetriever);
        } else {
            _xifexpression = null;
        }

        return _xifexpression;
    }

    public static TypeReference createTypeReference(Type uType, Optional<? extends ConcreteClassifier> jType, TypeReference defaultReference, UserInteractor userInteractor) {
        return createTypeReference(
                uType,
                jType.isPresent() ? jType.get() : null,
                defaultReference,
                userInteractor
        );
    }

    public static TypeReference createTypeReference(Type uType, ConcreteClassifier jType, TypeReference defaultReference, UserInteractor userInteractor) {
        TypeReference typeReference = null;
        if (jType != null) {
            typeReference = JavaModificationUtil.createNamespaceClassifierReference(jType);
        } else if (uType != null && uType instanceof PrimitiveType) {
            typeReference = getJavaTypeReferenceForUmlPrimitiveType((PrimitiveType)uType);
        } else if (uType == null) {
            typeReference = defaultReference;
        }

        if (typeReference == null) {
            org.emftext.language.java.types.Type _target = defaultReference.getTarget();
            String _plus = "Something went wrong and no java::TypeReference could be created for:\n\t" + uType + "\nUsing " + _target;
            String _plus_1 = _plus + " instead.";
            CommonUtil.showMessage(userInteractor, _plus_1);
            typeReference = defaultReference;
        }

        return typeReference;
    }

    public static TypeReference getJavaTypeReferenceForUmlPrimitiveType(PrimitiveType umlPrimitiveType) {
        return UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.mapUmlPrimitiveTypeToJavaPrimitiveType(umlPrimitiveType);
    }

    public static boolean isSupportedUmlPrimitiveType(PrimitiveType umlPrimitiveType) {
        return UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.isSupportedUmlPrimitiveType(umlPrimitiveType);
    }

    public static Iterable<Pair<PrimitiveType, String>> getNotRegisteredPrimitiveTypesWithUnifiedNames(CorrespondenceRetriever correspondenceRetriever, ResourceSet resourceSet) {
        return UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.getNotRegisteredPrimitiveTypesWithUnifiedNames(correspondenceRetriever, resourceSet);
    }

    @XbaseGenerated
    public static Type getUmlTypeFromReference(TypeReference jRef, CorrespondenceRetriever correspondenceRetriever) {
        if (jRef instanceof ClassifierReference) {
            return _getUmlTypeFromReference((ClassifierReference)jRef, correspondenceRetriever);
        } else if (jRef instanceof NamespaceClassifierReference) {
            return _getUmlTypeFromReference((NamespaceClassifierReference)jRef, correspondenceRetriever);
        } else if (jRef instanceof org.emftext.language.java.types.PrimitiveType) {
            return _getUmlTypeFromReference((org.emftext.language.java.types.PrimitiveType)jRef, correspondenceRetriever);
        } else if (jRef != null) {
            return _getUmlTypeFromReference(jRef, correspondenceRetriever);
        } else if (jRef == null) {
            return _getUmlTypeFromReference((Void)null, correspondenceRetriever);
        } else {
            Object[] var10002 = new Object[]{jRef, correspondenceRetriever};
            throw new IllegalArgumentException("Unhandled parameter types: " + Arrays.asList(var10002).toString());
        }
    }

    private UmlJavaTypePropagationHelper() {
    }

    private static class PrimitiveTypesPropagation {
        private static final Map<String, UnifiedPrimitiveType> umlPrimitiveTypeNamesToUnifiedNames;

        private PrimitiveTypesPropagation() {
        }

        private static UnifiedPrimitiveType getUnifiedNameForUmlPrimitiveTypeName(PrimitiveType umlPrimitiveType) {
            return (UnifiedPrimitiveType)umlPrimitiveTypeNamesToUnifiedNames.get(StringExtensions.toFirstUpper(umlPrimitiveType.getName()));
        }

        private static UnifiedPrimitiveType getUnifiedNameForJavaPrimitiveTypeName(org.emftext.language.java.types.PrimitiveType javaPrimitiveType) {
            UnifiedPrimitiveType _switchResult = null;
            boolean _matched = false;
            if (javaPrimitiveType instanceof org.emftext.language.java.types.Boolean) {
                _matched = true;
                _switchResult = UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.BOOLEAN;
            }

            if (!_matched && javaPrimitiveType instanceof Char) {
                _matched = true;
                _switchResult = UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.CHAR;
            }

            if (!_matched && javaPrimitiveType instanceof Float) {
                _matched = true;
                _switchResult = UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.FLOAT;
            }

            if (!_matched && javaPrimitiveType instanceof Double) {
                _matched = true;
                _switchResult = UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.DOUBLE;
            }

            if (!_matched && javaPrimitiveType instanceof Int) {
                _matched = true;
                _switchResult = UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.INT;
            }

            if (!_matched && javaPrimitiveType instanceof Long) {
                _matched = true;
                _switchResult = UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.LONG;
            }

            if (!_matched && javaPrimitiveType instanceof Short) {
                _matched = true;
                _switchResult = UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.SHORT;
            }

            return _switchResult;
        }

        private static org.emftext.language.java.types.PrimitiveType unwrapWrappedPrimitiveType(TypeReference javaTypeReference) {
            Classifier classifier = JavaTypeUtil.getNormalizedClassifierFromTypeReference(javaTypeReference);
            if (classifier == null) {
                return null;
            } else {
                String qualifiedName = JavaTypeUtil.getQualifiedName(classifier);
                org.emftext.language.java.types.PrimitiveType _switchResult = null;
                if (qualifiedName != null) {
                    switch (qualifiedName) {
                        case "java.lang.Boolean" -> _switchResult = TypesFactory.eINSTANCE.createBoolean();
                        case "java.lang.Byte" -> _switchResult = TypesFactory.eINSTANCE.createByte();
                        case "java.lang.Character" -> _switchResult = TypesFactory.eINSTANCE.createChar();
                        case "java.lang.Double" -> _switchResult = TypesFactory.eINSTANCE.createDouble();
                        case "java.lang.Float" -> _switchResult = TypesFactory.eINSTANCE.createFloat();
                        case "java.lang.Integer" -> _switchResult = TypesFactory.eINSTANCE.createInt();
                        case "java.lang.Long" -> _switchResult = TypesFactory.eINSTANCE.createLong();
                        case "java.lang.Short" -> _switchResult = TypesFactory.eINSTANCE.createShort();
                        case "java.lang.Void" -> _switchResult = TypesFactory.eINSTANCE.createVoid();
                        default -> _switchResult = null;
                    }
                } else {
                    _switchResult = null;
                }

                return _switchResult;
            }
        }

        public static boolean isSupportedUmlPrimitiveType(PrimitiveType umlPrimitiveType) {
            UnifiedPrimitiveType _unifiedNameForUmlPrimitiveTypeName = getUnifiedNameForUmlPrimitiveTypeName(umlPrimitiveType);
            return _unifiedNameForUmlPrimitiveTypeName != null;
        }

        public static Iterable<Pair<PrimitiveType, String>> getNotRegisteredPrimitiveTypesWithUnifiedNames(CorrespondenceRetriever correspondenceRetriever, ResourceSet resourceSet) {
            Functions.Function1<PrimitiveType, Boolean> _function = (it) -> hasCorrespondingElement(it, correspondenceRetriever);
            Iterable<PrimitiveType> notRegisteredPrimitiveTypes = IterableExtensions.filter(UmlTypeUtil.getUmlPrimitiveTypes(resourceSet), _function);
            Functions.Function1<PrimitiveType, Pair<PrimitiveType, String>> _function_1 = (it) -> {
                String _string = getUnifiedNameForUmlPrimitiveTypeName(it).toString();
                return Pair.of(it, _string);
            };
            return IterableExtensions.map(notRegisteredPrimitiveTypes, _function_1);
        }

        private static boolean hasCorrespondingElement(PrimitiveType primitiveType, CorrespondenceRetriever correspondenceRetriever) {
            UnifiedPrimitiveType unifiedType = getUnifiedNameForUmlPrimitiveTypeName(primitiveType);
            if (unifiedType != null) {
                EObject alreadyRegisteredElement = correspondenceRetriever.retrieveCorrespondingElement(Literals.PRIMITIVE_TYPE, PrimitiveType.class, unifiedType.toString());
                return alreadyRegisteredElement == null;
            } else {
                return false;
            }
        }

        protected static PrimitiveType _mapJavaPrimitiveTypeToUmlPrimitiveType(TypeReference javaTypeReference, CorrespondenceRetriever correspondenceRetriever) {
            PrimitiveType _xblockexpression = null;
            Classifier classifier = JavaTypeUtil.getNormalizedClassifierFromTypeReference(javaTypeReference);
            PrimitiveType _xifexpression = null;
            if (classifier != null) {
                PrimitiveType _xblockexpression_1 = null;
                org.emftext.language.java.types.PrimitiveType unwrappedPrimitive = unwrapWrappedPrimitiveType(javaTypeReference);
                if (unwrappedPrimitive != null) {
                    return mapJavaPrimitiveTypeToUmlPrimitiveType(unwrappedPrimitive, correspondenceRetriever);
                }

                PrimitiveType _xifexpression_1 = null;
                if (!Objects.equals(JavaTypeUtil.getQualifiedName(classifier), "java.lang.String") && !Objects.equals(JavaTypeUtil.getQualifiedName(classifier), "java.lang.CharSequence")) {
                    return null;
                }

                EObject _retrieveCorrespondingElement = correspondenceRetriever.retrieveCorrespondingElement(Literals.PRIMITIVE_TYPE, PrimitiveType.class, UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.STRING.toString());
                _xifexpression_1 = (PrimitiveType)_retrieveCorrespondingElement;
                _xifexpression = _xifexpression_1;
            }

            return _xifexpression;
        }

        public static TypeReference mapUmlPrimitiveTypeToJavaPrimitiveType(PrimitiveType uType) {
            UnifiedPrimitiveType unifiedType = getUnifiedNameForUmlPrimitiveTypeName(uType);
            if (unifiedType == null) {
                String _name = uType.getName();
                String _plus = "(uml -> java) Unsupported uml::PrimitiveType with name: " + _name;
                String _plus_1 = _plus + "\n Please use the types defined in \"pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml\" ";
                String _plus_2 = _plus_1 + "or \"pathmap://UML_LIBRARIES/JavaPrimitiveTypes.library.uml\".";
                UmlJavaTypePropagationHelper.logger.warn(_plus_2);
                return TypesFactory.eINSTANCE.createVoid();
            } else {
                if (unifiedType != null) {
                    switch (unifiedType) {
                        case BOOLEAN -> {
                            return TypesFactory.eINSTANCE.createBoolean();
                        }
                        case BYTE -> {
                            return TypesFactory.eINSTANCE.createByte();
                        }
                        case CHAR -> {
                            return TypesFactory.eINSTANCE.createChar();
                        }
                        case FLOAT -> {
                            return TypesFactory.eINSTANCE.createFloat();
                        }
                        case DOUBLE -> {
                            return TypesFactory.eINSTANCE.createDouble();
                        }
                        case INT -> {
                            return TypesFactory.eINSTANCE.createInt();
                        }
                        case LONG -> {
                            return TypesFactory.eINSTANCE.createLong();
                        }
                        case SHORT -> {
                            return TypesFactory.eINSTANCE.createShort();
                        }
                        case STRING -> {
                            return JavaModificationUtil.createNamespaceClassifierReferenceForName("java.lang", "String");
                        }
                        case VOID -> {
                            return TypesFactory.eINSTANCE.createVoid();
                        }
                    }
                }

                return null;
            }
        }

        protected static PrimitiveType _mapJavaPrimitiveTypeToUmlPrimitiveType(org.emftext.language.java.types.PrimitiveType javaTypeReference, CorrespondenceRetriever correspondenceRetriever) {
            PrimitiveType _xblockexpression = null;
            UnifiedPrimitiveType unifiedPrimitiveType = getUnifiedNameForJavaPrimitiveTypeName(javaTypeReference);
            PrimitiveType _xifexpression = null;
            if (unifiedPrimitiveType != null) {
                EObject _retrieveCorrespondingElement = correspondenceRetriever.retrieveCorrespondingElement(Literals.PRIMITIVE_TYPE, PrimitiveType.class, unifiedPrimitiveType.toString());
                _xifexpression = (PrimitiveType)_retrieveCorrespondingElement;
            }

            return _xifexpression;
        }

        @XbaseGenerated
        public static PrimitiveType mapJavaPrimitiveTypeToUmlPrimitiveType(TypeReference javaTypeReference, CorrespondenceRetriever correspondenceRetriever) {
            if (javaTypeReference instanceof org.emftext.language.java.types.PrimitiveType) {
                return _mapJavaPrimitiveTypeToUmlPrimitiveType((org.emftext.language.java.types.PrimitiveType)javaTypeReference, correspondenceRetriever);
            } else if (javaTypeReference != null) {
                return _mapJavaPrimitiveTypeToUmlPrimitiveType(javaTypeReference, correspondenceRetriever);
            } else {
                Object[] var10002 = new Object[]{javaTypeReference, correspondenceRetriever};
                throw new IllegalArgumentException("Unhandled parameter types: " + Arrays.asList(var10002).toString());
            }
        }

        static {
            umlPrimitiveTypeNamesToUnifiedNames = Collections.unmodifiableMap(CollectionLiterals.newHashMap(new Pair[]{Pair.of("Boolean", UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.BOOLEAN), Pair.of("Bool", UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.BOOLEAN), Pair.of("Byte", UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.BYTE), Pair.of("Char", UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.CHAR), Pair.of("Float", UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.FLOAT), Pair.of("Real", UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.DOUBLE), Pair.of("Double", UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.DOUBLE), Pair.of("Long", UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.LONG), Pair.of("Short", UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.SHORT), Pair.of("Integer", UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.INT), Pair.of("Int", UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.INT), Pair.of("String", UmlJavaTypePropagationHelper.PrimitiveTypesPropagation.UnifiedPrimitiveType.STRING)}));
        }

        private static enum UnifiedPrimitiveType {
            BOOLEAN,
            BYTE,
            CHAR,
            FLOAT,
            DOUBLE,
            INT,
            LONG,
            SHORT,
            STRING,
            VOID;

            private UnifiedPrimitiveType() {
            }
        }
    }
}
