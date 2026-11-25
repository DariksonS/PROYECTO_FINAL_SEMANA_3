package vista.noticias;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vista.Estilos;
import vista.Idioma;

//Pantalla para buscar noticias por palabra
public class VistaBuscar extends JFrame
{
    private static final long serialVersionUID = 1L;

    private final String textoEjemplo = Idioma.t("Escribe una palabra, por ejemplo: Perú, dólar, cine...", "Type a word, for example: Peru, dollar, movies...");
    private final JTextField txtBuscar = new JTextField(textoEjemplo);
    private final JButton btnBuscarPalabra;
    private final JPanel contenedorResultados = new JPanel();
    private final JLabel lblResultado = new JLabel(" ");
    private final JButton[] botonesNav = new JButton[4];

    public VistaBuscar()
    {
        setTitle("NotiRed - " + Idioma.t("Buscar", "Search"));
        setSize(920, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Estilos.FONDO);
        setLayout(new BorderLayout());

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(Estilos.crearBarraSuperior(Idioma.t("Buscar noticias", "Search stories"), Idioma.t("Encuentra noticias por palabras del título o del contenido", "Find stories by words in the title or the content"), null), BorderLayout.NORTH);

        JPanel zonaBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 22));
        zonaBusqueda.setBackground(Estilos.FONDO);
        txtBuscar.setForeground(Color.GRAY);
        JPanel barraBusqueda = Estilos.crearCampoPildora(txtBuscar, null);
        barraBusqueda.setPreferredSize(new Dimension(560, 50));
        btnBuscarPalabra = Estilos.crearBotonRedondo(Idioma.t("Buscar", "Search"), Estilos.AZUL_BOTON, Color.WHITE, 120, 46, 46);
        zonaBusqueda.add(barraBusqueda);
        zonaBusqueda.add(btnBuscarPalabra);

        lblResultado.setFont(Estilos.fuente(Font.BOLD, 14));
        lblResultado.setForeground(Estilos.TEXTO_SUAVE);
        lblResultado.setBorder(new EmptyBorder(0, 50, 8, 0));

        JPanel centroNorte = new JPanel(new BorderLayout());
        centroNorte.setBackground(Estilos.FONDO);
        centroNorte.add(zonaBusqueda, BorderLayout.NORTH);
        centroNorte.add(lblResultado, BorderLayout.SOUTH);
        norte.add(centroNorte, BorderLayout.CENTER);
        add(norte, BorderLayout.NORTH);

        contenedorResultados.setLayout(new BoxLayout(contenedorResultados, BoxLayout.Y_AXIS));
        contenedorResultados.setBackground(Estilos.FONDO);
        contenedorResultados.setBorder(new EmptyBorder(0, 20, 20, 20));
        mostrarMensaje(Idioma.t("Escribe una palabra y presiona Enter o el botón Buscar", "Type a word and press Enter or the Search button"));

        JScrollPane scroll = new JScrollPane(contenedorResultados);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Estilos.FONDO);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        add(Estilos.crearBarraInferior(1, botonesNav), BorderLayout.SOUTH);
    }

    public void mostrarResultados(List<TarjetaNoticia> tarjetas, String palabra)
    {
        contenedorResultados.removeAll();
        lblResultado.setText(tarjetas.size() + Idioma.t(" resultados para \"", " results for \"") + palabra + "\"");
        if (tarjetas.isEmpty())
        {
            mostrarMensaje(Idioma.t("No se encontraron noticias con esa palabra", "No stories found with that word"));
        }
        for (TarjetaNoticia t : tarjetas)
        {
            contenedorResultados.add(t);
            contenedorResultados.add(Box.createVerticalStrut(14));
        }
        contenedorResultados.revalidate();
        contenedorResultados.repaint();
    }

    private void mostrarMensaje(String texto)
    {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(Estilos.fuente(Font.PLAIN, 15));
        lbl.setForeground(Estilos.TEXTO_SUAVE);
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(30, 0, 0, 0));
        contenedorResultados.add(lbl);
    }

    public String getTextoEjemplo()
    {
        return textoEjemplo;
    }

    public JTextField getTxtBuscar()
    {
        return txtBuscar;
    }

    public JButton getBtnBuscarPalabra()
    {
        return btnBuscarPalabra;
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
