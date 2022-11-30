/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author nekro
 */
import javax.swing.JOptionPane;

public class VistaMensajes {

    public VistaMensajes(String error) {

        switch (error) {
            case "ERROR":
                JOptionPane.showMessageDialog(null, "Se ha producido un error");
                break;
            case "CONECTADO":
                JOptionPane.showMessageDialog(null, "Se ha conectado correctamente");
                break;
            case "ERROR_DATA":
                JOptionPane.showMessageDialog(null, "Los datos introducidos son erroneos");
                break;
            default:
                throw new AssertionError();
        }
    }

}
