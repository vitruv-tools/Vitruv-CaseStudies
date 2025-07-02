package tools.vitruv.applications.util.temporary.java;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.activextendannotations.Utility;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.generics.GenericsFactory;
import org.emftext.language.java.generics.QualifiedTypeArgument;
import org.emftext.language.java.generics.TypeArgument;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.ImportsFactory;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypedElement;
import org.emftext.language.java.types.TypesFactory;
import tools.vitruv.applications.util.temporary.other.UriUtil;

@Utility
public final class JavaTypeUtil {
    private static final Logger logger = Logger.getLogger(JavaTypeUtil.class.getSimpleName());
    private static final List<Class<?>> supportedCollectionTypes = Collections.unmodifiableList(
            CollectionLiterals.newArrayList(new Class<?>[]{ArrayList.class, LinkedList.class, HashSet.class}));


    private static Type _getTypeFromReference(TypeReference typeReference) {
        String _plus = String.valueOf(typeReference) + " is neither a NamespaceClassifierReference nor a PrimitiveType. Returning null.";
        logger.warn(_plus);
        return null;
    }

    private static Type _getTypeFromReference(Void nullTypeReference) {
        logger.warn("Cannot get Type of a null-TypeReference. Returning null.");
        return null;
    }

    private static Type _getTypeFromReference(PrimitiveType primitiveType) {
        return primitiveType;
    }

    private static Type _getTypeFromReference(ClassifierReference classifierReference) {
        return classifierReference.getTarget();
    }

    private static Type _getTypeFromReference(NamespaceClassifierReference namespace) {
        return namespace != null && !IterableExtensions.isNullOrEmpty(namespace.getClassifierReferences()) ? getTypeFromReference((TypeReference)IterableExtensions.head(namespace.getClassifierReferences())) : null;
    }

    public static Classifier getClassifierFromTypeReference(TypeReference typeRef) {
        Type type = getTypeFromReference(typeRef);
        if (type instanceof Classifier) {
            return (Classifier)type;
        } else {
            logger.warn("The TypeReference " + String.valueOf(typeRef) + " does not contain a Classifier. Returning null.");
            return null;
        }
    }

    public static Classifier getNormalizedClassifierFromTypeReference(TypeReference typeRef) {
        Type type = getTypeFromReference(typeRef);
        if (type instanceof Classifier) {
            boolean _eIsProxy = ((Classifier)type).eIsProxy();
            if (_eIsProxy) {
                Resource _eResource = ((Classifier)type).eResource();
                ResourceSet _resourceSet = null;
                if (_eResource != null) {
                    _resourceSet = _eResource.getResourceSet();
                }

                EObject _resolve = EcoreUtil.resolve(type, _resourceSet);
                type = (Classifier)_resolve;
            }

            UriUtil.normalizeURI(type);
            return (Classifier)type;
        } else {
            logger.warn("The TypeReference " + String.valueOf(typeRef) + " does not contain a Classifier. Returning null.");
            return null;
        }
    }

    public static Interface getInterfaceFromTypeReference(TypeReference typeRef) {
        Type type = getTypeFromReference(typeRef);
        if (type instanceof Interface) {
            return (Interface)type;
        } else {
            logger.warn("The TypeReference " + String.valueOf(typeRef) + " does not contain a Interface. Returning null.");
            return null;
        }
    }

    public static EList<NamespaceClassifierReference> createNamespaceReferenceFromList(List<? extends ConcreteClassifier> list) {
        BasicEList<NamespaceClassifierReference> typeReferences = new BasicEList();

        for(ConcreteClassifier i : list) {
            NamespaceClassifierReference _createNamespaceClassifierReference = JavaModificationUtil.createNamespaceClassifierReference(i);
            typeReferences.add(_createNamespaceClassifierReference);
        }

        return typeReferences;
    }

    public static TypeReference createCollectiontypeReference(String collectionQualifiedName, ConcreteClassifier innerTypeClass) {
        NamespaceClassifierReference innerTypeReference = JavaModificationUtil.createNamespaceClassifierReference(innerTypeClass);
        QualifiedTypeArgument qualifiedTypeArgument = GenericsFactory.eINSTANCE.createQualifiedTypeArgument();
        qualifiedTypeArgument.setTypeReference(innerTypeReference);
        NamespaceClassifierReference collectionClassNamespaceReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(collectionQualifiedName);
        EList<TypeArgument> _typeArguments = ((ClassifierReference)collectionClassNamespaceReference.getClassifierReferences().get(0)).getTypeArguments();
        _typeArguments.add(qualifiedTypeArgument);
        return collectionClassNamespaceReference;
    }

    public static void setTypeReference(TypedElement typedElement, TypeReference typeRef) {
        if (typeRef != null) {
            typedElement.setTypeReference(typeRef);
        } else {
            typedElement.setTypeReference(TypesFactory.eINSTANCE.createVoid());
        }

    }

    protected static boolean _typeReferenceEquals(TypeReference typeRef1, TypeReference typeRef2) {
        Logger var10000 = logger;
        String var10001 = String.valueOf(typeRef1);
        var10000.warn("No dispatch Method found for the typeReferences " + var10001 + " and " + String.valueOf(typeRef2) + ". Returning false.");
        return false;
    }

    protected static boolean _typeReferenceEquals(NamespaceClassifierReference typeRef1, NamespaceClassifierReference typeRef2) {
        return getClassifierFromTypeReference(typeRef1).getName().equals(getClassifierFromTypeReference(typeRef2).getName());
    }

    protected static boolean _typeReferenceEquals(PrimitiveType primType1, PrimitiveType primtype2) {
        return primType1.getClass().equals(primtype2.getClass());
    }

    protected static boolean _typeReferenceEquals(Void type1, Void typ22) {
        logger.warn("Both TypeReferences to compare are null. Returning true.");
        return true;
    }

    public static TypeReference getInnerTypeReferenceOfCollectionTypeReference(TypeReference typeRef) {
        if (typeRef instanceof NamespaceClassifierReference) {
            TypeArgument _head = (TypeArgument)IterableExtensions.head(((ClassifierReference)IterableExtensions.head(((NamespaceClassifierReference)typeRef).getClassifierReferences())).getTypeArguments());
            return ((QualifiedTypeArgument)_head).getTypeReference();
        } else {
            logger.warn("Cannot get inner TypeReference of a non-NamespaceClassifierReference. Returning null.");
            return null;
        }
    }

    public static boolean hasSameTargetReference(TypeReference reference1, TypeReference reference2) {
        if (!Objects.equals(reference1, reference2) && !reference1.equals(reference2)) {
            Classifier target1 = getClassifierFromTypeReference(reference1);
            Classifier target2 = getClassifierFromTypeReference(reference2);
            return Objects.equals(target1, target2) || target1.equals(target2);
        } else {
            return true;
        }
    }

    public static ArrayList<Interface> findImplementingInterfacesFromTypeRefs(EList<TypeReference> typeReferences) {
        ArrayList<Interface> implementingInterfaces = new ArrayList();

        for(TypeReference typeRef : typeReferences) {
            Classifier classifier = getNormalizedClassifierFromTypeReference(typeRef);
            if (classifier instanceof Interface) {
                implementingInterfaces.add((Interface)classifier);
            }
        }

        return implementingInterfaces;
    }

    public static String getQualifiedName(Classifier classifier) {
        if (classifier instanceof ConcreteClassifier) {
            String _xifexpression = null;
            CompilationUnit _containingCompilationUnit = ((ConcreteClassifier)classifier).getContainingCompilationUnit();
            boolean _tripleNotEquals = _containingCompilationUnit != null;
            if (_tripleNotEquals) {
                _xifexpression = IterableExtensions.join(((ConcreteClassifier)classifier).getContainingCompilationUnit().getNamespaces(), ".");
            } else {
                _xifexpression = "";
            }

            String _xifexpression_1 = null;
            boolean _notEquals = !Objects.equals(_xifexpression, "");
            if (_notEquals) {
                String _name = ((ConcreteClassifier)classifier).getName();
                _xifexpression_1 = _xifexpression + "." + _name;
            } else {
                _xifexpression_1 = ((ConcreteClassifier)classifier).getName();
            }

            return _xifexpression_1;
        } else {
            return classifier.getName();
        }
    }

    public static boolean isCollectionTypeReference(TypeReference jRef) {
        Classifier classifier = getNormalizedClassifierFromTypeReference(jRef);
        if (classifier != null) {
            String qualifiedName = getQualifiedName(classifier);
            Functions.Function1<Class<?>, Boolean> _function = (it) -> {
                String _name = it.getName();
                return Objects.equals(_name, qualifiedName);
            };
            boolean _exists = IterableExtensions.exists(supportedCollectionTypes, _function);
            if (_exists) {
                return true;
            }
        }

        return false;
    }

    protected static TypeReference _getInnerTypeRefOfCollectionReference(TypeReference collRef) {
        return null;
    }

    protected static TypeReference _getInnerTypeRefOfCollectionReference(ClassifierReference collRef) {
        TypeArgument typeArgument = (TypeArgument)IterableExtensions.head(collRef.getTypeArguments());
        if (typeArgument != null && typeArgument instanceof QualifiedTypeArgument) {
            TypeReference typeRef = ((QualifiedTypeArgument)typeArgument).getTypeReference();
            return typeRef;
        } else {
            return null;
        }
    }

    protected static TypeReference _getInnerTypeRefOfCollectionReference(NamespaceClassifierReference collRef) {
        TypeReference _xifexpression = null;
        boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(collRef.getClassifierReferences());
        boolean _not = !_isNullOrEmpty;
        if (_not) {
            _xifexpression = getInnerTypeRefOfCollectionReference((TypeReference)IterableExtensions.head(collRef.getClassifierReferences()));
        } else {
            _xifexpression = null;
        }

        return _xifexpression;
    }

    public static void addJavaImport(CompilationUnit compUnit, TypeReference jTypeRef) {
        if (!(jTypeRef instanceof PrimitiveType)) {
            if (jTypeRef.getTarget() != null && jTypeRef.getTarget() instanceof ConcreteClassifier) {
                Type _target = jTypeRef.getTarget();
                ConcreteClassifier jType = (ConcreteClassifier)_target;
                addJavaImport(compUnit, jType);
                if (jTypeRef instanceof NamespaceClassifierReference) {
                    EList<TypeArgument> typeArguments = ((ClassifierReference)((NamespaceClassifierReference)jTypeRef).getClassifierReferences().get(0)).getTypeArguments();
                    Functions.Function1<QualifiedTypeArgument, Boolean> _function = (it) -> {
                        TypeReference _typeReference = it.getTypeReference();
                        return _typeReference != null;
                    };

                    for(QualifiedTypeArgument typeArgument : IterableExtensions.filter(Iterables.filter(typeArguments, QualifiedTypeArgument.class), _function)) {
                        typeArgument.getTypeReference();
                        addJavaImport(compUnit, typeArgument.getTypeReference());
                    }
                }
            }

        }
    }

    public static ClassifierImport addJavaImport(CompilationUnit compUnit, ConcreteClassifier jType) {
        if (compUnit == null) {
            return null;
        } else if (jType != null && !(jType instanceof PrimitiveType)) {
            boolean samePackage = false;
            Functions.Function1<ClassifierImport, Boolean> _function = (it) -> {
                String _name = it.getClassifier().getName();
                String _name_1 = jType.getName();
                return Objects.equals(_name, _name_1);
            };
            boolean alreadyImported = IterableExtensions.exists(Iterables.filter(IterableExtensions.filterNull(compUnit.getImports()), ClassifierImport.class), _function);
            if (!alreadyImported) {
                ClassifierImport classifierImport = ImportsFactory.eINSTANCE.createClassifierImport();
                CompilationUnit _containingCompilationUnit = jType.getContainingCompilationUnit();
                boolean _tripleNotEquals = _containingCompilationUnit != null;
                if (_tripleNotEquals) {
                    EList<String> _namespaces = jType.getContainingCompilationUnit().getNamespaces();
                    boolean _tripleNotEquals_1 = _namespaces != null;
                    if (_tripleNotEquals_1) {
                        classifierImport.getNamespaces().addAll(jType.getContainingCompilationUnit().getNamespaces());
                    }
                }

                classifierImport.setClassifier(jType);
                compUnit.getImports().add(classifierImport);
                return classifierImport;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static NamespaceClassifierReference createCollectionTypeReference(NamespaceClassifierReference collectionTypeReference, TypeReference innerTypeReference) {
        TypeReference wrappedInnerReference = innerTypeReference;
        if (innerTypeReference instanceof PrimitiveType) {
            wrappedInnerReference = JavaModificationUtil.getWrapperTypeReferenceForPrimitiveType(innerTypeReference);
        }

        QualifiedTypeArgument qualifiedTypeArgument = GenericsFactory.eINSTANCE.createQualifiedTypeArgument();
        qualifiedTypeArgument.setTypeReference(wrappedInnerReference);
        EList<TypeArgument> _typeArguments = ((ClassifierReference)collectionTypeReference.getClassifierReferences().get(0)).getTypeArguments();
        _typeArguments.add(qualifiedTypeArgument);
        return collectionTypeReference;
    }

    public static NamespaceClassifierReference createCollectionTypeReference(ConcreteClassifier collectionTypeClassifier, TypeReference innerTypeReference) {
        NamespaceClassifierReference collectionTypeReference = JavaModificationUtil.createNamespaceClassifierReference(collectionTypeClassifier);
        return createCollectionTypeReference(collectionTypeReference, innerTypeReference);
    }

    public static NamespaceClassifierReference createCollectionTypeReference(Class<?> collectionType, TypeReference innerTypeReference) {
        String _name = collectionType.getName();
        String _simpleName = collectionType.getSimpleName();
        String _plus = "." + _simpleName;
        String collectionNamespace = _name.replace(_plus, "");
        String collectionSimpleName = collectionType.getSimpleName();
        NamespaceClassifierReference collectionTypeReference = JavaModificationUtil.createNamespaceClassifierReferenceForName(collectionNamespace, collectionSimpleName);
        return createCollectionTypeReference(collectionTypeReference, innerTypeReference);
    }

    @XbaseGenerated
    private static Type getTypeFromReference(TypeReference classifierReference) {
        if (classifierReference instanceof ClassifierReference) {
            return _getTypeFromReference((ClassifierReference)classifierReference);
        } else if (classifierReference instanceof NamespaceClassifierReference) {
            return _getTypeFromReference((NamespaceClassifierReference)classifierReference);
        } else if (classifierReference instanceof PrimitiveType) {
            return _getTypeFromReference((PrimitiveType)classifierReference);
        } else {
            return classifierReference != null ? _getTypeFromReference(classifierReference) : _getTypeFromReference((Void)null);
        }
    }

    @XbaseGenerated
    public static boolean typeReferenceEquals(TypeReference typeRef1, TypeReference typeRef2) {
        if (typeRef1 instanceof NamespaceClassifierReference && typeRef2 instanceof NamespaceClassifierReference) {
            return _typeReferenceEquals((NamespaceClassifierReference)typeRef1, (NamespaceClassifierReference)typeRef2);
        } else if (typeRef1 instanceof PrimitiveType && typeRef2 instanceof PrimitiveType) {
            return _typeReferenceEquals((PrimitiveType)typeRef1, (PrimitiveType)typeRef2);
        } else {
            return typeRef1 != null && typeRef2 != null ? _typeReferenceEquals(typeRef1, typeRef2) : _typeReferenceEquals((Void)null, (Void)null);
        }
    }

    @XbaseGenerated
    public static TypeReference getInnerTypeRefOfCollectionReference(TypeReference collRef) {
        if (collRef instanceof ClassifierReference) {
            return _getInnerTypeRefOfCollectionReference((ClassifierReference)collRef);
        } else if (collRef instanceof NamespaceClassifierReference) {
            return _getInnerTypeRefOfCollectionReference((NamespaceClassifierReference)collRef);
        } else if (collRef != null) {
            return _getInnerTypeRefOfCollectionReference(collRef);
        } else {
            Object[] var10002 = new Object[]{collRef};
            throw new IllegalArgumentException("Unhandled parameter types: " + Arrays.asList(var10002).toString());
        }
    }

    private JavaTypeUtil() {
    }
}
