package vista.noticias;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Noticia;
import vista.Estilos;
import vista.Idioma;

//Tarjeta blanca con sombra que muestra una noticia en la lista
public class TarjetaNoticia extends JPanel
{
    private static final long serialVersionUID = 1L;

    private final JButton btnMasInfo;
    private final Noticia noticia;

    public TarjetaNoticia(Noticia noticia, int cantidadComentarios, boolean guardada)
    {
        this.noticia = noticia;
        setLayout(new BorderLayout(20, 0));
        setOpaque(false);
        setBorder(new EmptyBorder(16, 16, 18, 22));
        setMaximumSize(new Dimension(820, 190));
        setPreferredSize(new Dimension(820, 190));
        setAlignmentX(CENTER_ALIGNMENT);

        JPanel imagen = Estilos.crearPanelImagen(noticia.getRutaImagen(), noticia.getCategoria(), 230, 156);

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setOpaque(false);

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);

        JPanel filaEtiqueta = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        filaEtiqueta.setOpaque(false);
        filaEtiqueta.add(Estilos.crearEtiquetaCategoria(noticia.getCategoria()));
        if (guardada)
        {
            JLabel lblGuardada = new JLabel(Idioma.t("   Guardada", "   Saved"));
            lblGuardada.setFont(Estilos.fuente(Font.BOLD, 11));
            lblGuardada.setForeground(Estilos.AZUL_BOTON);
            filaEtiqueta.add(lblGuardada);
        }
        filaEtiqueta.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel("<html><body style='width: 395px;'>" + recortar(noticia.getTitulo(), 70) + "</body></html>");
        lblTitulo.setFont(Estilos.fuente(Font.BOLD, 18));
        lblTitulo.setForeground(Estilos.TEXTO);
        lblTitulo.setBorder(new EmptyBorder(8, 0, 0, 0));
        lblTitulo.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblResumen = new JLabel("<html><body style='width: 395px;'>" + recortar(noticia.getContenido(), 110) + "</body></html>");
        lblResumen.setFont(Estilos.fuente(Font.PLAIN, 13));
        lblResumen.setForeground(Estilos.TEXTO_SUAVE);
        lblResumen.setBorder(new EmptyBorder(6, 0, 0, 0));
        lblResumen.setAlignmentX(LEFT_ALIGNMENT);

        textos.add(filaEtiqueta);
        textos.add(lblTitulo);
        textos.add(lblResumen);

        JPanel inferior = new JPanel(new BorderLayout());
        inferior.setOpaque(false);

        String comentarios = cantidadComentarios + Idioma.t(" comentarios", " comments");
        JLabel lblDatos = new JLabel(noticia.getAutor() + "   ·   " + Idioma.fecha(noticia.getFecha()) + "   ·   " + comentarios);
        lblDatos.setFont(Estilos.fuente(Font.PLAIN, 12));
        lblDatos.setForeground(Estilos.TEXTO_SUAVE);

        btnMasInfo = Estilos.crearBotonRedondo(Idioma.t("Leer más  →", "Read more  →"), Estilos.AZUL_BOTON, Color.WHITE, 130, 36, 36);

        inferior.add(lblDatos, BorderLayout.WEST);
        inferior.add(btnMasInfo, BorderLayout.EAST);

        contenido.add(textos, BorderLayout.NORTH);
        contenido.add(inferior, BorderLayout.SOUTH);

        add(imagen, BorderLayout.WEST);
        add(contenido, BorderLayout.CENTER);
    }

    //Dibuja el fondo blanco redondeado con una sombra suave
    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 0, 0, 18));
        g2.fillRoundRect(3, 5, getWidth() - 6, getHeight() - 6, 24, 24);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth() - 6, getHeight() - 8, 24, 24);
        g2.dispose();
        super.paintComponent(g);
    }

    //En la vista previa solo se muestra el inicio del texto, la noticia completa se ve en "Leer más"
    private String recortar(String texto, int maximo)
    {
        if (texto.length() <= maximo)
        {
            return texto;
        }
        return texto.substring(0, maximo).trim() + "...";
    }

    public JButton getBtnMasInfo()
    {
        return btnMasInfo;
    }

    public Noticia getNoticia()
    {
        return noticia;
    }
}
