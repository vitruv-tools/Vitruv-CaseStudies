package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaClassBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaTypeHelper

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.*
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.*

class CompositeDataTypeMappingTransformationTestNew extends Pcm2JavaTransformationTest {
	
	@Test
	def void testAddCompositeDataType() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		
		changePcmView[
			modifySingleRepository[
				dataTypes__Repository += createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
			]
		]
		
		validateJavaView[
			val expectedCompilationUnit = new JavaClassBuilder(new JavaTypeHelper(),
				Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			).build
			
			assertCompilationUnits(List.of(expectedCompilationUnit))
		]
	}

	@Test
	def void testRenameCompositeDataType() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView[
			modifySingleRepository[
				dataTypes__Repository += createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
			]
		]
		
		changePcmView[
			modifySingleRepository[
				var dataType = dataTypes__Repository.filter(CompositeDataType).claimOne
				dataType.entityName = Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + Pcm2JavaTestUtils.RENAME
			]
		]
		
		validateJavaView[
			val expectedCompilationUnit = new JavaClassBuilder(new JavaTypeHelper(),
				Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + Pcm2JavaTestUtils.RENAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			).build
			
			assertCompilationUnits(List.of(expectedCompilationUnit))
		]
	}

	@Test
	def void testAddPrimitiveTypeToCompositeDataType() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView[
			modifySingleRepository[
				dataTypes__Repository += createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				dataTypes__Repository += createPrimitiveDataType(PrimitiveTypeEnum.INT)
			]
		]
		
		changePcmView[
			modifySingleRepository[
				val innerType = dataTypes__Repository.filter(PrimitiveDataType).claimOne
				val dataType = dataTypes__Repository.filter(CompositeDataType).claimOne
				dataType.innerDeclaration_CompositeDataType += createInnerDeclaration(innerType, Pcm2JavaTestUtils.INNER_DEC_NAME)
			]
		]
		
		validateJavaView[
			val typeHelper = new JavaTypeHelper()
			val expectedCompilationUnit = new JavaClassBuilder(typeHelper, 
					Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME, 
					Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
				)
				.addPrivateField(Pcm2JavaTestUtils.INNER_DEC_NAME, typeHelper.int)
				.addGetterForField(Pcm2JavaTestUtils.INNER_DEC_NAME)
				.addSetterForField(Pcm2JavaTestUtils.INNER_DEC_NAME)
				.build
			
			assertCompilationUnits(List.of(expectedCompilationUnit))
		]
	}
	
	@Test
	def void testAddCompositeDataTypeToCompositeDataType() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView[
			modifySingleRepository[
				dataTypes__Repository += createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				dataTypes__Repository += createCompositeDataType("InnerCompositeDataTypeTest")
			]
		]
		
		changePcmView[
			modifySingleRepository[
				val outerCompositeDataType = dataTypes__Repository.filter(CompositeDataType).filter(ctd | ctd.entityName == Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME).claimOne
				val innerCompositeDataType = dataTypes__Repository.filter(CompositeDataType).filter(ctd | ctd.entityName == "InnerCompositeDataTypeTest").claimOne
				outerCompositeDataType.innerDeclaration_CompositeDataType += createInnerDeclaration(innerCompositeDataType, Pcm2JavaTestUtils.INNER_DEC_NAME)
			]
		]
		
		validateJavaView[
			val typeHelper = new JavaTypeHelper()
			val innerDataTypeExpectedCompilationUnit = new JavaClassBuilder(typeHelper, 
					"InnerCompositeDataTypeTest", 
					Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
				)
				.build
			
			val outerDataTypeExpectedCompilationUnit = new JavaClassBuilder(typeHelper, 
					Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME, 
					Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
				)
				.addPrivateField(Pcm2JavaTestUtils.INNER_DEC_NAME, getReference(innerDataTypeExpectedCompilationUnit))
				.addGetterForField(Pcm2JavaTestUtils.INNER_DEC_NAME)
				.addSetterForField(Pcm2JavaTestUtils.INNER_DEC_NAME)
				.build
			
			assertCompilationUnits(List.of(innerDataTypeExpectedCompilationUnit, outerDataTypeExpectedCompilationUnit))
		]
	}
}
