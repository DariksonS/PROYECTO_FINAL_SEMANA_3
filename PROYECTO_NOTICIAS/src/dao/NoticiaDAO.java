package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import excepciones.BaseDatosException;
import modelo.Categoria;
import modelo.Noticia;

//Guarda las noticias en la base de datos SQLite
public class NoticiaDAO implements AlmacenNoticias
{
    public NoticiaDAO()
    {
        ConexionBD.iniciar();
    }

    @Override
    public Noticia guardar(Noticia noticia)
    {
        String sql = "INSERT INTO noticia (titulo, contenido, autor, categoria, fecha, imagen) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql))
        {
            sentencia.setString(1, noticia.getTitulo());
            sentencia.setString(2, noticia.getContenido());
            sentencia.setString(3, noticia.getAutor());
            sentencia.setString(4, noticia.getCategoria().name());
            sentencia.setString(5, noticia.getFecha().toString());
            sentencia.setString(6, noticia.getRutaImagen());
            sentencia.executeUpdate();

            int id = obtenerUltimoId(conexion);
            Noticia guardada = new Noticia(id, noticia.getTitulo(), noticia.getContenido(), noticia.getAutor(), noticia.getCategoria(), noticia.getFecha());
            guardada.setRutaImagen(noticia.getRutaImagen());
            return guardada;
        }
        catch (SQLException e)
        {
            throw new BaseDatosException("No se pudo guardar la noticia: " + e.getMessage());
        }
    }

    @Override
    public Noticia buscarPorId(int id)
    {
        String sql = "SELECT * FROM noticia WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql))
        {
            sentencia.setInt(1, id);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next())
            {
                return convertirANoticia(resultado);
            }
            return null;
        }
        catch (SQLException e)
        {
            throw new BaseDatosException("No se pudo buscar la noticia: " + e.getMessage());
        }
    }

    @Override
    public boolean actualizar(Noticia noticia)
    {
        String sql = "UPDATE noticia SET titulo = ?, contenido = ?, categoria = ?, imagen = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql))
        {
            sentencia.setString(1, noticia.getTitulo());
            sentencia.setString(2, noticia.getContenido());
            sentencia.setString(3, noticia.getCategoria().name());
            sentencia.setString(4, noticia.getRutaImagen());
            sentencia.setInt(5, noticia.getId());
            int filasCambiadas = sentencia.executeUpdate();
            return filasCambiadas > 0;
        }
        catch (SQLException e)
        {
            throw new BaseDatosException("No se pudo actualizar la noticia: " + e.getMessage());
        }
    }

    //Transacción: se borra la noticia, sus comentarios y su marca de guardado, o no se borra nada
    @Override
    public boolean eliminar(int id)
    {
        try (Connection conexion = ConexionBD.conectar())
        {
            conexion.setAutoCommit(false);
            try (PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM noticia WHERE id = ?"))
            {
                sentencia.setInt(1, id);
                int filasBorradas = sentencia.executeUpdate();
                borrarRelacionados(conexion, id);
                conexion.commit();
                return filasBorradas > 0;
            }
            catch (SQLException e)
            {
                conexion.rollback();
                throw e;
            }
        }
        catch (SQLException e)
        {
            throw new BaseDatosException("No se pudo eliminar la noticia: " + e.getMessage());
        }
    }

    @Override
    public List<Noticia> listar()
    {
        String sql = "SELECT * FROM noticia ORDER BY id";
        List<Noticia> lista = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql))
        {
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next())
            {
                lista.add(convertirANoticia(resultado));
            }
            return lista;
        }
        catch (SQLException e)
        {
            throw new BaseDatosException("No se pudo listar las noticias: " + e.getMessage());
        }
    }

    @Override
    public int contar()
    {
        String sql = "SELECT COUNT(*) FROM noticia";
        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql))
        {
            ResultSet resultado = sentencia.executeQuery();
            resultado.next();
            return resultado.getInt(1);
        }
        catch (SQLException e)
        {
            throw new BaseDatosException("No se pudo contar las noticias: " + e.getMessage());
        }
    }

    //Al borrar una noticia también se borran sus comentarios y su marca de guardado
    private void borrarRelacionados(Connection conexion, int id) throws SQLException
    {
        try (PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM comentario WHERE noticia_id = ?"))
        {
            sentencia.setInt(1, id);
            sentencia.executeUpdate();
        }
        try (PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM guardado WHERE noticia_id = ?"))
        {
            sentencia.setInt(1, id);
            sentencia.executeUpdate();
        }
    }

    //Pide a SQLite el ID que acaba de generar con el último INSERT
    private int obtenerUltimoId(Connection conexion) throws SQLException
    {
        try (Statement sentencia = conexion.createStatement())
        {
            ResultSet resultado = sentencia.executeQuery("SELECT last_insert_rowid()");
            resultado.next();
            return resultado.getInt(1);
        }
    }

    //Convierte la fila actual del resultado en un objeto Noticia
    private Noticia convertirANoticia(ResultSet resultado) throws SQLException
    {
        int id = resultado.getInt("id");
        String titulo = resultado.getString("titulo");
        String contenido = resultado.getString("contenido");
        String autor = resultado.getString("autor");
        Categoria categoria = Categoria.desdeTexto(resultado.getString("categoria"));
        LocalDate fecha = LocalDate.parse(resultado.getString("fecha"));
        Noticia noticia = new Noticia(id, titulo, contenido, autor, categoria, fecha);
        noticia.setRutaImagen(resultado.getString("imagen"));
        return noticia;
    }
}
