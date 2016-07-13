package unlam.analisisdesoftware.JavaMetricsParser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LineComment;

public class JavaFileParserCommentVisitor extends ASTVisitor {
	private CompilationUnit compilationUnit;
	private int LineCount;
	
	public JavaFileParserCommentVisitor(CompilationUnit compilationUnit) {
		super();
		this.compilationUnit = compilationUnit;
		this.LineCount = 0;
	}
	
	@Override
	public boolean visit(LineComment node) {
		LineCount++;
		return true;		
	}

	@Override
	public boolean visit(BlockComment node) {
		int startLineNumber = compilationUnit.getLineNumber(node.getStartPosition()) - 1;
        int endLineNumber = compilationUnit.getLineNumber(node.getStartPosition() + node.getLength()) - 1;
		
        LineCount = LineCount + (endLineNumber - startLineNumber);
		return true;
	}

	@Override
	public boolean visit(Javadoc node) {
		int startLineNumber = compilationUnit.getLineNumber(node.getStartPosition());
        int endLineNumber = compilationUnit.getLineNumber(node.getStartPosition() + node.getLength()-1);
		
        LineCount = LineCount + (endLineNumber - startLineNumber);
		return true;
	}

	public int getLineCount() {
		return LineCount;
	}
}
