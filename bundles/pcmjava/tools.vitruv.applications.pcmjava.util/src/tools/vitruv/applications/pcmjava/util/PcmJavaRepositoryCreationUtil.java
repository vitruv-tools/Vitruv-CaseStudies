package tools.vitruv.applications.pcmjava.util;

import java.util.ArrayList;
import java.util.List;

import tools.vitruv.domains.pcm.PcmDomainProvider;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.domains.java.JavaDomainProvider;

/**
 * Util class for the PCM Java case study
 *
 * @author Langhamm
 *
 */
public class PcmJavaRepositoryCreationUtil {

    /**
     * Util classes should not have public constructor
     */
    private PcmJavaRepositoryCreationUtil() {

    }

    /**
     * creates and returns the metarepository for the PCM and Java case study
     *
     * @return the PCMJava MetaRepository
     */
    public static List<VitruvDomain> createPcmJamoppMetamodels() {
    	List<VitruvDomain> result = new ArrayList<VitruvDomain>();
        result.add(new PcmDomainProvider().getDomain());
        result.add(new JavaDomainProvider().getDomain());
        return result;
    }

}
