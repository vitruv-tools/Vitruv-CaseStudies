package tools.vitruv.applications.cbs.commonalities;

import static java.util.stream.Collectors.groupingBy;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

import edu.kit.ipd.sdq.commons.util.java.Pair;
import tools.vitruv.commonalities.componentbasedsystems.ComponentBasedSystemsDomainProvider;
import tools.vitruv.commonalities.objectorienteddesign.ObjectOrientedDesignDomainProvider;
import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.pcm.PcmDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.propagation.impl.CompositeDecomposingChangePropagationSpecification;
import tools.vitruv.framework.domains.VitruvDomain;

//TODO The Commonalities language should // generate a combined
// change propagation specification for all the partial change propagation specifications it
// generates via the Reactions language.
public class CbsCommonalitiesChangePropagationSpecifications {
    //
    // generate with:
    // find src-gen -type f -name '*ChangePropagationSpecification.java' -printf
    // "%P\n"
    // | sed -E 's|/|.|g;s|(.*)\.java|new \1()|g' | paste -sd "," -
    private static final Map<Pair<VitruvDomain, VitruvDomain>, ? extends Collection<? extends ChangePropagationSpecification>> allChangePropagationSpecifications = //
            Stream.of(//
                    new mir.reactions.interfaceMethodFromUML.InterfaceMethodFromUMLChangePropagationSpecification(),
                    new mir.reactions.repositoryToPCM.RepositoryToPCMChangePropagationSpecification(),
                    new mir.reactions.compositeDataTypeToObjectOrientedDesign.CompositeDataTypeToObjectOrientedDesignChangePropagationSpecification(),
                    new mir.reactions.compositeDataTypeElementFromObjectOrientedDesign.CompositeDataTypeElementFromObjectOrientedDesignChangePropagationSpecification(),
                    new mir.reactions.operationFromObjectOrientedDesign.OperationFromObjectOrientedDesignChangePropagationSpecification(),
                    new mir.reactions.repositoryFromObjectOrientedDesign.RepositoryFromObjectOrientedDesignChangePropagationSpecification(),
                    new mir.reactions.compositeDataTypeElementToPCM.CompositeDataTypeElementToPCMChangePropagationSpecification(),
                    new mir.reactions.constructorToJava.ConstructorToJavaChangePropagationSpecification(),
                    new mir.reactions.propertyFromJava.PropertyFromJavaChangePropagationSpecification(),
                    new mir.reactions.operationParameterToObjectOrientedDesign.OperationParameterToObjectOrientedDesignChangePropagationSpecification(),
                    new mir.reactions.packageToUML.PackageToUMLChangePropagationSpecification(),
                    new mir.reactions.operationFromPCM.OperationFromPCMChangePropagationSpecification(),
                    new mir.reactions.packageToJava.PackageToJavaChangePropagationSpecification(),
                    new mir.reactions.interfaceFromUML.InterfaceFromUMLChangePropagationSpecification(),
                    new mir.reactions.classFromUML.ClassFromUMLChangePropagationSpecification(),
                    new mir.reactions.compositeDataTypeFromPCM.CompositeDataTypeFromPCMChangePropagationSpecification(),
                    new mir.reactions.interfaceFromJava.InterfaceFromJavaChangePropagationSpecification(),
                    new mir.reactions.interfaceMethodToJava.InterfaceMethodToJavaChangePropagationSpecification(),
                    new mir.reactions.interfaceToJava.InterfaceToJavaChangePropagationSpecification(),
                    new mir.reactions.componentFromPCM.ComponentFromPCMChangePropagationSpecification(),
                    new mir.reactions.methodParameterFromJava.MethodParameterFromJavaChangePropagationSpecification(),
                    new mir.reactions.methodParameterToUML.MethodParameterToUMLChangePropagationSpecification(),
                    new mir.reactions.componentToPCM.ComponentToPCMChangePropagationSpecification(),
                    new mir.reactions.propertyToUML.PropertyToUMLChangePropagationSpecification(),
                    new mir.reactions.methodParameterFromUML.MethodParameterFromUMLChangePropagationSpecification(),
                    new mir.reactions.operationParameterToPCM.OperationParameterToPCMChangePropagationSpecification(),
                    new mir.reactions.methodParameterToJava.MethodParameterToJavaChangePropagationSpecification(),
                    new mir.reactions.compositeDataTypeElementFromPCM.CompositeDataTypeElementFromPCMChangePropagationSpecification(),
                    new mir.reactions.componentInterfaceToObjectOrientedDesign.ComponentInterfaceToObjectOrientedDesignChangePropagationSpecification(),
                    new mir.reactions.operationParameterFromObjectOrientedDesign.OperationParameterFromObjectOrientedDesignChangePropagationSpecification(),
                    new mir.reactions.propertyToJava.PropertyToJavaChangePropagationSpecification(),
                    new mir.reactions.classFromJava.ClassFromJavaChangePropagationSpecification(),
                    new mir.reactions.repositoryToObjectOrientedDesign.RepositoryToObjectOrientedDesignChangePropagationSpecification(),
                    new mir.reactions.interfaceToUML.InterfaceToUMLChangePropagationSpecification(),
                    new mir.reactions.operationToObjectOrientedDesign.OperationToObjectOrientedDesignChangePropagationSpecification(),
                    new mir.reactions.classToUML.ClassToUMLChangePropagationSpecification(),
                    new mir.reactions.propertyFromUML.PropertyFromUMLChangePropagationSpecification(),
                    new mir.reactions.constructorFromJava.ConstructorFromJavaChangePropagationSpecification(),
                    new mir.reactions.interfaceMethodToUML.InterfaceMethodToUMLChangePropagationSpecification(),
                    new mir.reactions.operationToPCM.OperationToPCMChangePropagationSpecification(),
                    new mir.reactions.interfaceMethodFromJava.InterfaceMethodFromJavaChangePropagationSpecification(),
                    new mir.reactions.compositeDataTypeElementToObjectOrientedDesign.CompositeDataTypeElementToObjectOrientedDesignChangePropagationSpecification(),
                    new mir.reactions.classMethodFromUML.ClassMethodFromUMLChangePropagationSpecification(),
                    new mir.reactions.classToJava.ClassToJavaChangePropagationSpecification(),
                    new mir.reactions.packageFromJava.PackageFromJavaChangePropagationSpecification(),
                    new mir.reactions.componentInterfaceFromObjectOrientedDesign.ComponentInterfaceFromObjectOrientedDesignChangePropagationSpecification(),
                    new mir.reactions.operationParameterFromPCM.OperationParameterFromPCMChangePropagationSpecification(),
                    new mir.reactions.compositeDataTypeFromObjectOrientedDesign.CompositeDataTypeFromObjectOrientedDesignChangePropagationSpecification(),
                    new mir.reactions.constructorFromUML.ConstructorFromUMLChangePropagationSpecification(),
                    new mir.reactions.classMethodFromJava.ClassMethodFromJavaChangePropagationSpecification(),
                    new mir.reactions.componentInterfaceToPCM.ComponentInterfaceToPCMChangePropagationSpecification(),
                    new mir.reactions.classMethodToJava.ClassMethodToJavaChangePropagationSpecification(),
                    new mir.reactions.constructorToUML.ConstructorToUMLChangePropagationSpecification(),
                    new mir.reactions.componentInterfaceFromPCM.ComponentInterfaceFromPCMChangePropagationSpecification(),
                    new mir.reactions.componentToObjectOrientedDesign.ComponentToObjectOrientedDesignChangePropagationSpecification(),
                    new mir.reactions.compositeDataTypeToPCM.CompositeDataTypeToPCMChangePropagationSpecification(),
                    new mir.reactions.classMethodToUML.ClassMethodToUMLChangePropagationSpecification(),
                    new mir.reactions.componentFromObjectOrientedDesign.ComponentFromObjectOrientedDesignChangePropagationSpecification(),
                    new mir.reactions.packageFromUML.PackageFromUMLChangePropagationSpecification(),
                    new mir.reactions.repositoryFromPCM.RepositoryFromPCMChangePropagationSpecification()
                    //
                    )
            .collect(groupingBy(spec -> new Pair<>(spec.getSourceDomain(), spec.getTargetDomain())));

    private static abstract class CbsCommonalitiesChangePropagationSpecification
    extends CompositeDecomposingChangePropagationSpecification {
        public CbsCommonalitiesChangePropagationSpecification(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
            super(sourceDomain, targetDomain);
            for (var spec : allChangePropagationSpecifications.get(new Pair<>(sourceDomain, targetDomain))) {
                addChangeMainprocessor(spec);
            }
        }
    }

    public static class Uml2ObjectOrientedDesign extends CbsCommonalitiesChangePropagationSpecification {
        public Uml2ObjectOrientedDesign() {
            super(new UmlDomainProvider().getDomain(), new ObjectOrientedDesignDomainProvider().getDomain());
        }
    }

    public static class ObjectOrientedDesign2Uml extends CbsCommonalitiesChangePropagationSpecification {
        public ObjectOrientedDesign2Uml() {
            super(new ObjectOrientedDesignDomainProvider().getDomain(), new UmlDomainProvider().getDomain());
        }
    }

    public static class Java2ObjectOrientedDesign extends CbsCommonalitiesChangePropagationSpecification {
        public Java2ObjectOrientedDesign() {
            super(new JavaDomainProvider().getDomain(), new ObjectOrientedDesignDomainProvider().getDomain());
        }
    }

    public static class ObjectOrientedDesign2Java extends CbsCommonalitiesChangePropagationSpecification {
        public ObjectOrientedDesign2Java() {
            super(new ObjectOrientedDesignDomainProvider().getDomain(), new JavaDomainProvider().getDomain());
        }
    }

    public static class Pcm2ComponentBasedSystems extends CbsCommonalitiesChangePropagationSpecification {
        public Pcm2ComponentBasedSystems() {
            super(new PcmDomainProvider().getDomain(), new ComponentBasedSystemsDomainProvider().getDomain());
        }
    }

    public static class ComponentBasedSystems2Pcm extends CbsCommonalitiesChangePropagationSpecification {
        public ComponentBasedSystems2Pcm() {
            super(new ComponentBasedSystemsDomainProvider().getDomain(), new PcmDomainProvider().getDomain());
        }
    }

    public static class ObjectOrientedDesign2ComponentBasedSystems
    extends CbsCommonalitiesChangePropagationSpecification {
        public ObjectOrientedDesign2ComponentBasedSystems() {
            super(new ObjectOrientedDesignDomainProvider().getDomain(),
                    new ComponentBasedSystemsDomainProvider().getDomain());
        }
    }

    public static class ComponentBasedSystems2ObjectOrientedDesign
    extends CbsCommonalitiesChangePropagationSpecification {
        public ComponentBasedSystems2ObjectOrientedDesign() {
            super(new ComponentBasedSystemsDomainProvider().getDomain(),
                    new ObjectOrientedDesignDomainProvider().getDomain());
        }
    }
}
