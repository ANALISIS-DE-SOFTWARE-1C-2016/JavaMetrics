package unlam.analisisdesoftware.JavaMetricsUI;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JList;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import org.eclipse.jdt.core.dom.Comment;
import javax.swing.SwingConstants;

import org.fife.ui.rtextarea.*;

import unlam.analisisdesoftware.JavaMetricsParser.JavaFileParser;
import unlam.analisisdesoftware.JavaMetricsParser.JavaFileParserClassMethodVisitor;
import unlam.analisisdesoftware.JavaMetricsParser.JavaFileParserClassVisitor;
import unlam.analisisdesoftware.JavaMetricsParser.JavaFileParserCommentVisitor;
import unlam.analisisdesoftware.JavaMetricsParser.JavaFileParserHalsteadVisitor;
import unlam.analisisdesoftware.JavaMetricsParser.JavaFileParserMethodFanInVisitor;
import unlam.analisisdesoftware.JavaMetricsParser.JavaFileParserMethodFanOutVisitor;
import unlam.analisisdesoftware.JavaMetricsParser.JavaFileParserMethodVisitor;

import org.fife.ui.rsyntaxtextarea.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

public class MainWindow {

	private JFrame frmJavaMetrics;
	private HelpWindow helpWindow;
	private AboutWindow aboutWindow;
	private JFileChooser fileChooser;
	private JavaFileParser JFP;
	
	public static final int WINDOW_WIDTH = 1024;
	public static final int WINDOW_HEIGHT = 768;
	
	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}
	
	// No expongo el frame para hacerlo visible.
	public void setVisible(boolean Visible) {
		frmJavaMetrics.setVisible(Visible);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJavaMetrics = new JFrame();
		frmJavaMetrics.setTitle("Java Metrics");
		frmJavaMetrics.setBounds(new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT));
		frmJavaMetrics.setName("MainFrame");
		frmJavaMetrics.setResizable(false);
		frmJavaMetrics.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		frmJavaMetrics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJavaMetrics.getContentPane().setLayout(null);		
		
		// la centro segun la resolucion del monitor.
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int ScreenWidth = gd.getDisplayMode().getWidth();
		int ScreenHeight = gd.getDisplayMode().getHeight();
		frmJavaMetrics.setBounds((ScreenWidth-frmJavaMetrics.getWidth())/2, (ScreenHeight-frmJavaMetrics.getHeight())/2, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		fileChooser = new JFileChooser();
		
		JLabel lblClasses = new JLabel("Clases: ");
		lblClasses.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblClasses.setBounds(20, 38, 142, 14);
		frmJavaMetrics.getContentPane().add(lblClasses);
	
		JLabel lblMethods = new JLabel("Metodos: ");
		lblMethods.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMethods.setBounds(334, 38, 142, 14);
		frmJavaMetrics.getContentPane().add(lblMethods);
		
		final JLabel lblLineCount = new JLabel("Lineas del metodo: ");
		lblLineCount.setBounds(677, 66, 241, 14);
		frmJavaMetrics.getContentPane().add(lblLineCount);
		
		final JLabel lblComments = new JLabel("Lineas comentadas:");
		lblComments.setBounds(677, 93, 241, 14);
		frmJavaMetrics.getContentPane().add(lblComments);
		
		final JLabel lblCommentPerc = new JLabel("Porcentaje de codigo comentado:");
		lblCommentPerc.setBounds(677, 147, 241, 14);
		frmJavaMetrics.getContentPane().add(lblCommentPerc);
		
		final JLabel lblCyclomaticComplex = new JLabel("Complejidad Ciclomatica: ");
		lblCyclomaticComplex.setBounds(677, 174, 241, 14);
		frmJavaMetrics.getContentPane().add(lblCyclomaticComplex);
		
		final JLabel lblMethodAnalysis = new JLabel("Analisis del Metodo: ");
		lblMethodAnalysis.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMethodAnalysis.setBounds(677, 38, 142, 14);
		frmJavaMetrics.getContentPane().add(lblMethodAnalysis);
		
		final JLabel lblFanIn = new JLabel("Fan In: ");
		lblFanIn.setBounds(677, 201, 241, 14);
		frmJavaMetrics.getContentPane().add(lblFanIn);
		
		final JLabel lblFanOut = new JLabel("Fan Out: ");
		lblFanOut.setBounds(677, 228, 241, 14);
		frmJavaMetrics.getContentPane().add(lblFanOut);
		
		final JLabel lblHalsteadLength = new JLabel("Longitud de Halstead: ");
		lblHalsteadLength.setBounds(677, 255, 241, 14);
		frmJavaMetrics.getContentPane().add(lblHalsteadLength);
		
		final JLabel lblHalsteadVolume = new JLabel("Volumen de Halstead: ");
		lblHalsteadVolume.setBounds(677, 282, 241, 14);
		frmJavaMetrics.getContentPane().add(lblHalsteadVolume);
		
		// Creo las listas aqui para que esten disponibles en los eventos mas abajo.
		final JList<String> listClasses = new JList<String>();
		listClasses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listClasses.setBackground(new Color(237, 243, 250));
		listClasses.setBounds(20, 65, 275, 202);
		frmJavaMetrics.getContentPane().add(listClasses);

		// Agrego scrollbars al listMethods
		JScrollPane scrollListClasses = new JScrollPane(listClasses);
		scrollListClasses.setBounds(new Rectangle(20, 65, 275, 231));
	    frmJavaMetrics.getContentPane().add(scrollListClasses);				

		final JList<String> listMethods = new JList<String>();
		listMethods.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMethods.setBackground(new Color(237, 243, 250));
		listMethods.setBounds(334, 65, 300, 202);
		frmJavaMetrics.getContentPane().add(listMethods);			

		// Agrego scrollbars al listMethods
		JScrollPane scrollListMethods = new JScrollPane(listMethods);
		scrollListMethods.setBounds(new Rectangle(334, 65, 300, 231));
		frmJavaMetrics.getContentPane().add(scrollListMethods);				
		
		// Menues.
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, WINDOW_WIDTH, 26);
		frmJavaMetrics.getContentPane().add(menuBar);
		
		JMenu FileMenu = new JMenu("Archivo");
		menuBar.add(FileMenu);
		
		JMenuItem OpenFileMenuItem = new JMenuItem("Abrir...");
		FileMenu.add(OpenFileMenuItem);
		
		JMenu HelpMenu = new JMenu("Ayuda");
		menuBar.add(HelpMenu);
		
		JMenuItem HelpContentsMenuItem = new JMenuItem("Temas de ayuda...");
		HelpMenu.add(HelpContentsMenuItem);
		
		JMenuItem AboutMenuItem = new JMenuItem("Acerca de Java Metrics...");
		HelpMenu.add(AboutMenuItem);
		
		final JLabel lblLineCountValue = new JLabel("--");
		lblLineCountValue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLineCountValue.setBounds(887, 66, 97, 14);
		frmJavaMetrics.getContentPane().add(lblLineCountValue);
		
		final JLabel lblCommentsValue = new JLabel("--");
		lblCommentsValue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCommentsValue.setBounds(887, 93, 97, 14);
		frmJavaMetrics.getContentPane().add(lblCommentsValue);
		
		final JLabel lblCommentPercValue = new JLabel("--");
		lblCommentPercValue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCommentPercValue.setBounds(887, 147, 97, 14);
		frmJavaMetrics.getContentPane().add(lblCommentPercValue);
		
		final JLabel lblCyclomaticComplexValue = new JLabel("--");
		lblCyclomaticComplexValue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCyclomaticComplexValue.setBounds(887, 174, 97, 14);
		frmJavaMetrics.getContentPane().add(lblCyclomaticComplexValue);
		
		final JLabel lblFanInValue = new JLabel("--");
		lblFanInValue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFanInValue.setBounds(887, 201, 97, 14);
		frmJavaMetrics.getContentPane().add(lblFanInValue);
		
		final JLabel lblFanOutValue = new JLabel("--");
		lblFanOutValue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFanOutValue.setBounds(887, 228, 97, 14);
		frmJavaMetrics.getContentPane().add(lblFanOutValue);
		
		final JLabel lblHalsteadLengthValue = new JLabel("--");
		lblHalsteadLengthValue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHalsteadLengthValue.setBounds(887, 255, 97, 14);
		frmJavaMetrics.getContentPane().add(lblHalsteadLengthValue);
		
		final JLabel lblHalsteadVolumeValue = new JLabel("--");
		lblHalsteadVolumeValue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHalsteadVolumeValue.setBounds(887, 282, 97, 14);
		frmJavaMetrics.getContentPane().add(lblHalsteadVolumeValue);
		
		JLabel lblWhiteLineCount = new JLabel("Lineas en blanco:");
		lblWhiteLineCount.setBounds(677, 120, 241, 14);
		frmJavaMetrics.getContentPane().add(lblWhiteLineCount);
		
		final JLabel lblWhiteLineCountValue = new JLabel("--");
		lblWhiteLineCountValue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWhiteLineCountValue.setBounds(887, 120, 97, 14);
		frmJavaMetrics.getContentPane().add(lblWhiteLineCountValue);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(20, 309, 975, 395);
		frmJavaMetrics.getContentPane().add(tabbedPane);
		
		JPanel methodBodyPanel = new JPanel();
		methodBodyPanel.setLayout(null);
		tabbedPane.addTab("Codigo del Metodo", null, methodBodyPanel, null);
		
		final RSyntaxTextArea textArea = new RSyntaxTextArea();
		textArea.setBounds(483, 6, 4, 20);
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		textArea.setCurrentLineHighlightColor(Color.WHITE);
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		textArea.setPopupMenu(null);
		methodBodyPanel.add(textArea);
		
		// Agrego scrollbars al textArea
		final RTextScrollPane scrollTextArea = new RTextScrollPane(textArea);
		scrollTextArea.setBounds(1, 0, 967, 364);
		scrollTextArea.getGutter().setLineNumberingStartIndex(0);
		scrollTextArea.getGutter().setBackground(Color.WHITE);
		methodBodyPanel.add(scrollTextArea);
		
		JPanel halsteadDetailPanel = new JPanel();
		halsteadDetailPanel.setLayout(null);
		tabbedPane.addTab("Detalles de Halstead", null, halsteadDetailPanel, null);
		
		final RSyntaxTextArea textAreaOperatorsDetail = new RSyntaxTextArea();
		textAreaOperatorsDetail.setBounds(23, 1, 435, 351);
		textAreaOperatorsDetail.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
		textAreaOperatorsDetail.setCurrentLineHighlightColor(Color.WHITE);
		textAreaOperatorsDetail.setBackground(Color.WHITE);
		textAreaOperatorsDetail.setVisible(true);
		textAreaOperatorsDetail.setEditable(false);
		textAreaOperatorsDetail.setPopupMenu(null);
		halsteadDetailPanel.add(textAreaOperatorsDetail);
		
		// Agrego scrollbars al textArea
		final RTextScrollPane scrollTextAreaOperatorsDetail = new RTextScrollPane(textAreaOperatorsDetail);
		scrollTextAreaOperatorsDetail.setBounds(488, 0, 480, 364);
		scrollTextAreaOperatorsDetail.getGutter().setLineNumberingStartIndex(0);
		scrollTextAreaOperatorsDetail.getGutter().setBackground(Color.WHITE);
		scrollTextAreaOperatorsDetail.setLineNumbersEnabled(false);
		halsteadDetailPanel.add(scrollTextAreaOperatorsDetail);
		
		final RSyntaxTextArea textAreaOperandsDetail = new RSyntaxTextArea();
		textAreaOperandsDetail.setBounds(510, 6, 4, 20);
		textAreaOperandsDetail.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
		textAreaOperandsDetail.setCurrentLineHighlightColor(Color.WHITE);
		textAreaOperandsDetail.setBackground(Color.WHITE);
		textAreaOperandsDetail.setVisible(true);
		textAreaOperandsDetail.setEditable(false);
		textAreaOperandsDetail.setPopupMenu(null);
		halsteadDetailPanel.add(textAreaOperandsDetail);
		
		// Agrego scrollbars al textArea
		final RTextScrollPane scrollTextAreaOperandsDetails = new RTextScrollPane(textAreaOperandsDetail);
		scrollTextAreaOperandsDetails.setBounds(1, 0, 480, 364);
		scrollTextAreaOperandsDetails.getGutter().setLineNumberingStartIndex(0);
		scrollTextAreaOperandsDetails.getGutter().setBackground(Color.WHITE);
		scrollTextAreaOperandsDetails.setLineNumbersEnabled(false);
		halsteadDetailPanel.add(scrollTextAreaOperandsDetails);
				
		// Eventos
		OpenFileMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File file = null;
				fileChooser.setCurrentDirectory(new File("."));
				int returnVal = fileChooser.showOpenDialog(frmJavaMetrics);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					file = fileChooser.getSelectedFile();
					try {
						
						// Limpio la interfaz de usuario.
						DefaultListModel<String> emptyModel = new DefaultListModel<String>();
						listClasses.setModel(emptyModel);
						listMethods.setModel(emptyModel);
						textArea.setText("--");
						textAreaOperandsDetail.setText("--");
						textAreaOperatorsDetail.setText("--");
						lblLineCountValue.setText("--");
						lblCommentsValue.setText("--");
						lblWhiteLineCountValue.setText("--");
						lblCommentPercValue.setText("--");
						lblCyclomaticComplexValue.setText("--");
						lblFanInValue.setText("--");
						lblFanOutValue.setText("--");
						lblHalsteadLengthValue.setText("--");
						lblHalsteadVolumeValue.setText("--");
							
						// comienzo a procesar el archivo.
						JFP = new JavaFileParser(file);
						
						// Parseo las clases.
						JavaFileParserClassVisitor classVisitor = new JavaFileParserClassVisitor();
						JFP.getCompilationUnit().accept(classVisitor);
						
						// Lleno la lista.
						DefaultListModel<String> listModel = new DefaultListModel<String>();
						listClasses.setModel(listModel);
						for (String className : classVisitor.getClassNames()) {
							listModel.addElement(className);
						}
						
						// Si tengo elementos selecciono el primero
						if (listClasses.getModel().getSize() > 0) {
							listClasses.setSelectedIndex(0);
						} else {
							// No hubo resultados.
							javax.swing.JOptionPane.showMessageDialog(frmJavaMetrics, 
									"No se ha detectado codigo java en el archivo.",
									"Atencion!", JOptionPane.WARNING_MESSAGE);
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}				
			}
		});
		
		listClasses.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
				// evito ejecutar multiples veces mientras el
				// componente cambia el seleccionado
				if (arg0.getValueIsAdjusting()) {
					return;
				}
				
				String className = listClasses.getSelectedValue();
				
				if (className != null) {
					
					// Parseo los metodos de la clase seleccionada.
					JavaFileParserClassMethodVisitor classMethodVisitor = new JavaFileParserClassMethodVisitor(className);
					JFP.getCompilationUnit().accept(classMethodVisitor);
					
					// Lleno la lista de metodos.
					DefaultListModel<String> listModel = new DefaultListModel<String>();
					listMethods.setModel(listModel);
					for (String methodName : classMethodVisitor.getMethodNames()) {
						listModel.addElement(methodName);
					}	
					
					// Si tengo elementos selecciono el primero
					if (listMethods.getModel().getSize() > 0) {
						listMethods.setSelectedIndex(0);
					}
				}
			}
		});
		
		listMethods.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
				// evito ejecutar multiples veces mientras el
				// componente cambia el seleccionado
				if (arg0.getValueIsAdjusting()) {
					return;
				}
				
				String methodName = listMethods.getSelectedValue();
				String className = listClasses.getSelectedValue();
				
				if (className != null && methodName != null) {
					
					// Parseo el metodo seleccionado.
					JavaFileParserMethodVisitor methodVisitor = new JavaFileParserMethodVisitor(methodName, className);
					JFP.getCompilationUnit().accept(methodVisitor);
					
					// Limpio el text area.
					textArea.setText("\n");
					
					// Muestro el codigo del metodo.
					// Tomo el rango de lineas del visitor.
					int methodStartLine = methodVisitor.getMethodStartLine();
					int methodEndLine = methodVisitor.getMethodEndLine();
					int whiteLineCount = 0; 
					// Como el rango de lineas viene daddo por la posicion relativa en la cmpilationUnit
					// interna, debo restar 1, para coincidir con el indice de la lista
					for (String line : JFP.getSource().subList(methodStartLine - 1, methodEndLine - 1)) {
						/* Cuento las lineas en blanco. */
						textArea.append(line + "\n");
						
						line = line.trim();
						if (line.isEmpty()) {
							whiteLineCount++;
						}
					}
					
					// Dejo visible el texto desde el principio.
					textArea.setCaretPosition(0);
					
					// Calculo las metricas.
					int totalMethodLines = methodVisitor.getLineCount() - whiteLineCount;
					int cyclomaticComplexity = methodVisitor.getCyclomaticComplexity();
					int commentedLines = 0;

					// El methodVisitor tiene todas las instancias de comentarios del metodo.
					// Debo recorrer los comentarios debido a que no son compilables,
					// y visitarlos uno a uno acumulando su cantidad de lineas.
					for (Comment comment : (List<Comment>) methodVisitor.getCommentNodes()) {
						JavaFileParserCommentVisitor commentVisitor = new JavaFileParserCommentVisitor(JFP.getCompilationUnit());
						comment.accept(commentVisitor);
						commentedLines = commentedLines + commentVisitor.getLineCount();
					}
				
					// Calculo el porcentaje de lineas comentadas.
					double commentedLinesPerc = 100 * (commentedLines * 1.0) / (totalMethodLines * 1.0);
					
					// Calculo el FanIn
					JavaFileParserMethodFanInVisitor methodFanInVisitor = new JavaFileParserMethodFanInVisitor(methodVisitor.getMethodNode().getName().toString());
					JFP.getCompilationUnit().accept(methodFanInVisitor);
					int methodFanIn = methodFanInVisitor.getCallers();
					
					// Calculo el FanOut
					JavaFileParserMethodFanOutVisitor methodFanOutVisitor =  new JavaFileParserMethodFanOutVisitor();
					methodVisitor.getMethodNode().accept(methodFanOutVisitor);
					int methodFanOut = methodFanOutVisitor.getCallees();
					
					JavaFileParserHalsteadVisitor halsteadVisitor = new JavaFileParserHalsteadVisitor(methodVisitor.getMethodBody());
					methodVisitor.getMethodNode().accept(halsteadVisitor);
					
					// resuelvo las metricas que no pude hacer con el modelo de visitors.
					halsteadVisitor.resolveHalsteadMetrics();
					
					// recupero los valores calculados
					Integer halsteadLength = halsteadVisitor.getLength();
					double halsteadVolume = halsteadVisitor.getVolume();
					
					// Muestro las metricas calculadas.
					lblLineCountValue.setText(Integer.toString(totalMethodLines));
					lblCommentsValue.setText(Integer.toString(commentedLines));
					lblWhiteLineCountValue.setText(Integer.toString(whiteLineCount));
					lblCommentPercValue.setText("%" + String.format("%.2f", commentedLinesPerc));
					lblCyclomaticComplexValue.setText(Integer.toString(cyclomaticComplexity));
					lblFanInValue.setText(Integer.toString(methodFanIn));
					lblFanOutValue.setText(Integer.toString(methodFanOut));
					lblHalsteadLengthValue.setText(Integer.toString(halsteadLength));
					lblHalsteadVolumeValue.setText(String.format("%.2f", halsteadVolume));
					
					GenerateHalsteadOperandReport(halsteadVisitor, textAreaOperandsDetail);
					GenerateHalsteadOperatorReport(halsteadVisitor, textAreaOperatorsDetail);
				}
			}
		});	
		
		frmJavaMetrics.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				try {
					// cambio el tema del editor.
					try {
						EditorTheme editorTheme = new EditorTheme();
						Theme theme = new Theme(textArea);
						theme = Theme.load(new ByteArrayInputStream(editorTheme.getThemeXML().getBytes()));
						theme.apply(textArea);
						
						// cambio estilos que no estan en el tema original
						textArea.setBackground(new Color(237, 243, 250));
						textArea.setCurrentLineHighlightColor(new Color(184, 207, 229));
						scrollTextArea.getGutter().setBackground(new Color(237, 243, 250));
												
						textAreaOperandsDetail.setBackground(new Color(237, 243, 250));
						//textAreaOperandsDetail.setCurrentLineHighlightColor(new Color(184, 207, 229));
						textAreaOperandsDetail.setCurrentLineHighlightColor(new Color(237, 243, 250));
						scrollTextAreaOperandsDetails.getGutter().setBackground(new Color(237, 243, 250));
						scrollTextAreaOperandsDetails.getGutter().setLineNumberFont(
								scrollTextArea.getGutter().getLineNumberFont());
						
						textAreaOperatorsDetail.setBackground(new Color(237, 243, 250));
						// textAreaOperatorsDetail.setCurrentLineHighlightColor(new Color(184, 207, 229));
						textAreaOperatorsDetail.setCurrentLineHighlightColor(new Color(237, 243, 250));
						scrollTextAreaOperatorsDetail.getGutter().setBackground(new Color(237, 243, 250));
						scrollTextAreaOperatorsDetail.getGutter().setLineNumberFont(
								scrollTextArea.getGutter().getLineNumberFont());
						
					} finally {
						textArea.setVisible(true);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}	
			}
		});	
		
		// Cada vez que modifico un propiedad, en este caso el texto del label, cambio el color si hace falta.
		lblCyclomaticComplexValue.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {	
				
				try {
					int value = Integer.parseInt(lblCyclomaticComplexValue.getText()); 
					if (value > 10) {
						lblCyclomaticComplexValue.setForeground(new Color(255, 0, 0));
					} else {
						lblCyclomaticComplexValue.setForeground(new Color(0, 0, 0));
					}
				
				} catch (IllegalArgumentException e) {
					// solo enmascaro la excepcion.
				}
			}
		});
		
		// inicializo en null.
		this.helpWindow = null;
		
		// Muestro la ayuda.
		HelpContentsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (helpWindow == null) {
						helpWindow = new HelpWindow();
					}
					helpWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		this.aboutWindow = null;
		
		// Muestro la ayuda.
		AboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (aboutWindow == null) {
						aboutWindow = new AboutWindow();
					}
					aboutWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void GenerateHalsteadOperandReport(
			JavaFileParserHalsteadVisitor halsteadVisitor, RSyntaxTextArea textArea) {
		
		// Detalle de halstead.
		ArrayList<String> lines = new ArrayList<String>(); 
		
		lines.clear();
		
		// Operandos.
		HashMap <String, Integer> operands = halsteadVisitor.getOperands();
		Iterator <String> operandKeySetIterator = operands.keySet().iterator();
		String operand = "";
		
		// Limpio el text area.
		textArea.setText("");
		
		// Encabezado.
		textArea.append("\t\n");	
		textArea.append("\tOPERANDOS\n");
		textArea.append("\t------------------\n");
		textArea.append("\tN2 (TOTALES): " + String.valueOf(halsteadVisitor.get_N2()) + "\n");
		textArea.append("\tn2 (UNICOS):  " + String.valueOf(halsteadVisitor.get_n2()) + "\n");
		textArea.append("\t------------------\n");
		textArea.append("\tCANTIDAD\tOPERANDO\n");
		textArea.append("\t--------\t--------\n");
		
		while (operandKeySetIterator.hasNext()) {
			operand = (String) operandKeySetIterator.next();
			String trimmedOperand = operand.trim();
			lines.add("\t" +  ((Integer)operands.get(operand)).toString() + "\t\t" + trimmedOperand + "\n");
		}
		
		// Debiado al tabulado el sort no tiene sentido.
		// Collections.sort(lines);
		
		for(String line : lines) {
			textArea.append(line);
		}
		
		// Dejo visible el texto desde el principio.
		textArea.setCaretPosition(0);
	}
	
	private void GenerateHalsteadOperatorReport(
			JavaFileParserHalsteadVisitor halsteadVisitor, RSyntaxTextArea textArea) {
		
		// Detalle de halstead.
		ArrayList<String> lines = new ArrayList<String>();

		lines.clear();
		
		// Operadores.
		HashMap <String, Integer> operators = halsteadVisitor.getOperators();
		Iterator <String> operatorKeySetIterator = operators.keySet().iterator();
		String operator = "";
		
		// Limpio el text area.
		textArea.setText("");
		
		// Encabezado.
		textArea.append("\t\n");	
		textArea.append("\tOPERADORES\n");
		textArea.append("\t------------------\n");
		textArea.append("\tN1 (TOTALES): " + String.valueOf(halsteadVisitor.get_N1()) + "\n");
		textArea.append("\tn1 (UNICOS):  " + String.valueOf(halsteadVisitor.get_n1()) + "\n");
		textArea.append("\t------------------\n");
		textArea.append("\tCANTIDAD\tOPERADOR\n");
		textArea.append("\t--------\t--------\n");
		while (operatorKeySetIterator.hasNext()) {
			operator = (String) operatorKeySetIterator.next();
			String trimmedOperator = operator.trim();
			lines.add("\t" + ((Integer)operators.get(operator)).toString() +"\t\t" + trimmedOperator + "\n");
		}
		
		// Debiado al tabulado el sort no tiene sentido.
		// Collections.sort(lines);
		
		for(String line : lines) {
			textArea.append(line);
		}
		
		// Dejo visible el texto desde el principio.
		textArea.setCaretPosition(0);
		
	}
}
