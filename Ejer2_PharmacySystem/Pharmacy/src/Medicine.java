import java.rmi.server.UnicastRemoteObject;

public class Medicine extends UnicastRemoteObject implements MedicineInterface {
    private String name;
    private float unitPrice;
    private int stock;

    public Medicine() throws Exception {
        super();
    }

    public Medicine(String name, float price, int stock) throws Exception {
        super();
        this.name = name;
        this.unitPrice = price;
        this.stock = stock;
    }

    @Override
    public Medicine getMedicine(int amount) throws Exception {
        if (this.stock <= 0)
            throw new StockException("Stock vacio");
        if (this.stock - amount < 0)
            throw new StockException("No hay suficiente Stock");

        this.stock -= amount;
        Medicine aux = new Medicine(name, unitPrice * amount, stock);
        return aux;
    }

    @Override
    public int getStock() throws Exception {
        return this.stock;
    }

    @Override
    public String print() throws Exception {
        return "Nombre: " + this.name + "\nPrecio: " + this.unitPrice + "\nStock: " + this.stock;
    }

    @Override
    public String getName() throws Exception {
        return this.name;
    }

    @Override
    public float getPrice() throws Exception {
        return this.unitPrice;
    }
}
