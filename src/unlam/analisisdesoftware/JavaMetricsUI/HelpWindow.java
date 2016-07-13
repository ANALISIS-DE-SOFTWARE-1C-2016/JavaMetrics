package unlam.analisisdesoftware.JavaMetricsUI;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JScrollPane;

public class HelpWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5776281436313207978L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public HelpWindow() {
		setResizable(false);
		setAlwaysOnTop(true);
		setType(Type.UTILITY);
		setTitle("Ayuda");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		// la centro segun la resolucion del monitor.
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int ScreenWidth = gd.getDisplayMode().getWidth();
		int ScreenHeight = gd.getDisplayMode().getHeight();
		this.setBounds((ScreenWidth-this.getWidth())/2, (ScreenHeight-this.getHeight())/2, 800, 600);		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 54, 770, 494);
		contentPane.add(scrollPane);
		
		JTextPane txtpnLosIdentificadoresDe = new JTextPane();
		txtpnLosIdentificadoresDe.setFont(new Font("Consolas", Font.PLAIN, 13));
		scrollPane.setViewportView(txtpnLosIdentificadoresDe);
		txtpnLosIdentificadoresDe.setText("Los identificadores de las siguientes categor\u00EDas son contados como operandos:\r\n\r\nIdentificadores simples:\r\n\t\r\n\t- Tods los indentidficadores que no sean palabras reservadas.\r\n\r\nIdentificadores de tipo:\r\n\r\n\t- Tipos primitivos: bool , byte, int, float,char, double, long, short, signed, unsigned, void\r\n\t- Tipos complejos como nombres de clases.\r\n\t\r\nConstantes:\r\n\t\r\n\t-Literales de tipo caracter, numericos y cadenas de texto.\r\n\r\nLos identificadores de las siguientes categor\u00EDas son contados como operadores:\r\n\r\nEspecificadores de clases de almacenamiento:\r\n\t\r\n\t- auto, extern, register, static, typedef, virtual, mutable, inline.\r\n\r\nCalificadores de tipo:\r\n\t\r\n\t- const, friend, volatile,transient, final\r\n\r\nTodas las palabras reservadas de JAVA que no se incluyan en las categorias anteriores:\r\n\t\r\n\t- break, case, continue, default, do, if, else,enum, for, goto, if, new, return, asm,\r\n\t  operator, private, protected, public,sizeof, struct, switch, union, while, this,\r\n\t  namespace, using, try, catch,throw, throws, finally, strictfp, instanceof, interface,\r\n\t  extends, implements,abstract, concrete, const_cast, static_cast, dynamic_cast,\r\n\t  reinterpret_cast, typeid, template, explicit, true, false, typename \r\n\r\nOperadores aritmenticos de JAVA:\r\n\r\n\t\t!\t\t!=\t\t%\t\t%=\t\t\r\n\t\t&\t\t&&\t\t||\t\t&=\t\r\n\t\t*\t\t*=\t\t+\t\t++\t\r\n\t\t+=\t\t-\t\t--\t\t-= \r\n\t\t/\t\t/=\t\t:\t\t::\r\n\t\t<\t\t<<\t\t<<= \t\t<=\r\n\t\t=\t\t== \t\t>\t\t>=\r\n\t\t>>\t\t>>>\t\t>>=\t\t>>>=\r\n\t\t?\r\n\t\t^\t\t^=\t\t|\t\t|=\r\n\t\t~\t\t=&");
		txtpnLosIdentificadoresDe.setBackground(SystemColor.menu);
		
		JLabel lblNewLabel = new JLabel("Metricas de Halstead");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(12, 13, 770, 28);
		contentPane.add(lblNewLabel);
	}
}
