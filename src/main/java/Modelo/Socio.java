/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author nekro
 */
public class Socio {

    private final String codigo, DNI, entrada, mail, name, categoria, tef;

    public Socio() {
        this.codigo = null;
        this.DNI = null;
        this.entrada = null;
        this.mail = null;
        this.categoria = null;
        this.tef = null;
        this.name = null;
        
        
    }

    public Socio(String codigo, String name, String dni, String tef, String mail, String entrada, String categoria) {
        this.codigo = codigo;
        this.DNI = dni;
        this.entrada = entrada;
        this.mail = mail;
        this.categoria = categoria;
        this.tef = tef;
        this.name = name;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public String getDNI() {
        return this.DNI;
    }

    public String getEntrada() {
        return this.entrada;
    }

    public String getMail() {
        return this.mail;
    }

    public String getName() {
        return this.name;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public String getTef() {
        return this.tef;
    }
}
