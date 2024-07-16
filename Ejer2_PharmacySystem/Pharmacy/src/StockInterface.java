import java.rmi.Remote;
import java.util.*;

// Interfaz remota para manipular el inventario de la farmacia
public interface StockInterface extends Remote {

    // Método remoto para obtener el inventario completo de medicamentos
    public HashMap<String, MedicineInterface> getStockProducts() throws Exception;

    // Método remoto para agregar un nuevo medicamento al inventario
    public void addMedicine(String name, float price, int stock) throws Exception;

    // Método remoto para comprar un medicamento del inventario
    public MedicineInterface buyMedicine(String name, int amount) throws Exception;
}
