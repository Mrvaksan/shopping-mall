public class Product {
    String name;
    double price;

    public Product(String name, double price){
        this.name=name;
        this.price=price;
    }

    void print(){
        System.out.printf("%-12s%12.2f\n",name,price);
    }
}
