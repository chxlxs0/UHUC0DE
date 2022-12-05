/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.VentanaNewMonitor;
import Vista.VistaMensajes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nekro
 */
public class ControladorNewMonitor implements ActionListener {

    private final VentanaNewMonitor añadirMonitor;
    private String codigo, DNI, entrada, mail, name, nick, tef;
    Monitores monitor;
    VistaMensajes mensaje;

    public ControladorNewMonitor(Monitores mon) {
        monitor = mon;
        añadirMonitor = new VentanaNewMonitor();
        añadirMonitor.setLocationRelativeTo(null);
        añadirMonitor.setVisible(true);

        addListeners();
    }

    private void getData() {
        this.codigo = añadirMonitor.code.getText();
        this.DNI = añadirMonitor.dni.getText();
        this.entrada = añadirMonitor.entrada.getText();
        this.mail = añadirMonitor.mail.getText();
        this.name = añadirMonitor.name.getText();
        this.nick = añadirMonitor.nick.getText();
        this.tef = añadirMonitor.tef.getText();
    }

    public String getCodigo() {return this.codigo;}
    public String getDNI() {return this.DNI;}
    public String getEntrada() {return this.entrada;}
    public String getMail() {return this.mail;}
    public String getName() {return this.name;}
    public String getNick() {return this.nick;}
    public String getTef() {return this.tef;}

    private void addListeners() {
        añadirMonitor.Insertar.addActionListener(this);
        añadirMonitor.Cancelar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println(ae.getActionCommand());
        switch (ae.getActionCommand()) {
            case "Insertar": {
            try {
                getData();
                this.monitor.InsertRow();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorNewMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            break;
            case "Cancelar": {

            }
            break;
            default:
                throw new AssertionError();
        }
    }

}
