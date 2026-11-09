package modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Insumo {
	private final StringProperty idProducto;
	private final StringProperty producto;
	private final StringProperty idCategoria;

	public Insumo(String idProducto, String producto, String idCategoria) {
		this.idProducto = new SimpleStringProperty(idProducto != null ? idProducto.trim() : "");
		this.producto = new SimpleStringProperty(producto != null ? producto.trim() : "");
		this.idCategoria = new SimpleStringProperty(idCategoria != null ? idCategoria.trim() : "");
	}

	public String getIdProducto() {
		return idProducto.get();
	}

	public void setIdProducto(String idProducto) {
		this.idProducto.set(idProducto);
	}

	public StringProperty idProductoProperty() {
		return idProducto;
	}

	public String getProducto() {
		return producto.get();
	}

	public void setProducto(String producto) {
		this.producto.set(producto);
	}

	public StringProperty productoProperty() {
		return producto;
	}

	public String getIdCategoria() {
		return idCategoria.get();
	}

	public void setIdCategoria(String idCategoria) {
		this.idCategoria.set(idCategoria);
	}

	public StringProperty idCategoriaProperty() {
		return idCategoria;
	}

	public String toLinea() {
		return getIdProducto() + "," + getProducto() + "," + getIdCategoria();
	}

	@Override
	public String toString() {
		return getIdProducto() + "\t\t" + getProducto() + "\t\t" + getIdCategoria();
	}
}
