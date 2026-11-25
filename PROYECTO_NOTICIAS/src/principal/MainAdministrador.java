package principal;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import controlador.NoticiaControlador;
import controlador.UsuarioControlador;
import dao.NoticiaDAO;
import excepciones.BaseDatosException;
import modelo.NoticiaModelo;
import vista.Idioma;
import vista.VentanaAdministrador;

//Segundo punto de inicio: abre solo el panel de administración (para el administrador, no para los usuarios)
public class MainAdministrador
{
    public static void main(String[] args)
    {
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
            NoticiaControlador controlador = new NoticiaControlador(new NoticiaModelo(new NoticiaDAO()));
            //Usa el mismo idioma que eligió el usuario en la app
            UsuarioControlador usuario = new UsuarioControlador();
            Idioma.setIngles(usuario.obtenerPerfil().getIdioma().equals("en"));

            VentanaAdministrador ventana = new VentanaAdministrador(controlador);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.refrescar();
            ventana.setVisible(true);
        }
        catch (BaseDatosException e)
        {
            JOptionPane.showMessageDialog(null, "No se pudo iniciar la administración: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
