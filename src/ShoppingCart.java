public class ShoppingCart {
    Product[] products=new Product[20];
    int itemCount=0;

    void addProduct(Product product){
        products[itemCount]=new Product(product.name,product.price);
        itemCount++;
    }

    void clear(){
        for (int i = 0; i < products.length; i++) {
            products[i]=null;
        }

        itemCount=0;
    } 

    void printProductList(){
        for (int i = 0; i < itemCount; i++) {
            System.out.print((i+1)+" ");
            products[i].print();
        }
    }

    double getTotalPayment(){
        double totalPayment=0;
        for (int i = 0; i < itemCount; i++) {
            totalPayment+=products[i].price;
        }
        return totalPayment;
    }
}
