package modelo;

import java.time.LocalDate;

import excepciones.DatoInvalidoException;

public class Comentario
{
    private int id;
    private int noticiaId;
    private String autor;
    private String texto;
    private LocalDate fecha;

    public Comentario(int id, int noticiaId, String autor, String texto, LocalDate fecha)
    {
        if (texto == null || texto.isBlank())
        {
            throw new DatoInvalidoException("El comentario no puede estar vacío");
        }
        if (texto.trim().length() > 300)
        {
            throw new DatoInvalidoException("El comentario no puede tener más de 300 caracteres");
        }
        this.id = id;
        this.noticiaId = noticiaId;
        this.autor = autor;
        this.texto = texto.trim();
        this.fecha = fecha;
    }

    public int getId()
    {
        return id;
    }

    public int getNoticiaId()
    {
        return noticiaId;
    }

    public String getAutor()
    {
        return autor;
    }

    public String getTexto()
    {
        return texto;
    }

    public LocalDate getFecha()
    {
        return fecha;
    }
}
