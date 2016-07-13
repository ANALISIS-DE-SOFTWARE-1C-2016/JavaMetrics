package unlam.analisisdesoftware.JavaMetricsParser;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class JavaFileParserClassMethodVisitor extends ASTVisitor{
	
	private ArrayList<String> methodNames;
	private ArrayList<MethodDeclaration> methodNodes;
	private String className;
	
	public JavaFileParserClassMethodVisitor(String className) {
		super();
		this.methodNames = new ArrayList<String>();
		this.methodNodes = new ArrayList<MethodDeclaration>();
		this.className = className;
	}
	
	// Aca sobreescribo el metodo visit con
	// cada tipo de nodo que me interesa.
	
	@Override
	public boolean visit(MethodDeclaration node) {
		ASTNode parent = node.getParent();
		// pregunto porque la clase puede ser anonima.
		if (parent.getNodeType() == ASTNode.TYPE_DECLARATION) {
			TypeDeclaration classNode = (TypeDeclaration)parent;
			String nodeName = classNode.getName().toString();
			if (className.equals(nodeName)) {
				// Aqui no me interesan los comentarios java doc. los borro.
				node.setJavadoc(null);
				methodNames.add(node.toString().split("\n|\n\r|\r")[0].replace("{", ""));
				methodNodes.add(node);
			}			
		}
		
		return true;
	}
	
	public ArrayList<String> getMethodNames() {
		return methodNames;
	}
	
	public ArrayList<MethodDeclaration> getMethodNodes() {
		return methodNodes;
	}

}
