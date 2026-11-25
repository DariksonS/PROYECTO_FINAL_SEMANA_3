package controlador;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import archivos.GestorArchivos;
import dao.ComentarioDAO;
import dao.GuardadoDAO;
import dao.PerfilDAO;
import modelo.Comentario;
import modelo.Perfil;

//Controla todo lo del usuario: su perfil, sus comentarios y sus noticias guardadas
public class UsuarioControlador
{
    private PerfilDAO perfilDAO;
    private ComentarioDAO comentarioDAO;
    private GuardadoDAO guardadoDAO;
    private Perfil perfil;

    public UsuarioControlador()
    {
        perfilDAO = new PerfilDAO();
        comentarioDAO = new ComentarioDAO();
        guardadoDAO = new GuardadoDAO();
        perfil = perfilDAO.cargar();
    }

    public Perfil obtenerPerfil()
    {
        return perfil;
    }

    //Si nuevaFoto es null se mantiene la foto actual
    public void actualizarPerfil(String nombre, String usuario, String correo, File nuevaFoto)
    {
        perfil.actualizarDatos(nombre, usuario, correo);
        if (nuevaFoto != null)
        {
            GestorArchivos.eliminarImagen(perfil.getRutaFoto());
            perfil.setRutaFoto(GestorArchivos.copiarImagen(nuevaFoto));
        }
        perfilDAO.actualizar(perfil);
    }

    public void cambiarClave(String actual, String nueva, String confirmacion)
    {
        perfil.cambiarClave(actual, nueva, confirmacion);
        perfilDAO.actualizar(perfil);
    }

    public void cambiarIdioma(String idioma)
    {
        perfil.setIdioma(idioma);
        perfilDAO.actualizar(perfil);
    }

    //Vuelve el perfil a los datos iniciales y borra sus noticias guardadas
    public void eliminarCuenta()
    {
        GestorArchivos.eliminarImagen(perfil.getRutaFoto());
        perfil = new Perfil("Usuario NotiRed", "usuario", "usuario.notired@gmail.com", "123456", null, perfil.getIdioma());
        perfilDAO.actualizar(perfil);
        guardadoDAO.quitarTodas();
    }

    public Comentario comentar(int noticiaId, String texto)
    {
        Comentario comentario = new Comentario(0, noticiaId, perfil.getNombre(), texto, LocalDate.now());
        comentarioDAO.guardar(comentario);
        return comentario;
    }

    public List<Comentario> obtenerComentarios(int noticiaId)
    {
        return comentarioDAO.listarPorNoticia(noticiaId);
    }

    public int contarComentarios(int noticiaId)
    {
        return comentarioDAO.contarPorNoticia(noticiaId);
    }

    //Si la noticia estaba guardada la quita, si no la guarda. Devuelve cómo quedó
    public boolean alternarGuardado(int noticiaId)
    {
        if (guardadoDAO.estaGuardada(noticiaId))
        {
            guardadoDAO.quitar(noticiaId);
            return false;
        }
        guardadoDAO.guardar(noticiaId);
        return true;
    }

    public boolean estaGuardada(int noticiaId)
    {
        return guardadoDAO.estaGuardada(noticiaId);
    }

    public List<Integer> obtenerIdsGuardados()
    {
        return guardadoDAO.listarIds();
    }
}
