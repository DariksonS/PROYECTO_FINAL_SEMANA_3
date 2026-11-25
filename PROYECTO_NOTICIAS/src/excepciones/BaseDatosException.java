package excepciones;

//Error al trabajar con la base de datos (no verificada)
public class BaseDatosException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public BaseDatosException(String mensaje)
    {
        super(mensaje);
    }
}
