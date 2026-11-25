package excepciones;

//Se lanza cuando ya existe una noticia con el mismo título (verificada)
public class NoticiaDuplicadaException extends Exception
{
    private static final long serialVersionUID = 1L;

    public NoticiaDuplicadaException(String titulo)
    {
        super("Ya existe una noticia con el título: " + titulo);
    }
}
