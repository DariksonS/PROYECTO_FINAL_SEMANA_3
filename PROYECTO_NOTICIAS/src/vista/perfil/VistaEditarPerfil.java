package vista.perfil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import modelo.Perfil;
import vista.Estilos;
import vista.Idioma;

//Pantalla para editar el nombre, usuario, correo y foto del perfil
public class VistaEditarPerfil extends JFrame
{
    private static final long serialVersionUID = 1L;

    private final JButton btnAtras = Estilos.crearBotonRedondo("", Estilos.CELESTE_ATRAS, Estilos.AZUL_BOTON, 115, 38, 38);
    private final JPanel contenedorAvatar = new JPanel(new BorderLayout());
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtUsuario = new JTextField();
    private final JTextField txtCorreo = new JTextField();
    private final JButton btnCambiarFoto;
    private final JButton btnGuardar;

    public VistaEditarPerfil()
    {
        setTitle("NotiRed - " + Idioma.t("Editar perfil", "Edit profile"));
        setSize(920, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Estilos.FONDO);
        setLayout(new BorderLayout());

        add(Estilos.crearEncabezadoAtras(Idioma.t("Editar perfil", "Edit profile"), Idioma.t("Actualiza tus datos y tu foto. Se guardan en la base de datos", "Update your details and photo. They are saved in the database"), btnAtras), BorderLayout.NORTH);

        JPanel tarjeta = Estilos.crearTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(20, 34, 24, 40));
        tarjeta.setMaximumSize(new Dimension(620, 560));
        tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);

        contenedorAvatar.setOpaque(false);
        contenedorAvatar.setPreferredSize(new Dimension(110, 110));

        btnCambiarFoto = Estilos.crearBotonRedondo(Idioma.t("Cambiar foto de perfil", "Change profile photo"), Estilos.CELESTE_ATRAS, Estilos.AZUL_BOTON, 210, 36, 36);

        //Fila con la foto a la izquierda y el botón para cambiarla a la derecha
        JPanel textosFoto = new JPanel();
        textosFoto.setLayout(new BoxLayout(textosFoto, BoxLayout.Y_AXIS));
        textosFoto.setOpaque(false);
        textosFoto.setBorder(new EmptyBorder(22, 0, 0, 0));
        JLabel lblFoto = Estilos.crearEtiquetaCampo(Idioma.t("Foto de perfil", "Profile photo"));
        JLabel lblAyudaFoto = new JLabel(Idioma.t("Formatos permitidos: JPG, PNG o GIF", "Allowed formats: JPG, PNG or GIF"));
        lblAyudaFoto.setFont(Estilos.fuente(Font.PLAIN, 12));
        lblAyudaFoto.setForeground(Estilos.TEXTO_SUAVE);
        lblAyudaFoto.setBorder(new EmptyBorder(0, 4, 8, 20));
        textosFoto.add(lblFoto);
        textosFoto.add(lblAyudaFoto);
        textosFoto.add(btnCambiarFoto);

        JPanel filaFoto = new JPanel(new BorderLayout(18, 0));
        filaFoto.setOpaque(false);
        filaFoto.setMaximumSize(new Dimension(540, 116));
        filaFoto.setAlignmentX(Component.LEFT_ALIGNMENT);
        filaFoto.add(contenedorAvatar, BorderLayout.WEST);
        filaFoto.add(textosFoto, BorderLayout.CENTER);

        btnGuardar = Estilos.crearBotonRedondo(Idioma.t("Guardar cambios", "Save changes"), Estilos.AZUL_BOTON, Color.WHITE, 540, 44, 14);
        btnGuardar.setAlignmentX(Component.LEFT_ALIGNMENT);

        tarjeta.add(filaFoto);
        tarjeta.add(Box.createVerticalStrut(16));
        agregarCampo(tarjeta, Idioma.t("Nombre completo", "Full name"), Idioma.t("Letras y espacios (también guion o apóstrofe), de 3 a 40 caracteres", "Letters and spaces (also hyphen or apostrophe), 3 to 40 characters"), txtNombre);
        agregarCampo(tarjeta, Idioma.t("Nombre de usuario", "Username"), Idioma.t("Letras, números, punto o guion bajo, sin espacios (3 a 20)", "Letters, numbers, dot or underscore, no spaces (3 to 20)"), txtUsuario);
        agregarCampo(tarjeta, Idioma.t("Correo electrónico", "Email"), Idioma.t("Gmail, Hotmail o UCSM. Ejemplo: nombre@gmail.com o nombre@ucsm.edu.pe", "Gmail, Hotmail or UCSM. Example: name@gmail.com or name@ucsm.edu.pe"), txtCorreo);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(btnGuardar);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(Estilos.FONDO);
        centro.setBorder(new EmptyBorder(18, 0, 18, 0));
        centro.add(tarjeta);

        JScrollPane scroll = new JScrollPane(centro);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    //Agrega una etiqueta, el campo de texto y una ayuda en gris debajo
    private void agregarCampo(JPanel panel, String etiqueta, String ayuda, JTextField campo)
    {
        JLabel lblEtiqueta = Estilos.crearEtiquetaCampo(etiqueta);
        lblEtiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setFont(Estilos.fuente(Font.PLAIN, 15));
        campo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Estilos.BORDE), new EmptyBorder(6, 10, 6, 10)));
        campo.setMaximumSize(new Dimension(540, 40));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lblAyuda = new JLabel(ayuda);
        lblAyuda.setFont(Estilos.fuente(Font.PLAIN, 12));
        lblAyuda.setForeground(Estilos.TEXTO_SUAVE);
        lblAyuda.setBorder(new EmptyBorder(3, 4, 12, 20));
        lblAyuda.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblEtiqueta);
        panel.add(campo);
        panel.add(lblAyuda);
    }

    public void cargarDatos(Perfil perfil)
    {
        txtNombre.setText(perfil.getNombre());
        txtUsuario.setText(perfil.getUsuario());
        txtCorreo.setText(perfil.getCorreo());
        mostrarFoto(perfil.getRutaFoto());
    }

    public void mostrarFoto(String ruta)
    {
        contenedorAvatar.removeAll();
        contenedorAvatar.add(Estilos.crearAvatar(110, ruta), BorderLayout.CENTER);
        contenedorAvatar.revalidate();
        contenedorAvatar.repaint();
    }

    public JButton getBtnAtras()
    {
        return btnAtras;
    }

    public JTextField getTxtNombre()
    {
        return txtNombre;
    }

    public JTextField getTxtUsuario()
    {
        return txtUsuario;
    }

    public JTextField getTxtCorreo()
    {
        return txtCorreo;
    }

    public JButton getBtnCambiarFoto()
    {
        return btnCambiarFoto;
    }

    public JButton getBtnGuardar()
    {
        return btnGuardar;
    }
}
