package modelo;

import excepciones.DatoInvalidoException;

//Datos del usuario de la app con sus validaciones
public class Perfil
{
    private String nombre;
    private String usuario;
    private String correo;
    private String clave;
    private String rutaFoto;
    private String idioma;

    public Perfil(String nombre, String usuario, String correo, String clave, String rutaFoto, String idioma)
    {
        this.nombre = nombre;
        this.usuario = usuario;
        this.correo = correo;
        this.clave = clave;
        this.rutaFoto = rutaFoto;
        this.idioma = idioma;
    }

    //Primero valida los tres datos y solo si todos son correctos los cambia
    public void actualizarDatos(String nuevoNombre, String nuevoUsuario, String nuevoCorreo)
    {
        String nombreValido = validarNombre(nuevoNombre);
        String usuarioValido = validarUsuario(nuevoUsuario);
        String correoValido = validarCorreo(nuevoCorreo);
        this.nombre = nombreValido;
        this.usuario = usuarioValido;
        this.correo = correoValido;
    }

    public void cambiarClave(String claveActual, String claveNueva, String confirmacion)
    {
        if (claveActual == null || !claveActual.equals(clave))
        {
            throw new DatoInvalidoException("La contraseña actual no es correcta");
        }
        if (claveNueva == null || claveNueva.length() < 6)
        {
            throw new DatoInvalidoException("La nueva contraseña debe tener al menos 6 caracteres");
        }
        if (claveNueva.contains(" "))
        {
            throw new DatoInvalidoException("La nueva contraseña no puede tener espacios");
        }
        if (!claveNueva.equals(confirmacion))
        {
            throw new DatoInvalidoException("La confirmación no coincide con la nueva contraseña");
        }
        if (claveNueva.equals(clave))
        {
            throw new DatoInvalidoException("La nueva contraseña debe ser diferente a la actual");
        }
        this.clave = claveNueva;
    }

    //Letras, espacios, guion y apóstrofe (por ejemplo María-José), entre 3 y 40 caracteres
    private String validarNombre(String valor)
    {
        if (valor == null || !valor.trim().matches("[\\p{L} '-]{3,40}"))
        {
            throw new DatoInvalidoException("El nombre solo puede tener letras, espacios, guion o apóstrofe (3 a 40 caracteres)");
        }
        return valor.trim();
    }

    //Letras, números, punto o guion bajo, entre 3 y 20 caracteres
    private String validarUsuario(String valor)
    {
        if (valor == null || !valor.trim().matches("[a-zA-Z0-9._]{3,20}"))
        {
            throw new DatoInvalidoException("El usuario solo puede tener letras, números, punto o guion bajo (3 a 20 caracteres, sin espacios)");
        }
        return valor.trim();
    }

    //Solo se aceptan correos de Gmail, Hotmail o de la UCSM
    private String validarCorreo(String valor)
    {
        if (valor == null || !valor.trim().toLowerCase().matches("[a-z0-9._]{3,30}@(gmail\\.com|hotmail\\.com|ucsm\\.edu\\.pe)"))
        {
            throw new DatoInvalidoException("Ingrese un correo válido de Gmail, Hotmail o UCSM, por ejemplo: nombre@gmail.com");
        }
        return valor.trim().toLowerCase();
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getUsuario()
    {
        return usuario;
    }

    public String getCorreo()
    {
        return correo;
    }

    public String getClave()
    {
        return clave;
    }

    public String getRutaFoto()
    {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto)
    {
        this.rutaFoto = rutaFoto;
    }

    public String getIdioma()
    {
        return idioma;
    }

    public void setIdioma(String idioma)
    {
        this.idioma = idioma;
    }

    public boolean tieneFoto()
    {
        return rutaFoto != null && !rutaFoto.isBlank();
    }
}
