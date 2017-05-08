package tools.vitruv.applications.umljava.util.java

import java.io.ByteArrayInputStream
import java.util.Collections
import java.util.Iterator
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.JavaRoot
import org.emftext.language.java.containers.Package
import org.emftext.language.java.imports.ClassifierImport
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypeReference
import tools.vitruv.domains.java.util.jamoppparser.JamoppParser
import static tools.vitruv.applications.umljava.util.java.JavaModifierUtil.*
import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import org.emftext.language.java.members.EnumConstant
import org.emftext.language.java.classifiers.Enumeration

class JavaContainerAndClassifierUtil {
    private static val logger = Logger.getLogger(JavaContainerAndClassifierUtil.simpleName)
    private new() {
        
    }
    
    /**
     * The created Class is not contained in a compilationunit.
     */
    def static createJavaClass(String name, JavaVisibility visibility, boolean abstr, boolean fin) {
        val jClass = ClassifiersFactory.eINSTANCE.createClass;
        setName(jClass, name)
        setJavaVisibilityModifier(jClass, visibility)
        setAbstract(jClass, abstr)
        setFinal(jClass, fin)

        return jClass;
    }
    
    def static createJavaPackage(String name, Package containingPackage) {
        val jPackage = ContainersFactory.eINSTANCE.createPackage
        setName(jPackage, name)
        jPackage.namespaces += getJavaPackageAsStringList(containingPackage)
        return jPackage
    }
    
    /**
     * The created Interface is not contained in a compilationunit.
     */
    def static createJavaInterface(String name, List<Interface> superInterfaces) {
        val jInterface = ClassifiersFactory.eINSTANCE.createInterface;
        setName(jInterface, name)
        jInterface.makePublic;
        if (!superInterfaces.nullOrEmpty) {
            jInterface.extends.addAll(createNamespaceReferenceFromList(superInterfaces));
        }
        return jInterface;
    }
    
    /**
     * The created Enum is not contained in a compilationunit.
     */
    def static createJavaEnum(String name, JavaVisibility visibility, List<EnumConstant> constantsList) {
        val jEnum = ClassifiersFactory.eINSTANCE.createEnumeration;
        setName(jEnum, name)
        setJavaVisibilityModifier(jEnum, visibility)
        addEnumConstantIfNotNull(jEnum, constantsList)
        return jEnum;
    }
    
    def static addEnumConstantIfNotNull(Enumeration jEnum, List<EnumConstant> constantsList) {
        if (!constantsList.nullOrEmpty) {
            jEnum.constants.addAll(constantsList)
        }
    }
    
    /**
     * The Method automatically sets the .java FileExtension
     */
    def static createEmptyCompilationUnit(String nameWithoutFileExtension) {
        val cu = ContainersFactory.eINSTANCE.createCompilationUnit
        cu.name = nameWithoutFileExtension
        return cu
    }
    
    def static createJavaCompilationUnitWithClassifierInPackage(ConcreteClassifier jClassifier, Package jPackage) {
        val compUnit = createEmptyCompilationUnit(jClassifier.name)
        compUnit.classifiers += jClassifier
        compUnit.namespaces.addAll(getJavaPackageAsStringList(jPackage))
        return compUnit
    }
    
    /**
     * Creates a Java-ClassifierImport from a qualified name
     */
    def static ClassifierImport createJavaClassImport(String qualifiedName) {
        val content = "package dummyPackage;\n " +
                "import " + qualifiedName + ";\n" +
                "public class DummyClass {}";
        val dummyCU = createJavaRoot("DummyClass", content) as CompilationUnit;
        val classifierImport = (dummyCU.getImports().get(0) as ClassifierImport)
        //EcoreUtil.copy(classifierImport);
        return classifierImport;
        
    }
    
    /**
     * Creates a JavaRoot Object with the given content
     * 
     */
    def static JavaRoot createJavaRoot(String name, String content) {
        val JamoppParser jaMoPPParser = new JamoppParser
        val inStream = new ByteArrayInputStream(content.bytes)
        val javaRoot = jaMoPPParser.parseCompilationUnitFromInputStream(URI.createFileURI(name + ".java"),
            inStream)
        javaRoot.name = name + ".java"
        EcoreUtil.remove(javaRoot)
        return javaRoot
    }
    
     def static CompilationUnit getContainingCompilationUnit(ConcreteClassifier jClassifier) {
        return jClassifier.eContainer as CompilationUnit
    }
    
    /**
     * Entfernt alle Classifier von Iterator anhand des Namens.
     * 
     * @param iter Iterator über eine Liste von TypeReferences
     * @param classif Klasse oder Interface, das entfernt werden soll
     */
    def static removeClassifierFromIterator(Iterator<TypeReference> iter, ConcreteClassifier classif) {
        while (iter.hasNext) {
            val type = (iter.next as NamespaceClassifierReference).classifierReferences.head.target
            if (classif.name.equals(type.name)) {
                iter.remove;
            }
        }
    }
    
    def static getJavaPackageAsStringList(Package jPackage) {
       if (jPackage === null) { //Defaultpackage
           return Collections.<String>emptyList()
       }
       val packageStringList = EcoreUtil.copyAll(jPackage.namespaces) //TODO Was ist, wenn namespaces null
       packageStringList += jPackage.name
       return packageStringList
   }
   
   def static Field getJavaAttributeByName(Class jClass, String attributeName) {
       val candidates = jClass.members.filter(Field)
       for (member : candidates) {
           if (member.name == attributeName) {
               return member as Field
           }
       }
       return null
   }
   
   def static Constructor getFirstJavaConstructor(Class jClass) {
       val candidates = jClass.members.filter(Constructor)
       if (!candidates.nullOrEmpty) {
           return candidates.head
       } else {
           return null
       }
   }
   
   def static removeJavaClassifierFromPackage(Package jPackage,ConcreteClassifier jClassifier) {
        val iter = jPackage.compilationUnits.iterator
        while (iter.hasNext) {
            if (iter.next.name.equals(jClassifier.name)) {
                iter.remove;
            }
        }
    }
}