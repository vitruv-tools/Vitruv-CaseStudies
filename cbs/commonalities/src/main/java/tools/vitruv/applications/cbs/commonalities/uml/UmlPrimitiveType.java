package tools.vitruv.applications.cbs.commonalities.uml;

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
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.resource.UMLResource;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 * Represents UML's default primitive types.
 */
// TODO Xtend does not support enums with members yet.
public enum UmlPrimitiveType {

	BOOLEAN("Boolean"),
	INTEGER("Integer"),
	REAL("Real"),
	STRING("String"),
	UNLIMITED("UnlimitedNatural");

	private static final Logger logger = LogManager.getLogger(UmlPrimitiveType.class);

	private static final Map<String, UmlPrimitiveType> byUmlTypeName = new HashMap<>();

	static {
		for (UmlPrimitiveType type : values()) {
			byUmlTypeName.put(type.getUmlTypeName(), type);
		}
	}

	/**
	 * Gets the {@link UmlPrimitiveType} by its
	 * {@link UmlPrimitiveType#getUmlTypeName() UML type name}.
	 * 
	 * @param umlTypeName
	 *        the UML type name
	 * @return the UmlPrimitiveType, not <code>null</code>
	 * @throws IllegalArgumentException
	 *         if no UmlPrimitiveType is found
	 */
	public static UmlPrimitiveType getByUmlTypeName(String umlTypeName) {
		UmlPrimitiveType type = byUmlTypeName.get(umlTypeName);
		if (type == null) {
			throw new IllegalArgumentException("Could not find UmlPrimitiveType for UML type name '"
					+ umlTypeName + "'!");
		}
		return type;
	}

	// Loading of UML's PrimitiveTypes:

	// Stores the loaded UML primitive types:
	private static final ResourceSet resourceSet = new ResourceSetImpl();
	private static boolean alreadyLoaded = false;

	private static void loadUmlPrimitiveTypes() {
		if (alreadyLoaded) return;
		alreadyLoaded = true;

		logger.trace("Loading default UML primitive types.");
		Resource resource = resourceSet.getResource(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI), true);
		List<PrimitiveType> umlPrimitiveTypes = Lists.newArrayList(
				Iterators.filter(resource.getAllContents(), PrimitiveType.class));

		// Bind UML primitive types to enum values:
		for (PrimitiveType umlType : umlPrimitiveTypes) {
			String typeName = umlType.getName();
			UmlPrimitiveType typeEnum;
			try {
				typeEnum = getByUmlTypeName(typeName);
			} catch (IllegalArgumentException e) {
				// Enum value not found:
				throw new IllegalStateException("Our UmlPrimitiveType enum is missing the value for UML type '"
						+ typeName + "'.");
			}
			assert typeEnum != null;
			checkState(typeEnum.umlType == null, "UmlPrimitiveType '" + typeEnum.name()
					+ "' aready has an assigned UML type!");
			typeEnum.umlType = umlType;
		}

		// Check for enum values with unassigned UML type:
		for (UmlPrimitiveType typeEnum : values()) {
			checkState(typeEnum.umlType != null, "UmlPrimitiveType '" + typeEnum.name()
					+ "' has no assigned UML type!");
		}
	}

	// ----------

	private final String umlTypeName;
	private PrimitiveType umlType = null; // lazily assigned

	private UmlPrimitiveType(String umlTypeName) {
		this.umlTypeName = umlTypeName;
	}

	public String getUmlTypeName() {
		return umlTypeName;
	}

	public PrimitiveType getUmlType() {
		loadUmlPrimitiveTypes();
		assert umlType != null; // Should already have been checked during the loading of UML types
		return umlType;
	}
}
