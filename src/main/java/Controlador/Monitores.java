/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Vista.VentanaMonitores;
import Vista.VentanaNewMonitor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nekro
 */
public class Monitores {

    private final Connection conexion;
    private final VentanaMonitores gMonitores;
    DefaultTableModel model;

    public Monitores(Conexion con, VentanaMonitores gMonitores) {
        this.conexion = con.getConexion();
        this.gMonitores = gMonitores;
    }
    
    public void visualizarMonitores() {
        model = new DefaultTableModel();

        model.addColumn("Código");
        model.addColumn("Nombre");
        model.addColumn("DNI");
        model.addColumn("Teléfono");
        model.addColumn("Correo");
        model.addColumn("Fecha de Incorporación");
        model.addColumn("Nick");

        String selectAllProducts = "SELECT * FROM MONITOR";

        System.out.println("Query creada lista para usarse");

        Statement st;

        gMonitores.tablaMonitores.setModel(model);

        String[] datos = new String[7];
        try {
            st = this.conexion.createStatement();

            ResultSet resultado = st.executeQuery(selectAllProducts);

            while (resultado.next()) {

                for (int i = 0; i < 7; i++) {
                    datos[i] = resultado.getString(i + 1);
                    System.out.println(datos[i]);
                }
                System.out.println("Acaba de salir del for");
                model.addRow(datos);
            }

        } catch (SQLException e) {
        }
    }

    public void InsertRow(String codigo, String DNI, String entrada, String mail, String name, String nick, String tef) {
        String insertProduct = "INSERT COl";
    }


}
