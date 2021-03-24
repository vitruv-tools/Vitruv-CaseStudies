package tools.vitruv.applications.pcmjava.depinjecttransformations.pcm2java

import java.util.ArrayList
import java.util.HashMap
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.annotations.AnnotationInstance
import org.emftext.language.java.annotations.AnnotationsFactory
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.references.IdentifierReference
import org.emftext.language.java.references.MethodCall
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.statements.ExpressionStatement
import org.emftext.language.java.statements.Statement
import org.emftext.language.java.statements.StatementsFactory
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypesFactory
import org.palladiosimulator.pcm.core.composition.AssemblyConnector
import org.palladiosimulator.pcm.core.composition.AssemblyContext
import org.palladiosimulator.pcm.core.composition.ComposedStructure
import org.palladiosimulator.pcm.core.composition.CompositionFactory
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.system.System
import tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.UserInteractor

import static tools.vitruv.domains.java.util.JavaModificationUtil.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import edu.kit.ipd.sdq.activextendannotations.Utility
import org.palladiosimulator.pcm.repository.Repository

/**
 * 
 * @author Alexander Monev
 */
 @Utility
class PcmJamoppUtilsGuice {

	static val Logger logger = Logger.getLogger(PcmJamoppUtilsGuice.simpleName)

	public static final int SELECT_KEEP_OLD_BINDING = 0
	public static final int SELECT_REPLACE_WITH_NEW_BINDING = 1

	def static createConfigureMethodForAssemblyContext(AssemblyContext assemblyContext, RepositoryComponent component,
		CorrespondenceModel correspondenceModel, UserInteractor userInteractor) {
		var Class jaMoPPCompositeClass = null
		try {
			jaMoPPCompositeClass = correspondenceModel.getCorrespondingEObjectsByType(
				assemblyContext.parentStructure__AssemblyContext, Class).claimOne

		} catch (RuntimeException e) {
			val String msg = "Can not create field for component " + component.entityName +
				" because the component does not have a corresponding class yet."
			userInteractor.notificationDialogBuilder.message(msg).windowModality(WindowModality.MODELESS).
				startInteraction()
			throw e
		}

		PcmJamoppUtilsGuice.addGuiceModuleInterfaceToClass(jaMoPPCompositeClass)
		val classMethod = PcmJamoppUtilsGuice.addConfigureMethodToModule(jaMoPPCompositeClass)
		return classMethod
	}

	def static EObject[] createBindCall(AssemblyContext assemblyContext, RepositoryComponent component,
		ClassMethod configureMethod, CorrespondenceModel correspondenceModel, UserInteractor userInteractor) {

		if (configureMethod.parameters.nullOrEmpty) {
			return null
		}

		val system = assemblyContext.parentStructure__AssemblyContext
		val implClass = CorrespondenceModelUtil.getCorrespondingEObjectsByType(correspondenceModel, component, Class).
			claimOne

		var AssemblyConnector acToRemove = null
		var updateSystem = false

		for (OperationProvidedRole providedRole : component.providedRoles_InterfaceProvidingEntity.filter(
			OperationProvidedRole)) {

			val opInterface = providedRole.providedInterface__OperationProvidedRole
			val interfaceClass = CorrespondenceModelUtil.getCorrespondingEObjectsByType(correspondenceModel,
				opInterface, Interface).claimOne
			var keepOldBinding = false

			val expressionStatements = configureMethod.statements.filter(ExpressionStatement)
			for (ExpressionStatement expr : expressionStatements) {

				val interfaceAndClassName = PcmJamoppUtilsGuice.returnInterfaceAndClassNameForBindExpression(expr)

				if (interfaceAndClassName !== null) {
					val interfaceName = interfaceAndClassName.key
					val className = interfaceAndClassName.value

					if (className !== null && interfaceName == interfaceClass.name) {
						if (className != implClass.name) { // this condition is to avoid adding the same statement twice
						// A binding for opInterface already exists and the new binding binds it to a different implementation
						// The user must decide whether to only keep the old binding, or replace it with the new one	
							if (PcmJamoppUtilsGuice.
								checkIfUserWantsToReplaceInterfaceBinding(interfaceClass.name, implClass.name,
									userInteractor)) {
								// Remove old binding from statements
								configureMethod.statements.remove(expr)
								// Remove old assembly connector
								acToRemove = PcmJamoppUtilsGuice.
									findAssemblyConnectorForOperationInterface(opInterface, system)
							} else {
								keepOldBinding = true
							}
						}
					}
				}

			}

			if (!keepOldBinding) {
				// add bind call
				PcmJamoppUtilsGuice.addBindCallToConfigureMethod(configureMethod, interfaceClass.name, implClass.name)

				// create assembly connector
				if (PcmJamoppUtilsGuice.createAssemblyConnector(opInterface, component, assemblyContext, providedRole,
					system, userInteractor) !== null) {
					updateSystem = true
				}

			}
		}

		val affectedClass = configureMethod.containingConcreteClassifier
		PcmJamoppUtilsGuice.saveResourceForClass(affectedClass)

		if (acToRemove !== null) {
			EcoreUtil.remove(acToRemove)
			updateSystem = true
		}

		if (updateSystem) {
			PcmJamoppUtilsGuice.saveResourceForSystem(system)
		}

		return null

	}

	def static ensureConstructorWithInjectAnnotation(Class jaMoPPClass) {
		val constructor = JavaMemberAndParameterUtil.getOrCreateConstructorToClass(jaMoPPClass)
		if (!checkConstructorForInjectionTag(constructor)) {
			addInjectToConstructor(constructor, jaMoPPClass)
		}
		return constructor
	}

	def static addInjectToConstructor(Constructor constructor, ConcreteClassifier jaMoPPClass) {
		// Add @Inject tag
		val annotation = AnnotationsFactory.eINSTANCE.createAnnotationInstance()
		val classifierReference = createNamespaceClassifierReferenceForName("javax.inject", "Inject")
		val Classifier classifier = classifierReference.classifierReferences.get(0).target
		annotation.setAnnotation(classifier)
		constructor.annotationsAndModifiers.add(0, annotation)
		addImportToClassFromString(jaMoPPClass, #["javax", "inject"], "Inject")
	}

	def static boolean checkConstructorForInjectionTag(Constructor constructor) {
		val annotations = constructor.annotationsAndModifiers.filter(AnnotationInstance)
		for (AnnotationInstance annotationInstance : annotations) {
			if (annotationInstance.annotation.name == "Inject") {
				return true
			}
		}
		return false
	}

	def static addGuiceModuleInterfaceToClass(Class jaMoPPClass) {
		addImportToClassFromString(jaMoPPClass, #["com", "google", "inject"], "Binder")
		addImportToClassFromString(jaMoPPClass, #["com", "google", "inject"], "Module")
	}

	def static addConfigureMethodToModule(Class jaMoPPClass) {
		val classifierReference = createNamespaceClassifierReferenceForName("", "Binder")
		val classifier = classifierReference.classifierReferences.get(0)

		// Check if configure method has already been added before
		// TODO: check if method signature is equivalent to (Binder binder)
		val checkForExistingConfigureMethod = jaMoPPClass.members.filter(ClassMethod).filter [ method |
			method.name == "configure"
		]
		if (!checkForExistingConfigureMethod.nullOrEmpty) {
			return checkForExistingConfigureMethod.get(0)
		}

		val ClassMethod method = MembersFactory.eINSTANCE.createClassMethod
		method.name = "configure"
		method.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
		method.typeReference = TypesFactory.eINSTANCE.createVoid

		val Parameter parameter = createOrdinaryParameter(classifier, "binder")
		method.parameters.add(parameter)
		jaMoPPClass.members.add(method)

		val Interface jaMoPPInterface = ClassifiersFactory.eINSTANCE.createInterface
		jaMoPPInterface.name = "Module"
		val namespaceClassifierRef = createNamespaceClassifierReference(jaMoPPInterface)

		// Check if "implements Module" has already been added before
		if (checkIfClassImplementsInterface(jaMoPPClass, jaMoPPInterface)) {
			return method
		}

		jaMoPPClass.implements.add(namespaceClassifierRef)
		return method
	}

	// TODO: check if a similar method exists in POJO utils
	private def static checkIfClassImplementsInterface(Class jaMoPPClass, Classifier jaMoPPInterface) {
		val ClassifierReference classifierRef = TypesFactory.eINSTANCE.createClassifierReference
		classifierRef.target = EcoreUtil.copy(jaMoPPInterface)
		return ( !jaMoPPClass.implements.filter(NamespaceClassifierReference).filter [ ref |
			EcoreUtil.equals(ref, classifierRef)
		].nullOrEmpty)
	}

	def static updateConfigure(Repository repository, ConcreteClassifier affectedClass, ClassMethod oldMethod, ClassMethod newMethod,
		CorrespondenceModel ci, UserInteractor userInteractor) {
		// logger.info("Update statements in configure method")
		var saveSystemResource = false
		var saveClassResource = false

		// Save all mappings between assembly connectors and bindings, so we can easily add/remove a connector if needed
		val system = ci.getCorrespondingEObjectsByType(affectedClass, System).claimOne
		val interfaceToConnectorMappings = returnMappingsBetweenConnectorsAndInterfaceBindings(system, ci)

		val oldStatements = oldMethod.statements
		val newStatements = newMethod.statements
		val oldMinusNew = new ArrayList(oldStatements)
		val newMinusOld = new ArrayList(newStatements)
		// Save all mappings (before the code change) in a HashMap, so we can easily check if a mapping already exists
		val interfaceToImplMappings = new HashMap<String, String>()
		// Directly comparing statements with equals doesn't work, so we compare their arguments as strings instead
		// For the old statements before code change we've already got the HashMap, since we assume there are no multiple
		// bindings for the same interface in the old statements, as that would cause a runtime exception by Guice,
		// and we would also warn the user when such a case occurs.
		val newMappings = new ArrayList<Pair<String, String>>

		// TODO: refactor filter statements
		for (ExpressionStatement expr : oldStatements.filter(ExpressionStatement)) {
			val interfaceAndClassName = returnInterfaceAndClassNameForBindExpression(expr)
			val interfaceName = interfaceAndClassName.key
			val className = interfaceAndClassName.value
			if (interfaceName !== null) {
				// logger.info("oldstatement bind " + interfaceName + " to " + className)
				interfaceToImplMappings.put(interfaceName, className)
			}

		}

		for (ExpressionStatement expr : newStatements.filter(ExpressionStatement)) {
			val interfaceAndClassName = returnInterfaceAndClassNameForBindExpression(expr)
			val interfaceName = interfaceAndClassName.key
			// val className = interfaceAndClassName.value
			if (interfaceName !== null) {
				// logger.info("newstatement bind " + interfaceName + " to " + className)
				newMappings.add(interfaceAndClassName)
			}
		}

		val iterOldMinusNew = oldMinusNew.listIterator
		while (iterOldMinusNew.hasNext) {
			val next = iterOldMinusNew.next
			if (newMappings.contains(returnInterfaceAndClassNameForBindExpression(next as ExpressionStatement))) {
				iterOldMinusNew.remove
			}
		}

		val iterNewMinusOld = newMinusOld.listIterator
		while (iterNewMinusOld.hasNext) {
			val next = iterNewMinusOld.next
			val interfaceAndClassName = returnInterfaceAndClassNameForBindExpression(next as ExpressionStatement)
			val interfaceName = interfaceAndClassName.key
			val className = interfaceAndClassName.value
			if (interfaceName !== null && interfaceToImplMappings.get(interfaceName) == className) {
				iterNewMinusOld.remove
			}
		}

		// Check if any statement has been removed or modified
		if (!oldMinusNew.empty) {
			// logger.info("statement removed or modified")
			for (ExpressionStatement expr : oldMinusNew.filter(ExpressionStatement)) {
				val interfaceAndClassName = returnInterfaceAndClassNameForBindExpression(expr)
				if (interfaceAndClassName !== null) {
					val interfaceName = interfaceAndClassName.key
					val className = interfaceAndClassName.value
					// TODO: differentiate between modified and removed statement
					if (className !== null) {
						EcoreUtil.remove(interfaceToConnectorMappings.get(interfaceName))
						saveSystemResource = true
					}
				}
			}
		}

		// Check if any statement has been added or modified
		if (!newMinusOld.empty) {
			// logger.info("statement added or modified")
			for (ExpressionStatement expr : newMinusOld.filter(ExpressionStatement)) {
				val interfaceAndClassName = returnInterfaceAndClassNameForBindExpression(expr)
				if (interfaceAndClassName !== null) {
					val interfaceName = interfaceAndClassName.key
					val className = interfaceAndClassName.value
					if (className !== null) {
						val classAlreadyMappedToInterface = interfaceToImplMappings.get(interfaceName)
						if (classAlreadyMappedToInterface !== null && classAlreadyMappedToInterface != className) {

							if (!newMappings.contains(interfaceAndClassName)) {
								// An existing statement has been modified -> only update the assembly connector
								// logger.info("existing statement modified -> modify assembly context/connector")
								val acToUpdate = interfaceToConnectorMappings.get(interfaceName)
								if (acToUpdate !== null) {
									// change providing assembly context of connector
									updateProvidedRoleOfAssemblyConnector(acToUpdate,
										findOperationInterfaceByName(repository, interfaceName),
										findBasicComponentByName(repository, className, ci), system, userInteractor)
								}
							} else {
								// Another statement mapping the same interface exists and a new one has been added
								// logger.info("mapping for interface already exists")
								if (checkIfUserWantsToReplaceInterfaceBinding(interfaceName, className,
									userInteractor)) {
									// Remove old binding and keep newly added one
									// logger.info("remove old binding and keep newly added one")
									removeStatementForInterfaceAndClassName(newMethod.statements, interfaceName,
										classAlreadyMappedToInterface)
									val acToUpdate = interfaceToConnectorMappings.get(interfaceName)
									// change providing assembly context of connector
									updateProvidedRoleOfAssemblyConnector(acToUpdate,
										findOperationInterfaceByName(repository, interfaceName),
										findBasicComponentByName(repository, className, ci), system, userInteractor)
								} else {
									// Remove newly added binding and keep old one
									// logger.info("remove newly added binding and keep old one")
									newMethod.statements.remove(expr)
								}

								saveClassResource = true
							}
							saveSystemResource = true

						} else if (classAlreadyMappedToInterface === null) {
							// This is the first mapping for the interface -> create new assembly connector
							// logger.info("first mapping for interface -> create new assembly connector")
							createAssemblyConnector(
								findOperationInterfaceByName(repository, interfaceName),
								findBasicComponentByName(repository, className, ci),
								null,
								null,
								system,
								userInteractor
							)
							saveSystemResource = true
						}

					}
				}
			}
		}

		if (saveSystemResource) {
			saveResourceForSystem(system)
		}
		if (saveClassResource) {
			saveResourceForClass(affectedClass)
		}

	}

	def static checkIfUserWantsToReplaceInterfaceBinding(String interfaceName, String className, UserInteractor ui) {
		val msg = "Interface " + interfaceName + " is already mapped to basic component " + className +
			" . Adding another binding for the same interface will lead to a runtime exception by Guice."
		val choice = ui.singleSelectionDialogBuilder.message(msg).choices(
			#["Keep old binding", "Replace with new binding"]).windowModality(WindowModality.MODAL)
		switch choice {
			case SELECT_KEEP_OLD_BINDING: {
				return false
			}
			case SELECT_REPLACE_WITH_NEW_BINDING: {
				return true
			}
		}
		return false
	}

	// ================================================================================
	// Helper methods for manipulating the configure method
	// ================================================================================
	private def static returnMappingsBetweenConnectorsAndInterfaceBindings(System system, CorrespondenceModel ci) {
		val assemblyConnectors = system.connectors__ComposedStructure.filter(AssemblyConnector)
		val interfaceToConnectorMappings = new HashMap<String, AssemblyConnector>()
		for (AssemblyConnector ac : assemblyConnectors) {
			val providedRole = ac.providedRole_AssemblyConnector
			if (providedRole !== null) {
				val opInterface = providedRole.providedInterface__OperationProvidedRole
				if (opInterface !== null) {
					val interfaceClass = ci.getCorrespondingEObjectsByType(opInterface, Interface).claimOne
					interfaceToConnectorMappings.put(interfaceClass.name, ac)
				}
			}
		}
		return interfaceToConnectorMappings
	}

	def static returnInterfaceAndClassNameForBindExpression(ExpressionStatement expr) {
		val binderReference = expr.expression as IdentifierReference
		if (binderReference !== null) {
			val bindCall = binderReference.next as MethodCall
			if (!bindCall.arguments.nullOrEmpty) {
				val interfaceRef = bindCall.arguments.get(0) as IdentifierReference
				val interfaceName = interfaceRef.target.name.split("\\.").get(0)
				if (bindCall.next !== null) {
					if (bindCall.next instanceof MethodCall) {
						val toCall = bindCall.next as MethodCall
						if (!toCall.arguments.nullOrEmpty) {
							val classRef = toCall.arguments.get(0) as IdentifierReference
							val className = classRef.target.name.split("\\.").get(0)
							return interfaceName -> className
						}
					}
				}

			}
		}
	}

	private def static interfaceAndClassNameEqualBindExpressionArguments(ExpressionStatement expr, String interfaceName,
		String className) {
		val interfaceAndClassName = returnInterfaceAndClassNameForBindExpression(expr)
		if (interfaceAndClassName !== null) {
			val interfaceNameInExpression = interfaceAndClassName.key
			if (interfaceNameInExpression == interfaceName) {
				val classNameInExpression = interfaceAndClassName.value
				if (classNameInExpression == className) {
					return true
				}
			}
		}
		return false
	}

	def static addBindCallToConfigureMethod(ClassMethod configureMethod, String interfaceName, String implClassName) {

		for (ExpressionStatement expr : configureMethod.statements.filter(ExpressionStatement)) {
			if (interfaceAndClassNameEqualBindExpressionArguments(expr, interfaceName, implClassName)) {
				return null // avoid adding the same statement twice
			}
		}

		val expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
		val binderReference = ReferencesFactory.eINSTANCE.createIdentifierReference

		// create "binder."
		binderReference.target = configureMethod.parameters.get(0)
		expressionStatement.expression = binderReference

		// create "bind(Comp.class)."
		val ClassMethod bindMethod = MembersFactory.eINSTANCE.createClassMethod
		bindMethod.name = "bind"
		val classTypeReference = createNamespaceClassifierReferenceForName("", "Class")
		bindMethod.parameters.add(createOrdinaryParameter(classTypeReference, interfaceName + ".class"))
		val MethodCall bindCall = ReferencesFactory.eINSTANCE.createMethodCall
		bindCall.target = bindMethod

		val bindParameterStatement = StatementsFactory.eINSTANCE.createExpressionStatement
		val bindParamReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		bindParamReference.target = bindMethod.parameters.get(0)
		bindParameterStatement.expression = bindParamReference
		bindCall.arguments.add(bindParameterStatement.expression)

		// create "to(CompImpl.class)"
		val ClassMethod toMethod = MembersFactory.eINSTANCE.createClassMethod
		toMethod.name = "to"
		toMethod.parameters.add(createOrdinaryParameter(classTypeReference, implClassName + ".class"))
		val MethodCall toCall = ReferencesFactory.eINSTANCE.createMethodCall
		toCall.target = toMethod

		val toParameterStatement = StatementsFactory.eINSTANCE.createExpressionStatement
		val toParamReference = ReferencesFactory.eINSTANCE.createIdentifierReference
		toParamReference.target = toMethod.parameters.get(0)
		toParameterStatement.expression = toParamReference
		toCall.arguments.add(toParameterStatement.expression)

		// create "bind(Comp.class).to(CompImpl.class)"
		bindCall.next = toCall

		// create "binder.bind(Comp.class).to(CompImpl.class)"
		binderReference.next = bindCall
		configureMethod.statements.add(expressionStatement)
		return expressionStatement
	}

	private def static removeStatementForInterfaceAndClassName(EList<Statement> statements, String interfaceName,
		String className) {
		for (ExpressionStatement expr : statements.filter(ExpressionStatement)) {
			if (interfaceAndClassNameEqualBindExpressionArguments(expr, interfaceName, className)) {
				statements.remove(expr)
				return
			}
		}
	}

	def static removeStatementForAssemblyConnector(ClassMethod configureMethod, AssemblyConnector assemblyConnector,
		CorrespondenceModel ci) {
		val opInterface = assemblyConnector.providedRole_AssemblyConnector.providedInterface__OperationProvidedRole
		val implClass = ci.getCorrespondingEObjectsByType(
			assemblyConnector.providingAssemblyContext_AssemblyConnector.encapsulatedComponent__AssemblyContext, Class).
			claimOne
		val interfaceClass = ci.getCorrespondingEObjectsByType(opInterface, Interface).claimOne
		for (ExpressionStatement expr : configureMethod.statements.filter(ExpressionStatement)) {
			if (interfaceAndClassNameEqualBindExpressionArguments(expr, interfaceClass.name, implClass.name)) {
				configureMethod.statements.remove(expr)
			}
		}
	}

	// ================================================================================
	// Helper methods for assembly context transformation (code to PCM)
	// ================================================================================
	// This method may take null as input for the assembly context and the operation provided role.
	// (only occurs when transforming code to PCM)
	// If the assembly context is null, an assembly context is created for the basic component before creating
	// the assembly connector.
	// The operation provided role in the input may also be null. If it's null,
	// the method checks the basic component for a role providing the given operation interface.
	def static createAssemblyConnector(
		OperationInterface opInterface,
		RepositoryComponent component,
		AssemblyContext assemblyContext,
		OperationProvidedRole providedRole,
		ComposedStructure system,
		UserInteractor userInteractor
	) {

		var ac = assemblyContext
		if (ac === null) {
			ac = CompositionFactory.eINSTANCE.createAssemblyContext
			ac.setEntityName("assemblyContext_" + component.entityName)
			ac.setEncapsulatedComponent__AssemblyContext(component)
			ac.setParentStructure__AssemblyContext(system)
		}

		val acWithRequiredRole = findRequiredRoleInSystem(opInterface, system)
		if (acWithRequiredRole !== null) {
			// create assembly connector
			val assemblyConnector = CompositionFactory.eINSTANCE.createAssemblyConnector
			assemblyConnector.setProvidingAssemblyContext_AssemblyConnector(ac)
			assemblyConnector.setRequiringAssemblyContext_AssemblyConnector(acWithRequiredRole.key)
			assemblyConnector.setEntityName(
				"assemblyConnector_" + ac.encapsulatedComponent__AssemblyContext.entityName + "_" +
					acWithRequiredRole.key.encapsulatedComponent__AssemblyContext.entityName)
			var provRole = providedRole
			if (provRole === null) {
				provRole = findProvidedRoleForComponentAndInterface(component, opInterface)
			}
			assemblyConnector.setProvidedRole_AssemblyConnector(provRole)
			assemblyConnector.setRequiredRole_AssemblyConnector(acWithRequiredRole.value)
			system.getConnectors__ComposedStructure.add(assemblyConnector)
			return assemblyConnector
		} else {
			for (ProvidedDelegationConnector connector : system.connectors__ComposedStructure.filter(
				ProvidedDelegationConnector)) {
				val OperationProvidedRole sysProvidedRole = connector.innerProvidedRole_ProvidedDelegationConnector
				val providedInterface = sysProvidedRole.providedInterface__OperationProvidedRole
				if (providedInterface !== null && providedInterface.equals(opInterface)) {
					return null // system operation required role -> don't display warning
				}
			}
			val String msg = "Can not create assembly connector providing interface " + opInterface.entityName +
				" because no operation requiring role for interface " + opInterface.entityName +
				" was found in the system. Only assembly context for " + component.entityName + " was created."
			userInteractor.notificationDialogBuilder.message(msg).windowModality(WindowModality.MODELESS).
				startInteraction()
			return null
		}
	}

	private def static updateProvidedRoleOfAssemblyConnector(AssemblyConnector assemblyConnector,
		OperationInterface opInterface, RepositoryComponent component, ComposedStructure system,
		UserInteractor userInteractor) {

		var ac = findAssemblyContextForBasicComponent(component, system)
		if (ac === null) {
			ac = CompositionFactory.eINSTANCE.createAssemblyContext
			ac.setEntityName("assemblyContext_" + component.entityName)
			ac.setEncapsulatedComponent__AssemblyContext(component)
			ac.setParentStructure__AssemblyContext(system)
		}

		assemblyConnector.setProvidingAssemblyContext_AssemblyConnector(ac)
		val providedRole = findProvidedRoleForComponentAndInterface(component, opInterface)
		assemblyConnector.setProvidedRole_AssemblyConnector(providedRole)
	}

	private def static findRequiredRoleInSystem(OperationInterface opInterface, ComposedStructure system) {

		for (AssemblyContext ac : system.assemblyContexts__ComposedStructure) {
			val comp = ac.encapsulatedComponent__AssemblyContext
			for (OperationRequiredRole requiredRole : comp.requiredRoles_InterfaceRequiringEntity.filter(
				OperationRequiredRole)) {
				val reqInterface = requiredRole.requiredInterface__OperationRequiredRole
				if (reqInterface.equals(opInterface)) {
					return ac -> requiredRole
				}
			}
		}
		// TODO: does it make any sense to look for a required delegation connector?
		for (RequiredDelegationConnector connector : system.connectors__ComposedStructure.filter(
			RequiredDelegationConnector)) {
			val OperationRequiredRole requiredRole = connector.innerRequiredRole_RequiredDelegationConnector
			val reqInterface = requiredRole.requiredInterface__OperationRequiredRole
			if (reqInterface !== null && reqInterface.equals(opInterface)) {
				return connector.assemblyContext_RequiredDelegationConnector -> requiredRole
			}
		}

		return null
	}

	// TODO: check if a similar method exists in POJO utils
	private def static findProvidedRoleForComponentAndInterface(RepositoryComponent component,
		OperationInterface opInterface) {
		for (OperationProvidedRole providedRole : component.providedRoles_InterfaceProvidingEntity.filter(
			OperationProvidedRole)) {
			val providedInterface = providedRole.providedInterface__OperationProvidedRole
			if (providedInterface.equals(opInterface)) {
				return providedRole
			}
		}

		return null
	}

	// TODO: check if a similar method exists in POJO utils
	def static findAssemblyConnectorForOperationInterface(OperationInterface opInterface, ComposedStructure system) {
		for (AssemblyConnector ac : system.getConnectors__ComposedStructure.filter(AssemblyConnector)) {
			if (ac.providedRole_AssemblyConnector.providedInterface__OperationProvidedRole.equals(opInterface)) {
				return ac
			}
		}
	}

	// TODO: check if a similar method exists in POJO utils
	private def static findAssemblyContextForBasicComponent(RepositoryComponent component, ComposedStructure system) {
		for (AssemblyContext ac : system.assemblyContexts__ComposedStructure) {
			if (ac.encapsulatedComponent__AssemblyContext.equals(component)) {
				return ac
			}
		}
	}

	// TODO: check if a similar method exists in POJO utils
	private def static findBasicComponentByName(Repository repository, String entityName, CorrespondenceModel ci) {
		for (BasicComponent comp : repository.components__Repository.filter(BasicComponent)) {
			try {
				val implClass = ci.getCorrespondingEObjectsByType(comp, Class).claimOne
				if (implClass.name == entityName) {
					return comp
				}
			} catch (IllegalArgumentException ex) {
				// because of java.lang.IllegalArgumentException: Invalid type given EmptyModelImpl
				logger.trace(ex)
			}
		}

		return null
	}

	// TODO: check if a similar method exists in POJO utils
	private def static findOperationInterfaceByName(Repository repository, String entityName) {
		for (OperationInterface opInterface : repository.interfaces__Repository.filter(OperationInterface)) {
			if (opInterface.entityName == entityName) {
				return opInterface
			}
		}
		return null
	}

	def static saveResourceForClass(ConcreteClassifier jaMoPPClass) {
		jaMoPPClass.eResource.save(null)
	}

	def static saveResourceForSystem(ComposedStructure system) {
		system.eResource.save(null)
	}

	def static EObject[] createBindCallForConnector(AssemblyContext assemblyContext,
		AssemblyConnector assemblyConnector, CorrespondenceModel correspondenceModel, UserInteractor userInteractor) {
		val configureMethod = correspondenceModel.getCorrespondingEObjectsByType(assemblyContext, ClassMethod).claimOne
		configureMethod.statements.removeAll(configureMethod.statements)

		val system = assemblyContext.parentStructure__AssemblyContext
		val assemblyConnectors = system.connectors__ComposedStructure.filter(AssemblyConnector)

		val newOpInterface = assemblyConnector.providedRole_AssemblyConnector.providedInterface__OperationProvidedRole
		var ExpressionStatement newStatement = null
		var removeNewBinding = false
		var AssemblyConnector connectorToRemove = null

		for (AssemblyConnector ac : assemblyConnectors) {
			if (!configureMethod.parameters.nullOrEmpty) {
				val implClass = correspondenceModel.getCorrespondingEObjectsByType(
					ac.providingAssemblyContext_AssemblyConnector.encapsulatedComponent__AssemblyContext, Class).
					claimOne

				val opInterface = ac.providedRole_AssemblyConnector.providedInterface__OperationProvidedRole
				val interfaceClass = correspondenceModel.getCorrespondingEObjectsByType(opInterface, Interface).claimOne

				var removeOldBinding = false
				// A binding for opInterface already exists -> check if the new binding binds it to a different implementation
				// If that is the case, the user must decide whether to only keep the old binding, or replace it with the new one	
				if (opInterface.equals(newOpInterface) && !ac.equals(assemblyConnector) &&
					!ac.providingAssemblyContext_AssemblyConnector.encapsulatedComponent__AssemblyContext.equals(
						assemblyConnector.providingAssemblyContext_AssemblyConnector.
							encapsulatedComponent__AssemblyContext)) {
					if (PcmJamoppUtilsGuice.
						checkIfUserWantsToReplaceInterfaceBinding(interfaceClass.name, implClass.name,
							userInteractor)) {
						// Old binding won't be readded to the list of statements
						removeOldBinding = true
						// Remove old assembly connector
						connectorToRemove = ac
					} else {
						if (newStatement !== null) { // New binding has already been readded to the list of statements
						// -> remove it
							configureMethod.statements.remove(newStatement)
						} else { // New binding will be handled later in the loop -> remember not to add it
							removeNewBinding = true
						}
						// Remove newly created assembly connector
						connectorToRemove = assemblyConnector
					}
				}

				if (!removeOldBinding && !(removeNewBinding && ac.equals(assemblyConnector))) {
					val expressionStatement = PcmJamoppUtilsGuice.addBindCallToConfigureMethod(configureMethod,
						interfaceClass.name, implClass.name)

					if (expressionStatement !== null && ac.equals(assemblyConnector)) {
						newStatement = expressionStatement
					} else if (expressionStatement === null &&
						ac.requiringAssemblyContext_AssemblyConnector ==
							assemblyConnector.requiringAssemblyContext_AssemblyConnector &&
						ac.requiredRole_AssemblyConnector == assemblyConnector.requiredRole_AssemblyConnector) {
						// don't add equal assembly connectors
						connectorToRemove = assemblyConnector
					}
				}
			}
		}

		val affectedClass = configureMethod.containingConcreteClassifier
		PcmJamoppUtilsGuice.saveResourceForClass(affectedClass)

		if (connectorToRemove !== null) {
			EcoreUtil.remove(connectorToRemove)
			PcmJamoppUtilsGuice.saveResourceForSystem(system)
		}

		return null

	}

}
