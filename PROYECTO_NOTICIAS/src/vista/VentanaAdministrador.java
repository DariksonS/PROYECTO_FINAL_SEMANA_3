package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import controlador.NoticiaControlador;
import excepciones.ArchivoException;
import excepciones.BaseDatosException;
import excepciones.DatoInvalidoException;
import excepciones.NoticiaDuplicadaException;
import excepciones.NoticiaNoEncontradaException;
import modelo.Categoria;
import modelo.Noticia;

//Panel de administración: editar, eliminar, reporte y estadísticas
public class VentanaAdministrador extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;

    private NoticiaControlador controlador;

    private JTextField txtId;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JComboBox<Categoria> cmbCategoria;
    private JTextArea txtContenido;
    private JLabel lblRutaImagen;
    private JLabel lblVistaImagen;
    private File imagenSeleccionada;

    private JTextField txtBuscar;
    private JComboBox<String> cmbFiltro;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;

    private JButton btnElegirImagen;
    private JButton btnPublicar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnBuscar;
    private JButton btnFiltrar;
    private JButton btnVerTodas;
    private JButton btnReporte;
    private JButton btnEstadisticas;
    private JButton btnRespaldo;
    private JButton btnRestaurar;

    public VentanaAdministrador(NoticiaControlador controlador)
    {
        this.controlador = controlador;
        setTitle(Idioma.t("NotiRed - Administración", "NotiRed - Management"));
        setSize(1250, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(crearPanelBusqueda(), BorderLayout.NORTH);
        add(crearPanelTabla(), BorderLayout.CENTER);
        add(crearPanelFormulario(), BorderLayout.WEST);
        add(crearPanelImagen(), BorderLayout.EAST);
        add(crearPanelInferior(), BorderLayout.SOUTH);

        mostrarNoticias(controlador.obtenerNoticias());
    }

    //Vuelve a cargar la tabla con los datos actuales de la base de datos
    public void refrescar()
    {
        limpiarFormulario();
        mostrarNoticias(controlador.obtenerNoticias());
    }

    private JPanel crearPanelBusqueda()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder(Idioma.t("Buscar y filtrar", "Search and filter")));

        txtBuscar = new JTextField(20);
        btnBuscar = crearBoton(Idioma.t("Buscar", "Search"));

        cmbFiltro = new JComboBox<>();
        cmbFiltro.addItem(Idioma.t("Todas", "All"));
        for (Categoria categoria : controlador.obtenerCategorias())
        {
            cmbFiltro.addItem(Idioma.categoria(categoria));
        }
        btnFiltrar = crearBoton(Idioma.t("Filtrar", "Filter"));
        btnVerTodas = crearBoton(Idioma.t("Ver todas", "Show all"));

        panel.add(new JLabel(Idioma.t("Palabra:", "Word:")));
        panel.add(txtBuscar);
        panel.add(btnBuscar);
        panel.add(new JLabel(Idioma.t("   Categoría:", "   Category:")));
        panel.add(cmbFiltro);
        panel.add(btnFiltrar);
        panel.add(btnVerTodas);
        return panel;
    }

    private JScrollPane crearPanelTabla()
    {
        String[] columnas = {"ID", Idioma.t("Título", "Title"), Idioma.t("Categoría", "Category"), Idioma.t("Autor", "Author"), Idioma.t("Fecha", "Date"), Idioma.t("Foto", "Photo")};
        modeloTabla = new DefaultTableModel(columnas, 0)
        {
            private static final long serialVersionUID = 1L;

            //Las celdas no se pueden editar directamente en la tabla
            @Override
            public boolean isCellEditable(int fila, int columna)
            {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(24);
        tabla.getColumnModel().getColumn(0).setMaxWidth(50);
        tabla.getColumnModel().getColumn(5).setMaxWidth(60);

        //Clase adaptadora: al hacer clic en una fila se cargan sus datos en el formulario
        tabla.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                cargarNoticiaSeleccionada();
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder(Idioma.t("Noticias", "Stories")));
        return scroll;
    }

    private JPanel crearPanelFormulario()
    {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(Idioma.t("Datos de la noticia", "Story details")));
        panel.setPreferredSize(new Dimension(340, 0));

        JPanel campos = new JPanel(new GridLayout(4, 2, 5, 5));
        txtId = new JTextField();
        txtId.setEditable(false);
        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        cmbCategoria = new JComboBox<>(controlador.obtenerCategorias());
        Estilos.traducirCombo(cmbCategoria);

        campos.add(new JLabel("ID:"));
        campos.add(txtId);
        campos.add(new JLabel(Idioma.t("Título:", "Title:")));
        campos.add(txtTitulo);
        campos.add(new JLabel(Idioma.t("Autor:", "Author:")));
        campos.add(txtAutor);
        campos.add(new JLabel(Idioma.t("Categoría:", "Category:")));
        campos.add(cmbCategoria);

        txtContenido = new JTextArea();
        txtContenido.setLineWrap(true);
        txtContenido.setWrapStyleWord(true);
        JScrollPane scrollContenido = new JScrollPane(txtContenido);
        scrollContenido.setBorder(BorderFactory.createTitledBorder(Idioma.t("Contenido", "Content")));

        JPanel panelImagen = new JPanel(new BorderLayout(5, 5));
        btnElegirImagen = crearBoton(Idioma.t("Elegir foto...", "Choose photo..."));
        lblRutaImagen = new JLabel(Idioma.t("Sin foto", "No photo"));
        panelImagen.add(btnElegirImagen, BorderLayout.WEST);
        panelImagen.add(lblRutaImagen, BorderLayout.CENTER);

        JPanel botones = new JPanel(new GridLayout(2, 2, 5, 5));
        btnPublicar = crearBoton(Idioma.t("Publicar", "Post"));
        btnEditar = crearBoton(Idioma.t("Guardar cambios", "Save changes"));
        btnEliminar = crearBoton(Idioma.t("Eliminar", "Delete"));
        btnLimpiar = crearBoton(Idioma.t("Limpiar", "Clear"));
        botones.add(btnPublicar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);

        JPanel abajo = new JPanel(new BorderLayout(5, 5));
        abajo.add(panelImagen, BorderLayout.NORTH);
        abajo.add(botones, BorderLayout.SOUTH);

        panel.add(campos, BorderLayout.NORTH);
        panel.add(scrollContenido, BorderLayout.CENTER);
        panel.add(abajo, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelImagen()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(Idioma.t("Foto", "Photo")));
        panel.setPreferredSize(new Dimension(250, 0));
        lblVistaImagen = new JLabel(Idioma.t("Sin foto", "No photo"), SwingConstants.CENTER);
        lblVistaImagen.setForeground(Color.GRAY);
        panel.add(lblVistaImagen, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelInferior()
    {
        JPanel panel = new JPanel(new BorderLayout());
        lblTotal = new JLabel();
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEstadisticas = crearBoton(Idioma.t("Estadísticas", "Statistics"));
        btnReporte = crearBoton(Idioma.t("Exportar reporte", "Export report"));
        btnRespaldo = crearBoton(Idioma.t("Copia de seguridad", "Backup"));
        btnRestaurar = crearBoton(Idioma.t("Restaurar copia", "Restore backup"));
        botones.add(btnEstadisticas);
        botones.add(btnReporte);
        botones.add(btnRespaldo);
        botones.add(btnRestaurar);

        panel.add(lblTotal, BorderLayout.WEST);
        panel.add(botones, BorderLayout.EAST);
        return panel;
    }

    private JButton crearBoton(String texto)
    {
        JButton boton = new JButton(texto);
        boton.addActionListener(this);
        return boton;
    }

    //Cada botón llama a su método y cualquier error se muestra en una ventanita
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object origen = e.getSource();
        try
        {
            if (origen == btnPublicar)
            {
                publicar();
            }
            else if (origen == btnEditar)
            {
                editar();
            }
            else if (origen == btnEliminar)
            {
                eliminar();
            }
            else if (origen == btnLimpiar)
            {
                limpiarFormulario();
            }
            else if (origen == btnElegirImagen)
            {
                elegirImagen();
            }
            else if (origen == btnBuscar)
            {
                mostrarNoticias(controlador.buscarPorPalabra(txtBuscar.getText()));
            }
            else if (origen == btnFiltrar)
            {
                filtrar();
            }
            else if (origen == btnVerTodas)
            {
                txtBuscar.setText("");
                cmbFiltro.setSelectedIndex(0);
                mostrarNoticias(controlador.obtenerNoticias());
            }
            else if (origen == btnReporte)
            {
                exportarReporte();
            }
            else if (origen == btnEstadisticas)
            {
                mostrarEstadisticas();
            }
            else if (origen == btnRespaldo)
            {
                crearRespaldo();
            }
            else if (origen == btnRestaurar)
            {
                restaurarRespaldo();
            }
        }
        catch (DatoInvalidoException ex)
        {
            mostrarError(Idioma.t("Dato inválido", "Invalid data"), ex.getMessage());
        }
        catch (NoticiaNoEncontradaException | NoticiaDuplicadaException ex)
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

    private void publicar() throws NoticiaDuplicadaException
    {
        Categoria categoria = (Categoria) cmbCategoria.getSelectedItem();
        Noticia noticia = controlador.publicarNoticia(txtTitulo.getText(), txtContenido.getText(), txtAutor.getText(), categoria.getNombre(), imagenSeleccionada);
        JOptionPane.showMessageDialog(this, Idioma.t("Noticia publicada con ID ", "Story posted with ID ") + noticia.getId());
        limpiarFormulario();
        mostrarNoticias(controlador.obtenerNoticias());
    }

    private void editar() throws NoticiaNoEncontradaException, NoticiaDuplicadaException
    {
        if (txtId.getText().isBlank())
        {
            throw new DatoInvalidoException(Idioma.t("Primero seleccione una noticia de la tabla", "First select a story from the table"));
        }
        Categoria categoria = (Categoria) cmbCategoria.getSelectedItem();
        controlador.editarNoticia(txtId.getText(), txtTitulo.getText(), txtContenido.getText(), categoria.getNombre(), imagenSeleccionada);
        JOptionPane.showMessageDialog(this, Idioma.t("Noticia actualizada correctamente", "Story updated successfully"));
        limpiarFormulario();
        mostrarNoticias(controlador.obtenerNoticias());
    }

    private void eliminar() throws NoticiaNoEncontradaException
    {
        if (txtId.getText().isBlank())
        {
            throw new DatoInvalidoException(Idioma.t("Primero seleccione una noticia de la tabla", "First select a story from the table"));
        }
        int respuesta = JOptionPane.showConfirmDialog(this, Idioma.t("¿Seguro que desea eliminar esta noticia?", "Are you sure you want to delete this story?"), Idioma.t("Confirmar", "Confirm"), JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION)
        {
            controlador.eliminarNoticia(txtId.getText());
            limpiarFormulario();
            mostrarNoticias(controlador.obtenerNoticias());
        }
    }

    //El índice 0 es "Todas", los demás siguen el orden de las categorías
    private void filtrar()
    {
        int indice = cmbFiltro.getSelectedIndex();
        if (indice == 0)
        {
            mostrarNoticias(controlador.obtenerNoticias());
        }
        else
        {
            Categoria categoria = Categoria.values()[indice - 1];
            mostrarNoticias(controlador.filtrarPorCategoria(categoria.getNombre()));
        }
    }

    private void elegirImagen()
    {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle(Idioma.t("Elegir foto de la noticia", "Choose the story photo"));
        selector.setFileFilter(new FileNameExtensionFilter(Idioma.t("Imágenes (jpg, png, gif)", "Images (jpg, png, gif)"), "jpg", "jpeg", "png", "gif"));
        int resultado = selector.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION)
        {
            imagenSeleccionada = selector.getSelectedFile();
            lblRutaImagen.setText(imagenSeleccionada.getName());
            mostrarImagen(imagenSeleccionada.getAbsolutePath());
        }
    }

    private void exportarReporte()
    {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle(Idioma.t("Guardar reporte", "Save report"));
        selector.setSelectedFile(new File("reporte_noticias.txt"));
        int resultado = selector.showSaveDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION)
        {
            File destino = selector.getSelectedFile();
            int cantidad = controlador.exportarReporte(destino);
            JOptionPane.showMessageDialog(this, Idioma.t("Se exportaron ", "Exported ") + cantidad + Idioma.t(" noticias a: ", " stories to: ") + destino.getName());
        }
    }

    //Guarda todas las noticias serializadas en un archivo .dat
    private void crearRespaldo()
    {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle(Idioma.t("Guardar copia de seguridad", "Save backup"));
        selector.setSelectedFile(new File("respaldo_noticias.dat"));
        if (selector.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            int cantidad = controlador.crearRespaldo(selector.getSelectedFile());
            JOptionPane.showMessageDialog(this, Idioma.t("Copia de seguridad creada con ", "Backup created with ") + cantidad + Idioma.t(" noticias", " stories"));
        }
    }

    private void restaurarRespaldo()
    {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle(Idioma.t("Abrir copia de seguridad", "Open backup"));
        selector.setFileFilter(new FileNameExtensionFilter(Idioma.t("Copia de seguridad (.dat)", "Backup (.dat)"), "dat"));
        if (selector.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            int cantidad = controlador.restaurarRespaldo(selector.getSelectedFile());
            JOptionPane.showMessageDialog(this, Idioma.t("Se restauraron ", "Restored ") + cantidad + Idioma.t(" noticias (las repetidas se saltaron)", " stories (duplicates were skipped)"));
            refrescar();
        }
    }

    private void mostrarEstadisticas()
    {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 5));
        for (Categoria categoria : controlador.obtenerCategorias())
        {
            panel.add(new JLabel(Idioma.categoria(categoria) + ":"));
            panel.add(new JLabel(String.valueOf(controlador.contarPorCategoria(categoria.getNombre()))));
        }
        panel.add(new JLabel("TOTAL:"));
        panel.add(new JLabel(String.valueOf(controlador.totalNoticias())));
        JOptionPane.showMessageDialog(this, panel, Idioma.t("Noticias por categoría", "Stories by category"), JOptionPane.INFORMATION_MESSAGE);
    }

    //Llena la tabla con la lista de noticias recibida
    private void mostrarNoticias(List<Noticia> noticias)
    {
        modeloTabla.setRowCount(0);
        for (Noticia noticia : noticias)
        {
            String foto = "No";
            if (noticia.tieneImagen())
            {
                foto = Idioma.t("Sí", "Yes");
            }
            Object[] fila = {noticia.getId(), noticia.getTitulo(), Idioma.categoria(noticia.getCategoria()), noticia.getAutor(), noticia.getFecha(), foto};
            modeloTabla.addRow(fila);
        }
        lblTotal.setText(Idioma.t("Noticias registradas: ", "Registered stories: ") + controlador.totalNoticias() + Idioma.t("   |   Mostrando: ", "   |   Showing: ") + noticias.size());
    }

    private void cargarNoticiaSeleccionada()
    {
        int fila = tabla.getSelectedRow();
        if (fila == -1)
        {
            return;
        }
        String id = String.valueOf(modeloTabla.getValueAt(fila, 0));
        try
        {
            Noticia noticia = controlador.buscarPorId(id);
            txtId.setText(String.valueOf(noticia.getId()));
            txtTitulo.setText(noticia.getTitulo());
            txtAutor.setText(noticia.getAutor());
            txtAutor.setEditable(false);
            cmbCategoria.setSelectedItem(noticia.getCategoria());
            txtContenido.setText(noticia.getContenido());
            txtContenido.setCaretPosition(0);
            imagenSeleccionada = null;
            if (noticia.tieneImagen())
            {
                lblRutaImagen.setText(Idioma.t("Foto guardada", "Saved photo"));
                mostrarImagen(noticia.getRutaImagen());
            }
            else
            {
                lblRutaImagen.setText(Idioma.t("Sin foto", "No photo"));
                mostrarImagen(null);
            }
        }
        catch (NoticiaNoEncontradaException | BaseDatosException ex)
        {
            mostrarError("Error", ex.getMessage());
        }
    }

    //Muestra la foto escalada en el panel derecho
    private void mostrarImagen(String ruta)
    {
        if (ruta == null || !new File(ruta).exists())
        {
            lblVistaImagen.setIcon(null);
            lblVistaImagen.setText(Idioma.t("Sin foto", "No photo"));
            return;
        }
        ImageIcon icono = new ImageIcon(ruta);
        Image imagen = icono.getImage().getScaledInstance(230, 170, Image.SCALE_SMOOTH);
        lblVistaImagen.setText("");
        lblVistaImagen.setIcon(new ImageIcon(imagen));
    }

    private void limpiarFormulario()
    {
        txtId.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtAutor.setEditable(true);
        cmbCategoria.setSelectedIndex(0);
        txtContenido.setText("");
        imagenSeleccionada = null;
        lblRutaImagen.setText(Idioma.t("Sin foto", "No photo"));
        mostrarImagen(null);
        tabla.clearSelection();
    }

    private void mostrarError(String titulo, String mensaje)
    {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
    }
}
