package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import excepciones.BaseDatosException;
import modelo.Comentario;

//Guarda y lee los comentarios de cada noticia en SQLite
public class ComentarioDAO
{
    public void guardar(Comentario comentario)
    {
        String sql = "INSERT INTO comentario (noticia_id, autor, texto, fecha) VALUES (?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql))
        {
            sentencia.setInt(1, comentario.getNoticiaId());
            sentencia.setString(2, comentario.getAutor());
            sentencia.setString(3, comentario.getTexto());
            sentencia.setString(4, comentario.getFecha().toString());
            sentencia.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new BaseDatosException("No se pudo guardar el comentario: " + e.getMessage());
        }
    }

    public List<Comentario> listarPorNoticia(int noticiaId)
    {
        String sql = "SELECT * FROM comentario WHERE noticia_id = ? ORDER BY id";
        List<Comentario> lista = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql))
        {
            sentencia.setInt(1, noticiaId);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next())
            {
                int id = resultado.getInt("id");
                String autor = resultado.getString("autor");
                String texto = resultado.getString("texto");
                LocalDate fecha = LocalDate.parse(resultado.getString("fecha"));
                lista.add(new Comentario(id, noticiaId, autor, texto, fecha));
            }
            return lista;
        }
        catch (SQLException e)
        {
            throw new BaseDatosException("No se pudo leer los comentarios: " + e.getMessage());
        }
    }

    public int contarPorNoticia(int noticiaId)
    {
        return listarPorNoticia(noticiaId).size();
    }
}
