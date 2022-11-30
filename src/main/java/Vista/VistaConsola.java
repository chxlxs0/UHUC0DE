package Vista;
public class VistaConsola {
    public static void mensajeConsola (String texto){
        System.out.println(texto);
    };
    public static void mensajeConsola (String texto, String error){
        System.out.println(texto + "\n" + error);
    };
}
