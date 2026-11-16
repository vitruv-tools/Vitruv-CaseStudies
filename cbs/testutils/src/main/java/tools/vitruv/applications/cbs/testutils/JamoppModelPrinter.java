package tools.vitruv.applications.cbs.testutils;

import static org.emftext.language.java.classifiers.ClassifiersPackage.Literals.CLASS__DEFAULT_EXTENDS;
import static org.emftext.language.java.classifiers.ClassifiersPackage.Literals.CLASS__EXTENDS;
import static org.emftext.language.java.classifiers.ClassifiersPackage.Literals.INTERFACE__DEFAULT_EXTENDS;
import static org.emftext.language.java.classifiers.ClassifiersPackage.Literals.INTERFACE__EXTENDS;
import static org.emftext.language.java.commons.CommonsPackage.Literals.COMMENTABLE__LAYOUT_INFORMATIONS;
import static org.emftext.language.java.commons.CommonsPackage.Literals.NAMESPACE_AWARE_ELEMENT__NAMESPACES;
import static org.emftext.language.java.modifiers.ModifiersPackage.Literals.ANNOTABLE_AND_MODIFIABLE__ANNOTATIONS_AND_MODIFIERS;
import static org.emftext.language.java.types.TypesPackage.Literals.TYPED_ELEMENT__TYPE_REFERENCE;
import static tools.vitruv.change.testutils.printing.PrintMode.MULTI_LINE_LIST;
import static tools.vitruv.change.testutils.printing.PrintMode.SINGLE_LINE_LIST;
import static tools.vitruv.change.testutils.printing.PrintResult.NOT_RESPONSIBLE;
import static tools.vitruv.change.testutils.printing.PrintResult.PRINTED_NO_OUTPUT;
import static tools.vitruv.change.testutils.printing.PrintResultExtension.combine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.generics.ExtendsTypeArgument;
import org.emftext.language.java.generics.QualifiedTypeArgument;
import org.emftext.language.java.generics.SuperTypeArgument;
import org.emftext.language.java.generics.TypeArgument;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.TypeReference;

import tools.vitruv.change.testutils.printing.ModelPrinter;
import tools.vitruv.change.testutils.printing.PrintIdProvider;
import tools.vitruv.change.testutils.printing.PrintMode;
import tools.vitruv.change.testutils.printing.PrintResult;
import tools.vitruv.change.testutils.printing.PrintTarget;

/**
 * Prints JaMoPP models in a shortened, Java-like notation.
 */
public class JamoppModelPrinter implements ModelPrinter {
    private static final Set<EStructuralFeature> SHORTENED_REFERENCE_FEATURES = Set.of(INTERFACE__EXTENDS,
            INTERFACE__DEFAULT_EXTENDS, CLASS__EXTENDS, CLASS__DEFAULT_EXTENDS, TYPED_ELEMENT__TYPE_REFERENCE);

    private final ModelPrinter subPrinter;

    public JamoppModelPrinter(ModelPrinter subPrinter) {
        this.subPrinter = subPrinter;
    }

    public JamoppModelPrinter() {
        this.subPrinter = this;
    }

    @Override
    public PrintResult printObject(PrintTarget target, PrintIdProvider idProvider, Object object) {
        if (object instanceof Modifier) {
            return printObjectShortened(target, idProvider, object);
        }
        return NOT_RESPONSIBLE;
    }

    @Override
    public PrintResult printObjectShortened(PrintTarget target, PrintIdProvider idProvider, Object object) {
        return printShortenedJava(target, idProvider, object);
    }

    @Override
    public PrintResult printFeature(PrintTarget target, PrintIdProvider idProvider, EObject object,
            EStructuralFeature feature) {
        if (feature == COMMENTABLE__LAYOUT_INFORMATIONS) {
            return PRINTED_NO_OUTPUT;
        }
        return NOT_RESPONSIBLE;
    }

    @Override
    public PrintResult printFeatureValue(PrintTarget target, PrintIdProvider idProvider, EObject object,
            EStructuralFeature feature, Object value) {
        if (SHORTENED_REFERENCE_FEATURES.contains(feature)) {
            return printObjectShortened(target, idProvider, value);
        }
        return NOT_RESPONSIBLE;
    }

    @Override
    public PrintResult printFeatureValueList(PrintTarget target, PrintIdProvider idProvider, EObject object,
            EStructuralFeature feature, Collection<?> valueList) {
        if (feature == ANNOTABLE_AND_MODIFIABLE__ANNOTATIONS_AND_MODIFIERS) {
            return target.printList(valueList, SINGLE_LINE_LIST,
                    (subTarget, element) -> printShortenedJava(subTarget, idProvider, element));
        } else if (feature == NAMESPACE_AWARE_ELEMENT__NAMESPACES) {
            return target.printList(valueList, SINGLE_LINE_LIST,
                    (subTarget, element) -> subPrinter.printFeatureValue(subTarget, idProvider, object, feature,
                            element));
        }
        return NOT_RESPONSIBLE;
    }

    private PrintResult printShortenedJava(PrintTarget target, PrintIdProvider idProvider, Object object) {
        return switch (object) {
            case null -> NOT_RESPONSIBLE;
            case PrimitiveType primitive -> target.print(toFirstLower(primitive.eClass().getName()));
            case Modifier modifier -> target.print(toFirstLower(modifier.eClass().getName()));
            case TypeArgument typeArgument -> printTypeArgument(target, idProvider, typeArgument);
            case ClassifierReference reference -> printClassifierReference(target, idProvider, reference);
            case NamespaceClassifierReference reference -> {
                if (reference.getClassifierReferences().size() == 1) {
                    yield subPrinter.printObjectShortened(target, idProvider,
                            reference.getClassifierReferences().get(0));
                }
                throw new UnsupportedOperationException("I don’t know what this means!");
            }
            case ClassifierImport classifierImport -> target.print(classifierImport.getClassifier().getQualifiedName());
            case Import theImport -> printImport(target, theImport);
            default -> NOT_RESPONSIBLE;
        };
    }

    private PrintResult printTypeArgument(PrintTarget target, PrintIdProvider idProvider,
            TypeArgument typeArgument) {
        List<TypeReference> typeReferences;
        PrintResult boundResult;
        int arrayDimension = typeArgument.getArrayDimensionsAfter().size()
                + typeArgument.getArrayDimensionsBefore().size();
        if (typeArgument instanceof ExtendsTypeArgument extendsArgument) {
            typeReferences = extendsArgument.getExtendTypes();
            boundResult = target.print("? extends ");
        } else if (typeArgument instanceof SuperTypeArgument superArgument) {
            typeReferences = List.of(superArgument.getSuperType());
            boundResult = target.print("? super ");
        } else if (typeArgument instanceof QualifiedTypeArgument qualifiedArgument) {
            typeReferences = List.of(qualifiedArgument.getTypeReference());
            boundResult = PRINTED_NO_OUTPUT;
        } else {
            throw new IllegalArgumentException("Unsupported type argument: " + typeArgument);
        }
        PrintResult referencesResult = target.printIterableElements(typeReferences, SINGLE_LINE_LIST,
                (subTarget, reference) -> printObjectShortened(subTarget, idProvider, reference));
        PrintResult arrayResult = target.print("[]".repeat(arrayDimension));
        return combine(List.of(boundResult, referencesResult, arrayResult));
    }

    private PrintResult printClassifierReference(PrintTarget target, PrintIdProvider idProvider,
            ClassifierReference reference) {
        PrintResult nameResult = target.print(getBestName(reference.getTarget()));
        PrintResult argumentsResult = PRINTED_NO_OUTPUT;
        if (!reference.getTypeArguments().isEmpty()) {
            argumentsResult = target.printIterable("<", ">", reference.getTypeArguments(), SINGLE_LINE_LIST,
                    (subTarget, argument) -> subPrinter.printObjectShortened(subTarget, idProvider, argument));
        }
        return combine(List.of(nameResult, argumentsResult));
    }

    private static PrintResult printImport(PrintTarget target, Import theImport) {
        List<String> importedNames = new ArrayList<>();
        theImport.getImportedClassifiers().forEach(classifier -> importedNames.add(classifier.getQualifiedName()));
        theImport.getImportedMembers().stream().map(NamedElement::getName).forEach(importedNames::add);
        PrintMode printMode = importedNames.size() > 1 ? MULTI_LINE_LIST : SINGLE_LINE_LIST;
        return target.printList(importedNames, printMode, (subTarget, name) -> subTarget.print(name));
    }

    public String getBestName(Classifier classifier) {
        if (classifier instanceof ConcreteClassifier concreteClassifier
                && concreteClassifier.getContainingPackageName() != null) {
            return concreteClassifier.getQualifiedName();
        }
        return classifier.getName();
    }

    private static String toFirstLower(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        return Character.toLowerCase(string.charAt(0)) + string.substring(1);
    }

    @Override
    public ModelPrinter withSubPrinter(ModelPrinter subPrinter) {
        return new JamoppModelPrinter(subPrinter);
    }
}
