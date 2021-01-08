package tools.vitruv.applications.umljava.tests.java2uml.constructionsimulationtest

import org.junit.jupiter.api.Test
import java.io.File
import org.apache.commons.io.FilenameUtils
import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.*
import java.util.List
import tools.vitruv.applications.umljava.tests.java2uml.JavaToUmlTransformationTest
import org.junit.jupiter.api.Disabled
import java.nio.file.Path
import org.eclipse.emf.ecore.EObject
import static org.eclipse.emf.common.util.URI.createFileURI

/**
 * Test class for the reconstruction of existing java models
 */
class JavaConstructionSimulationTest extends JavaToUmlTransformationTest {

	/**
	 * Tests the files from the logger project by orhan obut
	 * https://github.com/orhanobut/logger (12.5.2017)
	 */
	@Test
	@Disabled("Does not run properly in Maven and requires manual checking anyway")
	def void testOrhanobutLoggerProject() {
		loadLoggerProjectIndividually("testresources/com", "com")
	// Check junit workspace
	}

	/** Tests the (generated) files from "myproject" by suresh519
	 * https://repository.genmymodel.com/suresh519/MyProject (12.5.2017)
	 * 
	 */
	@Test
	def void testMyProject() {
		loadMyProjectIndividually("testresources", "")
	// Check junit workspace
	}

	/**
	 * Loads a directory without manual sorting
	 * 
	 */
	def protected void loadDirectoryContents(String directoryPath, String namespace) {
		val directoryContents = new File(directoryPath).listFiles

		createAndSyncPackageInfo(directoryContents, directoryPath, namespace)

		for (file : directoryContents.filter["java".equals(FilenameUtils.getExtension(name))].filter [
			!name.equals("package-info.java")
		]) {
			resourceAt(Path.of(file.path)).startRecordingChanges => [
				contents += EObject.from(createFileURI(file.path))
			]
			propagate
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

		// all files are in the default package -> no package-info needed
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

	// All files are in the default package -> no subdirectories
	}

	def private void loadFirstFileWithTheName(File[] fileList, String name) {
		val file = fileList.findFirst[it.name.equals(name)]
		if (file !== null && file.exists) {
			resourceAt(Path.of(file.path)).startRecordingChanges => [
				contents += EObject.from(createFileURI(file.path))
			]
			propagate
		}
	}

	def private void createAndSyncPackageInfo(List<File> directoryContents, String directoryPath, String namespace) {
		val packageInfos = directoryContents.filter[name.equals("package-info.java")]
		val packageInfoFile = if (packageInfos.nullOrEmpty) {
				createPackageInfo(directoryPath, namespace)
			} else {
				packageInfos.head
			}
		resourceAt(Path.of(packageInfoFile.path)).startRecordingChanges => [
			contents += EObject.from(createFileURI(packageInfoFile.path))
		]
		propagate
	}
}
