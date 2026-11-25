package vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import modelo.Categoria;

//Guarda el idioma elegido y devuelve cada texto en español o en inglés
public class Idioma
{
    private static boolean ingles = false;

    public static void setIngles(boolean valor)
    {
        ingles = valor;
    }

    public static boolean esIngles()
    {
        return ingles;
    }

    //Recibe el texto en los dos idiomas y devuelve el del idioma actual
    public static String t(String espanol, String enIngles)
    {
        if (ingles)
        {
            return enIngles;
        }
        return espanol;
    }

    public static String categoria(Categoria categoria)
    {
        if (!ingles)
        {
            return categoria.getNombre();
        }
        switch (categoria)
        {
            case POLITICA:
                return "Politics";
            case DEPORTES:
                return "Sports";
            case TECNOLOGIA:
                return "Technology";
            case ECONOMIA:
                return "Economy";
            default:
                return "Entertainment";
        }
    }

    //Fecha escrita en palabras, por ejemplo "24 de noviembre de 2026" o "November 24, 2026"
    public static String fecha(LocalDate fecha)
    {
        if (ingles)
        {
            return fecha.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH));
        }
        return fecha.format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale.forLanguageTag("es")));
    }

    public static String fechaLarga(LocalDate fecha)
    {
        if (ingles)
        {
            return fecha.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH));
        }
        return fecha.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", Locale.forLanguageTag("es")));
    }
}
