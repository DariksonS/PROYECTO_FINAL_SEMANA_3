package excepciones;

//Error de validación de datos (no verificada)
public class DatoInvalidoException extends IllegalArgumentException
{
    private static final long serialVersionUID = 1L;

    public DatoInvalidoException(String mensaje)
    {
        super(mensaje);
    }
}
