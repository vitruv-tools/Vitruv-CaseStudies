/**
 * Operators used by the CBS commonalities.
 *
 * <p>The Commonalities language does not look operators up by their annotation. It resolves an
 * operator {@code name} imported from package {@code p} to the class {@code p.cl_operators__.name}
 * and reads the operator annotation from that class (see
 * {@code CommonalitiesOperatorConventions} in Vitruv-DSLs). In Xtend, the operator annotations are
 * active annotations that generate these classes. Java does not run active annotations, so every
 * operator in this module has a hand-written counterpart in a {@code cl_operators__} sub-package:
 * a subclass named like the operator that repeats the operator annotation and the constructor.
 *
 * <p>When adding or changing an operator, add or update its counterpart as well.
 * {@code OperatorAliasesTest} checks that both stay consistent.
 */
package tools.vitruv.applications.cbs.operators;
