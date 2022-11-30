package Modelo;
import java.sql.*;

public class Conexion {
    
    
    //**********Atributos*************
    Connection conexion;
    
    
    //***********Metodos**************
    public Conexion(String sgbd, String ip, String servicio_bd, String usuario,
                       String password) throws ClassNotFoundException, SQLException
    {
        
        System.out.println("Ha entrado");
        
        if(!(sgbd.equals("mariadb") || sgbd.equals("oracle") ))
            System.out.println("\nIntroduce datos correctos\n");
   
        String data = sgbd.equals("mariadb")? "jdbc:mariadb://172.18.1.241:3306/DDSI_013"  : 
                "jdbc:oracle:thin:@172.17.20.39:1521:etsi";
        
        
        
        conexion = DriverManager.getConnection(data, usuario, password);
    }
    
    public void desconexion() throws SQLException{
        conexion.close();
    }
    
    public Connection getConexion(){return conexion;}
    
    public Connection getConnection(){
        return conexion;
    }
    
    
    
}
