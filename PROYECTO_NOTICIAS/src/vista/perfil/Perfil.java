package vista.perfil;

//Datos del usuario (solo de demostración, no se guardan en la base de datos)
public class Perfil
{
    private String nombre;
    private String correo;
    private String nombreUsuario;
    private String clave;

    public Perfil(String nombre, String correo, String nombreUsuario, String clave)
    {
        this.nombre = nombre;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getCorreo()
    {
        return correo;
    }

    public String getNombreUsuario()
    {
        return nombreUsuario;
    }

    public String getClave()
    {
        return clave;
    }

    public boolean actualizarPerfil(String nuevoNombre, String nuevoCorreo, String nuevoUsuario)
    {
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()
                || nuevoCorreo == null || nuevoCorreo.trim().isEmpty())
                {
            return false;
        }
        this.nombre = nuevoNombre.trim();
        this.correo = nuevoCorreo.trim();
        if (nuevoUsuario != null && !nuevoUsuario.trim().isEmpty())
        {
            this.nombreUsuario = nuevoUsuario.trim();
        }
        return true;
    }

    public boolean cambiarClave(String claveActualIngresada, String claveNueva)
    {
        if (claveActualIngresada == null || claveNueva == null)
        {
            return false;
        }
        if (!this.clave.equals(claveActualIngresada))
        {
            return false;
        }
        if (claveNueva.trim().isEmpty())
        {
            return false;
        }
        this.clave = claveNueva;
        return true;
    }
}