package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import archivos.GestorArchivos;
import dao.AlmacenNoticias;
import excepciones.NoticiaDuplicadaException;
import excepciones.NoticiaNoEncontradaException;

//Lógica de negocio de las noticias
public class NoticiaModelo
{
    private AlmacenNoticias almacen;

    //Recibe dónde se guardarán las noticias
    public NoticiaModelo(AlmacenNoticias almacen)
    {
        this.almacen = almacen;
    }

    public Noticia agregarNoticia(String titulo, String contenido, String autor, Categoria categoria) throws NoticiaDuplicadaException
    {
        return agregarNoticia(titulo, contenido, autor, categoria, null);
    }

    public Noticia agregarNoticia(String titulo, String contenido, String autor, Categoria categoria, String rutaImagen) throws NoticiaDuplicadaException
    {
        if (existeTitulo(titulo, 0))
        {
            throw new NoticiaDuplicadaException(titulo.trim());
        }
        //El ID 0 es temporal, el almacén le asigna el ID real al guardar
        Noticia noticia = new Noticia(0, titulo, contenido, autor, categoria);
        noticia.setRutaImagen(rutaImagen);
        return almacen.guardar(noticia);
    }

    //Si rutaImagen es null se mantiene la foto que ya tenía
    public Noticia editarNoticia(int id, String titulo, String contenido, Categoria categoria, String rutaImagen) throws NoticiaNoEncontradaException, NoticiaDuplicadaException
    {
        Noticia noticia = buscarNoticia(id);
        if (existeTitulo(titulo, id))
        {
            throw new NoticiaDuplicadaException(titulo.trim());
        }
        noticia.setTitulo(titulo);
        noticia.setContenido(contenido);
        noticia.setCategoria(categoria);
        if (rutaImagen != null)
        {
            GestorArchivos.eliminarImagen(noticia.getRutaImagen());
            noticia.setRutaImagen(rutaImagen);
        }
        almacen.actualizar(noticia);
        return noticia;
    }

    public Noticia buscarNoticia(int id) throws NoticiaNoEncontradaException
    {
        Noticia noticia = almacen.buscarPorId(id);
        if (noticia == null)
        {
            throw new NoticiaNoEncontradaException(id);
        }
        return noticia;
    }

    //Al eliminar la noticia también se borra su foto de la carpeta imagenes
    public void eliminarNoticia(int id) throws NoticiaNoEncontradaException
    {
        Noticia noticia = buscarNoticia(id);
        almacen.eliminar(id);
        GestorArchivos.eliminarImagen(noticia.getRutaImagen());
    }

    //Ordena usando el compareTo de Noticia
    public List<Noticia> listarNoticias()
    {
        List<Noticia> lista = almacen.listar();
        Collections.sort(lista);
        return lista;
    }

    public List<Noticia> filtrarPorCategoria(Categoria categoria)
    {
        List<Noticia> resultado = new ArrayList<>();
        for (Noticia noticia : listarNoticias())
        {
            if (noticia.getCategoria() == categoria)
            {
                resultado.add(noticia);
            }
        }
        return resultado;
    }

    //Busca la palabra en el título o en el contenido sin importar mayúsculas
    public List<Noticia> buscarPorPalabra(String palabra)
    {
        String clave = palabra.toLowerCase();
        List<Noticia> resultado = new ArrayList<>();
        for (Noticia noticia : listarNoticias())
        {
            String titulo = noticia.getTitulo().toLowerCase();
            String contenido = noticia.getContenido().toLowerCase();
            if (titulo.contains(clave) || contenido.contains(clave))
            {
                resultado.add(noticia);
            }
        }
        return resultado;
    }

    public int contarNoticias()
    {
        return almacen.contar();
    }

    public int contarPorCategoria(Categoria categoria)
    {
        return filtrarPorCategoria(categoria).size();
    }

    //idIgnorado sirve para que al editar no se compare la noticia consigo misma
    private boolean existeTitulo(String titulo, int idIgnorado)
    {
        if (titulo == null)
        {
            return false;
        }
        for (Noticia noticia : almacen.listar())
        {
            if (noticia.getId() != idIgnorado && noticia.getTitulo().equalsIgnoreCase(titulo.trim()))
            {
                return true;
            }
        }
        return false;
    }
}
