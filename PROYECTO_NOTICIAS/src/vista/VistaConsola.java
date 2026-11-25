package vista;

import java.util.List;
import java.util.Scanner;

import controlador.NoticiaControlador;
import excepciones.BaseDatosException;
import excepciones.DatoInvalidoException;
import excepciones.NoticiaDuplicadaException;
import excepciones.NoticiaNoEncontradaException;
import modelo.Categoria;
import modelo.Noticia;

//Vista temporal por consola para probar el prototipo (luego se reemplaza por Swing)
public class VistaConsola
{
    private NoticiaControlador controlador;
    private Scanner scanner;

    public VistaConsola(NoticiaControlador controlador)
    {
        this.controlador = controlador;
    }

    public void iniciar()
    {
        try (Scanner lector = new Scanner(System.in))
        {
            scanner = lector;
            String opcion;
            do
            {
                mostrarMenu();
                opcion = leer("Elige una opción: ");
                procesarOpcion(opcion);
            }
            while (!opcion.equals("0"));
        }
    }

    private void mostrarMenu()
    {
        System.out.println();
        System.out.println("------ APP DE NOTICIAS ------");
        System.out.println("Noticias registradas: " + controlador.totalNoticias());
        System.out.println("1. Publicar noticia");
        System.out.println("2. Ver todas las noticias");
        System.out.println("3. Buscar noticia por ID");
        System.out.println("4. Filtrar por categoría");
        System.out.println("5. Buscar por palabra");
        System.out.println("6. Eliminar noticia");
        System.out.println("0. Salir");
        System.out.println("------------------------------");
    }

    //Cada opción llama al controlador y los errores se muestran sin cerrar el programa
    private void procesarOpcion(String opcion)
    {
        try
        {
            switch (opcion)
            {
                case "1":
                    publicar();
                    break;
                case "2":
                    mostrarLista(controlador.obtenerNoticias());
                    break;
                case "3":
                    mostrarDetalle(controlador.buscarPorId(leer("ID de la noticia: ")));
                    break;
                case "4":
                    mostrarCategorias();
                    mostrarLista(controlador.filtrarPorCategoria(leer("Categoría: ")));
                    break;
                case "5":
                    mostrarLista(controlador.buscarPorPalabra(leer("Palabra a buscar: ")));
                    break;
                case "6":
                    controlador.eliminarNoticia(leer("ID de la noticia a eliminar: "));
                    System.out.println("Noticia eliminada correctamente.");
                    break;
                case "0":
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        catch (DatoInvalidoException e)
        {
            System.out.println("Dato inválido: " + e.getMessage());
        }
        catch (NoticiaNoEncontradaException | NoticiaDuplicadaException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        catch (BaseDatosException e)
        {
            System.out.println("Error de base de datos: " + e.getMessage());
        }
    }

    private void publicar() throws NoticiaDuplicadaException
    {
        String titulo = leer("Título: ");
        String contenido = leer("Contenido: ");
        String autor = leer("Autor: ");
        mostrarCategorias();
        String categoria = leer("Categoría: ");
        Noticia noticia = controlador.publicarNoticia(titulo, contenido, autor, categoria);
        System.out.println("Noticia publicada con ID " + noticia.getId());
    }

    private void mostrarLista(List<Noticia> noticias)
    {
        if (noticias.isEmpty())
        {
            System.out.println("No hay noticias para mostrar.");
            return;
        }
        for (Noticia noticia : noticias)
        {
            System.out.println(noticia);
        }
    }

    private void mostrarDetalle(Noticia noticia)
    {
        System.out.println("------ " + noticia.getTitulo() + " ------");
        System.out.println("Autor: " + noticia.getAutor());
        System.out.println("Categoría: " + noticia.getCategoria());
        System.out.println("Fecha: " + noticia.getFecha());
        System.out.println(noticia.getContenido());
    }

    private void mostrarCategorias()
    {
        System.out.print("Categorías disponibles: ");
        for (Categoria categoria : controlador.obtenerCategorias())
        {
            System.out.print(categoria + "  ");
        }
        System.out.println();
    }

    private String leer(String mensaje)
    {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}
