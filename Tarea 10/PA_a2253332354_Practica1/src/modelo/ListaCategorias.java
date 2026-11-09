package modelo;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class ListaCategorias {
	private final ArrayList<Categoria> categorias;

	public ListaCategorias() {
		this.categorias = new ArrayList<>();
	}

	public boolean agregarCategoria(Categoria categoria) {
		if (buscarCategoriaPorId(categoria.getIdcategoria()) == null) {
			categorias.add(categoria);
			return true;
		}
		return false;
	}

	public boolean eliminarCategoriaPorId(String id) {
		Categoria categoria = buscarCategoriaPorId(id);
		if (categoria != null) {
			categorias.remove(categoria);
			return true;
		}
		return false;
	}

	public String toLinea() {
		StringBuilder resultado = new StringBuilder();
		for (Categoria categoria : categorias) {
			resultado.append(categoria.getIdcategoria())
					.append("\t\t")
					.append(categoria.getCategoria())
					.append("\n");
		}
		return resultado.toString();
	}

	public String toArchivo() {
		StringBuilder resultado = new StringBuilder();
		for (Categoria categoria : categorias) {
			resultado.append(categoria.toLinea()).append("\n");
		}
		return resultado.toString();
	}

	public Categoria buscarCategoriaPorId(String id) {
		if (id == null) {
			return null;
		}
		for (Categoria categoria : categorias) {
			if (categoria.getIdcategoria().equals(id.trim())) {
				return categoria;
			}
		}
		return null;
	}

	public String buscarCategoria(String id) {
		Categoria categoria = buscarCategoriaPorId(id);
		if (categoria != null) {
			return categoria.getCategoria();
		}
		return null;
	}

	public ComboBox<Categoria> agregarCategoriasAComboBox(ComboBox<Categoria> comboBox) {
		comboBox.getItems().clear();
		comboBox.getItems().addAll(categorias);
		return comboBox;
	}

	public ObservableList<Categoria> getObservableCategorias() {
		return FXCollections.observableArrayList(categorias);
	}

	public Object[] CategoriasArreglo() {
		return this.categorias.toArray();
	}

	public String[] idCategorias() {
		String[] datos = new String[this.categorias.size()];
		int pos = -1;
		for (Categoria nodo : this.categorias) {
			pos++;
			datos[pos] = nodo.getIdcategoria();
		}
		return datos;
	}

	public void cargarCategorias(ArrayList<String[]> categoriasString) {
		if (categoriasString == null) {
			return;
		}
		for (String[] categoriaString : categoriasString) {
			if (categoriaString.length >= 2) {
				String idCategoria = categoriaString[0].trim();
				String nombreCategoria = categoriaString[1].trim();
				Categoria categoria = new Categoria(idCategoria, nombreCategoria);
				this.agregarCategoria(categoria);
			}
		}
	}
}
