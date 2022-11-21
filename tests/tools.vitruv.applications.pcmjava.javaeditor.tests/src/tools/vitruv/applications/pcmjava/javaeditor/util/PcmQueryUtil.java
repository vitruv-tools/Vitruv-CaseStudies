package tools.vitruv.applications.pcmjava.javaeditor.util;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;

import java.util.stream.Collectors;

import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.framework.views.View;

public class PcmQueryUtil {
	private PcmQueryUtil() {
	}

	public static Repository claimSingleRepository(View view) {
		return claimOne(view.getRootObjects(Repository.class));
	}

	public static System claimSingleSystem(View view) {
		return claimOne(view.getRootObjects(System.class));
	}

	public static Repository claimPcmRepository(View view, String repositoryName) {
		return claimOne(view.getRootObjects(Repository.class).stream()
				.filter(it -> repositoryName.equals(it.getEntityName())).collect(Collectors.toList()));
	}

	public static <T extends RepositoryComponent> T claimComponent(Repository repository, String componentName,
			Class<T> componentType) {
		return claimOne(repository.getComponents__Repository().stream().filter(componentType::isInstance)
				.map(componentType::cast).filter(it -> componentName.equals(it.getEntityName()))
				.collect(Collectors.toList()));
	}
	
	public static <T extends DataType & NamedElement> T claimDataType(Repository repository, String dataTypeName,
			Class<T> dataTypeType) {
		return claimOne(repository.getDataTypes__Repository().stream().filter(dataTypeType::isInstance)
				.map(dataTypeType::cast).filter(it -> dataTypeName.equals(it.getEntityName()))
				.collect(Collectors.toList()));
	}
}

//package tools.vitruv.applications.pcmjava.tests.pcm2java
//
//import edu.kit.ipd.sdq.activextendannotations.Utility
//import org.palladiosimulator.pcm.repository.CompositeDataType
//import org.palladiosimulator.pcm.repository.OperationInterface
//import org.palladiosimulator.pcm.repository.PrimitiveDataType
//import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
//import org.palladiosimulator.pcm.repository.Repository
//import org.palladiosimulator.pcm.repository.RepositoryComponent
//import org.palladiosimulator.pcm.system.System
//import tools.vitruv.framework.views.View
//
//import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
//
//@Utility
//class PcmQueryUtil {
//	static def Repository claimSinglePcmRepository(View view) {
//		view.getRootObjects(Repository).claimOne
//	}
//
//	static def System claimSinglePcmSystem(View view) {
//		view.getRootObjects(System).claimOne
//	}
//
//	static def Repository claimPcmRepository(View view, String repositoryName) {
//		view.getRootObjects(Repository).filter[it.entityName == repositoryName].claimOne
//	}
//
//	static def RepositoryComponent claimComponent(Repository repository, String componentName) {
//		repository.components__Repository.filter[it.entityName == componentName].claimOne
//	}
//
//	static def PrimitiveDataType claimPrimitiveDataType(Repository repository, PrimitiveTypeEnum primitiveType) {
//		repository.dataTypes__Repository.filter(PrimitiveDataType).filter[it.type == primitiveType].claimOne
//	}
//
//	static def CompositeDataType claimCompositeDataType(Repository repository, String datatypeName) {
//		repository.dataTypes__Repository.filter(CompositeDataType).filter[it.entityName == datatypeName].claimOne
//	}
//
//	static def OperationInterface claimOperationInterface(Repository repository, String interfaceName) {
//		repository.interfaces__Repository.filter(OperationInterface).filter[it.entityName == interfaceName].claimOne
//	}
//}