package tools.vitruv.applications.umljava.java2uml.constructionsimulationtest

import org.eclipse.emf.common.util.URI
import tools.vitruv.applications.umljava.java2uml.Java2UmlTransformationTest
import org.junit.Test
import java.io.File
import org.apache.commons.io.FilenameUtils
import static tools.vitruv.applications.umljava.util.java.JavaContainerAndClassifierUtil.*
import org.junit.Ignore
import java.util.List

/**
 * Test class for the reconstruction of existing java models
 */
class JavaConstructionSimulationTest extends Java2UmlTransformationTest {
    
    
    /**
     * Tests the files from the logger project by orhan obut
     * https://github.com/orhanobut/logger
     * 
     * Add the "com" package of the testresource-folder to the src/ folder
     * before you start the test, so the java files could be found on the class path 
     * 
     */
    @Test @Ignore("see javadoc")
    def void testOrhanobutLoggerProject() {
        loadLoggerProjectIndividually("src/com", "com")
    }
    
    /** Tests the (generated) files from "myproject" by suresh519
     * https://github.com/orhanobut/logger
     * 
     * Add the files of the testresource-folder expect the com package folder to the src/ default package
     * before you start the test, so the java files could be found on the class path 
     * 
     */
    @Test @Ignore("see javadoc")
    def void testMyProject() {
        loadMyProjectIndividually("src", "")
    }
    
    
    /**
     * Loads a directory without manual sorting
     * 
     */
    def private void loadDirectoryContents(String directoryPath, String namespace) {
        val directoryContents = new File(directoryPath).listFiles
         
        createAndSyncPackageInfo(directoryContents, directoryPath, namespace)
            
        for (file : directoryContents.filter["java".equals(FilenameUtils.getExtension(name))].filter[!name.equals("package-info.java")]) {
            val res = getModelResource(URI.createFileURI(file.path)).allContents.head
            createAndSynchronizeModel(file.path, res)
        }
        for (file : directoryContents.filter[it.isDirectory]) {
            loadDirectoryContents(file.path, namespace + "." + file.name)
        }
    }
    
    /**
     * Loads the logger project files individually (sorts their dependencies)
     * 
     */
    def private void loadLoggerProjectIndividually(String directoryPath, String namespace) {
        val directoryContents = new File(directoryPath).listFiles
        
        createAndSyncPackageInfo(directoryContents, directoryPath, namespace)
        
        directoryContents.loadFirstFileWithTheName("LogAdapter.java")
        directoryContents.loadFirstFileWithTheName("AndroidLogAdapter.java")
        directoryContents.loadFirstFileWithTheName("LogLevel.java")
        directoryContents.loadFirstFileWithTheName("Settings.java")
        directoryContents.loadFirstFileWithTheName("Printer.java")
        directoryContents.loadFirstFileWithTheName("Logger.java")
        directoryContents.loadFirstFileWithTheName("LoggerPrinter.java")
        directoryContents.loadFirstFileWithTheName("Helper.java")
        
         
        for (file : directoryContents.filter[it.isDirectory]) {
            loadLoggerProjectIndividually(file.path, namespace + "." + file.name)
        }
    }
    
    /**
     * Loads myProject-files individually (sorts their dependencies)
     * 
     */
    def private void loadMyProjectIndividually(String directoryPath, String namespace) {
        val directoryContents = new File(directoryPath).listFiles
         
        //all files are in the default package -> no package-info needed
        
        directoryContents.loadFirstFileWithTheName("ArrayList.java")
        directoryContents.loadFirstFileWithTheName("Date.java")
        directoryContents.loadFirstFileWithTheName("IProxyDB.java")
        directoryContents.loadFirstFileWithTheName("IGISInterface.java")
        directoryContents.loadFirstFileWithTheName("TimerTask.java")
        directoryContents.loadFirstFileWithTheName("MsgBean.java")
        directoryContents.loadFirstFileWithTheName("IMsg.java")
        directoryContents.loadFirstFileWithTheName("MoveFunctionCls.java")
        directoryContents.loadFirstFileWithTheName("MoveMonitorCls.java")
        directoryContents.loadFirstFileWithTheName("GameTimeInfoCls.java")
        directoryContents.loadFirstFileWithTheName("ProxyDB.java")
        directoryContents.loadFirstFileWithTheName("ProxyGIS.java")
        directoryContents.loadFirstFileWithTheName("CheckObstacleCls.java")
        directoryContents.loadFirstFileWithTheName("MsgCls.java")
        directoryContents.loadFirstFileWithTheName("FuelConsumptionCls.java")
        
        //All files are in the default package -> no subdirectories
    }
    
    def private void loadFirstFileWithTheName(File[] fileList, String name) {
        val file = fileList.findFirst[it.name.equals(name)]
        if (file !== null && file.exists) {
            val ress = getModelResource(URI.createFileURI(file.path))
            val res = ress.allContents.head
            createAndSynchronizeModel(file.path, res)
        }
    }
    
    def private void createAndSyncPackageInfo(List<File> directoryContents, String directoryPath, String namespace) {
        val packageInfos = directoryContents.filter[name.equals("package-info.java")]
        var File packageInfoFile;
        if (packageInfos.nullOrEmpty) {
            packageInfoFile = createPackageInfo(directoryPath, namespace)
        } else {
            packageInfoFile = packageInfos.head
        }
        val packageInfoRes = getModelResource(URI.createFileURI(packageInfoFile.path)).allContents.head
        createAndSynchronizeModel(packageInfoFile.path, packageInfoRes)
    }
}