package modelo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Prestamo {
	private final DoubleProperty balance;
	private final DoubleProperty interest;
	private final IntegerProperty months;
	private final DoubleProperty payment;
	private final DoubleProperty finalPayment;
	private final DoubleProperty totalPayments;
	private final DoubleProperty interestPaid;

	public Prestamo() {
		this(0.0, 0.0, 0, 0.0);
	}

	public Prestamo(double balance, double interest, int months, double payment) {
		this.balance = new SimpleDoubleProperty(balance);
		this.interest = new SimpleDoubleProperty(interest);
		this.months = new SimpleIntegerProperty(months);
		this.payment = new SimpleDoubleProperty(payment);
		this.finalPayment = new SimpleDoubleProperty(0.0);
		this.totalPayments = new SimpleDoubleProperty(0.0);
		this.interestPaid = new SimpleDoubleProperty(0.0);
	}

	public double getBalance() {
		return balance.get();
	}

	public void setBalance(double balance) {
		this.balance.set(balance);
	}

	public DoubleProperty balanceProperty() {
		return balance;
	}

	public double getInterest() {
		return interest.get();
	}

	public void setInterest(double interest) {
		this.interest.set(interest);
	}

	public DoubleProperty interestProperty() {
		return interest;
	}

	public int getMonths() {
		return months.get();
	}

	public void setMonths(int months) {
		this.months.set(months);
	}

	public IntegerProperty monthsProperty() {
		return months;
	}

	public double getPayment() {
		return payment.get();
	}

	public void setPayment(double payment) {
		this.payment.set(payment);
	}

	public DoubleProperty paymentProperty() {
		return payment;
	}

	public double getFinalPayment() {
		return finalPayment.get();
	}

	public void setFinalPayment(double finalPayment) {
		this.finalPayment.set(finalPayment);
	}

	public DoubleProperty finalPaymentProperty() {
		return finalPayment;
	}

	public double getTotalPayments() {
		return totalPayments.get();
	}

	public void setTotalPayments(double totalPayments) {
		this.totalPayments.set(totalPayments);
	}

	public DoubleProperty totalPaymentsProperty() {
		return totalPayments;
	}

	public double getInterestPaid() {
		return interestPaid.get();
	}

	public void setInterestPaid(double interestPaid) {
		this.interestPaid.set(interestPaid);
	}

	public DoubleProperty interestPaidProperty() {
		return interestPaid;
	}
}
