package modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Categoria {
	private final StringProperty idcategoria;
	private final StringProperty categoria;

	public Categoria(String idcategoria, String categoria) {
		this.idcategoria = new SimpleStringProperty(idcategoria != null ? idcategoria.trim() : "");
		this.categoria = new SimpleStringProperty(categoria != null ? categoria.trim() : "");
	}

	public String getIdcategoria() {
		return idcategoria.get();
	}

	public void setIdcategoria(String idcategoria) {
		this.idcategoria.set(idcategoria);
	}

	public StringProperty idcategoriaProperty() {
		return idcategoria;
	}

	public String getCategoria() {
		return categoria.get();
	}

	public void setCategoria(String categoria) {
		this.categoria.set(categoria);
	}

	public StringProperty categoriaProperty() {
		return categoria;
	}

	public String toLinea() {
		return getIdcategoria() + "," + getCategoria();
	}

	@Override
	public String toString() {
		return this.getCategoria();
	}
}
