import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Shop {

    static void endShopping(ShoppingCart shoppingCart){
        //Sepeti yazd�r
        shoppingCart.printProductList();
        //�deme yap
        System.out.println("TOPLAM �DEME : "+shoppingCart.getTotalPayment());
        //Sepeti bo�alt
        shoppingCart.clear();
    }

    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        Product[] productList=new Product[64];
        int productCount=0;
        ShoppingCart shoppingCart=new ShoppingCart();

        //Dosyadan �r�n listesini okuma
        Scanner fileInput;
        try {
            fileInput = new Scanner(new File("products.txt"));
        } catch (FileNotFoundException e) {
            //Dosya bulunamad���nda hata mesaj� yazd�r�p programdan ��k�yoruz.
            System.out.println("products.txt bulunamad�.");
            return;
        }

        // Delimiter olarak hem sat�r sonu hem de virg�l ayarlad�k
        fileInput.useDelimiter(",|\\n|\\r\\n");

        //�ngilizce diline g�re okuyaca��z
        fileInput.useLocale(Locale.ENGLISH);

        //Dosyada �r�n bilgisi oldu�u s�rece devam eden bir d�ng� yazd�k
        while (fileInput.hasNext()) {
            String name;
            double price;

            name=fileInput.next();
            price=fileInput.nextDouble();

            //dosyadan okudu�um name ve price de�erleri ile yeni bir Product olu�turup 
            //productList dizisine ekledik
            productList[productCount]=new Product(name, price);
            productCount++;

            //maximum 64 product eklenebilir, ��nk� Product[] productList=new Product[64];
            if(productCount==64){
                break;
            }
        }

        int option;

        while (true) {
            System.out.println("CAN MARKET");
            System.out.println("1. Sepete �r�n Ekle");
            System.out.println("2. �deme Yap");
            System.out.println("3. ��k��");

            option=input.nextInt();

            switch (option) {
                case 1:
                    //�r�n listesi yaz�r�lacak
                    //Kullan�c� almak istedi�i �r�n�n numaras�n� girecek
                    //Se�ilen numara ile ilgili bir �r�n yoksa, �r�n listesi tekrar g�sterilecek
                    // 0 se�ilirse �nceki men�ye d�necek
                    //Sepetteki �r�n say�s� 20'ye ula�t���nda "Sepet doldu" uyar�s� verip, �deme sonucunu yazd�racak

                    while (true) {
                        System.out.println("�r�n listesi");
                        
                        for (int i = 0; i < productCount; i++) {
                            System.out.print((i+1)+" ");
                            productList[i].print();
                        }
                        System.out.println();

                        System.out.println("Almak istedi�in �r�n numaras�n� girin: (0: Geri)");
                        option=input.nextInt();

                        if(option==0){
                            break;
                        }else{
                            //�r�n� kontrol et
                            if(option<=productCount){
                                //Sepete ekle
                                shoppingCart.addProduct(productList[option-1]);

                                //Sepeti yazd�r
                                shoppingCart.printProductList();

                                if (shoppingCart.itemCount>=20) {
                                    System.out.println("Sepet doldu!");
                                    endShopping(shoppingCart);
                                }
                                break;
                            }else{
                                System.out.println("�r�n bulunamad�.");
                            }
                        }  
                    }

                    break;
                case 2:
                    //Se�ilen �r�nlerin listesi yazd�r�lacak
                    //E�er hen�z �r�n se�ilmemi�se "Sepetinizde �r�n yok" uyar�s� yazd�r�lacak
                    //�deme yap se�ildikten sonra sepet s�f�rlan�r.

                    if(shoppingCart.itemCount==0){
                        System.out.println("Sepetinizde �r�n yok");
                    }else{
                        endShopping(shoppingCart);
                    }

                    break;
                case 3:
                    // e�er sepette �r�n yoksa ��k�� yap�lacak, aksi halde men� yeniden yazd�r�lacak
                    if(shoppingCart.itemCount==0){
                        return;
                    }else{
                        System.out.println("Sepetinizde �r�nler var. �deme yap�n!");
                    }                    
                    break;
            
                default:
                    System.out.println("Yanl�� giri�!");
                    break;
            }   
        }
    }
}