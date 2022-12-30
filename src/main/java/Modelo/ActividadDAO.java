/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VentanaActividad;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author nekro
 */
public class ActividadDAO {

    private final Connection conexion;
    private final VentanaActividad vAct;
    private DefaultTableModel model;
    private Statement ps;
    private final String sgbd;

    int numeroInscritos;

    public ActividadDAO(Conexion con, VentanaActividad vAct, String SBD) {
        this.conexion = con.getConexion();
        this.vAct = vAct;
        this.sgbd = SBD;

        System.out.println("Base de datos escogida: " + this.sgbd);

    }

    public void listaUsuarios(String code) throws SQLException {

        model = new DefaultTableModel();
        numeroInscritos = 0;
        String llamadaProc;
        RellenarColumnas();

        this.vAct.TablaActividades.setModel(model);

        if (this.sgbd.equals("mariadb")) {
            llamadaProc = "{ call obtener_datos (?) } ";
        } else {
            llamadaProc = "{ call obtener_datos (?,?) } ";
        }

        ResultSet resultado;
        resultado = null;
        CallableStatement llamadaP = null;
        try {
            llamadaP = conexion.prepareCall(llamadaProc);
            llamadaP.setString(1, codACt(code));
            if (this.sgbd.equals("oracle")) {
                llamadaP.registerOutParameter(2, OracleTypes.CURSOR);
            }
            llamadaP.execute();

            if (this.sgbd.equals("mariadb")) {
                resultado = llamadaP.getResultSet();

            } else {
                resultado = (ResultSet) llamadaP.getObject(2);

            }

            String[] datos = new String[2];

            while (resultado.next()) {

                for (int i = 0; i < 2; i++) {
                    datos[i] = resultado.getString(i + 1);
                    System.out.println(datos[i]);
                }
                model.addRow(datos);
                numeroInscritos++;
                System.out.println("EL NUMERO DE INSCRITOS EN " + vAct.jComboBox1.getSelectedItem() + " ES: " + numeroInscritos);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                llamadaP.close();
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private String codACt(String nAct) throws SQLException {
        String selectAllProducts = String.format("SELECT ACTIVIDAD.IDACTIVIDAD FROM ACTIVIDAD WHERE ACTIVIDAD.NOMBRE = '%s'", nAct);
        ps = this.conexion.createStatement();

        ResultSet resultado = ps.executeQuery(selectAllProducts);
        String codigo = null;

        while (resultado.next()) {
            codigo = resultado.getString(1);
            System.out.println(codigo);
        }

        return codigo;
    }

    private void RellenarColumnas() {
        model.addColumn("Nombre");
        model.addColumn("Correo");
    }

}
