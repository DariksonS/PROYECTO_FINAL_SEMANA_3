package vista.perfil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import vista.Estilos;
import vista.Idioma;

//Pantalla para cambiar la contraseña pidiendo primero la actual
public class VistaCambiarPass extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;

    private final JButton btnAtras = Estilos.crearBotonRedondo("", Estilos.CELESTE_ATRAS, Estilos.AZUL_BOTON, 115, 38, 38);
    private final JPasswordField txtActual = new JPasswordField();
    private final JPasswordField txtNueva = new JPasswordField();
    private final JPasswordField txtConfirmar = new JPasswordField();
    private final JCheckBox chkMostrar = new JCheckBox();
    private final JButton btnGuardarPass;

    public VistaCambiarPass()
    {
        setTitle("NotiRed - " + Idioma.t("Contraseña", "Password"));
        setSize(920, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Estilos.FONDO);
        setLayout(new BorderLayout());

        add(Estilos.crearEncabezadoAtras(Idioma.t("Cambiar contraseña", "Change password"), Idioma.t("Por seguridad primero escribe tu contraseña actual", "For security, first type your current password"), btnAtras), BorderLayout.NORTH);

        JPanel tarjeta = Estilos.crearTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(20, 34, 24, 40));
        tarjeta.setMaximumSize(new Dimension(620, 420));
        tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);

        tarjeta.add(Estilos.crearTituloSeccion(Idioma.t("Nueva contraseña", "New password"), Idioma.t("Mínimo 6 caracteres, sin espacios y diferente a la actual", "At least 6 characters, no spaces and different from the current one")));
        tarjeta.add(Box.createVerticalStrut(14));
        agregarCampo(tarjeta, Idioma.t("Contraseña actual", "Current password"), txtActual);
        agregarCampo(tarjeta, Idioma.t("Nueva contraseña", "New password"), txtNueva);
        agregarCampo(tarjeta, Idioma.t("Repite la nueva contraseña", "Repeat the new password"), txtConfirmar);

        chkMostrar.setText(Idioma.t("Mostrar contraseñas", "Show passwords"));
        chkMostrar.setFont(Estilos.fuente(Font.PLAIN, 13));
        chkMostrar.setOpaque(false);
        chkMostrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        chkMostrar.addActionListener(this);

        btnGuardarPass = Estilos.crearBotonRedondo(Idioma.t("Guardar contraseña", "Save password"), Estilos.AZUL_BOTON, Color.WHITE, 540, 44, 14);
        btnGuardarPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        tarjeta.add(chkMostrar);
        tarjeta.add(Box.createVerticalStrut(14));
        tarjeta.add(btnGuardarPass);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(Estilos.FONDO);
        centro.setBorder(new EmptyBorder(22, 0, 18, 0));
        centro.add(tarjeta);
        add(centro, BorderLayout.CENTER);
    }

    private void agregarCampo(JPanel panel, String etiqueta, JPasswordField campo)
    {
        JLabel lbl = Estilos.crearEtiquetaCampo(etiqueta);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setFont(Estilos.fuente(Font.PLAIN, 15));
        campo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Estilos.BORDE), new EmptyBorder(6, 10, 6, 10)));
        campo.setMaximumSize(new Dimension(540, 40));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lbl);
        panel.add(campo);
        panel.add(Box.createVerticalStrut(12));
    }

    //Muestra u oculta las letras de las contraseñas
    @Override
    public void actionPerformed(ActionEvent e)
    {
        char caracter = '*';
        if (chkMostrar.isSelected())
        {
            caracter = (char) 0;
        }
        txtActual.setEchoChar(caracter);
        txtNueva.setEchoChar(caracter);
        txtConfirmar.setEchoChar(caracter);
    }

    public void limpiarCampos()
    {
        txtActual.setText("");
        txtNueva.setText("");
        txtConfirmar.setText("");
    }

    public String getClaveActual()
    {
        return new String(txtActual.getPassword());
    }

    public String getClaveNueva()
    {
        return new String(txtNueva.getPassword());
    }

    public String getClaveConfirmacion()
    {
        return new String(txtConfirmar.getPassword());
    }

    public JButton getBtnAtras()
    {
        return btnAtras;
    }

    public JButton getBtnGuardarPass()
    {
        return btnGuardarPass;
    }
}
