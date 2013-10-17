package ee.ut.math.tvt.flux;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;

public class PaymentWindow extends SalesDomainControllerImpl {
	private static final Logger log = LogManager.getLogger(PaymentWindow.class);
	public static void createPaymentWindow() {
		JFrame frame = new JFrame("Payment");	
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));

		panel.add(new JLabel("Total sum:"));

		final String sumText = String.valueOf(getTotalSum());
		JTextField sumField = new JTextField(sumText);
		sumField.setEditable(false);
		panel.add(sumField);

		panel.add(new JLabel("Payment amount:"));

		final JTextField paymentField = new JTextField();
		panel.add(paymentField);

		panel.add(new JLabel("Change amount:"));

		final JTextField changeField = new JTextField();
		changeField.setEditable(false);
		panel.add(changeField);


		//This able to see live calculation of change amount
		paymentField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {		
				//Typing check
				try{
					//Field check
					try{
						double payment =  Double.parseDouble(paymentField.getText());
						double sum = getTotalSum();
						if((payment-sum)>=0){
							changeField.setText(String.valueOf(payment-sum));
						}
						else{
							changeField.setText("Payment is insufficient");
						}
					}
					catch(NumberFormatException ee){
						changeField.setText("Type error");
					}

				}
				catch(NumberFormatException ee){
					changeField.setText("Type error");		
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {		
				try{
					double payment =  Double.parseDouble(paymentField.getText());
					double sum = getTotalSum();
					if((payment-sum)>=0){
						changeField.setText(String.valueOf(payment-sum));
					}
					else{
						changeField.setText("Payment is insufficient");
					}
				}
				catch(NumberFormatException ee){
					changeField.setText("Type error");		
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {		
			}

		});

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(panel);
		frame.setSize(100, 200);
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