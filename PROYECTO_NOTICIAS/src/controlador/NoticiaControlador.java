package controlador;

import java.io.File;
import java.util.List;

import archivos.GestorArchivos;
import excepciones.DatoInvalidoException;
import excepciones.NoticiaDuplicadaException;
import excepciones.NoticiaNoEncontradaException;
import modelo.Categoria;
import modelo.Noticia;
import modelo.NoticiaModelo;
import util.Repositorio;

//Intermediario entre la vista y el modelo, la vista solo llama a estos métodos
public class NoticiaControlador
{
    private NoticiaModelo modelo;

    public NoticiaControlador(NoticiaModelo modelo)
    {
        this.modelo = modelo;
    }

    public Noticia publicarNoticia(String titulo, String contenido, String autor, String categoriaTexto) throws NoticiaDuplicadaException
    {
        return publicarNoticia(titulo, contenido, autor, categoriaTexto, null);
    }

    //imagen puede ser null si la noticia no tiene foto
    public Noticia publicarNoticia(String titulo, String contenido, String autor, String categoriaTexto, File imagen) throws NoticiaDuplicadaException
    {
        Categoria categoria = Categoria.desdeTexto(categoriaTexto);
        String rutaImagen = null;
        if (imagen != null)
        {
            rutaImagen = GestorArchivos.copiarImagen(imagen);
        }
        return modelo.agregarNoticia(titulo, contenido, autor, categoria, rutaImagen);
    }

    //Si nuevaImagen es null se mantiene la foto actual
    public Noticia editarNoticia(String idTexto, String titulo, String contenido, String categoriaTexto, File nuevaImagen) throws NoticiaNoEncontradaException, NoticiaDuplicadaException
    {
        int id = convertirId(idTexto);
        Categoria categoria = Categoria.desdeTexto(categoriaTexto);
        String rutaImagen = null;
        if (nuevaImagen != null)
        {
            rutaImagen = GestorArchivos.copiarImagen(nuevaImagen);
        }
        return modelo.editarNoticia(id, titulo, contenido, categoria, rutaImagen);
    }

    public List<Noticia> obtenerNoticias()
    {
        return modelo.listarNoticias();
    }

    public Noticia buscarPorId(String idTexto) throws NoticiaNoEncontradaException
    {
        return modelo.buscarNoticia(convertirId(idTexto));
    }

    public void eliminarNoticia(String idTexto) throws NoticiaNoEncontradaException
    {
        modelo.eliminarNoticia(convertirId(idTexto));
    }

    public List<Noticia> filtrarPorCategoria(String categoriaTexto)
    {
        return modelo.filtrarPorCategoria(Categoria.desdeTexto(categoriaTexto));
    }

    public List<Noticia> buscarPorPalabra(String palabra)
    {
        if (palabra == null || palabra.isBlank())
        {
            throw new DatoInvalidoException("Debe ingresar una palabra para buscar");
        }
        return modelo.buscarPorPalabra(palabra.trim());
    }

    public Categoria[] obtenerCategorias()
    {
        return Categoria.values();
    }

    public int totalNoticias()
    {
        return modelo.contarNoticias();
    }

    public int contarPorCategoria(String categoriaTexto)
    {
        return modelo.contarPorCategoria(Categoria.desdeTexto(categoriaTexto));
    }

    //Crea un archivo de texto con todas las noticias y devuelve cuántas se exportaron
    public int exportarReporte(File destino)
    {
        if (destino == null)
        {
            throw new DatoInvalidoException("Debe elegir dónde guardar el reporte");
        }
        return GestorArchivos.exportarLista(modelo.listarNoticias(), "REPORTE DE NOTICIAS", destino);
    }

    public int crearRespaldo(File destino)
    {
        return GestorArchivos.guardarRespaldo(modelo.listarNoticias(), destino);
    }

    //Vuelve a publicar las noticias del respaldo; las que ya existen (mismo título) se saltan
    public int restaurarRespaldo(File origen)
    {
        Repositorio<Noticia> respaldo = GestorArchivos.leerRespaldo(origen);
        int restauradas = 0;
        for (Noticia noticia : respaldo.listar())
        {
            try
            {
                modelo.agregarNoticia(noticia.getTitulo(), noticia.getContenido(), noticia.getAutor(), noticia.getCategoria(), noticia.getRutaImagen());
                restauradas++;
            }
            catch (NoticiaDuplicadaException e)
            {
                //Si ya existe no se vuelve a agregar
            }
        }
        return restauradas;
    }

    //Convierte el ID de texto a número validando el formato
    private int convertirId(String idTexto)
    {
        int id;
        try
        {
            id = Integer.parseInt(idTexto.trim());
        }
        catch (NumberFormatException | NullPointerException e)
        {
            throw new DatoInvalidoException("El ID debe ser un número entero");
        }
        if (id <= 0)
        {
            throw new DatoInvalidoException("El ID debe ser mayor que cero");
        }
        return id;
    }
}
