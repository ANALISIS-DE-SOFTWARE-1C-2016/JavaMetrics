package unlam.analisisdesoftware.JavaMetricsMain;

import java.awt.EventQueue;

import unlam.analisisdesoftware.JavaMetricsUI.MainWindow;

public class JavaMetrics {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Creo La ventana.
					MainWindow mainWindow = new MainWindow();
					mainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
