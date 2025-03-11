package tools.vitruv.applications.cbs.commonalities.pcm;

import static com.google.common.base.Preconditions.checkState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.Repository;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Represents PCM's default primitive datatypes.
 */
// TODO Xtend does not support enums with members yet.
public enum PcmPrimitiveDataType {

	INT(PrimitiveTypeEnum.INT),
	STRING(PrimitiveTypeEnum.STRING),
	BOOL(PrimitiveTypeEnum.BOOL),
	DOUBLE(PrimitiveTypeEnum.DOUBLE),
	CHAR(PrimitiveTypeEnum.CHAR),
	BYTE(PrimitiveTypeEnum.BYTE);
	// TODO Missing in PCM's PrimitiveTypes.repository and therefore not supported by us currently.
	// LONG(PrimitiveTypeEnum.LONG);

	private static final Logger logger = LogManager.getLogger(PcmPrimitiveDataType.class);

	private static final Map<PrimitiveTypeEnum, PcmPrimitiveDataType> byPcmTypeEnum = new HashMap<>();

	static {
		for (PcmPrimitiveDataType type : values()) {
			byPcmTypeEnum.put(type.getPcmTypeEnum(), type);
		}
	}

	/**
	 * Gets the {@link PcmPrimitiveDataType} by its
	 * {@link PcmPrimitiveDataType#getPcmTypeEnum() PCM type enum}.
	 * 
	 * @param pcmTypeEnum
	 *        the PCM type enum
	 * @return the PcmPrimitiveDataType, not <code>null</code>
	 * @throws IllegalArgumentException
	 *         if no PcmPrimitiveDataType is found
	 */
	public static PcmPrimitiveDataType getByPcmTypeEnum(PrimitiveTypeEnum pcmTypeEnum) {
		PcmPrimitiveDataType type = byPcmTypeEnum.get(pcmTypeEnum);
		if (type == null) {
			throw new IllegalArgumentException("Could not find PcmPrimitiveDataType for PCM type enum '"
					+ pcmTypeEnum + "'!");
		}
		return type;
	}

	// Loading of PCM's PrimitiveDataTypes:

	private static final URI PCM_PRIMITIVE_TYPES_URI = URI.createURI("pathmap://PCM_MODELS/PrimitiveTypes.repository");

	// Stores the loaded PCM primitive datatypes:
	private static final ResourceSet resourceSet = new ResourceSetImpl();
	private static boolean alreadyLoaded = false;

	private static void loadPcmPrimitiveDataTypes() {
		if (alreadyLoaded) return;
		alreadyLoaded = true;

		logger.trace("Loading default PCM primitive data types.");
		Resource resource = resourceSet.getResource(PCM_PRIMITIVE_TYPES_URI, true);
		Repository repository = (Repository) resource.getContents().get(0);
		List<PrimitiveDataType> pcmPrimitiveDataTypes = Lists.newArrayList(
				Iterables.filter(repository.getDataTypes__Repository(), PrimitiveDataType.class));

		// Bind PCM primitive types to enum values:
		for (PrimitiveDataType pcmType : pcmPrimitiveDataTypes) {
			PrimitiveTypeEnum pcmTypeEnum = pcmType.getType();
			PcmPrimitiveDataType typeEnum;
			try {
				typeEnum = getByPcmTypeEnum(pcmTypeEnum);
			} catch (IllegalArgumentException e) {
				// Enum value not found:
				throw new IllegalStateException("Our PcmPrimitiveDataType enum is missing the value for PCM type '"
						+ pcmTypeEnum + "'.");
			}
			assert typeEnum != null;
			checkState(typeEnum.pcmType == null, "PcmPrimitiveDataType '" + typeEnum.name()
					+ "' aready has an assigned PCM type!");
			typeEnum.pcmType = pcmType;
		}

		// Check for enum values with unassigned PCM type:
		for (PcmPrimitiveDataType typeEnum : values()) {
			checkState(typeEnum.pcmType != null, "PcmPrimitiveDataType '" + typeEnum.name()
					+ "' has no assigned PCM type!");
		}
	}

	// ----------

	private final PrimitiveTypeEnum pcmTypeEnum;
	private PrimitiveDataType pcmType = null; // lazily assigned

	private PcmPrimitiveDataType(PrimitiveTypeEnum pcmTypeEnum) {
		this.pcmTypeEnum = pcmTypeEnum;
	}

	public PrimitiveTypeEnum getPcmTypeEnum() {
		return pcmTypeEnum;
	}

	public PrimitiveDataType getPcmType() {
		loadPcmPrimitiveDataTypes();
		assert pcmType != null; // Should already have been checked during the loading of PCM types
		return pcmType;
	}
}
