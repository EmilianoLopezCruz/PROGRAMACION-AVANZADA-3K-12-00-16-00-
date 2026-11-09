package controlador;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.CalculadoraPrestamo;
import modelo.Prestamo;
import utilerias.Dialogos;
import utilerias.Validador;

public class ControladorAsistentePrestamo {

	private static final String ESTILO_SOLO_LECTURA = "-fx-control-inner-background: #FFFF80;";
	private static final String ESTILO_EDITABLE = "-fx-control-inner-background: #FFFFFF;";

	@FXML
	private Label balanceLabel;
	@FXML
	private TextField balanceTextField;
	@FXML
	private Label interestLabel;
	@FXML
	private TextField interestTextField;
	@FXML
	private Label monthsLabel;
	@FXML
	private TextField monthsTextField;
	@FXML
	private Button monthsButton;
	@FXML
	private Label paymentLabel;
	@FXML
	private TextField paymentTextField;
	@FXML
	private Button paymentButton;
	@FXML
	private Button computeButton;
	@FXML
	private Button newLoanButton;
	@FXML
	private Label analysisLabel;
	@FXML
	private TextArea analysisTextArea;
	@FXML
	private Button exitButton;

	private final Prestamo prestamo = new Prestamo();
	private final CalculadoraPrestamo calculadora = new CalculadoraPrestamo();
	private boolean computePayment;

	@FXML
	public void initialize() {
		paymentButtonActionPerformed(null);
		Platform.runLater(() -> balanceTextField.requestFocus());
	}

	@FXML
	private void monthsButtonActionPerformed(ActionEvent e) {
		computePayment = false;
		paymentButton.setVisible(true);
		monthsButton.setVisible(false);
		monthsTextField.setText("");
		monthsTextField.setEditable(false);
		monthsTextField.setStyle(ESTILO_SOLO_LECTURA);
		monthsTextField.setFocusTraversable(false);
		paymentTextField.setEditable(true);
		paymentTextField.setStyle(ESTILO_EDITABLE);
		paymentTextField.setFocusTraversable(true);
		computeButton.setText("Compute Number of Payments");
		balanceTextField.requestFocus();
	}

	@FXML
	private void paymentButtonActionPerformed(ActionEvent e) {
		computePayment = true;
		paymentButton.setVisible(false);
		monthsButton.setVisible(true);
		monthsTextField.setEditable(true);
		monthsTextField.setStyle(ESTILO_EDITABLE);
		monthsTextField.setFocusTraversable(true);
		paymentTextField.setText("");
		paymentTextField.setEditable(false);
		paymentTextField.setStyle(ESTILO_SOLO_LECTURA);
		paymentTextField.setFocusTraversable(false);
		computeButton.setText("Compute Monthly Payment");
		balanceTextField.requestFocus();
	}

	@FXML
	private void computeButtonActionPerformed(ActionEvent e) {
		double balance;
		double interest;
		double payment;
		int months;

		if (Validador.validateDecimalNumber(balanceTextField)) {
			balance = Double.valueOf(balanceTextField.getText()).doubleValue();
		} else {
			Dialogos.mostrarErrorEntrada("Invalid or empty Loan Balance entry.\nPlease correct.",
					"Balance Input Error");
			return;
		}

		if (Validador.validateDecimalNumber(interestTextField)) {
			interest = Double.valueOf(interestTextField.getText()).doubleValue();
		} else {
			Dialogos.mostrarErrorEntrada("Invalid or empty Interest Rate entry.\nPlease correct.",
					"Interest Input Error");
			return;
		}

		prestamo.setBalance(balance);
		prestamo.setInterest(interest);

		if (computePayment) {
			if (Validador.validateDecimalNumber(monthsTextField)) {
				months = Integer.valueOf(monthsTextField.getText()).intValue();
				if (months <= 0) {
					monthsTextField.requestFocus();
					Dialogos.mostrarErrorEntrada("Number of Payments must be greater than zero.\nPlease correct.",
							"Number of Payments Input Error");
					return;
				}
			} else {
				Dialogos.mostrarErrorEntrada("Invalid or empty Number of Payments entry.\nPlease correct.",
						"Number of Payments Input Error");
				return;
			}
			payment = calculadora.calcularPagoMensual(balance, interest, months);
			paymentTextField.setText(calculadora.formatearDosDecimales(payment));
			prestamo.setMonths(months);
			prestamo.setPayment(payment);
		} else {
			if (Validador.validateDecimalNumber(paymentTextField)) {
				payment = Double.valueOf(paymentTextField.getText()).doubleValue();
				double minimumPayment = calculadora.obtenerPagoMinimo(balance, interest);
				if (payment <= minimumPayment) {
					boolean usarPagoMinimo = Dialogos.confirmarPagoMinimo(
							"Minimum payment must be $" + calculadora.formatearDosDecimales((int) (minimumPayment + 1.0))
									+ "\nDo you want to use the minimum payment?",
							"Input Error");
					if (usarPagoMinimo) {
						paymentTextField.setText(calculadora.formatearDosDecimales((int) (minimumPayment + 1.0)));
						payment = Double.valueOf(paymentTextField.getText()).doubleValue();
					} else {
						paymentTextField.requestFocus();
						return;
					}
				}
			} else {
				Dialogos.mostrarErrorEntrada("Invalid or empty Monthly Payment entry.\nPlease correct.",
						"Payment Input Error");
				return;
			}
			months = calculadora.calcularNumeroPagos(balance, interest, payment);
			monthsTextField.setText(String.valueOf(months));
			prestamo.setPayment(payment);
			prestamo.setMonths(months);
		}

		String reporte = calculadora.generarAnalisisPrestamo(prestamo);
		if (!computePayment) {
			monthsTextField.setText(String.valueOf(prestamo.getMonths()));
		}
		analysisTextArea.setText(reporte);
		Dialogos.guardarAnalisisTxt("HistorialAnalisisPrestamos.txt", reporte);

		computeButton.setDisable(true);
		newLoanButton.setDisable(false);
		newLoanButton.requestFocus();
	}

	@FXML
	private void newLoanButtonActionPerformed(ActionEvent e) {
		if (computePayment) {
			paymentTextField.setText("");
		} else {
			monthsTextField.setText("");
		}
		analysisTextArea.setText("");
		computeButton.setDisable(false);
		newLoanButton.setDisable(true);
		balanceTextField.requestFocus();
	}

	@FXML
	private void balanceTextFieldActionPerformed(ActionEvent e) {
		balanceTextField.setText(balanceTextField.getText().trim());
		interestTextField.requestFocus();
	}

	@FXML
	private void interestTextFieldActionPerformed(ActionEvent e) {
		interestTextField.setText(interestTextField.getText().trim());
		if (computePayment) {
			monthsTextField.requestFocus();
		} else {
			paymentTextField.requestFocus();
		}
	}

	@FXML
	private void monthsTextFieldActionPerformed(ActionEvent e) {
		monthsTextField.setText(monthsTextField.getText().trim());
		computeButton.requestFocus();
	}

	@FXML
	private void paymentTextFieldActionPerformed(ActionEvent e) {
		paymentTextField.setText(paymentTextField.getText().trim());
		computeButton.requestFocus();
	}

	@FXML
	private void exitButtonActionPerformed(ActionEvent e) {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}
}
