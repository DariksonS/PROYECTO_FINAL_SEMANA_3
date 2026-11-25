package vista.perfil;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import vista.Estilos;
import vista.Idioma;
import vista.noticias.TarjetaNoticia;

//Pantalla con las noticias que el usuario guardó para leer después
public class VistaGuardados extends JFrame
{
    private static final long serialVersionUID = 1L;

    private final JButton btnAtras = Estilos.crearBotonRedondo("", Estilos.CELESTE_ATRAS, Estilos.AZUL_BOTON, 115, 38, 38);
    private final JPanel contenedorGuardados = new JPanel();

    public VistaGuardados()
    {
        setTitle("NotiRed - " + Idioma.t("Guardadas", "Saved"));
        setSize(920, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Estilos.FONDO);
        setLayout(new BorderLayout());

        add(Estilos.crearEncabezadoAtras(Idioma.t("Noticias guardadas", "Saved stories"), Idioma.t("Tu lista de lectura: las noticias que guardaste para leer después", "Your reading list: the stories you saved to read later"), btnAtras), BorderLayout.NORTH);

        contenedorGuardados.setLayout(new BoxLayout(contenedorGuardados, BoxLayout.Y_AXIS));
        contenedorGuardados.setBackground(Estilos.FONDO);
        contenedorGuardados.setBorder(new EmptyBorder(18, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(contenedorGuardados);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Estilos.FONDO);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    public void mostrarTarjetas(List<TarjetaNoticia> tarjetas)
    {
        contenedorGuardados.removeAll();
        if (tarjetas.isEmpty())
        {
            agregarMensaje(Idioma.t("Aún no tienes noticias guardadas", "You don't have saved stories yet"), 17);
            agregarMensaje(Idioma.t("Abre una noticia y presiona \"Guardar para después\" para verla aquí", "Open a story and press \"Save for later\" to see it here"), 14);
        }
        for (TarjetaNoticia tarjeta : tarjetas)
        {
            contenedorGuardados.add(tarjeta);
            contenedorGuardados.add(Box.createVerticalStrut(14));
        }
        contenedorGuardados.revalidate();
        contenedorGuardados.repaint();
    }

    private void agregarMensaje(String texto, int tamano)
    {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(Estilos.fuente(Font.BOLD, tamano));
        lbl.setForeground(Estilos.TEXTO_SUAVE);
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(20, 0, 0, 0));
        contenedorGuardados.add(lbl);
    }

    public JButton getBtnAtras()
    {
        return btnAtras;
    }
}
