package unlam.analisisdesoftware.JavaMetricsUI;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AboutWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7149394239647076681L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AboutWindow() {
		setResizable(false);
		setAlwaysOnTop(true);
		setType(Type.UTILITY);
		setTitle("Acerca de Java Metrics");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		
		// la centro segun la resolucion del monitor.
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int ScreenWidth = gd.getDisplayMode().getWidth();
		int ScreenHeight = gd.getDisplayMode().getHeight();
		this.setBounds((ScreenWidth-this.getWidth())/2, (ScreenHeight-this.getHeight())/2, 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Java Metrics");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		lblNewLabel.setBounds(12, 13, 270, 32);
		contentPane.add(lblNewLabel);
		
		JLabel lblUnlamAnalisis = new JLabel("UNLaM - Analisis de Software 1C-2016");
		lblUnlamAnalisis.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnlamAnalisis.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblUnlamAnalisis.setBounds(12, 52, 270, 32);
		contentPane.add(lblUnlamAnalisis);
		
		JLabel lblBuild = new JLabel("Build 1.0.0.0");
		lblBuild.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuild.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblBuild.setBounds(12, 127, 270, 32);
		contentPane.add(lblBuild);
	}

}
