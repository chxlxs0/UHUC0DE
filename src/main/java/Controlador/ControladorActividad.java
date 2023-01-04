/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ActividadDAO;
import Vista.VentanaActividad;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.hibernate.Session;

/**
 *
 * @author nekro
 */
public class ControladorActividad implements ActionListener {

    private final VentanaActividad vAct;
    private final ActividadDAO actDAO;
    private String item;
    private final Session sesion;
    private final String SGBD;

    public ControladorActividad(Session session, String SGBD) {
        vAct = new VentanaActividad();
        actDAO = new ActividadDAO(session, vAct);
        this.SGBD = SGBD;
        this.sesion = session;

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
                System.out.println("\n\n\n" + item + "\n\n\n");
                
            try {
                actDAO.listaUsuarios(item,this.SGBD);
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
