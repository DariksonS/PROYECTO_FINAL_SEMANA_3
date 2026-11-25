package principal;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import controlador.NoticiaControlador;
import controlador.UsuarioControlador;
import dao.AlmacenNoticias;
import dao.NoticiaDAO;
import excepciones.BaseDatosException;
import excepciones.NoticiaDuplicadaException;
import modelo.NoticiaModelo;
import vista.GestorVentanas;

public class Main
{
    public static void main(String[] args)
    {
        //Tema Nimbus, ya viene incluido en Java y se ve más moderno que el tema por defecto
        try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e)
        {
            System.out.println("No se pudo aplicar el tema Nimbus, se usa el tema normal");
        }

        try
        {
            //Las noticias se guardan en la base de datos SQLite
            AlmacenNoticias almacen = new NoticiaDAO();
            NoticiaModelo modelo = new NoticiaModelo(almacen);
            NoticiaControlador controlador = new NoticiaControlador(modelo);

            //Solo carga ejemplos la primera vez, cuando la base de datos está vacía
            if (controlador.totalNoticias() == 0)
            {
                cargarDatosDePrueba(controlador);
            }

            UsuarioControlador usuario = new UsuarioControlador();
            GestorVentanas ventanas = new GestorVentanas(controlador, usuario);
            ventanas.iniciar();
        }
        catch (BaseDatosException e)
        {
            JOptionPane.showMessageDialog(null, "No se pudo iniciar la aplicación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Noticias de ejemplo para la demostración
    private static void cargarDatosDePrueba(NoticiaControlador controlador)
    {
        try
        {
            controlador.publicarNoticia("Perú clasifica a la siguiente fase", "La selección logró un triunfo clave como visitante.", "Redacción Deportes", "Deportes");
            controlador.publicarNoticia("Nuevo procesador presentado en feria", "La empresa anunció un chip con menor consumo de energía.", "Redacción Tech", "Tecnología");
            controlador.publicarNoticia("El dólar cierra a la baja", "El tipo de cambio retrocedió por tercer día consecutivo.", "Redacción Economía", "Economía");
        }
        catch (NoticiaDuplicadaException e)
        {
            System.out.println("Error al cargar datos de prueba: " + e.getMessage());
        }
    }
}
