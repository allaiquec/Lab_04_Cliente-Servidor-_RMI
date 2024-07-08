package Cards;

import java.rmi.Naming; // Para registrar y buscar objetos remotos
import java.util.List; // Para trabajar con listas
import java.util.Scanner; // Para entrada de usuario desde la consola

public class Client {
    public static void main(String[] args) {
        try {
            // Buscando el objeto remoto del servidor
            CardInterface server = (CardInterface) Naming.lookup("Server");
            
            // Configurando entrada de usuario
            Scanner scanner = new Scanner(System.in);
            int opcion;
            
            // Menú de opciones
            do {
                System.out.println("Seleccione una opción: ");
                System.out.println("1. Ver saldo actual");
                System.out.println("2. Recargar saldo");
                System.out.println("3. Realizar compra");
                System.out.println("4. Retirar saldo");
                System.out.println("5. Ver historial de transacciones");
                System.out.println("6. Salir");
                opcion = scanner.nextInt();
                
                switch (opcion) {
                    case 1:
                        // Obteniendo y mostrando el saldo actual
                        double saldo = server.verSaldo();
                        System.out.println("Saldo actual: " + saldo);
                        break;
                    case 2:
                        // Solicitando y realizando recarga de saldo
                        System.out.println("Ingrese la cantidad a recargar: ");
                        double recarga = scanner.nextDouble();
                        server.recargarSaldo(recarga);
                        System.out.println("Saldo recargado de " + recarga);
                        break;
                    case 3:
                        // Solicitando y realizando compra
                        int opcionCompra;
                    do {
                        System.out.println("Seleccione una opción:");
                        System.out.println("1. Listar productos");
                        System.out.println("2. Realizar compra");
                        System.out.println("3. Volver al menú principal");
                        opcionCompra = scanner.nextInt();

                        switch (opcionCompra) {
                            case 1:
                            // Lógica para listar productos
                                List<String> productos = server.obtenerProductos();
                                System.out.println("Productos disponibles:");
                                for (String producto : productos) 
                                    System.out.println(producto);
                                
                            break;
                        
                            case 2:
                            // Lógica para realizar compra
                            System.out.println("Ingrese el nombre del producto a comprar:");
                            scanner.nextLine(); // Consumir el salto de línea pendiente
                            String productoCompra = scanner.nextLine();
                            double precioProducto = server.obtenerPrecioProducto(productoCompra);
                            server.realizarCompra(precioProducto);
                            System.out.println("Compra realizada de: " + precioProducto);
                        
                            break;
                        }
                    } while (opcionCompra != 3);
                        break;

                    case 4:
                        // Solicitando y realizando retiro de saldo
                        System.out.println("Ingrese la cantidad a retirar: ");
                        double retiro = scanner.nextDouble();
                        server.retirarSaldo(retiro);
                        System.out.println("Retiro realizado de "+ retiro);
                        break;
                    case 5:
                        // Imprimir el historial de transacciones
                        List<String> historial = server.obtenerHistorial();
                        System.out.println("Historial de transacciones:");
                        for (String transaccion : historial) {
                            System.out.println(transaccion);
                        }
                        break;
                }
            } while (opcion != 6); // Salir del menú cuando la opción sea 6
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
