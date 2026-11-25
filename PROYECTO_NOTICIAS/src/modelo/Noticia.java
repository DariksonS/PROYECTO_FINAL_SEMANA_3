package modelo;

import java.io.Serializable;
import java.time.LocalDate;

import excepciones.DatoInvalidoException;
import util.Identificable;

public class Noticia implements Identificable, Comparable<Noticia>, Serializable
{
    private static final long serialVersionUID = 1L;

    private int id;
    private String titulo;
    private String contenido;
    private String autor;
    private Categoria categoria;
    private LocalDate fecha;
    private String rutaImagen;

    public Noticia(int id, String titulo, String contenido, String autor, Categoria categoria)
    {
        this(id, titulo, contenido, autor, categoria, LocalDate.now());
    }

    public Noticia(int id, String titulo, String contenido, String autor, Categoria categoria, LocalDate fecha)
    {
        if (categoria == null)
        {
            throw new DatoInvalidoException("La categoría es obligatoria");
        }
        this.id = id;
        this.titulo = validarTexto(titulo, "título");
        this.contenido = validarTexto(contenido, "contenido");
        this.autor = validarTexto(autor, "autor");
        this.categoria = categoria;
        this.fecha = fecha;
        this.rutaImagen = null;
    }

    //Valida que los campos de texto no estén vacíos
    private String validarTexto(String valor, String campo)
    {
        if (valor == null || valor.isBlank())
        {
            throw new DatoInvalidoException("El campo " + campo + " no puede estar vacío");
        }
        return valor.trim();
    }

    @Override
    public int getId()
    {
        return id;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public String getContenido()
    {
        return contenido;
    }

    public String getAutor()
    {
        return autor;
    }

    public Categoria getCategoria()
    {
        return categoria;
    }

    public LocalDate getFecha()
    {
        return fecha;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = validarTexto(titulo, "título");
    }

    public void setContenido(String contenido)
    {
        this.contenido = validarTexto(contenido, "contenido");
    }

    public void setCategoria(Categoria categoria)
    {
        if (categoria == null)
        {
            throw new DatoInvalidoException("La categoría es obligatoria");
        }
        this.categoria = categoria;
    }

    //Ruta de la foto guardada en la carpeta imagenes (puede ser null si no tiene foto)
    public String getRutaImagen()
    {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen)
    {
        this.rutaImagen = rutaImagen;
    }

    public boolean tieneImagen()
    {
        return rutaImagen != null && !rutaImagen.isBlank();
    }

    //Ordena de la más reciente a la más antigua; si son del mismo día, primero la de ID más alto (la última publicada)
    @Override
    public int compareTo(Noticia otra)
    {
        int resultado = otra.fecha.compareTo(this.fecha);
        if (resultado == 0)
        {
            resultado = Integer.compare(otra.id, this.id);
        }
        return resultado;
    }

    //Dos noticias son iguales si tienen el mismo ID
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }
        Noticia otra = (Noticia) obj;
        return id == otra.id;
    }

    @Override
    public String toString()
    {
        return "[" + id + "] " + titulo + " | " + categoria + " | " + autor + " | " + fecha;
    }
}
