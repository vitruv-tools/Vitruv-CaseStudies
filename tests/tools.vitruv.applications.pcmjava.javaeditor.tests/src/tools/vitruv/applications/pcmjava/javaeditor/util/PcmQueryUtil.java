package tools.vitruv.applications.pcmjava.javaeditor.util;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;

import java.util.Collection;
import java.util.stream.Collectors;

import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Interface;
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
		return claimNamedElement(repository.getComponents__Repository(), componentName, componentType);
	}

	public static <T extends Interface> T claimInterface(Repository repository, String interfaceName,
			Class<T> interfaceType) {
		return claimNamedElement(repository.getInterfaces__Repository(), interfaceName, interfaceType);
	}

	public static <T extends DataType & NamedElement> T claimDataType(Repository repository, String dataTypeName,
			Class<T> dataTypeType) {
		return claimNamedElement(repository.getDataTypes__Repository(), dataTypeName, dataTypeType);
	}
	
	public static <T extends NamedElement> T claimNamedElement(Collection<T> elements, String name) {
		return claimOne(elements.stream().filter(it -> name.equals(it.getEntityName())).collect(Collectors.toList()));
	}

	public static <T extends NamedElement> T claimNamedElement(Collection<?> elements, String name, Class<T> type) {
		return claimOne(elements.stream().filter(type::isInstance).map(type::cast)
				.filter(it -> name.equals(it.getEntityName())).collect(Collectors.toList()));
	}
}
