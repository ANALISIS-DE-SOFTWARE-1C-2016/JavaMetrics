package unlam.analisisdesoftware.JavaMetricsParser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class JavaFileParserMethodVisitor extends ASTVisitor {

	private String methodName;
	private MethodDeclaration methodNode;
	private int methodStartLine;
	private int methodEndLine;
	private String methodBody;	
	private String className;
	private int lineCount;
	private List<Comment> commentNodes;
	private int cyclomaticComplexity;

	public JavaFileParserMethodVisitor(String methodName, String className) {
		super();
		this.methodName = methodName;
		this.methodNode = null;
		this.methodStartLine = 0;
		this.methodEndLine = 0;
		this.methodBody = "";
		this.className = className;
		this.lineCount = 0;
		this.commentNodes = new ArrayList<Comment>();
		this.cyclomaticComplexity = 0;
	}
	
	// Aca sobreescribo el metodo visit con
	// cada tipo de nodo que me interesa.	
	
	@Override
	public boolean visit(MethodDeclaration node) {
		
		// Averiguo el nodo padre del metodo visitado,
		// y me fijo si es la misma clase que me pasaron.
		ASTNode parent = node.getParent();
		if (parent.getNodeType() == ASTNode.TYPE_DECLARATION) {
			
			TypeDeclaration classNode = (TypeDeclaration)parent;
			if (className.equals(classNode.getName().toString())) {
				
				// Ahora averiguo el prototipo del metodo y me fijo si es que me pasaron.
				String nodeName = node.toString().split("\n|\n\r|\r")[0].replace("{", "");
				if (methodName.equals(nodeName)) { 
					
					// Obtengo el codigo del metodo completo.
					methodBody = node.toString();
					methodNode = node;
					
					// Obtengo la unidad de compilacion de dl root(el dom completo)					
					CompilationUnit compilationUnit = (CompilationUnit)node.getRoot();
					
					// Obtengo el rango de lineas donde se ubica el metodo.
					methodStartLine = compilationUnit.getLineNumber(node.getStartPosition());
					methodEndLine = compilationUnit.getLineNumber(node.getStartPosition() + node.getLength()) + 1;
				
					// Calculo la cantidad de lineas totales.
					lineCount = methodEndLine - methodStartLine;
					
					// Ahora para cada comentario en el dom, me fijo si estan ubicados dentro del metodo.
					for (Comment comment : (List<Comment>)compilationUnit.getCommentList()) {
						int cline = compilationUnit.getLineNumber(comment.getStartPosition());
						
						if (cline >= methodStartLine && cline <= methodEndLine) {
							// Si, lo agrego a la lista.
							commentNodes.add(comment);
						}
					}
					
					// Calculo la complejidad ciclomatica valiendome de otro "Visitor"
					// Ahora calculo la complejidad del metodo
					JavaFileParserCyclomaticComplexityVisitor complexityVisitor = new JavaFileParserCyclomaticComplexityVisitor();
					
					// Visito el nodo que representa al metodo.
					node.accept(complexityVisitor);
					
					// Extraigo el valor calculado.
					cyclomaticComplexity = complexityVisitor.getCyclomaticComplexity();
				}							
			}
		}
		
		return false;		
	}
	
	public MethodDeclaration getMethodNode() {
		return methodNode;
	}

	public int getMethodStartLine() {
		return methodStartLine;
	}

	public int getMethodEndLine() {
		return methodEndLine;
	}

	public int getCyclomaticComplexity() {
		return cyclomaticComplexity;
	}

	public String getMethodBody() {
		return methodBody;
	}
	
	public int getLineCount() {
		return lineCount;
	}
	
	public List<Comment> getCommentNodes() {
		return commentNodes;
	}
}

