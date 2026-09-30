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
import static tools.vitruv.change.testutils.printing.PrintResultExtension.operator_plus;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
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

public class JamoppModelPrinter implements ModelPrinter {
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
		if (feature == INTERFACE__EXTENDS || feature == INTERFACE__DEFAULT_EXTENDS || feature == CLASS__EXTENDS
				|| feature == CLASS__DEFAULT_EXTENDS || feature == TYPED_ELEMENT__TYPE_REFERENCE) {
			return printObjectShortened(target, idProvider, value);
		}
		return NOT_RESPONSIBLE;
	}

	@Override
	public PrintResult printFeatureValueList(PrintTarget target, PrintIdProvider idProvider, EObject object,
			EStructuralFeature feature, Collection<?> valueList) {
		if (feature == ANNOTABLE_AND_MODIFIABLE__ANNOTATIONS_AND_MODIFIERS) {
			return target.<Object>printList(valueList, SINGLE_LINE_LIST,
					(subTarget, element) -> printShortenedJava(subTarget, idProvider, element));
		}
		if (feature == NAMESPACE_AWARE_ELEMENT__NAMESPACES) {
			return target.<Object>printList(valueList, SINGLE_LINE_LIST,
					(subTarget, element) -> subPrinter.printFeatureValue(subTarget, idProvider, object, feature,
							element));
		}
		return NOT_RESPONSIBLE;
	}

	private PrintResult printShortenedJava(PrintTarget target, PrintIdProvider idProvider, Object object) {
		return switch (object) {
		case ClassifierImport theImport -> target.print(theImport.getClassifier().getQualifiedName());
		case TypeArgument typeArgument -> printShortenedTypeArgument(target, idProvider, typeArgument);
		case Import theImport -> printShortenedImport(target, theImport);
		case Modifier modifier -> target.print(toFirstLower(modifier.eClass().getName()));
		case ClassifierReference reference -> printShortenedClassifierReference(target, idProvider, reference);
		case NamespaceClassifierReference reference -> {
			if (reference.getClassifierReferences().size() == 1) {
				yield subPrinter.printObjectShortened(target, idProvider, reference.getClassifierReferences().get(0));
			}
			throw new UnsupportedOperationException("I don’t know what this means!");
		}
		case PrimitiveType primitive -> target.print(toFirstLower(primitive.eClass().getName()));
		case null, default -> NOT_RESPONSIBLE;
		};
	}

	private PrintResult printShortenedTypeArgument(PrintTarget target, PrintIdProvider idProvider,
			TypeArgument typeArgument) {
		List<TypeReference> typeReferences;
		PrintResult prefixResult;
		int arrayDimension = typeArgument.getArrayDimensionsAfter().size()
				+ typeArgument.getArrayDimensionsBefore().size();
		switch (typeArgument) {
		case ExtendsTypeArgument extendsTypeArgument -> {
			typeReferences = extendsTypeArgument.getExtendTypes();
			prefixResult = target.print("? extends ");
		}
		case SuperTypeArgument superTypeArgument -> {
			typeReferences = List.of(superTypeArgument.getSuperType());
			prefixResult = target.print("? super ");
		}
		case QualifiedTypeArgument qualifiedTypeArgument -> {
			typeReferences = List.of(qualifiedTypeArgument.getTypeReference());
			prefixResult = PRINTED_NO_OUTPUT;
		}
		default -> throw new UnsupportedOperationException("Unsupported type argument: " + typeArgument);
		}
		PrintResult typeReferencesResult = target.printIterableElements(typeReferences, SINGLE_LINE_LIST,
				(subTarget, ref) -> printObjectShortened(subTarget, idProvider, ref));
		PrintResult arrayDimensionResult = target.print("[]".repeat(arrayDimension));
		return operator_plus(operator_plus(prefixResult, typeReferencesResult), arrayDimensionResult);
	}

	private PrintResult printShortenedClassifierReference(PrintTarget target, PrintIdProvider idProvider,
			ClassifierReference reference) {
		PrintResult nameResult = target.print(getBestName(reference.getTarget()));
		PrintResult typeArgumentsResult = PRINTED_NO_OUTPUT;
		if (!reference.getTypeArguments().isEmpty()) {
			typeArgumentsResult = target.printIterable("<", ">", reference.getTypeArguments(), SINGLE_LINE_LIST,
					(subTarget, argument) -> subPrinter.printObjectShortened(subTarget, idProvider, argument));
		}
		return operator_plus(nameResult, typeArgumentsResult);
	}

	private PrintResult printShortenedImport(PrintTarget target, Import theImport) {
		List<String> importedNames = Stream.concat(
				theImport.getImportedClassifiers().stream().map(ConcreteClassifier::getQualifiedName),
				theImport.getImportedMembers().stream().map(member -> member.getName()))
				.toList();
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
