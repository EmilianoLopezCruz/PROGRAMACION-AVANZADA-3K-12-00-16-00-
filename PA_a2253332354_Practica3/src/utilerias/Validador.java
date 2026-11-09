package utilerias;

import javafx.scene.control.TextField;

public class Validador {

	public static boolean validateDecimalNumber(TextField tf) {
		String s = tf.getText().trim();
		boolean hasDecimal = false;
		boolean valid = true;
		if (s.length() == 0) {
			valid = false;
		} else {
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c >= '0' && c <= '9') {
					continue;
				} else if (c == '.' && !hasDecimal) {
					hasDecimal = true;
				} else if (c == '-' && i == 0) {
					continue;
				} else {
					valid = false;
				}
			}
		}
		tf.setText(s);
		if (!valid) {
			tf.requestFocus();
		}
		return valid;
	}
}
