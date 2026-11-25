package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import excepciones.BaseDatosException;

//Guarda qué noticias marcó el usuario para leer después
public class GuardadoDAO
{
    public void guardar(int noticiaId)
    {
        ejecutar("INSERT OR IGNORE INTO guardado (noticia_id) VALUES (?)", noticiaId);
    }

    public void quitar(int noticiaId)
    {
        ejecutar("DELETE FROM guardado WHERE noticia_id = ?", noticiaId);
    }

    public boolean estaGuardada(int noticiaId)
    {
        return listarIds().contains(noticiaId);
    }

    public List<Integer> listarIds()
    {
        List<Integer> ids = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement("SELECT noticia_id FROM guardado"))
        {
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next())
            {
                ids.add(resultado.getInt("noticia_id"));
            }
            return ids;
        }
        catch (SQLException e)
        {
            throw new BaseDatosException("No se pudo leer las noticias guardadas: " + e.getMessage());
        }
    }

    public void quitarTodas()
    {
        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM guardado"))
        {
            sentencia.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new BaseDatosException("No se pudo borrar las noticias guardadas: " + e.getMessage());
        }
    }

    private void ejecutar(String sql, int noticiaId)
    {
        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql))
        {
            sentencia.setInt(1, noticiaId);
            sentencia.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new BaseDatosException("No se pudo actualizar las noticias guardadas: " + e.getMessage());
        }
    }
}
