package unlam.analisisdesoftware.JavaMetricsParser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class JavaFileParserMethodFanInVisitor extends ASTVisitor{
	private int callers;
	private String methodName;
	
	public JavaFileParserMethodFanInVisitor(String methodName) {
		super();
		this.callers = 0;
		this.methodName = methodName;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		MethodVisitor methodVisitor = new MethodVisitor(methodName);
		node.accept(methodVisitor);
		
		callers = callers + methodVisitor.getCallers();
		return true;
	}
	
	public int getCallers() {
		return callers;
	}
}

/**
 * Visitor ad-hoc.
 * @author brunosendras
 *
 */
class MethodVisitor extends ASTVisitor {
	private int callers;
	private String methodName;
	
	MethodVisitor(String methodName) {
		super();
		this.callers = 0;
		this.methodName = methodName;
	}

	@Override
	public boolean visit(MethodInvocation node) {
		String name = node.getName().getIdentifier(); 
		if (methodName.equals(name)) {
			//Aca el problema es que cuento los metdos que invocan al menos un metodo que
			// que coincide con el nombre pasado. hay que verificar la clase del metodo.
			callers++;
			
			// listo ya se que este metodo invoca al metodo seleccionado.
			// devuelvo falso para que no continue.
			return false;
		}
		
		return true;
	}
	
	public int getCallers() {
		return callers;
	}
}
