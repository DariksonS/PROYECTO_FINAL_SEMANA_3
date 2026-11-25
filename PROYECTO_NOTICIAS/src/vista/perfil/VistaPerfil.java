package vista.perfil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modelo.Perfil;
import vista.Estilos;
import vista.Idioma;

//Pantalla del perfil del usuario con sus datos, estadísticas y menú de opciones
public class VistaPerfil extends JFrame
{
    private static final long serialVersionUID = 1L;

    private final JPanel contenedorAvatar = new JPanel(new BorderLayout());
    private final JLabel lblNombre = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblUsuario = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblPublicadas = new JLabel("0", SwingConstants.CENTER);
    private final JLabel lblGuardadas = new JLabel("0", SwingConstants.CENTER);
    private final JButton btnEditarPerfil;
    private final JButton btnCuenta;
    private final JButton btnGuardado;
    private final JButton btnIdioma;
    private final JButton btnCerrarSesion;
    private final JButton[] botonesNav = new JButton[4];

    public VistaPerfil()
    {
        setTitle("NotiRed - " + Idioma.t("Perfil", "Profile"));
        setSize(920, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Estilos.FONDO);
        setLayout(new BorderLayout());

        add(Estilos.crearBarraSuperior(Idioma.t("Mi perfil", "My profile"), Idioma.t("Tus datos, tu actividad y la configuración de tu cuenta", "Your details, your activity and your account settings"), null), BorderLayout.NORTH);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(Estilos.FONDO);
        centro.setBorder(new EmptyBorder(18, 30, 16, 30));

        //Tarjeta con la foto, el nombre y las estadísticas (solo se muestran, no se editan aquí)
        JPanel tarjeta = Estilos.crearTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(18, 20, 20, 26));
        tarjeta.setMaximumSize(new Dimension(726, 250));
        tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);

        contenedorAvatar.setOpaque(false);
        contenedorAvatar.setMaximumSize(new Dimension(96, 96));
        contenedorAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblNombre.setFont(Estilos.fuente(Font.BOLD, 20));
        lblNombre.setForeground(Estilos.TEXTO);
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblUsuario.setFont(Estilos.fuente(Font.PLAIN, 13));
        lblUsuario.setForeground(Estilos.TEXTO_SUAVE);
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel estadisticas = new JPanel(new GridLayout(2, 2, 0, 0));
        estadisticas.setOpaque(false);
        estadisticas.setMaximumSize(new Dimension(360, 50));
        estadisticas.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPublicadas.setFont(Estilos.fuente(Font.BOLD, 18));
        lblPublicadas.setForeground(Estilos.AZUL_BOTON);
        lblGuardadas.setFont(Estilos.fuente(Font.BOLD, 18));
        lblGuardadas.setForeground(Estilos.AZUL_BOTON);
        estadisticas.add(lblPublicadas);
        estadisticas.add(lblGuardadas);
        estadisticas.add(crearTextoEstadistica(Idioma.t("Noticias publicadas", "Stories posted")));
        estadisticas.add(crearTextoEstadistica(Idioma.t("Noticias guardadas", "Saved stories")));

        tarjeta.add(contenedorAvatar);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(lblNombre);
        tarjeta.add(lblUsuario);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(estadisticas);

        btnEditarPerfil = Estilos.crearOpcionMenu(Idioma.t("Editar perfil", "Edit profile"), Idioma.t("Cambia tu nombre, tu nombre de usuario, tu correo y tu foto de perfil", "Change your name, username, email and profile photo"), Estilos.AZUL_BOTON, "E");
        btnCuenta = Estilos.crearOpcionMenu(Idioma.t("Cuenta y seguridad", "Account and security"), Idioma.t("Revisa los datos de tu cuenta, cambia tu contraseña o restablece tu cuenta", "Check your account details, change your password or reset your account"), new Color(22, 163, 74), "C");
        btnGuardado = Estilos.crearOpcionMenu(Idioma.t("Noticias guardadas", "Saved stories"), Idioma.t("Tu lista de lectura con las noticias que guardaste para leer después", "Your reading list with the stories you saved to read later"), new Color(217, 119, 6), "G");
        btnIdioma = Estilos.crearOpcionMenu(Idioma.t("Idioma", "Language"), Idioma.t("Elige el idioma de la aplicación  ·  Actual: Español", "Choose the app language  ·  Current: English"), new Color(124, 58, 237), "A");

        btnCerrarSesion = Estilos.crearBotonRedondo(Idioma.t("Cerrar sesión", "Log out"), Estilos.ROJO_PELIGRO, Color.WHITE, 180, 42, 42);
        btnCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);

        centro.add(tarjeta);
        centro.add(Box.createVerticalStrut(14));
        centro.add(btnEditarPerfil);
        centro.add(Box.createVerticalStrut(8));
        centro.add(btnCuenta);
        centro.add(Box.createVerticalStrut(8));
        centro.add(btnGuardado);
        centro.add(Box.createVerticalStrut(8));
        centro.add(btnIdioma);
        centro.add(Box.createVerticalStrut(16));
        centro.add(btnCerrarSesion);

        JScrollPane scroll = new JScrollPane(centro);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
        add(Estilos.crearBarraInferior(3, botonesNav), BorderLayout.SOUTH);
    }

    private JLabel crearTextoEstadistica(String texto)
    {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(Estilos.fuente(Font.PLAIN, 12));
        lbl.setForeground(Estilos.TEXTO_SUAVE);
        return lbl;
    }

    public void cargarPerfil(Perfil perfil, int publicadas, int guardadas)
    {
        contenedorAvatar.removeAll();
        contenedorAvatar.add(Estilos.crearAvatar(96, perfil.getRutaFoto()), BorderLayout.CENTER);
        lblNombre.setText(perfil.getNombre());
        lblUsuario.setText("@" + perfil.getUsuario() + "   ·   " + perfil.getCorreo());
        lblPublicadas.setText(String.valueOf(publicadas));
        lblGuardadas.setText(String.valueOf(guardadas));
        revalidate();
        repaint();
    }

    public JButton getBtnEditarPerfil()
    {
        return btnEditarPerfil;
    }

    public JButton getBtnCuenta()
    {
        return btnCuenta;
    }

    public JButton getBtnGuardado()
    {
        return btnGuardado;
    }

    public JButton getBtnIdioma()
    {
        return btnIdioma;
    }

    public JButton getBtnCerrarSesion()
    {
        return btnCerrarSesion;
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
