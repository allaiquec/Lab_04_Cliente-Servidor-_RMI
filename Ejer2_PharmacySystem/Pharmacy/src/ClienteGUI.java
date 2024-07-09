import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.HashMap;

public class ClienteGUI extends JFrame {
    private StockInterface pharm;
    private DefaultTableModel productTableModel;
    private DefaultTableModel historyTableModel;
    private JComboBox<String> productComboBox;
    private JSpinner quantitySpinner;
    private JLabel totalLabel;
    private double totalAmount = 0;

    public ClienteGUI() {
        try {
            pharm = (StockInterface) Naming.lookup("PHARMACY");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Sistema de Gestion de Farmacia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Creación del panel principal
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(207, 235, 247)); // Color celeste claro
        setContentPane(contentPane);

        // Panel principal dividido en dos secciones
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // Panel de lista de productos
        JPanel productListPanel = new JPanel(new BorderLayout());
        productListPanel.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Lista de Productos"));
        productListPanel.setBackground(Color.WHITE); // Fondo blanco
        mainPanel.add(productListPanel);

        productTableModel = new DefaultTableModel(new Object[] { "Nombre", "Precio", "Stock" }, 0);
        JTable productTable = new JTable(productTableModel);
        JScrollPane productScrollPane = new JScrollPane(productTable);
        productListPanel.add(productScrollPane, BorderLayout.CENTER);

        // Panel de historial de compras (boleta)
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Boleta"));
        historyPanel.setBackground(Color.WHITE); // Fondo blanco
        mainPanel.add(historyPanel);

        historyTableModel = new DefaultTableModel(new Object[] { "Nombre", "Precio Unitario", "Cantidad" }, 0);
        JTable historyTable = new JTable(historyTableModel);
        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);

        // Panel inferior para el total
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE); // Fondo blanco
        totalLabel = new JLabel("Total: S/. 0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        bottomPanel.add(totalLabel, BorderLayout.EAST);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(207, 235, 247)); // Color celeste claro
        contentPane.add(buttonPanel, BorderLayout.NORTH);

        JButton buyButton = new JButton("Comprar Producto");
        JButton finalizeButton = new JButton("Finalizar Compra");

        buttonPanel.add(buyButton);
        buttonPanel.add(finalizeButton);

        // Acción del botón Comprar Producto
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyProduct();
            }
        });

        // Acción del botón Finalizar Compra
        finalizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizePurchase();
            }
        });

        // Listar inicialmente los productos
        listProducts();
    }

    // Método para listar los productos
    private void listProducts() {
        try {
            productTableModel.setRowCount(0);
            HashMap<String, MedicineInterface> stock = pharm.getStockProducts();
            for (String key : stock.keySet()) {
                MedicineInterface med = stock.get(key);
                productTableModel.addRow(new Object[] { med.getName(), med.getPrice(), med.getStock() });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para realizar la compra de un producto
    private void buyProduct() {
        JDialog buyDialog = new JDialog(this, "Comprar Producto", true);
        buyDialog.setSize(400, 200);
        buyDialog.setLocationRelativeTo(this);
        buyDialog.setLayout(new GridLayout(3, 2, 10, 10));
        buyDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        buyDialog.add(new JLabel("Seleccione Producto:"));
        productComboBox = new JComboBox<>();
        try {
            HashMap<String, MedicineInterface> stock = pharm.getStockProducts();
            for (String key : stock.keySet()) {
                productComboBox.addItem(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        buyDialog.add(productComboBox);

        buyDialog.add(new JLabel("Cantidad:"));
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        buyDialog.add(quantitySpinner);

        JButton confirmButton = new JButton("Confirmar");
        JButton cancelButton = new JButton("Cancelar");

        buyDialog.add(confirmButton);
        buyDialog.add(cancelButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProduct = (String) productComboBox.getSelectedItem();
                int quantity = (Integer) quantitySpinner.getValue();
                try {
                    MedicineInterface purchased = pharm.buyMedicine(selectedProduct, quantity);
                    // Actualizar el modelo de tabla de historial
                    historyTableModel.addRow(new Object[] { purchased.getName(), purchased.getPrice(), quantity });
                    totalAmount += purchased.getPrice() * quantity;
                    totalLabel.setText("Total: S/." + String.format("%.2f", totalAmount));

                    // Actualizar la lista de productos
                    listProducts(); // Llamar al método para listar productos nuevamente
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(buyDialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                buyDialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyDialog.dispose();
            }
        });

        buyDialog.setVisible(true);
    }

    // Método para finalizar la compra
    private void finalizePurchase() {
        // Mostrar la boleta en una ventana de diálogo
        StringBuilder receipt = new StringBuilder();
        receipt.append("=== Boleta de Compra ===\n");
        for (int i = 0; i < historyTableModel.getRowCount(); i++) {
            String name = (String) historyTableModel.getValueAt(i, 0);
            double price = ((Number) historyTableModel.getValueAt(i, 1)).doubleValue();
            int quantity = (Integer) historyTableModel.getValueAt(i, 2);
            receipt.append(name).append(": ").append(quantity).append(" x S/. ").append(String.format("%.2f", price))
                    .append("\n");
        }
        receipt.append("\nTotal a Pagar: S/. ").append(String.format("%.2f", totalAmount));
        JOptionPane.showMessageDialog(this, receipt.toString(), "Boleta de Compra", JOptionPane.INFORMATION_MESSAGE);

        // Limpiar historial y actualizar total
        historyTableModel.setRowCount(0);
        totalAmount = 0;
        totalLabel.setText("Total: S/. 0.00");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClienteGUI().setVisible(true);
            }
        });
    }
}
