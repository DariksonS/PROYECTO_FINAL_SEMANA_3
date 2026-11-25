package vista.perfil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import vista.Estilos;
import vista.Idioma;

//Pantalla para elegir el idioma de la app
public class VistaIdioma extends JFrame
{
    private static final long serialVersionUID = 1L;

    private final JButton btnAtras = Estilos.crearBotonRedondo("", Estilos.CELESTE_ATRAS, Estilos.AZUL_BOTON, 115, 38, 38);
    private final JRadioButton rbEspanol = new JRadioButton("Español");
    private final JRadioButton rbIngles = new JRadioButton("English");
    private final JButton btnAplicar;

    public VistaIdioma()
    {
        setTitle("NotiRed - " + Idioma.t("Idioma", "Language"));
        setSize(920, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Estilos.FONDO);
        setLayout(new BorderLayout());

        add(Estilos.crearEncabezadoAtras(Idioma.t("Idioma", "Language"), Idioma.t("Elige en qué idioma quieres ver la aplicación", "Choose the language you want to use in the app"), btnAtras), BorderLayout.NORTH);

        JPanel tarjeta = Estilos.crearTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(20, 34, 24, 40));
        tarjeta.setMaximumSize(new Dimension(620, 380));
        tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbEspanol);
        grupo.add(rbIngles);

        btnAplicar = Estilos.crearBotonRedondo(Idioma.t("Aplicar idioma", "Apply language"), Estilos.AZUL_BOTON, Color.WHITE, 540, 44, 14);
        btnAplicar.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblNota = new JLabel(Idioma.t("<html>Se traducen los menús, botones, categorías y fechas.<br>El texto de cada noticia se muestra como lo escribió su autor.</html>", "<html>Menus, buttons, categories and dates are translated.<br>Each story's text is shown as its author wrote it.</html>"));
        lblNota.setFont(Estilos.fuente(Font.PLAIN, 12));
        lblNota.setForeground(Estilos.TEXTO_SUAVE);
        lblNota.setAlignmentX(Component.LEFT_ALIGNMENT);

        tarjeta.add(Estilos.crearTituloSeccion(Idioma.t("Idioma de la aplicación", "App language"), Idioma.t("El cambio se guarda y se mantiene al volver a abrir la app", "The change is saved and kept when you open the app again")));
        tarjeta.add(Box.createVerticalStrut(14));
        tarjeta.add(crearOpcion(rbEspanol, "La aplicación se mostrará en español"));
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(crearOpcion(rbIngles, "The app will be shown in English"));
        tarjeta.add(Box.createVerticalStrut(16));
        tarjeta.add(btnAplicar);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(lblNota);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(Estilos.FONDO);
        centro.setBorder(new EmptyBorder(22, 0, 18, 0));
        centro.add(tarjeta);
        add(centro, BorderLayout.CENTER);

        marcar(Idioma.esIngles());
    }

    //Fila con el botón de opción y una descripción debajo
    private JPanel crearOpcion(JRadioButton boton, String descripcion)
    {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(new Color(248, 250, 252));
        fila.setBorder(new EmptyBorder(10, 14, 10, 14));
        fila.setMaximumSize(new Dimension(540, 64));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        boton.setFont(Estilos.fuente(Font.BOLD, 17));
        boton.setOpaque(false);
        boton.setFocusPainted(false);
        JLabel lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setFont(Estilos.fuente(Font.PLAIN, 12));
        lblDescripcion.setForeground(Estilos.TEXTO_SUAVE);
        lblDescripcion.setBorder(new EmptyBorder(0, 26, 0, 0));
        fila.add(boton, BorderLayout.NORTH);
        fila.add(lblDescripcion, BorderLayout.CENTER);
        return fila;
    }

    public void marcar(boolean ingles)
    {
        rbIngles.setSelected(ingles);
        rbEspanol.setSelected(!ingles);
    }

    public boolean eligioIngles()
    {
        return rbIngles.isSelected();
    }

    public JButton getBtnAtras()
    {
        return btnAtras;
    }

    public JButton getBtnAplicar()
    {
        return btnAplicar;
    }
}
