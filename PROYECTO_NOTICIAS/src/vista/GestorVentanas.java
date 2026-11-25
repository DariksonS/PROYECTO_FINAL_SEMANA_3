package vista;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

import archivos.GestorArchivos;
import controlador.NoticiaControlador;
import controlador.UsuarioControlador;
import excepciones.ArchivoException;
import excepciones.BaseDatosException;
import excepciones.DatoInvalidoException;
import excepciones.NoticiaDuplicadaException;
import modelo.Categoria;
import modelo.Noticia;
import modelo.Perfil;
import vista.noticias.TarjetaNoticia;
import vista.noticias.VistaBuscar;
import vista.noticias.VistaCrearNoticia;
import vista.noticias.VistaDetalleNoticia;
import vista.noticias.VistaPrincipal;
import vista.perfil.VistaCambiarPass;
import vista.perfil.VistaCuenta;
import vista.perfil.VistaEditarPerfil;
import vista.perfil.VistaGuardados;
import vista.perfil.VistaIdioma;
import vista.perfil.VistaPerfil;

//Conecta las ventanas de NotiRed con los controladores y maneja la navegación entre ellas
public class GestorVentanas implements ActionListener
{
    private NoticiaControlador controlador;
    private UsuarioControlador usuario;
    private JFrame ventanaActual;
    private JFrame ventanaAnteriorDetalle;
    private File portadaSeleccionada;
    private File fotoPerfilSeleccionada;
    private int categoriaSeleccionada;

    private VistaPrincipal vPrincipal;
    private VistaBuscar vBuscar;
    private VistaCrearNoticia vCrear;
    private VistaDetalleNoticia vDetalle;
    private VistaPerfil vPerfil;
    private VistaEditarPerfil vEditarPerfil;
    private VistaCuenta vCuenta;
    private VistaCambiarPass vCambiarPass;
    private VistaGuardados vGuardados;
    private VistaIdioma vIdioma;

    private List<JButton> botonesInicio = new ArrayList<>();
    private List<JButton> botonesBuscar = new ArrayList<>();
    private List<JButton> botonesAgregar = new ArrayList<>();
    private List<JButton> botonesPerfil = new ArrayList<>();
    private List<TarjetaNoticia> tarjetasActuales = new ArrayList<>();

    public GestorVentanas(NoticiaControlador controlador, UsuarioControlador usuario)
    {
        this.controlador = controlador;
        this.usuario = usuario;
        Idioma.setIngles(usuario.obtenerPerfil().getIdioma().equals("en"));
        crearVentanas();
    }

    public void iniciar()
    {
        cargarInicio();
        irA(vPrincipal);
    }

    //Crea todas las ventanas con el idioma actual y conecta sus botones
    private void crearVentanas()
    {
        botonesInicio.clear();
        botonesBuscar.clear();
        botonesAgregar.clear();
        botonesPerfil.clear();
        tarjetasActuales.clear();
        categoriaSeleccionada = 0;

        vPrincipal = new VistaPrincipal();
        vBuscar = new VistaBuscar();
        vCrear = new VistaCrearNoticia();
        vDetalle = new VistaDetalleNoticia();
        vPerfil = new VistaPerfil();
        vEditarPerfil = new VistaEditarPerfil();
        vCuenta = new VistaCuenta();
        vCambiarPass = new VistaCambiarPass();
        vGuardados = new VistaGuardados();
        vIdioma = new VistaIdioma();

        conectarNavegacion(vPrincipal.getBtnInicio(), vPrincipal.getBtnBuscar(), vPrincipal.getBtnAgregar(), vPrincipal.getBtnPerfil());
        conectarNavegacion(vBuscar.getBtnInicio(), vBuscar.getBtnBuscar(), vBuscar.getBtnAgregar(), vBuscar.getBtnPerfil());
        conectarNavegacion(vCrear.getBtnInicio(), vCrear.getBtnBuscar(), vCrear.getBtnAgregar(), vCrear.getBtnPerfil());
        conectarNavegacion(vPerfil.getBtnInicio(), vPerfil.getBtnBuscar(), vPerfil.getBtnAgregar(), vPerfil.getBtnPerfil());

        //Los botones Volver de las pantallas del perfil regresan al perfil
        botonesPerfil.add(vEditarPerfil.getBtnAtras());
        botonesPerfil.add(vCuenta.getBtnAtras());
        botonesPerfil.add(vGuardados.getBtnAtras());
        botonesPerfil.add(vIdioma.getBtnAtras());

        JButton[] botones = {vEditarPerfil.getBtnAtras(), vCuenta.getBtnAtras(), vGuardados.getBtnAtras(), vIdioma.getBtnAtras(),
            vDetalle.getBtnInicio(), vDetalle.getBtnGuardar(), vDetalle.getBtnComentar(), vCambiarPass.getBtnAtras(), vCambiarPass.getBtnGuardarPass(),
            vCrear.getBtnAgregarPortada(), vCrear.getBtnPublicar(), vBuscar.getBtnBuscarPalabra(),
            vPerfil.getBtnEditarPerfil(), vPerfil.getBtnCuenta(), vPerfil.getBtnGuardado(), vPerfil.getBtnIdioma(), vPerfil.getBtnCerrarSesion(),
            vEditarPerfil.getBtnCambiarFoto(), vEditarPerfil.getBtnGuardar(), vCuenta.getBtnCambiarClave(), vCuenta.getBtnEliminarCuenta(), vIdioma.getBtnAplicar()};
        for (JButton boton : botones)
        {
            boton.addActionListener(this);
        }
        for (JButton chip : vPrincipal.getChipsCategoria())
        {
            chip.addActionListener(this);
        }
        vBuscar.getTxtBuscar().addActionListener(this);
        vDetalle.getTxtComentario().addActionListener(this);

        agregarTextoEjemplo(vBuscar.getTxtBuscar(), vBuscar.getTextoEjemplo());
        agregarTextoEjemplo(vDetalle.getTxtComentario(), vDetalle.getTextoEjemplo());
    }

    private void conectarNavegacion(JButton inicio, JButton buscar, JButton agregar, JButton perfil)
    {
        botonesInicio.add(inicio);
        botonesBuscar.add(buscar);
        botonesAgregar.add(agregar);
        botonesPerfil.add(perfil);
        inicio.addActionListener(this);
        buscar.addActionListener(this);
        agregar.addActionListener(this);
        perfil.addActionListener(this);
    }

    //Clase adaptadora: borra el texto de ejemplo al hacer clic y lo devuelve si el campo queda vacío
    private void agregarTextoEjemplo(JTextComponent campo, String textoEjemplo)
    {
        campo.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                if (campo.getText().equals(textoEjemplo))
                {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                if (campo.getText().trim().isEmpty())
                {
                    campo.setText(textoEjemplo);
                    campo.setForeground(Color.GRAY);
                }
            }
        });
    }

    //Oculta la ventana actual y muestra la nueva en la misma posición
    private void irA(JFrame destino)
    {
        if (ventanaActual != null && ventanaActual != destino)
        {
            destino.setLocation(ventanaActual.getLocation());
            ventanaActual.setVisible(false);
        }
        ventanaActual = destino;
        destino.setVisible(true);
        //Se pone el foco en un botón para que no se borre el texto de ejemplo de los campos al abrir
        if (destino == vBuscar)
        {
            vBuscar.getBtnInicio().requestFocusInWindow();
        }
        else if (destino == vDetalle)
        {
            vDetalle.getBtnInicio().requestFocusInWindow();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object origen = e.getSource();
        try
        {
            if (botonesInicio.contains(origen))
            {
                cargarInicio();
                irA(vPrincipal);
            }
            else if (botonesBuscar.contains(origen))
            {
                irA(vBuscar);
            }
            else if (botonesAgregar.contains(origen))
            {
                portadaSeleccionada = null;
                vCrear.limpiar(usuario.obtenerPerfil().getNombre());
                irA(vCrear);
            }
            else if (botonesPerfil.contains(origen))
            {
                abrirPerfil();
            }
            else if (origen == vDetalle.getBtnInicio())
            {
                volverDesdeDetalle();
            }
            else if (origen == vDetalle.getBtnGuardar())
            {
                alternarGuardado();
            }
            else if (origen == vDetalle.getBtnComentar() || origen == vDetalle.getTxtComentario())
            {
                comentar();
            }
            else if (origen == vCrear.getBtnAgregarPortada())
            {
                elegirPortada();
            }
            else if (origen == vCrear.getBtnPublicar())
            {
                publicar();
            }
            else if (origen == vBuscar.getTxtBuscar() || origen == vBuscar.getBtnBuscarPalabra())
            {
                buscar();
            }
            else if (origen == vPerfil.getBtnEditarPerfil())
            {
                fotoPerfilSeleccionada = null;
                vEditarPerfil.cargarDatos(usuario.obtenerPerfil());
                irA(vEditarPerfil);
            }
            else if (origen == vPerfil.getBtnCuenta())
            {
                vCuenta.cargarCuenta(usuario.obtenerPerfil());
                irA(vCuenta);
            }
            else if (origen == vPerfil.getBtnGuardado())
            {
                vGuardados.mostrarTarjetas(crearTarjetas(obtenerNoticiasGuardadas()));
                irA(vGuardados);
            }
            else if (origen == vPerfil.getBtnIdioma())
            {
                vIdioma.marcar(Idioma.esIngles());
                irA(vIdioma);
            }
            else if (origen == vPerfil.getBtnCerrarSesion())
            {
                cerrarSesion();
            }
            else if (origen == vEditarPerfil.getBtnCambiarFoto())
            {
                elegirFotoPerfil();
            }
            else if (origen == vEditarPerfil.getBtnGuardar())
            {
                guardarPerfil();
            }
            else if (origen == vCuenta.getBtnCambiarClave())
            {
                vCambiarPass.limpiarCampos();
                irA(vCambiarPass);
            }
            else if (origen == vCambiarPass.getBtnAtras())
            {
                irA(vCuenta);
            }
            else if (origen == vCambiarPass.getBtnGuardarPass())
            {
                cambiarClave();
            }
            else if (origen == vCuenta.getBtnEliminarCuenta())
            {
                eliminarCuenta();
            }
            else if (origen == vIdioma.getBtnAplicar())
            {
                aplicarIdioma();
            }
            else if (!filtrarSiEsChip(origen))
            {
                abrirDetalleSiEsTarjeta(origen);
            }
        }
        catch (DatoInvalidoException ex)
        {
            mostrarError(Idioma.t("Dato inválido", "Invalid data"), ex.getMessage());
        }
        catch (NoticiaDuplicadaException ex)
        {
            mostrarError("Error", ex.getMessage());
        }
        catch (BaseDatosException ex)
        {
            mostrarError(Idioma.t("Error de base de datos", "Database error"), ex.getMessage());
        }
        catch (ArchivoException ex)
        {
            mostrarError(Idioma.t("Error de archivo", "File error"), ex.getMessage());
        }
    }

    //Carga las tarjetas del inicio según la categoría elegida en los filtros
    private void cargarInicio()
    {
        List<Noticia> noticias;
        if (categoriaSeleccionada == 0)
        {
            noticias = controlador.obtenerNoticias();
        }
        else
        {
            Categoria categoria = Categoria.values()[categoriaSeleccionada - 1];
            noticias = controlador.filtrarPorCategoria(categoria.getNombre());
        }
        vPrincipal.limpiarNoticias();
        for (TarjetaNoticia tarjeta : crearTarjetas(noticias))
        {
            vPrincipal.agregarTarjeta(tarjeta);
        }
        if (noticias.isEmpty())
        {
            vPrincipal.mostrarMensajeVacio();
        }
    }

    private boolean filtrarSiEsChip(Object origen)
    {
        JButton[] chips = vPrincipal.getChipsCategoria();
        for (int i = 0; i < chips.length; i++)
        {
            if (chips[i] == origen)
            {
                categoriaSeleccionada = i;
                vPrincipal.marcarCategoria(i);
                cargarInicio();
                return true;
            }
        }
        return false;
    }

    private List<TarjetaNoticia> crearTarjetas(List<Noticia> noticias)
    {
        List<Integer> guardadas = usuario.obtenerIdsGuardados();
        List<TarjetaNoticia> tarjetas = new ArrayList<>();
        for (Noticia noticia : noticias)
        {
            int comentarios = usuario.contarComentarios(noticia.getId());
            boolean guardada = guardadas.contains(noticia.getId());
            TarjetaNoticia tarjeta = new TarjetaNoticia(noticia, comentarios, guardada);
            tarjeta.getBtnMasInfo().addActionListener(this);
            tarjetas.add(tarjeta);
            tarjetasActuales.add(tarjeta);
        }
        return tarjetas;
    }

    private List<Noticia> obtenerNoticiasGuardadas()
    {
        List<Integer> ids = usuario.obtenerIdsGuardados();
        List<Noticia> guardadas = new ArrayList<>();
        for (Noticia noticia : controlador.obtenerNoticias())
        {
            if (ids.contains(noticia.getId()))
            {
                guardadas.add(noticia);
            }
        }
        return guardadas;
    }

    //Busca a qué tarjeta pertenece el botón "Leer más" presionado
    private void abrirDetalleSiEsTarjeta(Object origen)
    {
        for (TarjetaNoticia tarjeta : tarjetasActuales)
        {
            if (tarjeta.getBtnMasInfo() == origen)
            {
                Noticia noticia = tarjeta.getNoticia();
                vDetalle.mostrarNoticia(noticia, usuario.estaGuardada(noticia.getId()));
                vDetalle.mostrarComentarios(usuario.obtenerComentarios(noticia.getId()));
                ventanaAnteriorDetalle = ventanaActual;
                irA(vDetalle);
                return;
            }
        }
    }

    //Vuelve a la pantalla desde donde se abrió la noticia (inicio, buscar o guardados)
    private void volverDesdeDetalle()
    {
        if (ventanaAnteriorDetalle == vGuardados)
        {
            vGuardados.mostrarTarjetas(crearTarjetas(obtenerNoticiasGuardadas()));
            irA(vGuardados);
        }
        else if (ventanaAnteriorDetalle == vBuscar)
        {
            irA(vBuscar);
        }
        else
        {
            cargarInicio();
            irA(vPrincipal);
        }
    }

    private void alternarGuardado()
    {
        int id = vDetalle.getNoticiaActual().getId();
        boolean guardada = usuario.alternarGuardado(id);
        vDetalle.marcarGuardada(guardada);
        if (guardada)
        {
            JOptionPane.showMessageDialog(vDetalle, Idioma.t("Noticia guardada. La encontrarás en Perfil > Noticias guardadas", "Story saved. You will find it in Profile > Saved stories"));
        }
    }

    //Guarda el comentario en la base de datos y vuelve a mostrar la lista
    private void comentar()
    {
        String texto = vDetalle.getTxtComentario().getText();
        if (texto.equals(vDetalle.getTextoEjemplo()))
        {
            texto = "";
        }
        int id = vDetalle.getNoticiaActual().getId();
        usuario.comentar(id, texto);
        vDetalle.mostrarComentarios(usuario.obtenerComentarios(id));
        vDetalle.getTxtComentario().setText("");
        vDetalle.actualizarContador();
    }

    private void elegirPortada()
    {
        File archivo = elegirImagen(vCrear, Idioma.t("Elegir imagen de portada", "Choose cover image"));
        if (archivo != null)
        {
            GestorArchivos.validarImagen(archivo);
            portadaSeleccionada = archivo;
            vCrear.mostrarPortada(archivo.getAbsolutePath());
        }
    }

    private void elegirFotoPerfil()
    {
        File archivo = elegirImagen(vEditarPerfil, Idioma.t("Elegir foto de perfil", "Choose profile photo"));
        if (archivo != null)
        {
            GestorArchivos.validarImagen(archivo);
            fotoPerfilSeleccionada = archivo;
            vEditarPerfil.mostrarFoto(archivo.getAbsolutePath());
        }
    }

    //Abre el JFileChooser solo con imágenes y devuelve el archivo elegido (o null)
    private File elegirImagen(JFrame ventana, String titulo)
    {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle(titulo);
        selector.setFileFilter(new FileNameExtensionFilter(Idioma.t("Imágenes (jpg, png, gif)", "Images (jpg, png, gif)"), "jpg", "jpeg", "jfif", "png", "gif"));
        int resultado = selector.showOpenDialog(ventana);
        if (resultado == JFileChooser.APPROVE_OPTION)
        {
            return selector.getSelectedFile();
        }
        return null;
    }

    private void publicar() throws NoticiaDuplicadaException
    {
        String titulo = vCrear.getTxtTitulo().getText();
        String contenido = vCrear.getTxtContenido().getText();
        String autor = vCrear.getTxtAutor().getText();
        Categoria categoria = (Categoria) vCrear.getCmbCategoria().getSelectedItem();

        Noticia noticia = controlador.publicarNoticia(titulo, contenido, autor, categoria.getNombre(), portadaSeleccionada);
        JOptionPane.showMessageDialog(vCrear, Idioma.t("Noticia publicada con éxito (ID ", "Story posted successfully (ID ") + noticia.getId() + ")");
        portadaSeleccionada = null;
        categoriaSeleccionada = 0;
        vPrincipal.marcarCategoria(0);
        cargarInicio();
        irA(vPrincipal);
    }

    private void buscar()
    {
        String palabra = vBuscar.getTxtBuscar().getText();
        if (palabra.equals(vBuscar.getTextoEjemplo()))
        {
            palabra = "";
        }
        List<Noticia> resultados = controlador.buscarPorPalabra(palabra);
        vBuscar.mostrarResultados(crearTarjetas(resultados), palabra.trim());
    }

    private void abrirPerfil()
    {
        Perfil perfil = usuario.obtenerPerfil();
        int publicadas = 0;
        for (Noticia noticia : controlador.obtenerNoticias())
        {
            if (noticia.getAutor().equalsIgnoreCase(perfil.getNombre()))
            {
                publicadas++;
            }
        }
        vPerfil.cargarPerfil(perfil, publicadas, usuario.obtenerIdsGuardados().size());
        irA(vPerfil);
    }

    private void guardarPerfil()
    {
        String nombre = vEditarPerfil.getTxtNombre().getText();
        String nombreUsuario = vEditarPerfil.getTxtUsuario().getText();
        String correo = vEditarPerfil.getTxtCorreo().getText();
        usuario.actualizarPerfil(nombre, nombreUsuario, correo, fotoPerfilSeleccionada);
        fotoPerfilSeleccionada = null;
        JOptionPane.showMessageDialog(vEditarPerfil, Idioma.t("Perfil actualizado correctamente", "Profile updated successfully"));
        abrirPerfil();
    }

    private void cambiarClave()
    {
        usuario.cambiarClave(vCambiarPass.getClaveActual(), vCambiarPass.getClaveNueva(), vCambiarPass.getClaveConfirmacion());
        JOptionPane.showMessageDialog(vCambiarPass, Idioma.t("Contraseña cambiada correctamente", "Password changed successfully"));
        vCambiarPass.limpiarCampos();
        vCuenta.cargarCuenta(usuario.obtenerPerfil());
        irA(vCuenta);
    }

    private void eliminarCuenta()
    {
        int respuesta = JOptionPane.showConfirmDialog(vCuenta, Idioma.t("¿Seguro que desea eliminar la cuenta? Se restablecerá el perfil y se borrarán las noticias guardadas.", "Are you sure you want to delete the account? The profile will be reset and saved stories will be removed."), Idioma.t("Eliminar cuenta", "Delete account"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION)
        {
            usuario.eliminarCuenta();
            JOptionPane.showMessageDialog(vCuenta, Idioma.t("La cuenta fue restablecida", "The account was reset"));
            abrirPerfil();
        }
    }

    //Guarda el idioma elegido y vuelve a crear todas las ventanas con los textos nuevos
    private void aplicarIdioma()
    {
        boolean ingles = vIdioma.eligioIngles();
        if (ingles)
        {
            usuario.cambiarIdioma("en");
        }
        else
        {
            usuario.cambiarIdioma("es");
        }
        Idioma.setIngles(ingles);

        Point posicion = ventanaActual.getLocation();
        JFrame[] viejas = {vPrincipal, vBuscar, vCrear, vDetalle, vPerfil, vEditarPerfil, vCuenta, vCambiarPass, vGuardados, vIdioma};
        for (JFrame ventana : viejas)
        {
            ventana.dispose();
        }
        crearVentanas();
        ventanaActual = null;
        vIdioma.setLocation(posicion);
        irA(vIdioma);
        JOptionPane.showMessageDialog(vIdioma, Idioma.t("El idioma se cambió a español", "The language was changed to English"));
    }

    private void cerrarSesion()
    {
        int respuesta = JOptionPane.showConfirmDialog(vPerfil, Idioma.t("¿Desea cerrar sesión y salir de NotiRed?", "Do you want to log out and exit NotiRed?"), Idioma.t("Cerrar sesión", "Log out"), JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }

    private void mostrarError(String titulo, String mensaje)
    {
        JOptionPane.showMessageDialog(ventanaActual, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
    }
}
