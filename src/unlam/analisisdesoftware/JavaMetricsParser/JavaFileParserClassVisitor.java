package unlam.analisisdesoftware.JavaMetricsParser;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class JavaFileParserClassVisitor extends ASTVisitor {
	
	private ArrayList<String> classNames;
	private ArrayList<TypeDeclaration> classNodes;

	public JavaFileParserClassVisitor() {
		super();
		this.classNames = new ArrayList<String>();
		this.classNodes = new ArrayList<TypeDeclaration>();
	}

	// Aca sobreescribo el metodo visit con
	// cada tipo de nodo que me interesa.
	
	@Override
	public boolean visit(TypeDeclaration node) {
		classNames.add(node.getName().toString());
		classNodes.add(node);
		return true;
	}

	public ArrayList<String> getClassNames() {
		return classNames;
	}

	public ArrayList<TypeDeclaration> getClassNodes() {
		return classNodes;
	}
}
