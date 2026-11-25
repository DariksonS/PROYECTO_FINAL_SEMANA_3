package vista;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modelo.Categoria;

//Colores, fuentes y componentes reutilizables para que todas las ventanas tengan el mismo diseño
public class Estilos
{
    public static final Color AZUL_BOTON = new Color(37, 99, 235);
    public static final Color AZUL_OSCURO = new Color(29, 78, 216);
    public static final Color CELESTE_ATRAS = new Color(219, 234, 254);
    public static final Color ROJO_PELIGRO = new Color(220, 38, 38);
    public static final Color GRIS_IMAGEN = new Color(203, 213, 225);
    public static final Color FONDO = new Color(243, 245, 249);
    public static final Color BLANCO = Color.WHITE;
    public static final Color TEXTO = new Color(17, 24, 39);
    public static final Color TEXTO_SUAVE = new Color(100, 108, 122);
    public static final Color BORDE = new Color(226, 230, 236);

    private static final String FUENTE = elegirFuente();

    //Usa Segoe UI si existe (Windows), si no la fuente por defecto
    private static String elegirFuente()
    {
        String[] disponibles = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String nombre : disponibles)
        {
            if (nombre.equals("Segoe UI"))
            {
                return "Segoe UI";
            }
        }
        return "SansSerif";
    }

    //Un texto dentro de <html> no se corta con "..." si la fuente de Windows es un poco más ancha
    public static String html(String texto)
    {
        return "<html>" + texto + "</html>";
    }

    public static Font fuente(int estilo, int tamano)
    {
        return new Font(FUENTE, estilo, tamano);
    }

    //Cada categoría tiene su propio color para la etiqueta y la imagen por defecto
    public static Color colorCategoria(Categoria categoria)
    {
        switch (categoria)
        {
            case POLITICA:
                return new Color(124, 58, 237);
            case DEPORTES:
                return new Color(22, 163, 74);
            case TECNOLOGIA:
                return new Color(37, 99, 235);
            case ECONOMIA:
                return new Color(217, 119, 6);
            default:
                return new Color(219, 39, 119);
        }
    }

    public static JButton crearBotonRedondo(String texto, Color fondo, Color textoColor, int ancho, int alto, int arco)
    {
        JButton btn = new JButton(texto)
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g)
            {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arco, arco);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setBackground(fondo);
        btn.setForeground(textoColor);
        btn.setFont(fuente(Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(ancho, alto));
        btn.setMaximumSize(new Dimension(ancho, alto));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        agregarEfectoHover(btn, fondo);
        return btn;
    }

    //Clase adaptadora: oscurece un poco el botón cuando el mouse pasa encima
    public static void agregarEfectoHover(JButton btn, Color normal)
    {
        Color encima = normal.darker();
        if (normal.equals(Color.WHITE) || normal.equals(CELESTE_ATRAS))
        {
            encima = new Color(235, 240, 248);
        }
        final Color colorEncima = encima;
        btn.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                btn.setBackground(colorEncima);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                btn.setBackground(normal);
            }
        });
    }

    public static JPanel crearCampoPildora(JComponent campoInterior, String iconoDerecha)
    {
        JPanel fila = new JPanel(new BorderLayout(10, 0))
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g)
            {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int h = getHeight() - 4;
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(2, 2, getWidth() - 4, h, h, h);
                g2.setColor(BORDE);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(2, 2, getWidth() - 4, h, h, h);
                g2.dispose();
            }
        };
        fila.setOpaque(false);
        fila.setBorder(new EmptyBorder(8, 22, 8, 22));
        fila.setMaximumSize(new Dimension(640, 52));
        fila.setPreferredSize(new Dimension(640, 50));
        campoInterior.setOpaque(false);
        campoInterior.setBorder(null);
        campoInterior.setFont(fuente(Font.PLAIN, 15));
        fila.add(campoInterior, BorderLayout.CENTER);
        return fila;
    }

    //Barra de navegación inferior con íconos dibujados y su texto debajo
    public static JPanel crearBarraInferior(int indiceActivo, JButton[] botonesOut)
    {
        JPanel nav = new JPanel(new GridLayout(1, 4));
        nav.setBackground(Color.WHITE);
        nav.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDE), new EmptyBorder(6, 40, 6, 40)));
        String[] textos = {Idioma.t("Inicio", "Home"), Idioma.t("Buscar", "Search"), Idioma.t("Publicar", "Post"), Idioma.t("Perfil", "Profile")};
        for (int i = 0; i < 4; i++)
        {
            boolean activo = i == indiceActivo;
            JButton btn = new BotonNavegacion(i, textos[i], activo);
            botonesOut[i] = btn;
            nav.add(btn);
        }
        return nav;
    }

    //Barra azul con botón para volver, título y una descripción corta
    public static JPanel crearEncabezadoAtras(String titulo, String descripcion, JButton btnAtras)
    {
        JPanel top = new JPanel(new BorderLayout(18, 0));
        top.setBackground(AZUL_BOTON);
        top.setBorder(new EmptyBorder(14, 22, 14, 22));
        btnAtras.setText(Idioma.t("←  Volver", "←  Back"));
        btnAtras.setPreferredSize(new Dimension(115, 38));
        btnAtras.setFont(fuente(Font.BOLD, 14));

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4));
        izquierda.setOpaque(false);
        izquierda.add(btnAtras);

        top.add(izquierda, BorderLayout.WEST);
        top.add(crearTextosEncabezado(titulo, descripcion, 22), BorderLayout.CENTER);
        return top;
    }

    //Barra azul superior con el nombre de la app, el título de la pantalla y su descripción
    public static JPanel crearBarraSuperior(String titulo, String descripcion, JComponent componenteDerecha)
    {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(AZUL_BOTON);
        barra.setBorder(new EmptyBorder(14, 30, 16, 30));
        barra.add(crearTextosEncabezado(titulo, descripcion, 26), BorderLayout.CENTER);

        if (componenteDerecha != null)
        {
            JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 14));
            derecha.setOpaque(false);
            derecha.add(componenteDerecha);
            barra.add(derecha, BorderLayout.EAST);
        }
        return barra;
    }

    //Marca NOTIRED pequeña, título grande y descripción, uno debajo de otro
    private static JPanel crearTextosEncabezado(String titulo, String descripcion, int tamanoTitulo)
    {
        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);

        JLabel lblMarca = new JLabel("NOTIRED");
        lblMarca.setFont(fuente(Font.BOLD, 11));
        lblMarca.setForeground(new Color(191, 219, 254));

        JLabel lblTitulo = new JLabel(html(titulo));
        lblTitulo.setFont(fuente(Font.BOLD, tamanoTitulo));
        lblTitulo.setForeground(Color.WHITE);
        //El borde derecho da espacio extra para que el texto no se corte con "..."
        lblTitulo.setBorder(new EmptyBorder(0, 0, 0, 40));

        JLabel lblDescripcion = new JLabel(html(descripcion));
        lblDescripcion.setFont(fuente(Font.PLAIN, 13));
        lblDescripcion.setForeground(new Color(219, 234, 254));
        lblDescripcion.setBorder(new EmptyBorder(2, 0, 0, 40));

        textos.add(lblMarca);
        textos.add(lblTitulo);
        textos.add(lblDescripcion);
        return textos;
    }

    //Panel blanco con esquinas redondeadas y sombra suave
    public static JPanel crearTarjeta()
    {
        JPanel tarjeta = new JPanel()
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g)
            {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 16));
                g2.fillRoundRect(3, 5, getWidth() - 6, getHeight() - 6, 24, 24);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 6, getHeight() - 8, 24, 24);
                g2.dispose();
            }
        };
        tarjeta.setOpaque(false);
        return tarjeta;
    }

    //Opción del menú de perfil: tarjeta con ícono, título, descripción y flecha
    public static JButton crearOpcionMenu(String titulo, String descripcion, Color colorIcono, String letraIcono)
    {
        JButton btn = new JButton()
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g)
            {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
                g2.setColor(BORDE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);

                int centroY = getHeight() / 2;
                g2.setColor(new Color(colorIcono.getRed(), colorIcono.getGreen(), colorIcono.getBlue(), 35));
                g2.fillOval(20, centroY - 22, 44, 44);
                g2.setColor(colorIcono);
                g2.setFont(fuente(Font.BOLD, 18));
                int anchoLetra = g2.getFontMetrics().stringWidth(letraIcono);
                g2.drawString(letraIcono, 42 - anchoLetra / 2, centroY + 7);

                g2.setColor(TEXTO);
                g2.setFont(fuente(Font.BOLD, 17));
                g2.drawString(titulo, 82, centroY - 4);
                g2.setColor(TEXTO_SUAVE);
                g2.setFont(fuente(Font.PLAIN, 13));
                g2.drawString(descripcion, 82, centroY + 17);

                g2.setColor(TEXTO_SUAVE);
                g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int x = getWidth() - 30;
                g2.drawLine(x, centroY - 6, x + 6, centroY);
                g2.drawLine(x + 6, centroY, x, centroY + 6);
                g2.dispose();
            }
        };
        btn.setBackground(Color.WHITE);
        btn.setPreferredSize(new Dimension(720, 80));
        btn.setMaximumSize(new Dimension(720, 80));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(0.5f);
        agregarEfectoHover(btn, Color.WHITE);
        return btn;
    }

    //Título de una sección con su descripción debajo
    public static JPanel crearTituloSeccion(String titulo, String descripcion)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        JLabel lblTitulo = new JLabel(html(titulo));
        lblTitulo.setFont(fuente(Font.BOLD, 22));
        lblTitulo.setForeground(TEXTO);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 0, 30));
        JLabel lblDescripcion = new JLabel(html(descripcion));
        lblDescripcion.setFont(fuente(Font.PLAIN, 13));
        lblDescripcion.setForeground(TEXTO_SUAVE);
        lblDescripcion.setBorder(new EmptyBorder(2, 0, 0, 30));
        panel.add(lblTitulo);
        panel.add(lblDescripcion);
        return panel;
    }

    //Hace que la lista de categorías se muestre en el idioma actual
    public static void traducirCombo(JComboBox<Categoria> combo)
    {
        combo.setRenderer(new DefaultListCellRenderer()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> lista, Object valor, int indice, boolean seleccionado, boolean foco)
            {
                super.getListCellRendererComponent(lista, valor, indice, seleccionado, foco);
                if (valor instanceof Categoria)
                {
                    setText(Idioma.categoria((Categoria) valor));
                }
                return this;
            }
        });
    }

    //Botón tipo "chip" para filtrar por categoría
    public static JButton crearChip(String texto)
    {
        JButton chip = new JButton(texto)
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g)
            {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
                if (getBackground().equals(Color.WHITE))
                {
                    g2.setColor(BORDE);
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        chip.setFont(fuente(Font.BOLD, 12));
        chip.setContentAreaFilled(false);
        chip.setBorderPainted(false);
        chip.setFocusPainted(false);
        chip.setMargin(new Insets(5, 14, 5, 14));
        chip.setCursor(new Cursor(Cursor.HAND_CURSOR));
        marcarChip(chip, false);
        return chip;
    }

    public static void marcarChip(JButton chip, boolean activo)
    {
        if (activo)
        {
            chip.setBackground(AZUL_BOTON);
            chip.setForeground(Color.WHITE);
        }
        else
        {
            chip.setBackground(Color.WHITE);
            chip.setForeground(TEXTO);
        }
    }

    public static JLabel crearEtiquetaCampo(String texto)
    {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(fuente(Font.BOLD, 13));
        lbl.setForeground(TEXTO_SUAVE);
        lbl.setBorder(new EmptyBorder(0, 4, 4, 20));
        return lbl;
    }

    //Etiqueta de la sección de la noticia, como en los periódicos: fondo de color y texto blanco
    public static JLabel crearEtiquetaCategoria(Categoria categoria)
    {
        JLabel etiqueta = new JLabel(Idioma.categoria(categoria));
        etiqueta.setOpaque(true);
        etiqueta.setBackground(colorCategoria(categoria));
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setFont(fuente(Font.BOLD, 11));
        etiqueta.setBorder(new EmptyBorder(2, 7, 2, 7));
        return etiqueta;
    }

    //Panel que dibuja la foto recortada con esquinas redondeadas, o un degradado con la categoría
    public static JPanel crearPanelImagen(String ruta, Categoria categoria, int ancho, int alto)
    {
        Image imagen = null;
        if (ruta != null && new File(ruta).exists())
        {
            imagen = new ImageIcon(ruta).getImage();
        }
        final Image foto = imagen;
        final Color color = colorCategoria(categoria);
        JPanel panel = new JPanel()
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g)
            {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                int w = getWidth();
                int h = getHeight();
                g2.clip(new RoundRectangle2D.Float(0, 0, w, h, 22, 22));
                if (foto != null && foto.getWidth(null) > 0)
                {
                    //Escala la foto para cubrir todo el espacio sin deformarla
                    double escala = Math.max((double) w / foto.getWidth(null), (double) h / foto.getHeight(null));
                    int fw = (int) (foto.getWidth(null) * escala);
                    int fh = (int) (foto.getHeight(null) * escala);
                    g2.drawImage(foto, (w - fw) / 2, (h - fh) / 2, fw, fh, null);
                }
                else
                {
                    g2.setPaint(new GradientPaint(0, 0, color, w, h, color.darker().darker()));
                    g2.fillRect(0, 0, w, h);
                    g2.setColor(new Color(255, 255, 255, 220));
                    g2.setFont(fuente(Font.BOLD, 18));
                    String texto = Idioma.categoria(categoria);
                    int tx = (w - g2.getFontMetrics().stringWidth(texto)) / 2;
                    g2.drawString(texto, tx, h / 2 + 6);
                }
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(ancho, alto));
        panel.setMinimumSize(new Dimension(ancho, alto));
        panel.setMaximumSize(new Dimension(ancho, alto));
        return panel;
    }

    //Avatar circular: muestra la foto de perfil si existe, si no un ícono de persona
    public static JPanel crearAvatar(int diametro, String rutaFoto)
    {
        Image imagen = null;
        if (rutaFoto != null && new File(rutaFoto).exists())
        {
            imagen = new ImageIcon(rutaFoto).getImage();
        }
        final Image foto = imagen;
        JPanel avatar = new JPanel()
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                int d = Math.min(getWidth(), getHeight()) - 6;
                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, d + 6, d + 6);
                g2.clip(new java.awt.geom.Ellipse2D.Float(3, 3, d, d));
                if (foto != null && foto.getWidth(null) > 0)
                {
                    double escala = Math.max((double) d / foto.getWidth(null), (double) d / foto.getHeight(null));
                    int fw = (int) (foto.getWidth(null) * escala);
                    int fh = (int) (foto.getHeight(null) * escala);
                    g2.drawImage(foto, 3 + (d - fw) / 2, 3 + (d - fh) / 2, fw, fh, null);
                }
                else
                {
                    g2.setPaint(new GradientPaint(0, 0, AZUL_BOTON, d, d, new Color(99, 102, 241)));
                    g2.fillOval(3, 3, d, d);
                    g2.setColor(new Color(235, 238, 242));
                    int cabeza = d / 3;
                    g2.fillOval(3 + (d - cabeza) / 2, 3 + d / 6, cabeza, cabeza);
                    g2.fillArc(3 + d / 6, 3 + d / 2 + 4, d * 2 / 3, d * 2 / 3, 0, 180);
                }
                g2.dispose();
            }
        };
        avatar.setOpaque(false);
        avatar.setPreferredSize(new Dimension(diametro, diametro));
        avatar.setMaximumSize(new Dimension(diametro, diametro));
        avatar.setAlignmentX(0.5f);
        return avatar;
    }

    //Botón de la barra inferior: dibuja su ícono con líneas para que se vea igual en cualquier PC
    private static class BotonNavegacion extends JButton
    {
        private static final long serialVersionUID = 1L;
        private int tipo;
        private boolean activo;

        public BotonNavegacion(int tipo, String texto, boolean activo)
        {
            super(texto);
            this.tipo = tipo;
            this.activo = activo;
            setVerticalAlignment(SwingConstants.BOTTOM);
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(fuente(Font.BOLD, 12));
            if (activo)
            {
                setForeground(AZUL_BOTON);
            }
            else
            {
                setForeground(TEXTO_SUAVE);
            }
            setPreferredSize(new Dimension(80, 52));
            setMargin(new Insets(0, 0, 0, 0));
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int cx = getWidth() / 2;
            int cy = 16;
            if (activo)
            {
                g2.setColor(CELESTE_ATRAS);
                g2.fillRoundRect(cx - 26, cy - 13, 52, 26, 26, 26);
            }
            if (activo)
            {
                g2.setColor(AZUL_BOTON);
            }
            else
            {
                g2.setColor(TEXTO_SUAVE);
            }
            g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            if (tipo == 0)
            {
                int[] xs = {cx - 9, cx, cx + 9};
                int[] ys = {cy - 1, cy - 9, cy - 1};
                g2.drawPolyline(xs, ys, 3);
                g2.drawRect(cx - 6, cy - 1, 12, 9);
            }
            else if (tipo == 1)
            {
                g2.drawOval(cx - 8, cy - 9, 13, 13);
                g2.drawLine(cx + 3, cy + 2, cx + 8, cy + 7);
            }
            else if (tipo == 2)
            {
                g2.drawLine(cx, cy - 8, cx, cy + 8);
                g2.drawLine(cx - 8, cy, cx + 8, cy);
            }
            else
            {
                g2.drawOval(cx - 5, cy - 10, 10, 10);
                g2.drawArc(cx - 9, cy + 1, 18, 14, 0, 180);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
