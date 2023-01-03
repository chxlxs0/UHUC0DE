/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ActividadDAO;
import Modelo.Conexion;
import Vista.VentanaActividad;
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
public class ControladorActividad implements ActionListener {

    private final VentanaActividad vAct;
    private final ActividadDAO actDAO;
    private String item;
    private final Connection conexion;

    public ControladorActividad(Conexion con, String SBD) {
        vAct = new VentanaActividad();
        actDAO = new ActividadDAO(con, vAct, SBD);

        this.conexion = con.getConexion();

        vAct.setLocationRelativeTo(null);
        vAct.setVisible(true);
        vAct.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addListeners();

    }

    private void addListeners() {
        vAct.Buscar.addActionListener(this);
        vAct.Salir.addActionListener(this);
    }

    private void getSelectedItem() {
        this.item = (String) vAct.jComboBox1.getSelectedItem();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Salir":
                vAct.dispose();

                break;
            case "Buscar": {
                getSelectedItem();
            try {
                actDAO.listaUsuarios(item);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorActividad.class.getName()).log(Level.SEVERE, null, ex);
            }
               // actDAO.getData(this.item);
            }
            break;
            default:
                throw new AssertionError();
        }
    }

}
