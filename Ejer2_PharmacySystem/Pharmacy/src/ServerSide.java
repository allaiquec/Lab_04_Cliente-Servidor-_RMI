import java.rmi.*;

// Clase que representa el lado del servidor en la aplicación de la farmacia
public class ServerSide {
    public static void main(String[] args) throws Exception {
        // Crear una instancia de la clase Stock que representa la farmacia
        Stock pharmacy = new Stock();

        // Agregar medicamentos al inventario de la farmacia
        pharmacy.addMedicine("Paracetamol", 3.2f, 10);
        pharmacy.addMedicine("Mejoral", 2.0f, 20);
        pharmacy.addMedicine("Amoxilina", 1.0f, 30);
        pharmacy.addMedicine("Aspirina", 5.0f, 40);

        // Publicar el objeto remoto de la farmacia utilizando RMI
        Naming.rebind("PHARMACY", pharmacy);

        // Imprimir mensaje indicando que el servidor está listo
        System.out.println("Server ready");
    }
}
