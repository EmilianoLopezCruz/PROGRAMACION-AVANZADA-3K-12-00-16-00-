package modelo;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListaInsumos {
	private final ArrayList<Insumo> insumos;

	public ListaInsumos() {
		this.insumos = new ArrayList<>();
	}

	public boolean agregarInsumo(Insumo insumo) {
		boolean inserto = true;
		if (buscarInsumoPorId(insumo.getIdProducto()) == null) {
			insumos.add(insumo);
		} else {
			inserto = false;
		}
		return inserto;
	}

	public boolean eliminarInsumoPorId(String id) {
		boolean elimino = true;
		Insumo insumo = buscarInsumoPorId(id);
		if (insumo != null) {
			insumos.remove(insumo);
		} else {
			elimino = false;
		}
		return elimino;
	}

	@Override
	public String toString() {
		StringBuilder resultado = new StringBuilder();
		for (Insumo insumo : insumos) {
			resultado.append(insumo.toString()).append("\n");
		}
		return resultado.toString();
	}

	public String toArchivo() {
		StringBuilder resultado = new StringBuilder();
		for (Insumo insumo : insumos) {
			resultado.append(insumo.toLinea()).append("\n");
		}
		return resultado.toString();
	}

	private Insumo buscarInsumoPorId(String id) {
		if (id == null) {
			return null;
		}
		for (Insumo insumo : insumos) {
			if (insumo.getIdProducto().equals(id.trim())) {
				return insumo;
			}
		}
		return null;
	}

	public String buscarInsumo(String id) {
		for (Insumo insumo : insumos) {
			if (insumo.getIdProducto().equals(id)) {
				return insumo.getProducto();
			}
		}
		return null;
	}

	public String[] idinsumos() {
		int pos = -1;
		String[] datos = new String[this.insumos.size()];
		for (Insumo nodo : this.insumos) {
			pos++;
			datos[pos] = nodo.getIdProducto();
		}
		return datos;
	}

	public ObservableList<Insumo> getObservableInsumos() {
		return FXCollections.observableArrayList(insumos);
	}

	public void cargarInsumo(ArrayList<String[]> insumosString) {
		if (insumosString == null) {
			return;
		}
		for (String[] categoriaString : insumosString) {
			if (categoriaString.length >= 3) {
				String id = categoriaString[0].trim();
				String insumo = categoriaString[1].trim();
				String idCategoria = categoriaString[2].trim();
				Insumo nodo = new Insumo(id, insumo, idCategoria);
				this.agregarInsumo(nodo);
			}
		}
	}
}
