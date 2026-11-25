package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import excepciones.BaseDatosException;
import modelo.Perfil;

//Lee y actualiza el perfil del usuario (siempre es la fila con id 1)
public class PerfilDAO
{
    public Perfil cargar()
    {
        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement("SELECT * FROM perfil WHERE id = 1"))
        {
            ResultSet resultado = sentencia.executeQuery();
            resultado.next();
            return new Perfil(resultado.getString("nombre"), resultado.getString("usuario"), resultado.getString("correo"), resultado.getString("clave"), resultado.getString("foto"), resultado.getString("idioma"));
        }
        catch (SQLException e)
        {
            throw new BaseDatosException("No se pudo cargar el perfil: " + e.getMessage());
        }
    }

    public void actualizar(Perfil perfil)
    {
        String sql = "UPDATE perfil SET nombre = ?, usuario = ?, correo = ?, clave = ?, foto = ?, idioma = ? WHERE id = 1";
        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql))
        {
            sentencia.setString(1, perfil.getNombre());
            sentencia.setString(2, perfil.getUsuario());
            sentencia.setString(3, perfil.getCorreo());
            sentencia.setString(4, perfil.getClave());
            sentencia.setString(5, perfil.getRutaFoto());
            sentencia.setString(6, perfil.getIdioma());
            sentencia.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new BaseDatosException("No se pudo guardar el perfil: " + e.getMessage());
        }
    }
}
