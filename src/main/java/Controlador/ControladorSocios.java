/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Modelo.Socio;
import Modelo.SocioDAO;
import Vista.VentanaSocio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author nekro
 */
public class ControladorSocios implements ActionListener {

    private final VentanaSocio gSocios;
    private final SocioDAO socios;
    private final Connection conexion;
    private ControladorVentanaNuevoSocio c;

    public ControladorSocios(Conexion con) throws SQLException {
        gSocios = new VentanaSocio();

        this.socios = new SocioDAO(con, gSocios);
        this.conexion = con.getConexion();
        socios.RefrescarPanelSocios();

        gSocios.setLocationRelativeTo(null);
        gSocios.setVisible(true);
        gSocios.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addListeners();
    }

    private void addListeners() {
        gSocios.newSocio.addActionListener(this);
        gSocios.deleteSocio.addActionListener(this);
        gSocios.actSocio.addActionListener(this);
        gSocios.Salir.addActionListener(this);
    }

    private Socio getData(int row) {
        Socio soocio;

        String[] datos = new String[8];

        datos[0] = (String) this.gSocios.tablaMonitores.getValueAt(row, 0);
        datos[1] = (String) this.gSocios.tablaMonitores.getValueAt(row, 1);
        datos[2] = (String) this.gSocios.tablaMonitores.getValueAt(row, 2);
        datos[3] = (String) this.gSocios.tablaMonitores.getValueAt(row, 3);
        datos[4] = (String) this.gSocios.tablaMonitores.getValueAt(row, 4);
        datos[5] = (String) this.gSocios.tablaMonitores.getValueAt(row, 5);
        datos[6] = (String) this.gSocios.tablaMonitores.getValueAt(row, 6);
        datos[7] = (String) this.gSocios.tablaMonitores.getValueAt(row, 7);
       
        soocio = new Socio(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6], datos[7]);
        return soocio;
    }

    @Override
    public void actionPerformed(ActionEvent ae
    ) {
        System.out.println(ae.getActionCommand());
        switch (ae.getActionCommand()) {
            case "Nuevo Socio": {
                this.c = new ControladorVentanaNuevoSocio(this.socios);
            }
            break;

            case "Baja de socio": {
                socios.DeleteSocio();
                socios.RefrescarPanelSocios();
            }
            break;

            case "Actualizar Socio": {

                try {
                    int row = gSocios.tablaMonitores.getSelectedRow();
                    Socio socio = getData(row);
                    socios.UpdateSocio(socio, socio.getCodigo());
                    ControladorVentanaNuevoSocio m = new ControladorVentanaNuevoSocio(socios, socio);

                } catch (SQLException ex) {
                    Logger.getLogger(ControladorSocios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case "Salir": {
                gSocios.dispose();
            }
            break;

            default:
                throw new AssertionError();
        }

    }

}
