package com.smohtadi.finView.tests;
import com.smohtadi.finView.dao.FacturaDao;
import com.smohtadi.finView.model.Factura;
import com.smohtadi.finView.model.Producto;
import com.smohtadi.finView.model.Proveedor;
import com.smohtadi.finView.model.ServerResponse;
import com.smohtadi.finView.parser.CsvParser;
import com.smohtadi.finView.services.ProductoService;
import com.smohtadi.finView.services.ProveedorService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class CsvParserTest {
//  @Test
//  public void parseProveedor() {
//    CsvParser csvParser = new CsvParser();
//    List<String[]> p = csvParser.parse("C:\\Users\\Salman\\Documents\\MyDocuments\\proveedores.csv");
//    for (String[] los : p) {
//      Proveedor proveedor = new Proveedor(los[0], los[1], los[2]);
//      ProveedorService.getInstance().createProveedor(proveedor);
//    }
//    assertEquals(true, true);
//  }

  @Test
  public void parseProducto() {
    CsvParser csvParser = new CsvParser();
    List<String[]> p = csvParser.parse("C:\\Users\\Salman\\Documents\\MyDocuments\\productos.csv",5);
    for (String[] los : p) {
      Producto producto = new Producto(los[1], los[0], Double.parseDouble(los[2]), los[4], los[3]);
      ProductoService.getInstance().createProducto(producto);
    }
    assertEquals(true, true);
  }


}
