package excepciones;

//Error al trabajar con archivos o imágenes (no verificada)
public class ArchivoException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ArchivoException(String mensaje)
    {
        super(mensaje);
    }
}
