package unlam.analisisdesoftware.JavaMetricsParser;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.fife.ui.rsyntaxtextarea.RSyntaxUtilities;

/**
 * @category implementation In JAVA Language: Operands : ---------- Tokens of
 *           the following categories are all counted as operands:
 * 
 *           IDENTIFIER all identifiers that are not reserved words
 * 
 *           TYPENAME(type specifiers) Reserved words that specify type: bool ,
 *           byte, int, float,char, double, long, short, signed, unsigned, void
 *           This class also includessome compiler specific nonstandard
 *           keywords.
 *
 *           CONSTANT Character, numeric or string constants.
 * 
 *           Operators : ----------- Tokens of the following categories are all
 *           counted as operators:
 * 
 *           SCSPEC (storage class specifiers) Reserved words that specify
 *           storage class: auto, extern, register, static, typedef, virtual,
 *           mutable, inline.
 * 
 *           TYPE_QUAL (type qualifiers) Reserved words that qualify type:
 *           const, friend, volatile,transient, final.
 * 
 *           RESERVEDO ther reserved words of JAVA: break, case, continue,
 *           default, do, if, else,enum, for, goto, if, new, return, asm,
 *           operator, private, protected, public,sizeof, struct, switch, union,
 *           while, this, namespace, using, try, catch,throw, throws, finally,
 *           strictfp, instanceof, interface, extends, implements,abstract,
 *           concrete, const_cast, static_cast, dynamic_cast,reinterpret_cast,
 *           typeid, template, explicit, true, false, typename Thisclass also
 *           includes some compiler specific nonstandard keywords.
 *
 *           OPERATOR! != % %= & && || &= ( ) { } [ ] * *= + ++ += , - -- -=-> .
 *           ... / /= : :: < << <<= <= = == > >= >> >>> >>=>>>= ? ^ ^= | |= ~ ;
 *           =& “ “ ‘ ‘ # ## ~
 * 
 *           Some special cases are as follows :
 * 
 *           - A pair of parenthesis is considered a single operator. - The
 *           delimiter ; is considered a single operator.The ternary operator
 *           ‘?’ followed by ‘:’ is considered a single operator as it is
 *           equivalent to “if-else”construct. x = (a == 3) ? a : b; is
 *           equivalent to:if (a == 3)x = a;elsex = b; . - A label is considered
 *           an operator if it is used as the target of a GOTO statement. - The
 *           following control structures case ...: for (...) if (...) switch
 *           (...) while(...) and try-catch (...) are treated in a special way
 *           .The colon and the parentheses are considered to be a part of the
 *           constructs. The caseand the colon or the “for (...)”, “if (...)”,
 *           “switch (...)”, “while(...)”, “try-catch( )” arecounted together as
 *           one operator.
 *
 *           - The comments are considered neither an operator nor an operand.
 * 
 *           - The function name is considered a single operator when it appears
 *           as calling a function ;but when it appears in declarations or in
 *           function definitions it is not counted as operator.
 *
 *           - Same is the case for the identifiers( or variables) and
 *           constants; when they appear indeclaration they are not considered
 *           as operands, they are considered operands onlywhen they appear with
 *           operators in expressions.As an example, func(a,b);-----here func, a
 *           and b are considered operands and ‘,’ and‘;’ operators as it is
 *           calling a function, but for the following case we do not treat
 *           func, aand b as operands int func(int a , int b) {...}
 *
 *           - Default parameter assignments are not counted, e.g.,class Point
 *           {Point(int x = 0,int y = 0);};is not counted. - new and delete
 *           considered same as the function calls, mainly because they
 *           areequivalent to the function calls. - There are two extra
 *           operators in JAVA : >>> and >>>+ each is considered a
 *           singleoperator.
 * @author brunosendras
 *
 */
public class JavaFileParserHalsteadVisitor extends ASTVisitor {
	public static final Integer MAX_ARITMETIC_OPERATOR_LENGTH = 4;
	private String operatorKeywords[] = { "auto", "extern", "register", "static", "typedef", "virtual", "mutable",
			"inline", "const", "friend", "volatile", "transient", "final", "break", "case", "continue", "default", "do",
			"if", "else", "enum", "for", "goto", "if", "new", "return", "asm", "operator", "private", "protected",
			"public", "sizeof", "struct", "switch", "union", "while", "this", "namespace", "using", "try", "catch",
			"throw", "throws", "finally", "strictfp", "instanceof", "interface", "extends", "implements", "abstract",
			"concrete", "const_cast", "static_cast", "dynamic_cast", "reinterpret_cast", "typeid", "template",
			"explicit", "true", "false", "typename" };

	/**
	 * N1: Operandos totales = suma de los miembros Integer del mapa. n1:
	 * Operandos unicos = cantidad de items del mapa.
	 */
	private HashMap<String, Integer> operands;

	/**
	 * N2: Operadores totales = suma de los miembros Integer del mapa. n2:
	 * Operadores unicos = cantidad de items del mapa.
	 */
	private HashMap<String, Integer> operators;

	private String methodBody;

	private int N1; // total operators
	private int n1; // unique operators
	private int N2; // total operands
	private int n2; // unique operands

	private boolean metricResolved;

	public JavaFileParserHalsteadVisitor(String methodBody) {
		super();
		this.metricResolved = false;
		this.N1 = 0;
		this.N2 = 0;
		this.n1 = 0;
		this.n2 = 0;
		this.operands = new HashMap<String, Integer>();
		this.operands.clear();
		this.operators = new HashMap<String, Integer>();
		this.methodBody = methodBody;
	}

	// ---------------------------------------
	// OPERANDOS
	// ---------------------------------------

	// declaraciones de primitivos
	@Override
	public boolean visit(PrimitiveType node) {
		String name = node.getPrimitiveTypeCode().toString();
		Integer count = 0;
		
		// es vacio?
		if (name.trim().isEmpty()) {
			// salgo y no lo cuento.
			return true;
		}
		
		if (operands.containsKey(name)) {
			count = operands.get(name);
		}
		operands.put(name, (count == 0) ? 1 : count + 1);
		return true;
	}

	// Todo lo que no sea una palabra reservada,
	// literales "true", "false" o "null"
	@Override
	public boolean visit(SimpleName node) {
		String name = node.getIdentifier();
		Integer count = 0;

		// es vacio?
		if (name.trim().isEmpty()) {
			// salgo y no lo cuento.
			return true;
		}
		
		if (operands.containsKey(name)) {
			count = operands.get(name);
		}
		operands.put(name, (count == 0) ? 1 : count + 1);
		return true;
	}

	// Literales char
	@Override
	public boolean visit(CharacterLiteral node) {
		String name = node.getEscapedValue();
		Integer count = 0;

		// es vacio?
		if (name.trim().isEmpty()) {
			// salgo y no lo cuento.
			return true;
		}
		
		if (operands.containsKey(name)) {
			count = operands.get(name);
		}
		operands.put(name, (count == 0) ? 1 : count + 1);
		return true;
	}

	// Literales string
	@Override
	public boolean visit(NumberLiteral node) {
		String name = node.getToken();
		Integer count = 0;

		// es vacio?
		if (name.trim().isEmpty()) {
			// salgo y no lo cuento.
			return true;
		}
		
		if (operands.containsKey(name)) {
			count = operands.get(name);
		}
		operands.put(name, (count == 0) ? 1 : count + 1);
		return true;
	}

	// Literales numericos
	@Override
	public boolean visit(StringLiteral node) {
		String name = node.getLiteralValue();
		Integer count = 0;
		
		// es vacio?
		if (name.trim().isEmpty()) {
			// salgo y no lo cuento.
			return true;
		}
		
		if (operands.containsKey(name)) {
			count = operands.get(name);
		}
		operands.put(name, (count == 0) ? 1 : count + 1);
		
		return true;
	}

	// ---------------------------------------
	// OPERADORES
	// ---------------------------------------
	private void getOperatorCount() {
		// Esto es aproximado y tiene mucho error hay que hacerlo con el modelo
		// de visitors.

		// Aca cuento los operadores con algun metodo
		char[] methodBodyArr = null;
		String methodBodyStr = "";
		
		// Piso los literales con basura, para que no sean
		// tomados en cuenta en la busqueda de operadores.
		String regex = "\"(?:\\\\.|[^\"\\\\])*\"";
		String methodBodyWithoutStringLiterals = methodBody.replaceAll(regex,  "\"@#####@\"");
		
		// recupero el texto del metodo.
		methodBodyArr = methodBodyWithoutStringLiterals.toCharArray();
		methodBodyStr = methodBodyWithoutStringLiterals;

		// Recorro el metodo en busca de operadores
		int i = 0;
		while (i < methodBodyArr.length) {

			char[] operator = new char[MAX_ARITMETIC_OPERATOR_LENGTH];
			boolean foundSomething = false;
			Integer count = 0;
			String name = "";
			
			// mientras no sea operador, avanzo.
			while((i < methodBodyArr.length) && !RSyntaxUtilities.isJavaOperator(methodBodyArr[i])) {
				i++;
			}
			
			// ahora avanzo mientras sea operador y me lo guardo.
			int k = 0;
			while((i < methodBodyArr.length) && RSyntaxUtilities.isJavaOperator(methodBodyArr[i]) && (k < MAX_ARITMETIC_OPERATOR_LENGTH)) {
				foundSomething = true;
				operator[k] = methodBodyArr[i];
				k++;
				i++;
			}
			
			// Encontre algo?
			if (foundSomething) {
				foundSomething = false;
				
				// si, lo analizo.
				name = String.copyValueOf(operator);
				
				// forme un operador?
				if (!name.trim().isEmpty() && (name.length() <= MAX_ARITMETIC_OPERATOR_LENGTH)) {
					// si, lo agrego al mapa.
					if (operators.containsKey(name)) {
						count = operators.get(name);	
					}
					operators.put(name, (count == 0) ? 1 : count + 1);
				}
			}
			
			i++;
		}

		// Cuento las ocurrencias de cada keyword en el texto del metodo.
		for (int j = 0; j < operatorKeywords.length; j++) {
			String name = operatorKeywords[j];
			Integer count = 0;
			
			// De esta forma me aseguro de buscar palabras completas.
			String patternStr = "\\b(" + name + ")\\b";
			Pattern pattern = Pattern.compile(patternStr);
			Matcher matcher = pattern.matcher(methodBodyStr);
		
			while (matcher.find()) {
				count++;
			}
			
			// si habia ocurrencias las cuento.
			if (count > 0) {
				// System.out.println(name + " " + String.valueOf(count1));
				operators.put(name, count);
			}
		}
		
		// Ahora sumo lo que conte.
		N1 = 0;
		
		// Operadores Unicos
		n1 = operators.size();

		// total de ocurrencias;
		for (int value : operators.values()) {
			N1 = N1 + value;
		}
	}

	private void getOperandCount() {
		// Aca cuento los resultados del map de operandos.
		N2 = 0;
		
		// Operandos Unicos
		n2 = operands.size();

		// total de ocurrencias;
		for (int value : operands.values()) {
			N2 = N2 + value;
		}
	}

	public void resolveHalsteadMetrics() {
		if (!metricResolved) {
			metricResolved = true;
			getOperatorCount();
			getOperandCount();
		}
	}

	/**
	 * N = N1 + N2
	 */
	public int getLength() {
		return N1 + N2;
	}

	/**
	 * n = n1 + n2 V = N * log2(n)
	 */
	public double getVolume() {
		int N = N1 + N2;
		int n = n1 + n2;
		return N * (Math.log(n) / Math.log(2));
	}

	public int get_N1() {
		return N1;
	}

	public int get_n1() {
		return n1;
	}

	public int get_N2() {
		return N2;
	}

	public int get_n2() {
		return n2;
	}
	
	public HashMap<String, Integer> getOperands() {
		return operands;
	}

	public HashMap<String, Integer> getOperators() {
		return operators;
	}	
}
