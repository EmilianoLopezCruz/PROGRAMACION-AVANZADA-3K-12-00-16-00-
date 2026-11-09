package modelo;

import java.text.DecimalFormat;

public class CalculadoraPrestamo {

	private static final DecimalFormat FORMATO_MONEDA = new DecimalFormat("0.00");

	public double obtenerInteresMensual(double interesAnualPct) {
		return interesAnualPct / 1200.0;
	}

	public double obtenerPagoMinimo(double balance, double interesAnualPct) {
		double monthlyInterest = obtenerInteresMensual(interesAnualPct);
		return balance * monthlyInterest + 1.0;
	}

	public double calcularPagoMensual(double balance, double interesAnualPct, int months) {
		double monthlyInterest = obtenerInteresMensual(interesAnualPct);
		if (interesAnualPct == 0.0) {
			return balance / months;
		}
		double multiplier = Math.pow(1.0 + monthlyInterest, months);
		return balance * monthlyInterest * multiplier / (multiplier - 1.0);
	}

	public int calcularNumeroPagos(double balance, double interesAnualPct, double payment) {
		double monthlyInterest = obtenerInteresMensual(interesAnualPct);
		if (interesAnualPct == 0.0) {
			return (int) (balance / payment);
		}
		return (int) ((Math.log(payment) - Math.log(payment - balance * monthlyInterest))
				/ Math.log(1.0 + monthlyInterest));
	}

	public String generarAnalisisPrestamo(Prestamo prestamo) {
		double balance = prestamo.getBalance();
		double interest = prestamo.getInterest();
		double monthlyInterest = obtenerInteresMensual(interest);
		double payment = Double.parseDouble(FORMATO_MONEDA.format(prestamo.getPayment()));
		prestamo.setPayment(payment);

		int months = prestamo.getMonths();
		double loanBalance = balance;
		for (int paymentNumber = 1; paymentNumber <= months - 1; paymentNumber++) {
			loanBalance += loanBalance * monthlyInterest - payment;
		}

		double finalPayment = loanBalance;
		if (finalPayment > payment) {
			loanBalance += loanBalance * monthlyInterest - payment;
			finalPayment = loanBalance;
			months++;
			prestamo.setMonths(months);
		}

		double totalPayments = (months - 1) * payment + finalPayment;
		double interestPaid = totalPayments - balance;

		prestamo.setFinalPayment(finalPayment);
		prestamo.setTotalPayments(totalPayments);
		prestamo.setInterestPaid(interestPaid);

		StringBuilder sb = new StringBuilder();
		sb.append("Loan Balance: $").append(FORMATO_MONEDA.format(balance)).append("\n");
		sb.append("Interest Rate: ").append(FORMATO_MONEDA.format(interest)).append("%\n\n");
		sb.append(months - 1).append(" Payments of $").append(FORMATO_MONEDA.format(payment)).append("\n");
		sb.append("Final Payment of: $").append(FORMATO_MONEDA.format(finalPayment)).append("\n");
		sb.append("Total Payments: $").append(FORMATO_MONEDA.format(totalPayments)).append("\n");
		sb.append("Interest Paid $").append(FORMATO_MONEDA.format(interestPaid));
		return sb.toString();
	}

	public String formatearDosDecimales(double valor) {
		return FORMATO_MONEDA.format(valor);
	}
}
