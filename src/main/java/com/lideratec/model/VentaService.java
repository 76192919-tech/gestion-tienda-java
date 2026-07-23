package model;

import java.util.ArrayList;

public class VentaService {

    // Colección para almacenar los detalles de la venta
    private ArrayList<DetalleVenta> detalles = new ArrayList<>();

    private final double IGV = 0.18;

    // VALIDACIONES

    public boolean validarNombre(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    public boolean validarCantidad(int cantidad) {
        return cantidad > 0;
    }

    public boolean validarStock(Producto producto, int cantidad) {

        if (producto == null) {
            return false;
        }

        return producto.getStock() >= cantidad;
    }

    // AGREGAR PRODUCTO

    public boolean agregarProducto(Producto producto, int cantidad) {

        if (!validarCantidad(cantidad)) {
            System.out.println("Cantidad inválida.");
            return false;
        }

        if (!validarStock(producto, cantidad)) {
            System.out.println("Stock insuficiente.");
            return false;
        }

        DetalleVenta detalle = new DetalleVenta(producto, cantidad);

        detalles.add(detalle);

        return true;
    }

    // CALCULAR SUBTOTAL

    public double calcularSubtotal() {

        double subtotal = 0;

        for (DetalleVenta detalle : detalles) {
            subtotal += detalle.getSubtotal();
        }

        return Math.round(subtotal * 100.0) / 100.0;
    }

    // CALCULAR IGV

    public double calcularIGV() {

        return Math.round(calcularSubtotal() * IGV * 100.0) / 100.0;

    }

    // CALCULAR TOTAL


    public double calcularTotal() {

        return calcularSubtotal() + calcularIGV();

    }

    // ACTUALIZAR INVENTARIO

    public void actualizarStock() {

        for (DetalleVenta detalle : detalles) {

            Producto producto = detalle.getProducto();

            producto.setStock(
                    producto.getStock() - detalle.getCantidad()
            );

        }

    }

    // MOSTRAR DETALLE

    public void mostrarVenta() {

        System.out.println("========== DETALLE ==========");

        for (DetalleVenta detalle : detalles) {

            System.out.println(detalle);

        }

        System.out.println("-----------------------------");
        System.out.println("Subtotal : S/ " + calcularSubtotal());
        System.out.println("IGV      : S/ " + calcularIGV());
        System.out.println("Total    : S/ " + calcularTotal());

    }

    public ArrayList<DetalleVenta> getDetalles() {
        return detalles;
    }

}
