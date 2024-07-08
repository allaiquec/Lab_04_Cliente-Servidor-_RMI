package Cards;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CardInterface extends Remote {
    // Métodos remotos para interacción con la tarjeta de crédito
    double verSaldo() throws RemoteException;
    void recargarSaldo(double monto) throws RemoteException;
    void realizarCompra(double monto) throws RemoteException;
    void retirarSaldo(double monto) throws RemoteException;
    List<String> obtenerProductos() throws RemoteException;
    double obtenerPrecioProducto(String producto) throws RemoteException;
    List<String> obtenerHistorial() throws RemoteException;
}
