package ACTIVIDAD_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Actividad1 extends JFrame implements ActionListener
{
    String[] dias = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
    JTextField[] campos = new JTextField[7];
    JButton boton = new JButton("Mostrar Grafico");
    PanelGrafico panel = new PanelGrafico();

    public Actividad1()
    {
        setTitle("Temperatura semanal");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel(new GridLayout(8, 2));

        for (int i = 0; i < 7; i++)
        {
            panelDatos.add(new JLabel(dias[i]));
            campos[i] = new JTextField(5);
            panelDatos.add(campos[i]);
        }

        panelDatos.add(new JLabel(""));
        panelDatos.add(boton);
        boton.addActionListener(this);

        add(panelDatos, BorderLayout.WEST);
        add(panel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e)
    {
        double[] temperaturas = new double[7];

        for (int i = 0; i < 7; i++)
        {
            try
            {
                temperaturas[i] = Double.parseDouble(campos[i].getText());
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Temperatura invalida en " + dias[i]);
                return;
            }
        }

        panel.setTemperaturas(temperaturas);
    }

    public static void main(String[] args)
    {
        Actividad1 ventana = new Actividad1();
        ventana.setVisible(true);
    }
}

class PanelGrafico extends JPanel
{
    String[] dias = {"Lun", "Mar", "Mie", "Jue", "Vie", "Sab", "Dom"};
    double[] temperaturas;

    public PanelGrafico()
    {
        setBackground(Color.WHITE);
    }

    public void setTemperaturas(double[] temperaturas)
    {
        this.temperaturas = temperaturas;
        repaint();
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        int margen = 50;
        int ancho = getWidth() - 2 * margen;
        int alto = getHeight() - 2 * margen;

        g.setColor(Color.BLACK);
        g.drawLine(margen, margen, margen, margen + alto);
        g.drawLine(margen, margen + alto, margen + ancho, margen + alto);
        g.drawString("Temperatura semanal", margen, margen - 20);

        if (temperaturas == null)
        {
            return;
        }

        double max = temperaturas[0];
        double min = temperaturas[0];

        for (int i = 1; i < 7; i++)
        {
            if (temperaturas[i] > max)
            {
                max = temperaturas[i];
            }
            if (temperaturas[i] < min)
            {
                min = temperaturas[i];
            }
        }

        if (max == min)
        {
            max = max + 1;
            min = min - 1;
        }

        int[] x = new int[7];
        int[] y = new int[7];
        int separacion = ancho / 6;

        for (int i = 0; i < 7; i++)
        {
            x[i] = margen + i * separacion;
            y[i] = margen + alto - 20 - (int) ((temperaturas[i] - min) / (max - min) * (alto - 40));
        }

        g.setColor(Color.BLUE);
        for (int i = 0; i < 6; i++)
        {
            g.drawLine(x[i], y[i], x[i + 1], y[i + 1]);
        }

        for (int i = 0; i < 7; i++)
        {
            g.setColor(Color.RED);
            g.fillOval(x[i] - 5, y[i] - 5, 10, 10);
            g.setColor(Color.BLACK);
            g.drawString(temperaturas[i] + "", x[i] - 10, y[i] - 10);
            g.drawString(dias[i], x[i] - 10, margen + alto + 20);
        }
    }
}