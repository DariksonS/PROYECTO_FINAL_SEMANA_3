package vista.noticias;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import modelo.Categoria;
import vista.Estilos;
import vista.Idioma;

//Pantalla con el formulario para publicar una noticia nueva
public class VistaCrearNoticia extends JFrame
{
    private static final long serialVersionUID = 1L;

    private final JTextField txtTitulo = new JTextField();
    private final JTextArea txtContenido = new JTextArea();
    private final JTextField txtAutor = new JTextField();
    private final JComboBox<Categoria> cmbCategoria = new JComboBox<>(Categoria.values());
    private final JButton btnAgregarPortada;
    private final JButton btnPublicar;
    private final JButton[] botonesNav = new JButton[4];
    private final String textoPortada = Idioma.t("+  Agregar imagen de portada (opcional)", "+  Add a cover image (optional)");

    public VistaCrearNoticia()
    {
        setTitle("NotiRed - " + Idioma.t("Publicar", "Post"));
        setSize(920, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Estilos.FONDO);
        setLayout(new BorderLayout());

        add(Estilos.crearBarraSuperior(Idioma.t("Publicar una noticia", "Post a story"), Idioma.t("Comparte lo que está pasando. Los campos con * son obligatorios", "Share what's happening. Fields marked with * are required"), null), BorderLayout.NORTH);

        JPanel formulario = Estilos.crearTarjeta();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.setBorder(new EmptyBorder(22, 30, 26, 34));
        formulario.setMaximumSize(new Dimension(700, 640));

        txtTitulo.setFont(Estilos.fuente(Font.BOLD, 16));
        configurarCampo(txtTitulo);

        btnAgregarPortada = new JButton(textoPortada)
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g)
            {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(248, 250, 252));
                g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
                g2.setColor(new Color(148, 163, 184));
                g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[] {6f, 6f}, 0f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnAgregarPortada.setForeground(Estilos.TEXTO_SUAVE);
        btnAgregarPortada.setFont(Estilos.fuente(Font.BOLD, 14));
        btnAgregarPortada.setPreferredSize(new Dimension(640, 150));
        btnAgregarPortada.setMaximumSize(new Dimension(640, 150));
        btnAgregarPortada.setContentAreaFilled(false);
        btnAgregarPortada.setBorderPainted(false);
        btnAgregarPortada.setFocusPainted(false);
        btnAgregarPortada.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregarPortada.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Fila con el autor y la categoría de la noticia
        JPanel filaDatos = new JPanel(new GridLayout(2, 2, 16, 2));
        filaDatos.setOpaque(false);
        filaDatos.setMaximumSize(new Dimension(640, 66));
        filaDatos.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtAutor.setFont(Estilos.fuente(Font.PLAIN, 14));
        txtAutor.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Estilos.BORDE), new EmptyBorder(4, 8, 4, 8)));
        cmbCategoria.setFont(Estilos.fuente(Font.PLAIN, 14));
        Estilos.traducirCombo(cmbCategoria);
        filaDatos.add(Estilos.crearEtiquetaCampo(Idioma.t("Autor *", "Author *")));
        filaDatos.add(Estilos.crearEtiquetaCampo(Idioma.t("Categoría *", "Category *")));
        filaDatos.add(txtAutor);
        filaDatos.add(cmbCategoria);

        txtContenido.setFont(Estilos.fuente(Font.PLAIN, 14));
        txtContenido.setLineWrap(true);
        txtContenido.setWrapStyleWord(true);
        txtContenido.setBorder(new EmptyBorder(10, 12, 10, 12));
        JScrollPane scrollContenido = new JScrollPane(txtContenido);
        scrollContenido.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1));
        scrollContenido.setPreferredSize(new Dimension(640, 130));
        scrollContenido.setMaximumSize(new Dimension(640, 130));
        scrollContenido.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnPublicar = Estilos.crearBotonRedondo(Idioma.t("Publicar noticia", "Post story"), Estilos.AZUL_BOTON, Color.WHITE, 640, 46, 14);
        btnPublicar.setAlignmentX(Component.LEFT_ALIGNMENT);

        formulario.add(crearEtiqueta(Idioma.t("Título *", "Title *")));
        formulario.add(txtTitulo);
        formulario.add(Box.createVerticalStrut(14));
        formulario.add(crearEtiqueta(Idioma.t("Imagen de portada", "Cover image")));
        formulario.add(btnAgregarPortada);
        formulario.add(Box.createVerticalStrut(14));
        formulario.add(filaDatos);
        formulario.add(Box.createVerticalStrut(12));
        formulario.add(crearEtiqueta(Idioma.t("Contenido *", "Content *")));
        formulario.add(scrollContenido);
        formulario.add(Box.createVerticalStrut(18));
        formulario.add(btnPublicar);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(Estilos.FONDO);
        centro.setBorder(new EmptyBorder(18, 0, 14, 0));
        formulario.setAlignmentX(Component.CENTER_ALIGNMENT);
        centro.add(formulario);

        JScrollPane scroll = new JScrollPane(centro);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
        add(Estilos.crearBarraInferior(2, botonesNav), BorderLayout.SOUTH);
    }

    private void configurarCampo(JTextField campo)
    {
        campo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Estilos.BORDE), new EmptyBorder(6, 10, 6, 10)));
        campo.setMaximumSize(new Dimension(640, 40));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JLabel crearEtiqueta(String texto)
    {
        JLabel lbl = Estilos.crearEtiquetaCampo(texto);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    //Muestra la foto elegida dentro del botón de portada
    public void mostrarPortada(String ruta)
    {
        ImageIcon icono = new ImageIcon(ruta);
        Image escalada = icono.getImage().getScaledInstance(-1, 138, Image.SCALE_SMOOTH);
        btnAgregarPortada.setText("");
        btnAgregarPortada.setIcon(new ImageIcon(escalada));
    }

    //Deja el formulario vacío y pone como autor el nombre del perfil
    public void limpiar(String autor)
    {
        txtTitulo.setText("");
        txtContenido.setText("");
        txtAutor.setText(autor);
        cmbCategoria.setSelectedIndex(0);
        btnAgregarPortada.setIcon(null);
        btnAgregarPortada.setText(textoPortada);
    }

    public JTextField getTxtTitulo()
    {
        return txtTitulo;
    }

    public JTextField getTxtAutor()
    {
        return txtAutor;
    }

    public JComboBox<Categoria> getCmbCategoria()
    {
        return cmbCategoria;
    }

    public JTextArea getTxtContenido()
    {
        return txtContenido;
    }

    public JButton getBtnAgregarPortada()
    {
        return btnAgregarPortada;
    }

    public JButton getBtnPublicar()
    {
        return btnPublicar;
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
