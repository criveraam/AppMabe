package com.ti.airmovil.mabe.Models;

/**
 * Created by tecnicoairmovil on 04/10/17.
 */

public class ReporteProductosModel {
    public String nombreProducto;
    public String precioMabe;
    public String precioWalmart;
    public String precioBestBuy;

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getPrecioMabe() {
        return precioMabe;
    }

    public void setPrecioMabe(String precioMabe) {
        this.precioMabe = precioMabe;
    }

    public String getPrecioWalmart() {
        return precioWalmart;
    }

    public void setPrecioWalmart(String precioWalmart) {
        this.precioWalmart = precioWalmart;
    }

    public String getPrecioBestBuy() {
        return precioBestBuy;
    }

    public void setPrecioBestBuy(String precioBestBuy) {
        this.precioBestBuy = precioBestBuy;
    }
}
