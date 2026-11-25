package excepciones;

//Se lanza cuando no existe una noticia con el ID buscado (verificada)
public class NoticiaNoEncontradaException extends Exception
{
    private static final long serialVersionUID = 1L;

    public NoticiaNoEncontradaException(int id)
    {
        super("No existe una noticia con el ID " + id);
    }
}
