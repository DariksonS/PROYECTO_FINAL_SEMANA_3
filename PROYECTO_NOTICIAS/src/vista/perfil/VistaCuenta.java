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
import javax.swing.border.EmptyBorder;

import modelo.Perfil;
import vista.Estilos;
import vista.Idioma;

//Pantalla con los datos de la cuenta y las opciones de seguridad
public class VistaCuenta extends JFrame
{
    private static final long serialVersionUID = 1L;

    private final JButton btnAtras = Estilos.crearBotonRedondo("", Estilos.CELESTE_ATRAS, Estilos.AZUL_BOTON, 115, 38, 38);
    private final JLabel lblCorreo = new JLabel();
    private final JLabel lblUsuario = new JLabel();
    private final JLabel lblClave = new JLabel();
    private final JButton btnCambiarClave;
    private final JButton btnEliminarCuenta;

    public VistaCuenta()
    {
        setTitle("NotiRed - " + Idioma.t("Cuenta", "Account"));
        setSize(920, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Estilos.FONDO);
        setLayout(new BorderLayout());

        add(Estilos.crearEncabezadoAtras(Idioma.t("Cuenta y seguridad", "Account and security"), Idioma.t("Revisa los datos de tu cuenta y mantén segura tu contraseña", "Check your account details and keep your password safe"), btnAtras), BorderLayout.NORTH);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(Estilos.FONDO);
        centro.setBorder(new EmptyBorder(22, 0, 18, 0));

        JPanel datos = Estilos.crearTarjeta();
        datos.setLayout(new BoxLayout(datos, BoxLayout.Y_AXIS));
        datos.setBorder(new EmptyBorder(18, 28, 22, 34));
        datos.setMaximumSize(new Dimension(620, 260));
        datos.setAlignmentX(Component.CENTER_ALIGNMENT);
        datos.add(Estilos.crearTituloSeccion(Idioma.t("Datos de la cuenta", "Account details"), Idioma.t("Para cambiarlos ve a Editar perfil", "To change them go to Edit profile")));
        datos.add(Box.createVerticalStrut(10));
        datos.add(crearFila(Idioma.t("Correo electrónico", "Email"), lblCorreo));
        datos.add(crearFila(Idioma.t("Usuario", "Username"), lblUsuario));
        datos.add(crearFila(Idioma.t("Contraseña", "Password"), lblClave));

        JPanel seguridad = Estilos.crearTarjeta();
        seguridad.setLayout(new BoxLayout(seguridad, BoxLayout.Y_AXIS));
        seguridad.setBorder(new EmptyBorder(18, 28, 22, 34));
        seguridad.setMaximumSize(new Dimension(620, 240));
        seguridad.setAlignmentX(Component.CENTER_ALIGNMENT);
        seguridad.add(Estilos.crearTituloSeccion(Idioma.t("Seguridad", "Security"), Idioma.t("Cambia tu contraseña o restablece tu cuenta", "Change your password or reset your account")));
        seguridad.add(Box.createVerticalStrut(14));
        btnCambiarClave = Estilos.crearBotonRedondo(Idioma.t("Cambiar contraseña", "Change password"), Estilos.AZUL_BOTON, Color.WHITE, 540, 42, 14);
        btnCambiarClave.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnEliminarCuenta = Estilos.crearBotonRedondo(Idioma.t("Eliminar cuenta", "Delete account"), Estilos.ROJO_PELIGRO, Color.WHITE, 540, 42, 14);
        btnEliminarCuenta.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lblAviso = new JLabel(Idioma.t("Al eliminar la cuenta se restablece tu perfil y se borran tus noticias guardadas", "Deleting the account resets your profile and removes your saved stories"));
        lblAviso.setFont(Estilos.fuente(Font.PLAIN, 12));
        lblAviso.setForeground(Estilos.TEXTO_SUAVE);
        lblAviso.setBorder(new EmptyBorder(6, 4, 0, 20));
        lblAviso.setAlignmentX(Component.LEFT_ALIGNMENT);
        seguridad.add(btnCambiarClave);
        seguridad.add(Box.createVerticalStrut(10));
        seguridad.add(btnEliminarCuenta);
        seguridad.add(lblAviso);

        centro.add(datos);
        centro.add(Box.createVerticalStrut(14));
        centro.add(seguridad);
        add(centro, BorderLayout.CENTER);
    }

    //Fila con el nombre del dato a la izquierda y su valor a la derecha
    private JPanel crearFila(String nombre, JLabel valor)
    {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setOpaque(false);
        fila.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Estilos.BORDE), new EmptyBorder(10, 2, 10, 2)));
        fila.setMaximumSize(new Dimension(560, 44));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(Estilos.fuente(Font.PLAIN, 14));
        lblNombre.setForeground(Estilos.TEXTO_SUAVE);
        valor.setFont(Estilos.fuente(Font.BOLD, 14));
        valor.setForeground(Estilos.TEXTO);
        fila.add(lblNombre, BorderLayout.WEST);
        fila.add(valor, BorderLayout.EAST);
        return fila;
    }

    public void cargarCuenta(Perfil perfil)
    {
        lblCorreo.setText(perfil.getCorreo());
        lblUsuario.setText("@" + perfil.getUsuario());
        //La contraseña se muestra con asteriscos, uno por cada letra
        String mascara = "";
        for (int i = 0; i < perfil.getClave().length(); i++)
        {
            mascara = mascara + "*";
        }
        lblClave.setText(mascara);
    }

    public JButton getBtnAtras()
    {
        return btnAtras;
    }

    public JButton getBtnCambiarClave()
    {
        return btnCambiarClave;
    }

    public JButton getBtnEliminarCuenta()
    {
        return btnEliminarCuenta;
    }
}
