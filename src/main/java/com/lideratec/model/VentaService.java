package com.lideratec;

import com.lideratec.modelo.DetalleVenta;
import com.lideratec.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

public class VentaService {

    private static final double IGV = 0.18;

    // Colección para almacenar los productos de la venta
    private List<DetalleVenta> detalleVenta;

    public VentaService() {
        detalleVenta = new ArrayList<>();
    }

    // ===========================
    // VALIDACIONES
    // ===========================

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

    // ===========================
    // AGREGAR PRODUCTO
    // ===========================

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

        detalleVenta.add(detalle);

        return true;
    }

    // ===========================
    // SUBTOTAL
    // ===========================

    public double calcularSubtotal() {

        double subtotal = 0;

        for (DetalleVenta detalle : detalleVenta) {
            subtotal += detalle.getSubtotal();
        }

        return Math.round(subtotal * 100.0) / 100.0;
    }

    // ===========================
    // IGV
    // ===========================

    public double calcularIGV() {

        double igv = calcularSubtotal() * IGV;

        return Math.round(igv * 100.0) / 100.0;
    }

    // ===========================
    // TOTAL
    // ===========================

    public double calcularTotal() {

        return calcularSubtotal() + calcularIGV();
    }

    // ===========================
    // ACTUALIZAR STOCK
    // ===========================

    public void actualizarInventario() {

        for (DetalleVenta detalle : detalleVenta) {

            Producto producto = detalle.getProducto();

            producto.setStock(
                    producto.getStock() - detalle.getCantidad()
            );
        }

    }

    // ===========================
    // MOSTRAR DETALLE
    // ===========================

    public void mostrarDetalle() {

        System.out.println("\n===== DETALLE DE VENTA =====");

        for (DetalleVenta detalle : detalleVenta) {

            System.out.println(
                    detalle.getProducto().getNombre()
                            + " | Cantidad: "
                            + detalle.getCantidad()
                            + " | Subtotal: S/. "
                            + detalle.getSubtotal()
            );
        }

        System.out.println("------------------------------");
        System.out.println("Subtotal : S/. " + calcularSubtotal());
        System.out.println("IGV      : S/. " + calcularIGV());
        System.out.println("Total    : S/. " + calcularTotal());

    }

    public List<DetalleVenta> getDetalleVenta() {
        return detalleVenta;
    }

}
