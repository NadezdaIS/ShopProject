import controllers.MenuController;
import controllers.ProductController;
import services.ProductServices;

public class Main {
    public static void main(String[] args) {
       // MenuController.start();
        ProductServices productServices = new ProductServices();
        ProductController.addNewProduct(productServices);
    }
}
