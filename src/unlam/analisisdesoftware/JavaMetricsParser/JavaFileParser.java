package unlam.analisisdesoftware.JavaMetricsParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class JavaFileParser {
	private File javaFile;
	private CompilationUnit compilationUnit;
	private List<String> source;
	
	public JavaFileParser(File javaFile) throws IOException {
		if (javaFile != null) {
			this.source = new ArrayList<String>();
			this.javaFile = javaFile;
			parse(this.javaFile);
		}
	}
	
	private void parse(File javaFile) throws IOException {		
		FileReader fileReader = new FileReader(javaFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuilder stringBuilder = new StringBuilder();
		String line = "";
		char content[] = null;
		
		try {
			
			// Elimino las lineas en blanco aunque creo que el parser las ignora.
			while ((line = bufferedReader.readLine()) != null) { 
				stringBuilder.append(line + "\n");	
				
				/* Asi ignoro lineas blancas y espacios.
				line = line.trim();
				if (line != "") {
					stringBuilder.append(line + "\n");	
				}
				*/
			}
			
			// Me quedo con el codigo en forma de StringList.
			this.source = Arrays.asList(stringBuilder.toString().split("\n|\n\r|\r"));
			
			// Preparo el array para el parser.
			content = new char [stringBuilder.length()];
			stringBuilder.getChars(0, stringBuilder.length()-1, content, 0);
			
			// Creo el parser con el contenido del archivo.
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setSource(content);
					
			// Creo la unidad de compilacion
			compilationUnit = (CompilationUnit) parser.createAST(null);
			
		} finally {
			bufferedReader.close();
		}
	}
	
	public List<String> getSource() {
		return source;
	}

	public CompilationUnit getCompilationUnit() {
		return compilationUnit;
	}
}