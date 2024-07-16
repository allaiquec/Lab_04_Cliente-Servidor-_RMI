// Excepción personalizada para manejar errores relacionados con el stock de medicamentos
public class StockException extends Exception {

    // Constructor que acepta un mensaje de error como parámetro
    public StockException(String msg) {
        // Llama al constructor de la clase base (Exception) con el mensaje de error
        super(msg);
    }
}
