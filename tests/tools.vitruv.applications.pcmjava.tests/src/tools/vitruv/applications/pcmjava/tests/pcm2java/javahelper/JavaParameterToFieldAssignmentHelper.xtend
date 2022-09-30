package tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper

import org.emftext.language.java.statements.Statement
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.expressions.ExpressionsFactory
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.operators.OperatorsFactory
import org.emftext.language.java.statements.StatementsFactory
import org.emftext.language.java.members.Field
import org.eclipse.emf.ecore.util.EcoreUtil

class JavaParameterToFieldAssignmentHelper {
	final String parameterName
	final TypeReference type
	final Field field
	
	final Parameter parameter
	final Statement statement
	
	new(String parameterName, TypeReference type, Field field) {
		this.parameterName = parameterName
		this.type = type
		this.field = field
		
		parameter = buildParameter()
		statement = buildStatement()
	}
	
	def Parameter getParameter() {
		return parameter
	}
	
	def Statement getStatement() {
		return statement
	}
	
	def private Parameter buildParameter() {
		var parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter
		parameter.name = parameterName
		parameter.typeReference = EcoreUtil.copy(type)
		return parameter
	}
	
	def private Statement buildStatement() {
		var childNext = ReferencesFactory.eINSTANCE.createIdentifierReference
		childNext.target = field
		
		var child = ReferencesFactory.eINSTANCE.createSelfReference
		child.self = LiteralsFactory.eINSTANCE.createThis
		child.next = childNext
		
		var value = ReferencesFactory.eINSTANCE.createIdentifierReference
		value.target = parameter
		
		var assignmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression
		assignmentExpression.child = child
		assignmentExpression.assignmentOperator = OperatorsFactory.eINSTANCE.createAssignment
		assignmentExpression.value = value
		
		var expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
		expressionStatement.expression = assignmentExpression
		
		return expressionStatement
	}
	
}
