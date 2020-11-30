package tools.vitruv.applications.util.temporary.pcm

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.palladiosimulator.pcm.repository.Parameter

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

@Utility
class PcmParameterUtil {
    def static setParameterName(Parameter parameter, String newName) {
        // Set entity name as well if it exists
        if (parameter.eClass.EAllAttributes.exists[name == "entityName"]) {
            parameter.eSet(parameter.eClass.EAllAttributes.filter[name == "entityName"].claimOne, newName)
        }
        parameter.parameterName = newName
    }
}
