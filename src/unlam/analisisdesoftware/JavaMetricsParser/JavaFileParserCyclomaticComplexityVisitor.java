package unlam.analisisdesoftware.JavaMetricsParser;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.WhileStatement;

import java.lang.String;

public class JavaFileParserCyclomaticComplexityVisitor extends ASTVisitor {
	
	private int cyclomaticComplexity;
	
	public JavaFileParserCyclomaticComplexityVisitor() {
		super();
		this.cyclomaticComplexity = 0;
	}

	// Aca sobreescribo el metodo visit con
	// cada tipo de nodo que me interesa.
	
	@Override
	public boolean visit(CatchClause node) {
		cyclomaticComplexity++;
		return true;
	}

	@Override
	public boolean visit(DoStatement node) {
		// Cuento el Do-While
		cyclomaticComplexity++;
		
		// Tengo que contar las condiciones.
		String Expression = node.getExpression().toString();
		cyclomaticComplexity = 	cyclomaticComplexity +
								StringUtils.countMatches(Expression, "&&") +
								StringUtils.countMatches(Expression, "||");
		return true;
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		cyclomaticComplexity++;
		return true;
	}

	@Override
	public boolean visit(ForStatement node) {
		cyclomaticComplexity++;
		return true;
	}

	@Override
	public boolean visit(IfStatement node) {
		// Cuento el if.
		cyclomaticComplexity++;
		
		// Tengo que contar las condiciones.
		String Expression = node.getExpression().toString();
		cyclomaticComplexity = 	cyclomaticComplexity +
								StringUtils.countMatches(Expression, "&&") +
								StringUtils.countMatches(Expression, "||");
		return true;
	}

	@Override
	public boolean visit(SwitchCase node) {
		cyclomaticComplexity++;
		return true;
	}

	@Override
	public boolean visit(WhileStatement node) {		
		// Cuento el While.
		cyclomaticComplexity++;
		
		// Tengo que contar las condiciones.
		String Expression = node.getExpression().toString();
		cyclomaticComplexity = 	cyclomaticComplexity +
								StringUtils.countMatches(Expression, "&&") +
								StringUtils.countMatches(Expression, "||");		
		return true;
	}

	public int getCyclomaticComplexity() {
		// Respeto la formula de McCabe: <nro de nodos condicion> + 1
		return cyclomaticComplexity + 1;
	}	
}
