package archivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import excepciones.ArchivoException;
import modelo.Noticia;
import util.Repositorio;

//Maneja los archivos locales: imágenes de las noticias y reportes de texto
public class GestorArchivos
{
    private static final String CARPETA_IMAGENES = "imagenes";

    //Copia la foto elegida a la carpeta imagenes del proyecto y devuelve su nueva ruta
    public static String copiarImagen(File origen)
    {
        if (origen == null || !origen.exists())
        {
            throw new ArchivoException("La imagen seleccionada no existe");
        }
        validarImagen(origen);
        try
        {
            Path carpeta = Paths.get(CARPETA_IMAGENES);
            if (!Files.exists(carpeta))
            {
                Files.createDirectories(carpeta);
            }
            //Se agrega la hora actual al nombre para que nunca se repita
            String nuevoNombre = System.currentTimeMillis() + "_" + origen.getName();
            Path destino = carpeta.resolve(nuevoNombre);
            Files.copy(origen.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
            return CARPETA_IMAGENES + "/" + nuevoNombre;
        }
        catch (IOException e)
        {
            throw new ArchivoException("No se pudo copiar la imagen: " + e.getMessage());
        }
    }

    //Revisa que la imagen se pueda abrir; las imágenes WEBP de algunas páginas web no son compatibles con Java
    public static void validarImagen(File archivo)
    {
        if (archivo == null || !archivo.exists())
        {
            throw new ArchivoException("La imagen seleccionada no existe");
        }
        if (!esImagen(archivo.getName()))
        {
            throw new ArchivoException("Solo se permiten imágenes JPG, JPEG, PNG o GIF");
        }
        try
        {
            if (ImageIO.read(archivo) == null)
            {
                throw new ArchivoException("No se puede abrir esta imagen. Es probable que sea formato WEBP aunque diga .jpg. Ábrela con Paint y guárdala como JPG o PNG");
            }
        }
        catch (IOException e)
        {
            throw new ArchivoException("No se pudo leer la imagen: " + e.getMessage());
        }
    }

    public static void eliminarImagen(String ruta)
    {
        if (ruta == null || ruta.isBlank())
        {
            return;
        }
        try
        {
            Files.deleteIfExists(Paths.get(ruta));
        }
        catch (IOException e)
        {
            throw new ArchivoException("No se pudo eliminar la imagen: " + e.getMessage());
        }
    }

    public static boolean esImagen(String nombreArchivo)
    {
        String nombre = nombreArchivo.toLowerCase();
        return nombre.endsWith(".jpg") || nombre.endsWith(".jpeg") || nombre.endsWith(".jfif") || nombre.endsWith(".png") || nombre.endsWith(".gif");
    }

    //Método genérico: escribe cualquier lista en un archivo de texto, un elemento por línea
    public static <T> int exportarLista(List<T> lista, String titulo, File destino)
    {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(destino)))
        {
            escritor.println(titulo);
            escritor.println("------------------------------");
            for (T elemento : lista)
            {
                escritor.println(elemento);
            }
            escritor.println("------------------------------");
            escritor.println("Total: " + lista.size());
            return lista.size();
        }
        catch (IOException e)
        {
            throw new ArchivoException("No se pudo crear el reporte: " + e.getMessage());
        }
    }

    //Serialización: guarda la lista completa de noticias como objetos en un archivo binario .dat
    public static int guardarRespaldo(List<Noticia> noticias, File destino)
    {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(destino)))
        {
            salida.writeObject(new ArrayList<Noticia>(noticias));
            return noticias.size();
        }
        catch (IOException e)
        {
            throw new ArchivoException("No se pudo crear la copia de seguridad: " + e.getMessage());
        }
    }

    //Lee el archivo .dat y devuelve las noticias en un Repositorio genérico
    public static Repositorio<Noticia> leerRespaldo(File origen)
    {
        Repositorio<Noticia> repositorio = new Repositorio<>();
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(origen)))
        {
            Object leido = entrada.readObject();
            if (!(leido instanceof List))
            {
                throw new ArchivoException("El archivo no es una copia de seguridad de NotiRed");
            }
            for (Object elemento : (List<?>) leido)
            {
                if (elemento instanceof Noticia)
                {
                    repositorio.agregar((Noticia) elemento);
                }
            }
            return repositorio;
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new ArchivoException("No se pudo leer la copia de seguridad: " + e.getMessage());
        }
    }
}
