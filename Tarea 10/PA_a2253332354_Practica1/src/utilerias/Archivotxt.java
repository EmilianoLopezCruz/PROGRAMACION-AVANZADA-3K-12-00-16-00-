package utilerias;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Archivotxt {
	private final String nombreArchivo;

	public Archivotxt(String nombreArchivo) {
		if (nombreArchivo != null && !nombreArchivo.contains(".")) {
			this.nombreArchivo = nombreArchivo + ".txt";
		} else {
			this.nombreArchivo = nombreArchivo;
		}
	}

	public boolean existe() {
		File archivo = new File(this.nombreArchivo);
		return archivo.exists();
	}

	public void guardar(String texto) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
			writer.write(texto != null ? texto : "");
		} catch (IOException e) {
			System.err.println("Error al guardar el archivo: " + e.getMessage());
		}
	}

	public ArrayList<String[]> cargar() {
		ArrayList<String[]> lineas = new ArrayList<>();
		if (this.existe()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
				String linea;
				while ((linea = reader.readLine()) != null) {
					if (!linea.trim().isEmpty()) {
						String[] elementos = linea.split(",");
						for (int i = 0; i < elementos.length; i++) {
							elementos[i] = elementos[i].trim();
						}
						lineas.add(elementos);
					}
				}
			} catch (IOException e) {
				System.err.println("Error al cargar el archivo: " + e.getMessage());
			}
		} else {
			lineas = null;
		}
		return lineas;
	}
}
