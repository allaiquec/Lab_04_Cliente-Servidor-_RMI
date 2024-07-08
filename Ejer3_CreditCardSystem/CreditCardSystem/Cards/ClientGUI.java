package Cards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.List;

public class ClientGUI {
    private static CardInterface server;
    private static JPanel mainPanel;
    private static CardLayout cardLayout;
    private static JFrame frame;

    public static void main(String[] args) {
        try {
            // Conectar al servidor RMI
            server = (CardInterface) Naming.lookup("rmi://localhost/Server");

            // Crear la interfaz gráfica
            frame = new JFrame("Sistema de Tarjetas de Crédito");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null); // Center the frame
            cardLayout = new CardLayout();
            mainPanel = new JPanel(cardLayout);
            mainPanel.setBackground(Color.decode("#F2EAE1"));

            JPanel loginPanel = createLoginPanel();
            JPanel menuPanel = createMenuPanel();
            JPanel verSaldoPanel = createVerSaldoPanel();
            JPanel recargarSaldoPanel = createRecargarSaldoPanel();
            JPanel realizarCompraPanel = createRealizarCompraPanel();
            JPanel retirarSaldoPanel = createRetirarSaldoPanel();
            JPanel verHistorialPanel = createVerHistorialPanel();

            // Agregar paneles al CardLayout
            mainPanel.add(loginPanel, "Login");
            mainPanel.add(menuPanel, "Menu");
            mainPanel.add(verSaldoPanel, "VerSaldo");
            mainPanel.add(recargarSaldoPanel, "RecargarSaldo");
            mainPanel.add(realizarCompraPanel, "RealizarCompra");
            mainPanel.add(retirarSaldoPanel, "RetirarSaldo");
            mainPanel.add(verHistorialPanel, "VerHistorial");

            frame.add(mainPanel);
            cardLayout.show(mainPanel, "Login");

            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.decode("#F2EAE1"));
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        JLabel loginTitleLabel = new JLabel("Ingrese su número de tarjeta y contraseña");
        loginTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        loginTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginTitleLabel.setForeground(Color.BLACK);

        JTextField cardNumberField = new JTextField();
        cardNumberField.setMaximumSize(new Dimension(200, 30));
        cardNumberField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(200, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cardNumberLabel = new JLabel("Número de Tarjeta:");
        cardNumberLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        cardNumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardNumberLabel.setForeground(Color.BLACK);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setForeground(Color.BLACK);

        JButton loginButton = new JButton("Ingresar");
        loginButton.setBackground(Color.decode("#feaf00"));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(true);
        loginButton.setContentAreaFilled(true);
        loginButton.setOpaque(true);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cardNumber = cardNumberField.getText();
                String password = new String(passwordField.getPassword());

                if (cardNumber.equals("4321") && password.equals("1234")) {
                    cardLayout.show(mainPanel, "Menu");
                } else {
                    JOptionPane.showMessageDialog(frame, "Número de tarjeta o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(loginTitleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(cardNumberLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loginPanel.add(cardNumberField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(passwordLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loginPanel.add(passwordField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createVerticalGlue());

        return loginPanel;
    }

    private static JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.decode("#F2EAE1"));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        JLabel mainTitleLabel = new JLabel("Sistema de tarjetas");
        mainTitleLabel.setFont(new Font("Bold", Font.BOLD, 24)); //cambio
        mainTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainTitleLabel.setForeground(Color.BLACK);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuPanel.add(mainTitleLabel);

        JLabel titleLabel = new JLabel("Seleccione la operación a realizar");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.BLACK);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuPanel.add(titleLabel);

        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        String[] buttonLabels = {
            "Ver Saldo", "Recargar Saldo", "Realizar Compra", 
            "Retirar Saldo", "Ver Historial", "Salir"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setMaximumSize(new Dimension(200, 40));
            if (label.equals("Salir")) {
                button.setBackground(Color.RED);
            } else {
                button.setBackground(Color.decode("#feaf00"));
            }
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorderPainted(true);
            button.setContentAreaFilled(true);
            button.setOpaque(true);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 1),
                    BorderFactory.createEmptyBorder(5, 15, 5, 15)));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuPanel.add(button);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // Action listeners para cada botón del menú
        Component[] menuComponents = menuPanel.getComponents();
        for (Component component : menuComponents) {
            if (component instanceof JButton) {
                ((JButton) component).addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton source = (JButton) e.getSource();
                        switch (source.getText()) {
                            case "Ver Saldo":
                                try {
                                    double saldo = server.verSaldo();
                                    JLabel saldoLabel = new JLabel("Saldo actual: $" + saldo, SwingConstants.CENTER);
                                    saldoLabel.setFont(new Font("Arial", Font.BOLD, 16));
                                    JPanel verSaldoPanel = (JPanel) mainPanel.getComponent(2);
                                    verSaldoPanel.removeAll();
                                    verSaldoPanel.add(saldoLabel, BorderLayout.CENTER);
                                    verSaldoPanel.add(createBackButton(), BorderLayout.SOUTH);
                                    verSaldoPanel.revalidate();
                                    verSaldoPanel.repaint();
                                    cardLayout.show(mainPanel, "VerSaldo");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                break;
                            case "Recargar Saldo":
                                cardLayout.show(mainPanel, "RecargarSaldo");
                                break;
                            case "Realizar Compra":
                                cardLayout.show(mainPanel, "RealizarCompra");
                                break;
                            case "Retirar Saldo":
                                cardLayout.show(mainPanel, "RetirarSaldo");
                                break;
                            case "Ver Historial":
                                try {
                                    List<String> historial = server.obtenerHistorial();
                                    JTextArea historialArea = new JTextArea();
                                    historialArea.setEditable(false);
                                    historialArea.setBackground(Color.decode("#F2EAE1"));
                                    historialArea.setForeground(Color.BLACK);
                                    historialArea.setText("");
                                    for (String accion : historial) {
                                        historialArea.append(accion + "\n");
                                    }
                                    JScrollPane scrollPane = new JScrollPane(historialArea);
                                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                                    JPanel verHistorialPanel = (JPanel) mainPanel.getComponent(6);
                                    verHistorialPanel.removeAll();
                                    verHistorialPanel.add(scrollPane, BorderLayout.CENTER);
                                    verHistorialPanel.add(createBackButton(), BorderLayout.SOUTH);
                                    verHistorialPanel.revalidate();
                                    verHistorialPanel.repaint();
                                    cardLayout.show(mainPanel, "VerHistorial");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                break;
                            case "Salir":
                                frame.dispose();
                                break;
                        }
                    }
                });
            }
        }

        return menuPanel;
    }

    private static JPanel createVerSaldoPanel() {
        JPanel verSaldoPanel = new JPanel(new BorderLayout());
        verSaldoPanel.setBackground(Color.decode("#F2EAE1"));
        verSaldoPanel.add(createBackButton(), BorderLayout.SOUTH);
        return verSaldoPanel;
    }

    private static JPanel createRecargarSaldoPanel() {
        JPanel recargarSaldoPanel = new JPanel();
        recargarSaldoPanel.setBackground(Color.decode("#F2EAE1"));
        recargarSaldoPanel.setLayout(new BoxLayout(recargarSaldoPanel, BoxLayout.Y_AXIS));

        JLabel recargarLabel = new JLabel("¿Cuánto desea recargar?");
        recargarLabel.setFont(new Font("Arial", Font.BOLD, 16));
        recargarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        recargarLabel.setForeground(Color.BLACK);

        JTextField recargaField = new JTextField();
        recargaField.setMaximumSize(new Dimension(200, 30));
        recargaField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton recargarButton = createActionButton("Recargar");
        recargarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String montoStr = recargaField.getText();
                if (montoStr != null && !montoStr.isEmpty()) {
                    try {
                        double monto = Double.parseDouble(montoStr);
                        server.recargarSaldo(monto);
                        JOptionPane.showMessageDialog(null, "Saldo recargado: $" + monto);
                        recargaField.setText(""); // Limpiar campo de texto después de recargar
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        recargarSaldoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        recargarSaldoPanel.add(recargarLabel);
        recargarSaldoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        recargarSaldoPanel.add(recargaField);
        recargarSaldoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        recargarSaldoPanel.add(recargarButton);
        recargarSaldoPanel.add(Box.createVerticalGlue());
        recargarSaldoPanel.add(createBackButton());

        return recargarSaldoPanel;
    }

    private static JPanel createRealizarCompraPanel() {
        JPanel realizarCompraPanel = new JPanel();
        realizarCompraPanel.setBackground(Color.decode("#F2EAE1"));
        realizarCompraPanel.setLayout(new BoxLayout(realizarCompraPanel, BoxLayout.Y_AXIS));

        JLabel productosLabel = new JLabel("Seleccione un producto:");
        productosLabel.setFont(new Font("Arial", Font.BOLD, 16));
        productosLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        productosLabel.setForeground(Color.BLACK);

        String[] productos = {"Aspirina - $10", "Paracetamol - $15", "Mejoral - $12"};
        JComboBox<String> productosComboBox = new JComboBox<>(productos);
        productosComboBox.setMaximumSize(new Dimension(200, 30));
        productosComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cantidadLabel = new JLabel("Cantidad:");
        cantidadLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cantidadLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cantidadLabel.setForeground(Color.BLACK);

        JTextField cantidadField = new JTextField();
        cantidadField.setMaximumSize(new Dimension(200, 30));
        cantidadField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton comprarButton = createActionButton("Comprar");
        comprarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String producto = (String) productosComboBox.getSelectedItem();
                String[] productoInfo = producto.split(" - \\$");
                double precio = Double.parseDouble(productoInfo[1]);
                String cantidadStr = cantidadField.getText();
                if (cantidadStr != null && !cantidadStr.isEmpty()) {
                    try {
                        int cantidad = Integer.parseInt(cantidadStr);
                        double monto = precio * cantidad;
                        double saldoActual = server.verSaldo();
                        if (saldoActual >= monto) {
                            server.realizarCompra(monto);
                            JOptionPane.showMessageDialog(null, "Compra realizada: " + productoInfo[0] + " x " + cantidad + " = $" + monto);
                            cantidadField.setText(""); // Limpiar campo de texto después de comprar
                        } else {
                            JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar la compra.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        realizarCompraPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        realizarCompraPanel.add(productosLabel);
        realizarCompraPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        realizarCompraPanel.add(productosComboBox);
        realizarCompraPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        realizarCompraPanel.add(cantidadLabel);
        realizarCompraPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        realizarCompraPanel.add(cantidadField);
        realizarCompraPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        realizarCompraPanel.add(comprarButton);
        realizarCompraPanel.add(Box.createVerticalGlue());
        realizarCompraPanel.add(createBackButton());

        return realizarCompraPanel;
    }

    private static JPanel createRetirarSaldoPanel() {
        JPanel retirarSaldoPanel = new JPanel();
        retirarSaldoPanel.setBackground(Color.decode("#F2EAE1"));
        retirarSaldoPanel.setLayout(new BoxLayout(retirarSaldoPanel, BoxLayout.Y_AXIS));

        JLabel retirarLabel = new JLabel("¿Cuánto desea retirar?");
        retirarLabel.setFont(new Font("Arial", Font.BOLD, 16));
        retirarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        retirarLabel.setForeground(Color.BLACK);

        JTextField retiroField = new JTextField();
        retiroField.setMaximumSize(new Dimension(200, 30));
        retiroField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton retirarButton = createActionButton("Retirar");
        retirarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String montoStr = retiroField.getText();
                if (montoStr != null && !montoStr.isEmpty()) {
                    try {
                        double monto = Double.parseDouble(montoStr);
                        double saldoActual = server.verSaldo();
                        if (saldoActual >= monto) {
                            server.retirarSaldo(monto);
                            JOptionPane.showMessageDialog(null, "Saldo retirado: $" + monto);
                            retiroField.setText(""); // Limpiar campo de texto después de retirar
                        } else {
                            JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar el retiro.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        retirarSaldoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        retirarSaldoPanel.add(retirarLabel);
        retirarSaldoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        retirarSaldoPanel.add(retiroField);
        retirarSaldoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        retirarSaldoPanel.add(retirarButton);
        retirarSaldoPanel.add(Box.createVerticalGlue());
        retirarSaldoPanel.add(createBackButton());

        return retirarSaldoPanel;
    }

    private static JPanel createVerHistorialPanel() {
        JPanel verHistorialPanel = new JPanel(new BorderLayout());
        verHistorialPanel.setBackground(Color.decode("#F2EAE1"));
        JTextArea historialArea = new JTextArea();
        historialArea.setEditable(false);
        historialArea.setBackground(Color.decode("#F2EAE1"));
        historialArea.setForeground(Color.BLACK);
        verHistorialPanel.add(new JScrollPane(historialArea), BorderLayout.CENTER);
        verHistorialPanel.add(createBackButton(), BorderLayout.SOUTH);
        return verHistorialPanel;
    }

    private static JButton createBackButton() {
        JButton backButton = new JButton("Atrás");
        backButton.setBackground(Color.decode("#bf7e06"));
        backButton.setForeground(Color.WHITE);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT); //cambio
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(true);
        backButton.setOpaque(true);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Menu");
            }
        });
        return backButton;
    }

    private static JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode("#feaf00"));
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); //cambio
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 30));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        return button;
    }
}
