package com.ti.airmovil.mabe.Models;

/**
 * Created by tecnicoairmovil on 04/10/17.
 */

public class ReporteProductosModel {
    private String nombreProducto;
    private String precioMabe;
    private String precioWalmart;
    private String precioBestBuy;
    private String porcentajeWalmart;
    private String porcentajeBestBuy;
    private int colorWalmart;
    private int colorBestBuy;
    private String crawler;

    public String getCrawler() {
        return crawler;
    }

    public void setCrawler(String crawler) {
        this.crawler = crawler;
    }

    public String getNombreProducto() {return nombreProducto;}
    public void setNombreProducto(String nombreProducto) {this.nombreProducto = nombreProducto;}

    public String getPrecioMabe() {return precioMabe;}
    public void setPrecioMabe(String precioMabe) {this.precioMabe = precioMabe;}

    public String getPrecioWalmart() {return precioWalmart;}
    public void setPrecioWalmart(String precioWalmart) {this.precioWalmart = precioWalmart;}

    public String getPrecioBestBuy() {return precioBestBuy;}
    public void setPrecioBestBuy(String precioBestBuy) {this.precioBestBuy = precioBestBuy;}

    public String getPorcentajeWalmart() {return porcentajeWalmart;}
    public void setPorcentajeWalmart(String porcentajeWalmart) {this.porcentajeWalmart = porcentajeWalmart;}

    public String getPorcentajeBestBuy() {return porcentajeBestBuy;}
    public void setPorcentajeBestBuy(String porcentajeBresBuy) {this.porcentajeBestBuy = porcentajeBresBuy;}

    public int getColorWalmart() {return colorWalmart;}
    public void setColorWalmart(int colorWalmart) {this.colorWalmart = colorWalmart;}

    public int getColorBestBuy() {return colorBestBuy;}
    public void setColorBestBuy(int colorBestBuy) {this.colorBestBuy = colorBestBuy;}
}
