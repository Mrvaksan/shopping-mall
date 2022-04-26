import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Shop {

    static void endShopping(ShoppingCart shoppingCart){
        //Sepeti yazdır
        shoppingCart.printProductList();
        //Ödeme yap
        System.out.println("TOPLAM ÖDEME : "+shoppingCart.getTotalPayment());
        //Sepeti boşalt
        shoppingCart.clear();
    }

    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        Product[] productList=new Product[64];
        int productCount=0;
        ShoppingCart shoppingCart=new ShoppingCart();

        //Dosyadan ürün listesini okuma
        Scanner fileInput;
        try {
            fileInput = new Scanner(new File("products.txt"));
        } catch (FileNotFoundException e) {
            //Dosya bulunamadığında hata mesajı yazdırıp programdan çıkıyoruz.
            System.out.println("products.txt bulunamadı.");
            return;
        }

        // Delimiter olarak hem satır sonu hem de virgül ayarladık
        fileInput.useDelimiter(",|\\n");

        //İngilizce diline göre okuyacağız
        fileInput.useLocale(Locale.ENGLISH);

        //Dosyada ürün bilgisi olduğu sürece devam eden bir döngü yazdık
        while (fileInput.hasNext()) {
            String name;
            double price;

            name=fileInput.next();
            price=fileInput.nextDouble();

            //dosyadan okuduğum name ve price değerleri ile yeni bir Product oluşturup 
            //productList dizisine ekledik
            productList[productCount]=new Product(name, price);
            productCount++;

            //maximum 64 product eklenebilir, çünkü Product[] productList=new Product[64];
            if(productCount==64){
                break;
            }
        }

        int option;

        while (true) {
            System.out.println("AKBABA MARKET");
            System.out.println("1. Sepete Ürün Ekle");
            System.out.println("2. Ödeme Yap");
            System.out.println("3. Çıkış");

            option=input.nextInt();

            switch (option) {
                case 1:
                    //Ürün listesi yazırılacak
                    //Kullanıcı almak istediği ürünün numarasını girecek
                    //Seçilen numara ile ilgili bir ürün yoksa, ürün listesi tekrar gösterilecek
                    // 0 seçilirse önceki menüye dönecek
                    //Sepetteki ürün sayısı 20'ye ulaştığında "Sepet doldu" uyarısı verip, ödeme sonucunu yazdıracak

                    while (true) {
                        System.out.println("Ürün listesi");
                        
                        for (int i = 0; i < productCount; i++) {
                            System.out.print((i+1)+" ");
                            productList[i].print();
                        }
                        System.out.println();

                        System.out.println("Almak istediğin ürün numarasını girin: (0: Geri)");
                        option=input.nextInt();

                        if(option==0){
                            break;
                        }else{
                            //ürünü kontrol et
                            if(option<=productCount){
                                //Sepete ekle
                                shoppingCart.addProduct(productList[option-1]);

                                //Sepeti yazdır
                                shoppingCart.printProductList();

                                if (shoppingCart.itemCount>=20) {
                                    System.out.println("Sepet doldu!");
                                    endShopping(shoppingCart);
                                }
                                break;
                            }else{
                                System.out.println("Ürün bulunamadı.");
                            }
                        }  
                    }

                    break;
                case 2:
                    //Seçilen ürünlerin listesi yazdırılacak
                    //Eğer henüz ürün seçilmemişse "Sepetinizde ürün yok" uyarısı yazdırılacak
                    //Ödeme yap seçildikten sonra sepet sıfırlanır.

                    if(shoppingCart.itemCount==0){
                        System.out.println("Sepetinizde ürün yok");
                    }else{
                        endShopping(shoppingCart);
                    }

                    break;
                case 3:
                    // eğer sepette ürün yoksa çıkış yapılacak, aksi halde menü yeniden yazdırılacak
                    if(shoppingCart.itemCount==0){
                        return;
                    }else{
                        System.out.println("Sepetinizde ürünler var. Ödeme yapın!");
                    }                    
                    break;
            
                default:
                    System.out.println("Yanlış giriş!");
                    break;
            }   
        }
    }
}