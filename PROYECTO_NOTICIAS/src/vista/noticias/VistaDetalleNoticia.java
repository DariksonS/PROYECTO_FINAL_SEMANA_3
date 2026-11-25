package vista.noticias;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import modelo.Comentario;
import modelo.Noticia;
import vista.Estilos;
import vista.Idioma;

//Pantalla que muestra una noticia completa con su foto, botón de guardar y comentarios
public class VistaDetalleNoticia extends JFrame
{
    private static final long serialVersionUID = 1L;

    private final JButton btnInicio;
    private final JButton btnGuardar;
    private final JButton btnComentar;
    private final JLabel lblTitulo = new JLabel();
    private final JLabel lblDatos = new JLabel();
    private final JLabel lblComentarios = new JLabel();
    private final JPanel filaEtiqueta = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    private final JPanel contenedorImagen = new JPanel(new BorderLayout());
    private final JTextArea txtContenido = new JTextArea();
    private final JPanel panelComentariosLista = new JPanel();
    private final JTextField txtComentario = new JTextField();
    private final JLabel lblContador = new JLabel("0 / 300");
    private final String textoEjemplo = Idioma.t("Escribe un comentario...", "Write a comment...");
    private Noticia noticiaActual;

    public VistaDetalleNoticia()
    {
        setTitle("NotiRed - " + Idioma.t("Noticia", "Story"));
        setSize(920, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Estilos.FONDO);
        setLayout(new BorderLayout());

        btnInicio = Estilos.crearBotonRedondo("", Estilos.CELESTE_ATRAS, Estilos.AZUL_BOTON, 115, 38, 38);
        add(Estilos.crearEncabezadoAtras(Idioma.t("Noticia completa", "Full story"), Idioma.t("Lee la noticia, guárdala para después y deja tu opinión", "Read the story, save it for later and share your opinion"), btnInicio), BorderLayout.NORTH);

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Color.WHITE);
        cuerpo.setBorder(new EmptyBorder(24, 70, 30, 70));

        filaEtiqueta.setOpaque(false);
        filaEtiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblTitulo.setFont(Estilos.fuente(Font.BOLD, 28));
        lblTitulo.setForeground(Estilos.TEXTO);
        lblTitulo.setBorder(new EmptyBorder(10, 0, 4, 0));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblDatos.setFont(Estilos.fuente(Font.PLAIN, 14));
        lblDatos.setForeground(Estilos.TEXTO_SUAVE);
        lblDatos.setBorder(new EmptyBorder(0, 0, 16, 0));
        lblDatos.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenedorImagen.setOpaque(false);
        contenedorImagen.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenedorImagen.setMaximumSize(new Dimension(740, 300));

        txtContenido.setEditable(false);
        txtContenido.setLineWrap(true);
        txtContenido.setWrapStyleWord(true);
        txtContenido.setFont(Estilos.fuente(Font.PLAIN, 16));
        txtContenido.setForeground(Estilos.TEXTO);
        txtContenido.setBackground(Color.WHITE);
        txtContenido.setBorder(new EmptyBorder(20, 0, 18, 0));
        txtContenido.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnGuardar = Estilos.crearBotonRedondo("", Estilos.CELESTE_ATRAS, Estilos.AZUL_BOTON, 230, 38, 38);
        JPanel filaAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        filaAcciones.setOpaque(false);
        filaAcciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        filaAcciones.setMaximumSize(new Dimension(740, 46));
        filaAcciones.add(btnGuardar);

        lblComentarios.setFont(Estilos.fuente(Font.BOLD, 18));
        lblComentarios.setForeground(Estilos.TEXTO);
        lblComentarios.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Estilos.BORDE), new EmptyBorder(18, 0, 6, 0)));
        lblComentarios.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblComentarios.setMaximumSize(new Dimension(740, 54));

        panelComentariosLista.setLayout(new BoxLayout(panelComentariosLista, BoxLayout.Y_AXIS));
        panelComentariosLista.setBackground(Color.WHITE);
        panelComentariosLista.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtComentario.setText(textoEjemplo);
        txtComentario.setForeground(Color.GRAY);
        JPanel campoComentario = Estilos.crearCampoPildora(txtComentario, null);
        btnComentar = Estilos.crearBotonRedondo(Idioma.t("Comentar", "Comment"), Estilos.AZUL_BOTON, Color.WHITE, 120, 44, 44);
        JPanel filaComentar = new JPanel(new BorderLayout(10, 0));
        filaComentar.setOpaque(false);
        filaComentar.setAlignmentX(Component.LEFT_ALIGNMENT);
        filaComentar.setMaximumSize(new Dimension(740, 52));
        filaComentar.add(campoComentario, BorderLayout.CENTER);
        filaComentar.add(btnComentar, BorderLayout.EAST);

        cuerpo.add(filaEtiqueta);
        cuerpo.add(lblTitulo);
        cuerpo.add(lblDatos);
        cuerpo.add(contenedorImagen);
        cuerpo.add(txtContenido);
        cuerpo.add(filaAcciones);
        cuerpo.add(Box.createVerticalStrut(20));
        cuerpo.add(lblComentarios);
        cuerpo.add(panelComentariosLista);
        //Evento de teclado: cada vez que se suelta una tecla se actualiza el contador de letras
        txtComentario.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                actualizarContador();
            }
        });
        lblContador.setFont(Estilos.fuente(Font.PLAIN, 12));
        lblContador.setForeground(Estilos.TEXTO_SUAVE);
        lblContador.setBorder(new EmptyBorder(4, 18, 0, 0));
        lblContador.setAlignmentX(Component.LEFT_ALIGNMENT);

        cuerpo.add(Box.createVerticalStrut(14));
        cuerpo.add(filaComentar);
        cuerpo.add(lblContador);

        JScrollPane scroll = new JScrollPane(cuerpo);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    public void mostrarNoticia(Noticia n, boolean guardada)
    {
        noticiaActual = n;
        filaEtiqueta.removeAll();
        filaEtiqueta.add(Estilos.crearEtiquetaCategoria(n.getCategoria()));
        lblTitulo.setText("<html><body style='width: 600px;'>" + n.getTitulo() + "</body></html>");
        lblDatos.setText(Idioma.t("Por ", "By ") + n.getAutor() + "   ·   " + Idioma.fecha(n.getFecha()));
        contenedorImagen.removeAll();
        contenedorImagen.add(Estilos.crearPanelImagen(n.getRutaImagen(), n.getCategoria(), 740, 300), BorderLayout.CENTER);
        txtContenido.setText(n.getContenido());
        txtContenido.setCaretPosition(0);
        marcarGuardada(guardada);
        txtComentario.setText(textoEjemplo);
        txtComentario.setForeground(Color.GRAY);
        actualizarContador();
        revalidate();
        repaint();
    }

    public void marcarGuardada(boolean guardada)
    {
        if (guardada)
        {
            btnGuardar.setText(Idioma.t("Guardada  ·  Quitar", "Saved  ·  Remove"));
        }
        else
        {
            btnGuardar.setText(Idioma.t("Guardar para después", "Save for later"));
        }
    }

    //Muestra cada comentario guardado en la base de datos con su autor y fecha
    public void mostrarComentarios(List<Comentario> comentarios)
    {
        lblComentarios.setText(Idioma.t("Comentarios (", "Comments (") + comentarios.size() + ")");
        panelComentariosLista.removeAll();
        if (comentarios.isEmpty())
        {
            JLabel vacio = new JLabel(Idioma.t("Aún no hay comentarios. ¡Sé el primero en opinar!", "No comments yet. Be the first to share your opinion!"));
            vacio.setFont(Estilos.fuente(Font.PLAIN, 14));
            vacio.setForeground(Estilos.TEXTO_SUAVE);
            vacio.setBorder(new EmptyBorder(6, 0, 6, 0));
            panelComentariosLista.add(vacio);
        }
        for (Comentario comentario : comentarios)
        {
            panelComentariosLista.add(crearItemComentario(comentario));
        }
        panelComentariosLista.revalidate();
        panelComentariosLista.repaint();
    }

    private JPanel crearItemComentario(Comentario comentario)
    {
        JPanel item = new JPanel(new BorderLayout(12, 0));
        item.setBackground(new Color(248, 250, 252));
        item.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, Estilos.AZUL_BOTON), new EmptyBorder(10, 14, 10, 14)));
        item.setMaximumSize(new Dimension(740, 90));
        item.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblAutor = new JLabel(comentario.getAutor() + "   ·   " + Idioma.fecha(comentario.getFecha()));
        lblAutor.setFont(Estilos.fuente(Font.BOLD, 13));
        lblAutor.setForeground(Estilos.TEXTO);

        JLabel lblTexto = new JLabel("<html><body style='width: 620px;'>" + comentario.getTexto() + "</body></html>");
        lblTexto.setFont(Estilos.fuente(Font.PLAIN, 14));
        lblTexto.setForeground(Estilos.TEXTO);

        item.add(lblAutor, BorderLayout.NORTH);
        item.add(lblTexto, BorderLayout.CENTER);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setOpaque(false);
        contenedor.setBorder(new EmptyBorder(0, 0, 8, 0));
        contenedor.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenedor.setMaximumSize(new Dimension(740, 98));
        contenedor.add(item, BorderLayout.CENTER);
        return contenedor;
    }

    //Muestra cuántas letras lleva el comentario y se pone rojo si pasa de 300
    public void actualizarContador()
    {
        int cantidad = txtComentario.getText().length();
        if (txtComentario.getText().equals(textoEjemplo))
        {
            cantidad = 0;
        }
        lblContador.setText(cantidad + " / 300");
        if (cantidad > 300)
        {
            lblContador.setForeground(Estilos.ROJO_PELIGRO);
        }
        else
        {
            lblContador.setForeground(Estilos.TEXTO_SUAVE);
        }
    }

    public Noticia getNoticiaActual()
    {
        return noticiaActual;
    }

    public String getTextoEjemplo()
    {
        return textoEjemplo;
    }

    public JButton getBtnInicio()
    {
        return btnInicio;
    }

    public JButton getBtnGuardar()
    {
        return btnGuardar;
    }

    public JButton getBtnComentar()
    {
        return btnComentar;
    }

    public JTextField getTxtComentario()
    {
        return txtComentario;
    }
}
