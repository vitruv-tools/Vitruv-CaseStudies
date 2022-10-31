package tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper

import edu.kit.ipd.sdq.commons.util.java.Pair
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.imports.Import
import org.emftext.language.java.imports.ImportsFactory
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.statements.StatementsFactory
import org.emftext.language.java.types.TypeReference

import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

/**
 * Caution: the FluentJavaClassBuilder does not check for correctness of the builder API calls.
 * This means that the caller has to ensure that references to fields (e.g. for getters, setters,
 * initializations, ...) reference existing fields. Also the Builder doesn't prevent adding
 * duplicated members (e.g. adding a getter for a field twice results in two identical getter methods).
 */
class FluentJavaClassBuilder{
	
	// === data ===
	boolean addConstructor
	final String className
	final String namespace
	final List<Import> classImports
	final List<TypeReference> implementedTypes
	TypeReference extendsType
	final List<FieldInformation> fields
	final List<String> gettersForField
	final List<String> settersForField
	final List<MethodDescription> classMethods
	final List<String> constructorInitializations
	final List<Pair<String, ConstructorArguments>> constructorConstructions
		
	new(String className, String namespace) {
		this.addConstructor = false
		
		this.className = className
		this.namespace = namespace
		this.classImports = new ArrayList<Import>()
		this.implementedTypes = new ArrayList<TypeReference>()
		this.extendsType = null
		this.fields = new ArrayList<FieldInformation>()
		this.gettersForField = new ArrayList<String>()
		this.settersForField = new ArrayList<String>()
		this.classMethods = new ArrayList<MethodDescription>()
		this.constructorInitializations = new ArrayList<String>()
		this.constructorConstructions = new ArrayList<Pair<String, ConstructorArguments>>()
	}
	
	// === builder-API ===
	
	def FluentJavaClassBuilder addImportWithNamespace(CompilationUnit importedCompilationUnit){
		var import = ImportsFactory.eINSTANCE.createClassifierImport
		import.namespaces += importedCompilationUnit.namespaces
		import.classifier = importedCompilationUnit.classifiers.claimOne
		
		this.classImports += import
		return this
	}
	
	def FluentJavaClassBuilder addImportWithoutNamespace(CompilationUnit importedCompilationUnit){
		var import = ImportsFactory.eINSTANCE.createClassifierImport
		import.classifier = importedCompilationUnit.classifiers.claimOne
		
		this.classImports += import
		return this
	}
	
	def FluentJavaClassBuilder addImplements(TypeReference type) {
		this.implementedTypes += type
		return this
	}
	
	def FluentJavaClassBuilder setExtends(TypeReference type){
		this.extendsType = type
		return this
	}
	
	def FluentJavaClassBuilder addPrivateField(String fieldName, TypeReference type) {
		this.fields.add(new FieldInformation(fieldName, type))
		return this
	}
	
	def FluentJavaClassBuilder addGetterForField(String fieldName) {
		this.gettersForField += fieldName
		return this
	}
	
	def FluentJavaClassBuilder addSetterForField(String fieldName) {
		this.settersForField += fieldName
		return this
	}
	
	def FluentJavaClassBuilder addMethod(MethodDescription description) {
		this.classMethods += description
		return this
	}
	
	def FluentJavaClassBuilder addConstructorInitalizationForField(String fieldName) {
		this.constructorInitializations += fieldName
		return this
	}
	
	def FluentJavaClassBuilder addConstructorConstructionForField(String fieldName, ConstructorArguments constructorArguments) {
		this.constructorConstructions += new Pair<String, ConstructorArguments>(fieldName, constructorArguments)
		return this
	}
	
	def FluentJavaClassBuilder addConstructor() {
		this.addConstructor = true
		return this
	}
	
	def CompilationUnit build() {
		val class = createClass [
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
			name = className
			implements += implementedTypes
			if(extendsType !== null) extends = extendsType
		]
		
		class.members += fields.map[createPrivateField_]
		if(addConstructor || !constructorInitializations.isEmpty || !constructorConstructions.isEmpty) class.members += createConstructor_(class)
		class.members += gettersForField.map[createGetterForField_(it, class)]
		class.members += settersForField.map[createSetterForField_(it, class)]
		
		class.members += classMethods.map[methodDescription | 
			val methodParameters = methodDescription.parameters.map[ parameterDescription |
				var parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter
				parameter.name = parameterDescription.name
				parameter.typeReference = EcoreUtil.copy(parameterDescription.type)
				return parameter
			]
			createClassMethod [
				name = methodDescription.name
				typeReference = methodDescription.returnType
				parameters += methodParameters
				annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
			]
		]
		
		return createCompilationUnit[
			name = namespace + "." + className + ".java"
			namespaces += namespace.split("\\.")
			classifiers += class
			imports += classImports
		]
	}
	
	// === internal builder ===
	
	private def Field createPrivateField_(FieldInformation fieldInformation) {
		createField[
			name = fieldInformation.name
			typeReference = fieldInformation.type
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPrivate
		]
	}
	
	private def ClassMethod createGetterForField_(String fieldName, Class classifier) {
		val field = classifier.fields.filter[name == fieldName].claimOne
		
		val identifiereReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		identifiereReference.target = field
		val returnStatement = StatementsFactory.eINSTANCE.createReturn
		returnStatement.returnValue = identifiereReference
		
		return createClassMethod[
			name = "get" + captialize(fieldName)
			typeReference = EcoreUtil.copy(field.typeReference)
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
			statements += returnStatement
		]
	}
	
	private def ClassMethod createSetterForField_(String fieldName, Class classifier) {
		val field = classifier.fields.filter[name == fieldName].claimOne
		val assignementHelper = new JavaParameterToFieldAssignmentHelper("set" + captialize(fieldName), EcoreUtil.copy(field.typeReference), field)
		
		return createClassMethod[
			name = "set" + captialize(fieldName)
			typeReference = createVoid()
			annotationsAndModifiers += ModifiersFactory.eINSTANCE.createPublic
			parameters += assignementHelper.parameter
			statements += assignementHelper.statement
		]
	}
	
	private def Constructor createConstructor_(Class classifier) {
		val constructor = MembersFactory.eINSTANCE.createConstructor
		
		constructor.name = classifier.name
		constructor.makePublic
		
		constructorConstructions.forEach[constructorDescription |
			val fieldName = constructorDescription.first
			val withNullLiteral = constructorDescription.second
			val field = classifier.fields.filter[name == constructorDescription.first].claimOne
			val assignementHelper = new JavaCreationToFieldAssignmentHelper(fieldName, EcoreUtil.copy(field.typeReference), field, withNullLiteral)
			
			constructor.statements += assignementHelper.statement
		]
		
		constructorInitializations.forEach[fieldName|
			val field = classifier.fields.filter[name == fieldName].claimOne
			val assignementHelper = new JavaParameterToFieldAssignmentHelper(fieldName, EcoreUtil.copy(field.typeReference), field)
			
			constructor.parameters += assignementHelper.parameter
			constructor.statements += assignementHelper.statement
		]
		
		return constructor
	}
}

class FieldInformation {
	public final String name
	public final TypeReference type
	
	new(String name, TypeReference type){
		this.name = name
		this.type = type
	}
}
