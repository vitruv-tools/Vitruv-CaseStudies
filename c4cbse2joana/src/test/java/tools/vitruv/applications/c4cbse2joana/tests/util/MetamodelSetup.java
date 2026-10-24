package tools.vitruv.applications.c4cbse2joana.tests.util;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import edu.kit.ipd.sdq.metamodels.confidentiality4cbse.Confidentiality4cbsePackage;
import edu.kit.ipd.sdq.metamodels.joana.JoanaPackage;

public final class MetamodelSetup {
	private MetamodelSetup() {
	}

	/**
	 * Registers the metamodels used by the case study without an OSGi environment. The Confidentiality4CBSE
	 * subpackages are only registered once their root package is initialized, so models containing their elements
	 * could otherwise not be loaded into views.
	 */
	public static void registerMetamodels() {
		EcorePlugin.ExtensionProcessor.process(null);
		Confidentiality4cbsePackage.eINSTANCE.eClass();
		JoanaPackage.eINSTANCE.eClass();
	}
}
