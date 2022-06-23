package tools.vitruv.applications.util.temporary.pcm;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.Repository;

public class PrimitiveTypesRepositoryLoader {
	private PrimitiveTypesRepositoryLoader() {
	}

	private static URI PRIMITIVETYPES_URI = URI.createURI("pathmap://PCM_MODELS/PrimitiveTypes.repository");

	private static Repository primitiveTypesRepository;
	private static Map<String, PrimitiveDataType> primitives = new HashMap<String, PrimitiveDataType>();

	/**
	 * Retrieves a map of {@link PrimitiveDataType}s as defined in the
	 * standard PCM resource repository.
	 * 
	 * @return A cached map of primitive data types.
	 */
	public static Map<String, PrimitiveDataType> getPrimitiveDataTypes() {
		if (primitiveTypesRepository == null) {
			primitiveTypesRepository = getPrimitiveTypesRepository();
			for (DataType d : primitiveTypesRepository.getDataTypes__Repository()) {
				if (d instanceof PrimitiveDataType) {
					PrimitiveDataType pdt = (PrimitiveDataType) d;
					primitives.put(pdt.getType().getName(), pdt);
				}
			}
		}
		return primitives;
	}

	private static Repository getPrimitiveTypesRepository() {
		if (primitiveTypesRepository != null) {
			return primitiveTypesRepository;
		}

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("repository", new XMIResourceFactoryImpl());

		ResourceSet resSet = new ResourceSetImpl();
		Resource resource = resSet.getResource(PRIMITIVETYPES_URI, true);

		primitiveTypesRepository = (Repository) resource.getContents().get(0);
		return primitiveTypesRepository;
	}

	public static PrimitiveDataType getPrimitiveDataTypeString() {
		return getPrimitiveDataTypes().get("STRING");
	}

}
