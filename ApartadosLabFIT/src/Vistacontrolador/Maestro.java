package Vistacontrolador;

final class Maestro {

    String nombre;
    String correo;
    String departamento;

    Maestro(String nombre, String correo, String departamento) {
        this.nombre = nombre;
        this.correo = correo;
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
