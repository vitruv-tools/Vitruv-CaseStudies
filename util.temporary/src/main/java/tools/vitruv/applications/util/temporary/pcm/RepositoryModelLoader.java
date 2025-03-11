package tools.vitruv.applications.util.temporary.pcm;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import org.palladiosimulator.pcm.repository.RepositoryPackage;

/**
 * Helper class for loading PCM Repositories from file system.
 *
 * @author Benjamin Hettwer
 */
public class RepositoryModelLoader {

    /**
     * Loads PCM resource.
     *
     * @param fileURI
     *            the file uri
     * @return the resource, or null if there was none found
     */
    public static Resource loadPcmResource(URI fileURI) {
        // Load the required meta class packages => PCM Repository
        RepositoryPackage.eINSTANCE.eClass();

        // Obtain a new resource set
        ResourceSet resourceSet = new ResourceSetImpl();

        // Register XML Factory implementation using DEFAULT_EXTENSION
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());

        // Demand load the resource for this file
        Resource resource = resourceSet.getResource(fileURI, true);

        return resource;
    }

}
