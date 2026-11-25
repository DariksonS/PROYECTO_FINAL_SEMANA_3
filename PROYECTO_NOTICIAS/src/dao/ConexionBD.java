package dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import excepciones.BaseDatosException;

//Abre la conexión con SQLite y prepara la base de datos
public class ConexionBD
{
    private static final String CARPETA = "datos";
    private static final String URL = "jdbc:sqlite:" + CARPETA + "/noticias.db";

    public static Connection conectar() throws SQLException
    {
        return DriverManager.getConnection(URL);
    }

    //Carga el driver, crea la carpeta datos y la tabla si no existen
    public static void iniciar()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e)
        {
            throw new BaseDatosException("No se encontró el driver de SQLite, agregue el .jar al proyecto");
        }

        File carpeta = new File(CARPETA);
        if (!carpeta.exists())
        {
            carpeta.mkdirs();
        }

        String sql = "CREATE TABLE IF NOT EXISTS noticia ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "titulo TEXT NOT NULL, "
                + "contenido TEXT NOT NULL, "
                + "autor TEXT NOT NULL, "
                + "categoria TEXT NOT NULL, "
                + "fecha TEXT NOT NULL, "
                + "imagen TEXT)";

        String sqlComentario = "CREATE TABLE IF NOT EXISTS comentario ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "noticia_id INTEGER NOT NULL, "
                + "autor TEXT NOT NULL, "
                + "texto TEXT NOT NULL, "
                + "fecha TEXT NOT NULL)";

        String sqlGuardado = "CREATE TABLE IF NOT EXISTS guardado ("
                + "noticia_id INTEGER PRIMARY KEY)";

        String sqlPerfil = "CREATE TABLE IF NOT EXISTS perfil ("
                + "id INTEGER PRIMARY KEY, "
                + "nombre TEXT NOT NULL, "
                + "usuario TEXT NOT NULL, "
                + "correo TEXT NOT NULL, "
                + "clave TEXT NOT NULL, "
                + "foto TEXT, "
                + "idioma TEXT NOT NULL)";

        //Perfil por defecto, solo se inserta la primera vez
        String sqlPerfilInicial = "INSERT OR IGNORE INTO perfil (id, nombre, usuario, correo, clave, foto, idioma) "
                + "VALUES (1, 'Usuario NotiRed', 'usuario', 'usuario.notired@gmail.com', '123456', NULL, 'es')";

        try (Connection conexion = conectar(); Statement sentencia = conexion.createStatement())
        {
            sentencia.execute(sql);
            sentencia.execute(sqlComentario);
            sentencia.execute(sqlGuardado);
            sentencia.execute(sqlPerfil);
            sentencia.execute(sqlPerfilInicial);
        }
        catch (SQLException e)
        {
            throw new BaseDatosException("No se pudo crear las tablas: " + e.getMessage());
        }
        agregarColumnaImagen();
    }

    //Si la base de datos es antigua y no tiene la columna imagen, se la agrega
    private static void agregarColumnaImagen()
    {
        try (Connection conexion = conectar(); Statement sentencia = conexion.createStatement())
        {
            sentencia.execute("ALTER TABLE noticia ADD COLUMN imagen TEXT");
        }
        catch (SQLException e)
        {
            //Si da error es porque la columna ya existe, no se hace nada
        }
    }
}
