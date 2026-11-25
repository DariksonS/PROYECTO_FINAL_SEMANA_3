package ACTIVIDAD_2;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;

public class ReproductorMusica extends JFrame{
	 private Clip clip;
	    private long posicionPausa = 0; 
	    private JButton btnReproducir;
	    private JButton btnPausar;
	    private JButton btnReanudar;
	    private JLabel lblEstado;

	    public ReproductorMusica() {
	        setTitle("Reproductor de Música");
	        setSize(380, 150);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setLayout(new BorderLayout(10, 10));

	        lblEstado = new JLabel("Cargando audio...", JLabel.CENTER);
	        add(lblEstado, BorderLayout.NORTH);

	        btnReproducir = new JButton("Reproducir");
	        btnPausar = new JButton("Pausar");
	        btnReanudar = new JButton("Reanudar");

	        btnPausar.setEnabled(false);
	        btnReanudar.setEnabled(false);

	        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	        panelBotones.add(btnReproducir);
	        panelBotones.add(btnPausar);
	        panelBotones.add(btnReanudar);
	        add(panelBotones, BorderLayout.CENTER);

	        btnReproducir.addActionListener(e -> {
	            if (clip != null) {
	                posicionPausa = 0;
	                clip.setMicrosecondPosition(0);
	                clip.start();
	                lblEstado.setText("Reproduciendo desde el inicio...");
	                btnPausar.setEnabled(true);
	                btnReanudar.setEnabled(false);
	            }
	        });

	        btnPausar.addActionListener(e -> {
	            if (clip != null && clip.isRunning()) {
	                posicionPausa = clip.getMicrosecondPosition();
	                clip.stop();
	                lblEstado.setText("Pausado");
	                btnPausar.setEnabled(false);
	                btnReanudar.setEnabled(true);
	            }
	        });

	        btnReanudar.addActionListener(e -> {
	            if (clip != null && !clip.isRunning()) {
	                clip.setMicrosecondPosition(posicionPausa);
	                clip.start();
	                lblEstado.setText("Reproduciendo...");
	                btnPausar.setEnabled(true);
	                btnReanudar.setEnabled(false);
	            }
	        });

	        cargarAudio();
	    }
	    private void cargarAudio() {
	        JFileChooser selector = new JFileChooser();
	        selector.setDialogTitle("Selecciona un archivo de audio (.wav)");
	        int resultado = selector.showOpenDialog(this);

	        if (resultado == JFileChooser.APPROVE_OPTION) {
	            File archivo = selector.getSelectedFile();
	            try {
	                AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivo);
	                clip = AudioSystem.getClip();
	                clip.open(audioStream);
	                lblEstado.setText("Listo: " + archivo.getName());
	            } catch (Exception ex) {
	                lblEstado.setText("Error al cargar el audio");
	                JOptionPane.showMessageDialog(this, "No se pudo abrir el archivo: " + ex.getMessage());
	            }
	        } else {
	            lblEstado.setText("No se seleccionó ningún archivo");
	            btnReproducir.setEnabled(false);
	        }
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            new ReproductorMusica().setVisible(true);
	        });
	    }
}

