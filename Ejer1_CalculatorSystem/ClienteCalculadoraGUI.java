import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.*;

public class ClienteCalculadoraGUI {
    private static Calculator calculadora;

    public static void main(String[] args) {
        try {
            calculadora = (Calculator) Naming.lookup("rmi://localhost/CalculatorService");
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con el servicio de calculadora: " + e.getMessage());
            System.exit(1);
        }

        SwingUtilities.invokeLater(() -> {
            crearYMostrarGUI();
        });
    }

    private static void crearYMostrarGUI() {
        JFrame frame = new JFrame("Calculadora");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.DARK_GRAY);
        frame.add(panel);
        colocarComponentes(panel);

        frame.setVisible(true);
    }

    private static void colocarComponentes(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel etiquetaNumero1 = new JLabel("Número 1:");
        etiquetaNumero1.setFont(new Font("Arial", Font.BOLD, 14));
        etiquetaNumero1.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(etiquetaNumero1, gbc);

        JTextField campoNumero1 = new JTextField(20);
        campoNumero1.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(campoNumero1, gbc);

        JLabel etiquetaNumero2 = new JLabel("Número 2:");
        etiquetaNumero2.setFont(new Font("Arial", Font.BOLD, 14));
        etiquetaNumero2.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(etiquetaNumero2, gbc);

        JTextField campoNumero2 = new JTextField(20);
        campoNumero2.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(campoNumero2, gbc);

        JButton botonSumar = new JButton("Sumar");
        botonSumar.setFont(new Font("Arial", Font.BOLD, 14));
        botonSumar.setBackground(new Color(102, 255, 102));
        botonSumar.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(botonSumar, gbc);

        JButton botonRestar = new JButton("Restar");
        botonRestar.setFont(new Font("Arial", Font.BOLD, 14));
        botonRestar.setBackground(new Color(255, 255, 102));
        botonRestar.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(botonRestar, gbc);

        JButton botonMultiplicar = new JButton("Multiplicar");
        botonMultiplicar.setFont(new Font("Arial", Font.BOLD, 14));
        botonMultiplicar.setBackground(new Color(255, 153, 51));
        botonMultiplicar.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(botonMultiplicar, gbc);

        JButton botonDividir = new JButton("Dividir");
        botonDividir.setFont(new Font("Arial", Font.BOLD, 14));
        botonDividir.setBackground(new Color(255, 102, 102));
        botonDividir.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(botonDividir, gbc);

        JLabel etiquetaResultado = new JLabel("Resultado:");
        etiquetaResultado.setFont(new Font("Arial", Font.BOLD, 14));
        etiquetaResultado.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(etiquetaResultado, gbc);

        JTextField campoResultado = new JTextField(20);
        campoResultado.setFont(new Font("Arial", Font.PLAIN, 14));
        campoResultado.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(campoResultado, gbc);

        botonSumar.addActionListener(e -> realizarOperacion(campoNumero1, campoNumero2, campoResultado, "sumar"));
        botonRestar.addActionListener(e -> realizarOperacion(campoNumero1, campoNumero2, campoResultado, "restar"));
        botonMultiplicar.addActionListener(e -> realizarOperacion(campoNumero1, campoNumero2, campoResultado, "multiplicar"));
        botonDividir.addActionListener(e -> realizarOperacion(campoNumero1, campoNumero2, campoResultado, "dividir"));
    }

    private static void realizarOperacion(JTextField campoNumero1, JTextField campoNumero2, JTextField campoResultado, String operacion) {
        try {
            int num1 = Integer.parseInt(campoNumero1.getText());
            int num2 = Integer.parseInt(campoNumero2.getText());
            int resultado = 0;
            switch (operacion) {
                case "sumar":
                    resultado = calculadora.add(num1, num2);
                    break;
                case "restar":
                    resultado = calculadora.sub(num1, num2);
                    break;
                case "multiplicar":
                    resultado = calculadora.mul(num1, num2);
                    break;
                case "dividir":
                    resultado = calculadora.div(num1, num2);
                    break;
            }
            campoResultado.setText(String.valueOf(resultado));
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null, "RemoteException: " + e.getMessage());
        } catch (ArithmeticException e) {
            JOptionPane.showMessageDialog(null, "ArithmeticException: División por cero");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese números válidos");
        }
    }
}
