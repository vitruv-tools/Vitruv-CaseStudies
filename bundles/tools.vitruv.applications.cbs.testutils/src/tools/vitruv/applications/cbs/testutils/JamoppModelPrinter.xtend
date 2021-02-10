package tools.vitruv.applications.cbs.testutils

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.generics.ExtendsTypeArgument
import org.emftext.language.java.generics.QualifiedTypeArgument
import org.emftext.language.java.generics.SuperTypeArgument
import org.emftext.language.java.generics.TypeArgument
import org.emftext.language.java.imports.ClassifierImport
import org.emftext.language.java.imports.Import
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.types.TypeReference
import tools.vitruv.testutils.printing.ModelPrinter
import tools.vitruv.testutils.printing.PrintTarget

import static org.emftext.language.java.classifiers.ClassifiersPackage.Literals.*
import static org.emftext.language.java.commons.CommonsPackage.Literals.*
import static org.emftext.language.java.modifiers.ModifiersPackage.Literals.*
import static org.emftext.language.java.types.TypesPackage.Literals.*
import static tools.vitruv.testutils.printing.PrintResult.*

import static extension tools.vitruv.testutils.printing.PrintResultExtension.*
import tools.vitruv.testutils.printing.PrintResult
import static tools.vitruv.testutils.printing.PrintMode.*
import tools.vitruv.testutils.printing.PrintIdProvider
import java.util.Collection

class JamoppModelPrinter implements ModelPrinter {
	val ModelPrinter subPrinter

	new(ModelPrinter subPrinter) {
		this.subPrinter = subPrinter
	}

	new() {
		this.subPrinter = this
	}

	override printObject(PrintTarget target, PrintIdProvider idProvider, Object object) {
		switch (object) {
			Modifier: printObjectShortened(target, idProvider, object)
			default: NOT_RESPONSIBLE
		}
	}

	override printObjectShortened(PrintTarget target, PrintIdProvider idProvider, Object object) {
		printShortenedJava(target, idProvider, object)
	}

	override printFeature(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature
	) {
		return switch (feature) {
			case COMMENTABLE__LAYOUT_INFORMATIONS: PRINTED_NO_OUTPUT
			default: NOT_RESPONSIBLE
		}
	}

	override PrintResult printFeatureValue(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature,
		Object value
	) {
		return switch (feature) {
			case INTERFACE__EXTENDS,
			case INTERFACE__DEFAULT_EXTENDS,
			case CLASS__EXTENDS,
			case CLASS__DEFAULT_EXTENDS,
			case TYPED_ELEMENT__TYPE_REFERENCE: target.printObjectShortened(idProvider, value)
			default: NOT_RESPONSIBLE
		}
	}

	override printFeatureValueList(
		PrintTarget target,
		PrintIdProvider idProvider,
		EObject object,
		EStructuralFeature feature,
		Collection<?> valueList
	) {
		switch (feature) {
			case ANNOTABLE_AND_MODIFIABLE__ANNOTATIONS_AND_MODIFIERS:
				target.printList(valueList, SINGLE_LINE) [ subTarget, element |
					subTarget.printShortenedJava(idProvider, element)
				]
			case NAMESPACE_AWARE_ELEMENT__NAMESPACES:
				target.printList(valueList, SINGLE_LINE) [ subTarget, element |
					subPrinter.printFeatureValue(subTarget, idProvider, object, feature, element)
				]
			default:
				NOT_RESPONSIBLE
		}
	}

	def private dispatch PrintResult printShortenedJava(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		PrimitiveType primitive
	) {
		print(primitive.eClass.name.toFirstLower)
	}

	def private dispatch PrintResult printShortenedJava(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		Modifier modifier
	) {
		print(modifier.eClass.name.toFirstLower)
	}

	def private dispatch PrintResult printShortenedJava(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		TypeArgument typeArgument
	) {
		var List<TypeReference> typeReferences
		val arrayDimension = typeArgument.arrayDimensionsAfter.size + typeArgument.arrayDimensionsBefore.size
		switch (typeArgument) {
			ExtendsTypeArgument: {
				typeReferences = typeArgument.extendTypes
				print('? extends ')
			}
			SuperTypeArgument: {
				typeReferences = #[typeArgument.superType]
				print('? super ')
			}
			QualifiedTypeArgument: {
				typeReferences = #[typeArgument.typeReference]
				PRINTED_NO_OUTPUT
			}
		} + //
		target.printIterableElements(typeReferences, SINGLE_LINE) [ subTarget, ref |
			printObjectShortened(subTarget, idProvider, ref)
		] + //
		print((0 ..< arrayDimension).join('')['[]'])
	}

	def private dispatch PrintResult printShortenedJava(
		PrintTarget target,
		PrintIdProvider idProvider,
		ClassifierReference reference
	) {
		target.print(reference.target.bestName) + //
		if (!reference.typeArguments.isEmpty) {
			target.printIterable('<', '>', reference.typeArguments, SINGLE_LINE) [ subTarget, argument |
				subPrinter.printObjectShortened(subTarget, idProvider, argument)
			]
		} else
			PRINTED_NO_OUTPUT
	}

	def private dispatch PrintResult printShortenedJava(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		NamespaceClassifierReference reference
	) {
		if (reference.classifierReferences.size == 1) {
			subPrinter.printObjectShortened(target, idProvider, reference.classifierReferences.get(0))
		} else {
			throw new UnsupportedOperationException("I don’t know what this means!")
		}
	}

	def getBestName(Classifier classifier) {
		switch (classifier) {
			ConcreteClassifier case classifier.containingPackageName !== null: classifier.qualifiedName
			default: classifier.name
		}
	}

	def private dispatch PrintResult printShortenedJava(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		ClassifierImport theImport
	) {
		print(theImport.classifier.qualifiedName)
	}

	def private dispatch PrintResult printShortenedJava(
		extension PrintTarget target,
		PrintIdProvider idProvider,
		Import theImport
	) {
		val importedNames = (
			theImport.importedClassifiers.map[qualifiedName] + theImport.importedMembers.map[name]
		).toList
		val printMode = if (importedNames.size > 1) MULTI_LINE else SINGLE_LINE
		target.printList(importedNames.toList, printMode)[extension subTarget, name|print(name)]
	}

	def private dispatch printShortenedJava(PrintTarget target, PrintIdProvider idProvider, Object object) {
		NOT_RESPONSIBLE
	}

	def private dispatch printShortenedJava(PrintTarget target, PrintIdProvider idProvider, Void object) {
		NOT_RESPONSIBLE
	}

	override withSubPrinter(ModelPrinter subPrinter) {
		new JamoppModelPrinter(subPrinter)
	}
}
