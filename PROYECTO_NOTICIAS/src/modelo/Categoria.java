package modelo;

import excepciones.DatoInvalidoException;

public enum Categoria
{
    POLITICA("Política"),
    DEPORTES("Deportes"),
    TECNOLOGIA("Tecnología"),
    ECONOMIA("Economía"),
    ENTRETENIMIENTO("Entretenimiento");

    private String nombre;

    Categoria(String nombre)
    {
        this.nombre = nombre;
    }

    public String getNombre()
    {
        return nombre;
    }

    //Convierte el texto ingresado por el usuario en una categoría
    public static Categoria desdeTexto(String texto)
    {
        if (texto == null || texto.isBlank())
        {
            throw new DatoInvalidoException("Debe indicar una categoría");
        }
        for (Categoria categoria : values())
        {
            if (categoria.name().equalsIgnoreCase(texto.trim()) || categoria.nombre.equalsIgnoreCase(texto.trim()))
            {
                return categoria;
            }
        }
        throw new DatoInvalidoException("Categoría no válida: " + texto);
    }

    @Override
    public String toString()
    {
        return nombre;
    }
}
