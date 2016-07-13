package unlam.analisisdesoftware.JavaMetricsParser;

import java.util.HashMap;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class JavaFileParserMethodFanOutVisitor extends ASTVisitor {
	
	private HashMap<String, Integer> callees;
	public JavaFileParserMethodFanOutVisitor() {
		super();
		this.callees = new HashMap<String, Integer>();
	}

	@Override
	public boolean visit(MethodInvocation node) {
		// el error aqui es que si bien no se cuentan los metodos duplicados,
		// si dos clases tienen un metoodo que se llama igual, no lo voy a estar contando.
		Integer count = 0;
		String name = node.getName().toString();
		if (callees.containsKey(name)) {
			count = callees.get(name);
		}
		callees.put(name, (count == 0)? 1 : count +1);
		return true;
	}

	public int getCallees() {
		return callees.size();
	}
}
