package tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm

import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.members.ClassMethod

import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.hasSameSignature
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.getNormalizedClassifierFromTypeReference
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class EjbJava2PcmHelper {
	static def boolean overridesInterfaceMethod(ClassMethod classMethod, Class jaMoPPClass) {
		return null !== getOverridenInterfaceMethod(classMethod, jaMoPPClass)
	}

	static def getOverridenInterfaceMethod(ClassMethod classMethod, Class jaMoPPClass) {
		val implementedEjbInterfaces = jaMoPPClass.implements.map[getNormalizedClassifierFromTypeReference(it)].filter(
			typeof(Interface)).filter[EjbAnnotationHelper.isEjbBuisnessInterface(it)]
		for (ejbInterface : implementedEjbInterfaces) {
			val method = ejbInterface.methods.findFirst[hasSameSignature(it, classMethod)]
			if (null !== method) {
				return method
			}
		}
		return null
	}
}
