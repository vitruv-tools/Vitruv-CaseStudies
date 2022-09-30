package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import org.junit.jupiter.api.Test
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils

import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.*
import java.util.List
import org.palladiosimulator.pcm.repository.CompositeComponent

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import org.palladiosimulator.pcm.repository.BasicComponent
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.*
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.*
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaClassBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaTypeHelper
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaInterfaceBuilder

class CompositeComponentMappingTransformationTestNew extends Pcm2JavaTransformationTest {
	
	@Test
	def void testCreateCompositeComponent() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		
		changePcmView[
			modifySingleRepository[
				components__Repository += createCompositeComponent(Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME)	
			]
		]
		
		validateJavaView[
			val expectedCompilationUnit = new JavaClassBuilder(new JavaTypeHelper(), 
				Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME
			).build
			
			assertCompilationUnits(List.of(expectedCompilationUnit))
		]
	}
	
	@Test
	def void testAddProvidedRoleToCompositeComponent() {
		this.addRepositoryAndCompositeComponent()
		changePcmView[
			modifySingleRepository[
				interfaces__Repository += createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
			]
		]
		
		changePcmView[
			modifySingleRepository[
				var operationInterface = it.claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				var compositeComponent = claimComponent(Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME)
				compositeComponent.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(operationInterface, compositeComponent)
			]
		]
		
		validateJavaView[
			val interfaceCompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX).build
			val compositeComponentCompilationUnit = new JavaClassBuilder(new JavaTypeHelper(), 
				Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME
			)
			.addImplements(getReference(interfaceCompilationUnit))
			.build
			
			assertCompilationUnits(List.of(compositeComponentCompilationUnit, interfaceCompilationUnit))
		]
	}
	
	@Test
	def void testAddRequiredRoleToCompositeComponent() {
		this.addRepositoryAndCompositeComponent()
		changePcmView[
			modifySingleRepository[
				interfaces__Repository += createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
			]
		]
		
		changePcmView[
			modifySingleRepository[
				var operationInterface = it.claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				var compositeComponent = claimComponent(Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME)
				compositeComponent.requiredRoles_InterfaceRequiringEntity += createOperationRequiredRole(operationInterface, compositeComponent)
			]
		]
		
		validateJavaView[
			val interfaceCompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX).build
			val compositeComponentCompilationUnit = new JavaClassBuilder(new JavaTypeHelper(), 
				Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME
				)
				.addPrivateField(getRequiredInterfacFieldOrVariableName(Pcm2JavaTestUtils.INTERFACE_NAME, Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME), getReference(interfaceCompilationUnit))
				.addConstructorInitalizationForField(getRequiredInterfacFieldOrVariableName(Pcm2JavaTestUtils.INTERFACE_NAME, Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME))
				.addImportWithNamespace(interfaceCompilationUnit)
				.build
			
			assertCompilationUnits(List.of(compositeComponentCompilationUnit, interfaceCompilationUnit))
		]
	}
	
	@Test
	def void testAddAssemblyContextToCompositeComponent() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView[
			modifySingleRepository[
				it.components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				components__Repository += createCompositeComponent(Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME)	
			]
		]
		
		changePcmView[
			modifySingleRepository[
				var basicComponent = components__Repository.filter(BasicComponent).claimOne
				var compositeComponent = components__Repository.filter(CompositeComponent).claimOne
				compositeComponent.assemblyContexts__ComposedStructure += createAssemblyContext(basicComponent)
			]
		]
		
		
		validateJavaView [
			val typeHelper = new JavaTypeHelper()
			val basicComponentCompilationUnit = new JavaClassBuilder(typeHelper, 
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
				).build
			val compositeCompositeComponentCompilationUnit = new JavaClassBuilder(typeHelper,
				Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME
				)
				.addImportWithNamespace(basicComponentCompilationUnit)
				.addPrivateField("assemblyContext", getReference(basicComponentCompilationUnit))
				.addConstructorConstructionForField("assemblyContext")
				.build
			
			assertCompilationUnits(List.of(basicComponentCompilationUnit, compositeCompositeComponentCompilationUnit))
		]
	}
	
	@Test
	def void testAddRequiredDelegationRoleToCompositeComponent() {
		addRepositoryAndCompositeComponent()
		
		changePcmView[
			modifySingleRepository[
				interfaces__Repository += createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
			]
		]
		
		validateJavaView[
			val interfaceCompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX).build
			val compositeComponentCompilationUnit = new JavaClassBuilder(new JavaTypeHelper(), 
				Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME
			)
			.build
			
			assertCompilationUnits(List.of(compositeComponentCompilationUnit, interfaceCompilationUnit))
		]
	}
	
	def void addRepositoryAndCompositeComponent(){
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		
		changePcmView [
			modifySingleRepository [
				components__Repository += createCompositeComponent(Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME)	
			]
		]
	}
}
