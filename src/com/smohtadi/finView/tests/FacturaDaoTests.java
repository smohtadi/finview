package com.smohtadi.finView.tests;

import com.smohtadi.finView.dao.FacturaDao;
import com.smohtadi.finView.model.Factura;
import com.smohtadi.finView.model.ServerResponse;
import org.junit.Before;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import utils.Message;


public class FacturaDaoTests {
  private FacturaDao facturaDao = new FacturaDao();
  private Factura factura;

  @BeforeClass
  public static void createTable() {
    FacturaDao f = new FacturaDao();
    f.createTable();
  }
  // @Test
//  public void createTable() {
//    ServerResponse<Integer, String> actual = facturaDao.createTable();
//    String expected = "Sucess";
//    assertEquals(expected, actual.message);
//  }

//  @Before
//  public void setUp() {
//    factura = new Factura(0, 0, 100, 1000, 0, "10/12/2019", "Credito", "AAA100", "PERTECH");
//  }

  @Test
  public void canCreateOneRow() {
    ServerResponse<Integer, Integer> actual = facturaDao.create(factura);
    ServerResponse<Integer, Integer> expected = new ServerResponse<>(200, 1, Message.SUCCESS);
    assertEquals(expected.message, actual.message);
  }
}
