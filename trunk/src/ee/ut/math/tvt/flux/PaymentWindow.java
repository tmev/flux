package ee.ut.math.tvt.flux;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;*/


import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;

public class PaymentWindow extends SalesDomainControllerImpl {

	public static void createPaymentWindow() {
		JFrame frame = new JFrame("Payment");	
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.add(new JLabel("Total sum:"));
		String sumText = String.valueOf(getTotalSum());
		JTextField sumField = new JTextField(sumText);
		sumField.setEditable(false);
		panel.add(sumField);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	} 
	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createPaymentWindow();
			}
		});
	}
}