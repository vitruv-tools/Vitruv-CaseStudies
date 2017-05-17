package tools.vitruv.applications.umljava.java2uml.constructionsimulationtest

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import tools.vitruv.applications.umljava.java2uml.Java2UmlTransformationTest
import org.junit.Test
import java.io.File
import org.apache.commons.io.FilenameUtils
import java.io.PrintWriter
import org.emftext.language.java.containers.CompilationUnit
import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static tools.vitruv.applications.umljava.util.java.JavaContainerAndClassifierUtil.*
import tools.vitruv.framework.tuid.Tuid
import org.eclipse.emf.ecore.util.EcoreUtil
import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import org.eclipse.emf.ecore.resource.impl.ResourceImpl

class JavaConstructionSimulationTest extends Java2UmlTransformationTest {
    
    @Test
    def void testModel1() {
        loadDirectoryContents("src/resources", "resources")
    }
    
    @Test
    def void test2() {
        val ress = getModelResource(URI.createFileURI("src/resources/Has.java"))
        println((ress.contents.head as CompilationUnit).classifiers.head.members)
    }
    
    @Test
    def void test3() {
        loadDirectoryContents("src/com", "com")
    }
    
    
    def void loadDirectoryContents(String directoryPath, String namespace) {
        val directoryContents = new File(directoryPath).listFiles
         
        val packageInfos = directoryContents.filter[name.equals("package-info.java")]
        var File packageInfoFile;
        if (packageInfos.nullOrEmpty) {
            packageInfoFile = createPackageInfo(directoryPath, namespace)
        } else {
            packageInfoFile = packageInfos.head
        }
        val packageInfoRes = getModelResource(URI.createFileURI(packageInfoFile.path)).allContents.head
        createAndSynchronizeModel(packageInfoFile.path, packageInfoRes)
            
        for (file : directoryContents.filter["java".equals(FilenameUtils.getExtension(name))].filter[!name.equals("package-info.java")]) {
            val ress = getModelResource(URI.createFileURI(file.path))
            //val ress = new ResourceSetImpl().getResource(URI.createFileURI(file.path), true)
            println("Contents: " + ress.contents)
            val res = ress.allContents.head
            println("Classif: "+(res as CompilationUnit).classifiers.head)
            println("Member: "+(res as CompilationUnit).classifiers.head.members)
            createAndSynchronizeModel(file.path, res)
            /* 
            for (member : (res as CompilationUnit).classifiers.head.members) {
                saveAndSynchronizeChanges(member)
            }*/
        }
         
        for (file : directoryContents.filter[it.isDirectory]) {
            loadDirectoryContents(file.path, namespace + "." + file.name)
        }
    }
}