package vista.noticias;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import modelo.Categoria;
import vista.Estilos;
import vista.Idioma;

//Pantalla de inicio con las noticias más recientes y filtros por categoría
public class VistaPrincipal extends JFrame
{
    private static final long serialVersionUID = 1L;

    private final JPanel contenedorNoticias;
    private final JButton[] botonesNav = new JButton[4];
    private final JButton[] chipsCategoria;
    private final JLabel lblCantidad;
    private int cantidad;

    public VistaPrincipal()
    {
        setTitle("NotiRed - " + Idioma.t("Inicio", "Home"));
        setSize(920, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Estilos.FONDO);
        setLayout(new BorderLayout());

        //La fecha de hoy con la primera letra en mayúscula
        String hoy = Idioma.fechaLarga(LocalDate.now());
        hoy = hoy.substring(0, 1).toUpperCase() + hoy.substring(1);

        add(Estilos.crearBarraSuperior(Idioma.t("Noticias del día", "Today's news"), hoy + "  ·  " + Idioma.t("Lo que está pasando en Perú y el mundo", "What's happening in Peru and the world"), null), BorderLayout.NORTH);

        JPanel cuerpo = new JPanel(new BorderLayout());
        cuerpo.setBackground(Estilos.FONDO);

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(Estilos.FONDO);
        encabezado.setBorder(new EmptyBorder(18, 42, 4, 50));
        lblCantidad = new JLabel();
        lblCantidad.setFont(Estilos.fuente(Font.BOLD, 13));
        lblCantidad.setForeground(Estilos.TEXTO_SUAVE);
        encabezado.add(Estilos.crearTituloSeccion(Idioma.t("Lo más reciente", "Latest stories"), Idioma.t("Las últimas publicaciones de la comunidad NotiRed", "The latest posts from the NotiRed community")), BorderLayout.WEST);
        encabezado.add(lblCantidad, BorderLayout.EAST);

        //Fila de filtros: Todas + una por cada categoría
        JPanel filaChips = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        filaChips.setBackground(Estilos.FONDO);
        filaChips.setBorder(new EmptyBorder(4, 34, 4, 0));
        Categoria[] categorias = Categoria.values();
        chipsCategoria = new JButton[categorias.length + 1];
        chipsCategoria[0] = Estilos.crearChip(Idioma.t("Todas", "All"));
        filaChips.add(chipsCategoria[0]);
        for (int i = 0; i < categorias.length; i++)
        {
            chipsCategoria[i + 1] = Estilos.crearChip(Idioma.categoria(categorias[i]));
            filaChips.add(chipsCategoria[i + 1]);
        }
        Estilos.marcarChip(chipsCategoria[0], true);

        JPanel arriba = new JPanel(new BorderLayout());
        arriba.setBackground(Estilos.FONDO);
        arriba.add(encabezado, BorderLayout.NORTH);
        arriba.add(filaChips, BorderLayout.SOUTH);

        contenedorNoticias = new JPanel();
        contenedorNoticias.setLayout(new BoxLayout(contenedorNoticias, BoxLayout.Y_AXIS));
        contenedorNoticias.setBackground(Estilos.FONDO);
        contenedorNoticias.setBorder(new EmptyBorder(6, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(contenedorNoticias);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Estilos.FONDO);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        cuerpo.add(arriba, BorderLayout.NORTH);
        cuerpo.add(scroll, BorderLayout.CENTER);
        add(cuerpo, BorderLayout.CENTER);

        add(Estilos.crearBarraInferior(0, botonesNav), BorderLayout.SOUTH);
        limpiarNoticias();
    }

    public void agregarTarjeta(TarjetaNoticia tarjeta)
    {
        contenedorNoticias.add(tarjeta);
        contenedorNoticias.add(Box.createVerticalStrut(14));
        cantidad++;
        lblCantidad.setText(cantidad + Idioma.t(" noticias", " stories"));
        contenedorNoticias.revalidate();
        contenedorNoticias.repaint();
    }

    public void limpiarNoticias()
    {
        contenedorNoticias.removeAll();
        cantidad = 0;
        lblCantidad.setText("0" + Idioma.t(" noticias", " stories"));
        contenedorNoticias.revalidate();
        contenedorNoticias.repaint();
    }

    public void mostrarMensajeVacio()
    {
        JLabel lbl = new JLabel(Idioma.t("No hay noticias en esta categoría todavía", "There are no stories in this category yet"));
        lbl.setFont(Estilos.fuente(Font.PLAIN, 15));
        lbl.setForeground(Estilos.TEXTO_SUAVE);
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(40, 0, 0, 0));
        contenedorNoticias.add(lbl);
        contenedorNoticias.revalidate();
        contenedorNoticias.repaint();
    }

    //Pinta de azul el filtro elegido y los demás de blanco
    public void marcarCategoria(int indice)
    {
        for (int i = 0; i < chipsCategoria.length; i++)
        {
            Estilos.marcarChip(chipsCategoria[i], i == indice);
        }
    }

    public JButton[] getChipsCategoria()
    {
        return chipsCategoria;
    }

    public JButton getBtnInicio()
    {
        return botonesNav[0];
    }

    public JButton getBtnBuscar()
    {
        return botonesNav[1];
    }

    public JButton getBtnAgregar()
    {
        return botonesNav[2];
    }

    public JButton getBtnPerfil()
    {
        return botonesNav[3];
    }
}
