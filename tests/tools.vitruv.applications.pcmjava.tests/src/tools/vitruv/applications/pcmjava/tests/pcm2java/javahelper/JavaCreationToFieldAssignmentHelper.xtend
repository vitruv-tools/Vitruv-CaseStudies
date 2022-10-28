package tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper

import org.emftext.language.java.expressions.ExpressionsFactory
import org.emftext.language.java.instantiations.InstantiationsFactory
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.members.Field
import org.emftext.language.java.operators.OperatorsFactory
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.statements.Statement
import org.emftext.language.java.statements.StatementsFactory
import org.emftext.language.java.types.TypeReference

enum ConstructorArguments {
	WITH_NULL_LITERAL,
	WITHOUT_NULL_LITERAL
}

class JavaCreationToFieldAssignmentHelper {
	final TypeReference type
	final Field field
	final ConstructorArguments constructorArguments
	
	final Statement statement
	
	new(String parameterName, TypeReference type, Field field, ConstructorArguments constructorArguments) {
		this.type = type
		this.field = field
		this.constructorArguments = constructorArguments
		
		statement = buildStatement()
	}
	
	def Statement getStatement() {
		return statement
	}
	
	def private Statement buildStatement() {
		var childNext = ReferencesFactory.eINSTANCE.createIdentifierReference
		childNext.target = field
		
		var child = ReferencesFactory.eINSTANCE.createSelfReference
		child.self = LiteralsFactory.eINSTANCE.createThis
		child.next = childNext
		
		var value = InstantiationsFactory.eINSTANCE.createNewConstructorCall
		if(constructorArguments === ConstructorArguments.WITH_NULL_LITERAL) value.arguments += LiteralsFactory.eINSTANCE.createNullLiteral
		value.typeReference = type
		
		var assignmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression
		assignmentExpression.child = child
		assignmentExpression.assignmentOperator = OperatorsFactory.eINSTANCE.createAssignment
		assignmentExpression.value = value
		
		
		var expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
		expressionStatement.expression = assignmentExpression
		
		return expressionStatement
	}
	
}
