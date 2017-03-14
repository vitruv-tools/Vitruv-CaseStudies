package tools.vitruv.applications.umljava.java2uml

import org.apache.log4j.Logger;
import tools.vitruv.applications.umljava.java2uml.AbstractJavaUmlTest
import org.junit.Test
import static org.junit.Assert.*;
import org.eclipse.uml2.uml.UMLFactory
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*;
import org.junit.Ignore
import org.junit.Before
import org.junit.After
import org.apache.log4j.PropertyConfigurator
import org.junit.BeforeClass
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.ClassMethod
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.Abstract
import org.emftext.language.java.types.TypesFactory
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.members.InterfaceMethod
import org.emftext.language.java.types.NamespaceClassifierReference
import org.eclipse.uml2.uml.Type
import org.emftext.language.java.types.TypeReference

class JavaToUmlClassTest extends AbstractJavaUmlTest {
    
}