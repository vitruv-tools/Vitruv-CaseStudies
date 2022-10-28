package tools.vitruv.applications.pcmjava.tests.pcm2java

import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class Pcm2JavaTestUtils {
	// === names ===
	public static final String REPOSITORY_NAME = "testRepository";
	public static final String REPOSITORY_NAME_EXPECTED = "TestRepository";
	public static final String BASIC_COMPONENT_NAME = "TestBasicComponent";
	public static final String BASIC_COMPONENT_NAME_SEC = "TestBasicComponentSecond";
	public static final String INTERFACE_NAME = "TestInterface";
	public static final String RENAME = "Rename";
	public static final String OPERATION_SIGNATURE_1_NAME = "TestOperationSignature1";
	public static final String PARAMETER_NAME = "testParameterName";
	public static final String COMPOSITE_DATA_TYPE_NAME = "CompositeDataType";
	public static final String INNER_DEC_NAME = "testInnerDec";
	public static final String SYSTEM_NAME = "TestSystem";
	public static final String SYSTEM_NAME_CAMELCASE = "testSystem";
	public static final String ASSEMBLY_CONTEXT_NAME = "assemblyContext";
	public static final String COMPOSITE_COMPONENT_NAME = "TestCompositeComponent";
	public static final String COLLECTION_DATA_TYPE_NAME = "TestCollectionDatatype";
	
	public static final String IMPL_SUFIX = "Impl"
	
	// === namespaces ===
	public static final String DATATYPES_SUFIX = ".datatypes"
	public static final String CONTRACTS_SUFIX = ".contracts"
	
	// === datatypes ===
	public static final String VOID_COMPILATIONUNIT_NAME = "java.lang.Void.java"
	public static final String INTEGER_COMPILATIONUNIT_NAME = "java.lang.Integer.java"
	public static final String ARRAYLIST_COMPILATIONUNIT_NAME = "java.util.ArrayList.java"
}
