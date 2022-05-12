import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Shop {

    static void endShopping(ShoppingCart shoppingCart){
        //Sepeti yazdýr
        shoppingCart.printProductList();
        //Ödeme yap
        System.out.println("TOPLAM ÖDEME : "+shoppingCart.getTotalPayment());
        //Sepeti boþalt
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
            //Dosya bulunamadýðýnda hata mesajý yazdýrýp programdan çýkýyoruz.
            System.out.println("products.txt bulunamadý.");
            return;
        }

        // Delimiter olarak hem satýr sonu hem de virgül ayarladýk
        fileInput.useDelimiter(",|\\n|\\r\\n");

        //Ýngilizce diline göre okuyacaðýz
        fileInput.useLocale(Locale.ENGLISH);

        //Dosyada ürün bilgisi olduðu sürece devam eden bir döngü yazdýk
        while (fileInput.hasNext()) {
            String name;
            double price;

            name=fileInput.next();
            price=fileInput.nextDouble();

            //dosyadan okuduðum name ve price deðerleri ile yeni bir Product oluþturup 
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
            System.out.println("CAN MARKET");
            System.out.println("1. Sepete Ürün Ekle");
            System.out.println("2. Ödeme Yap");
            System.out.println("3. Çýkýþ");

            option=input.nextInt();

            switch (option) {
                case 1:
                    //Ürün listesi yazýrýlacak
                    //Kullanýcý almak istediði ürünün numarasýný girecek
                    //Seçilen numara ile ilgili bir ürün yoksa, ürün listesi tekrar gösterilecek
                    // 0 seçilirse önceki menüye dönecek
                    //Sepetteki ürün sayýsý 20'ye ulaþtýðýnda "Sepet doldu" uyarýsý verip, ödeme sonucunu yazdýracak

                    while (true) {
                        System.out.println("Ürün listesi");
                        
                        for (int i = 0; i < productCount; i++) {
                            System.out.print((i+1)+" ");
                            productList[i].print();
                        }
                        System.out.println();

                        System.out.println("Almak istediðin ürün numarasýný girin: (0: Geri)");
                        option=input.nextInt();

                        if(option==0){
                            break;
                        }else{
                            //ürünü kontrol et
                            if(option<=productCount){
                                //Sepete ekle
                                shoppingCart.addProduct(productList[option-1]);

                                //Sepeti yazdýr
                                shoppingCart.printProductList();

                                if (shoppingCart.itemCount>=20) {
                                    System.out.println("Sepet doldu!");
                                    endShopping(shoppingCart);
                                }
                                break;
                            }else{
                                System.out.println("Ürün bulunamadý.");
                            }
                        }  
                    }

                    break;
                case 2:
                    //Seçilen ürünlerin listesi yazdýrýlacak
                    //Eðer henüz ürün seçilmemiþse "Sepetinizde ürün yok" uyarýsý yazdýrýlacak
                    //Ödeme yap seçildikten sonra sepet sýfýrlanýr.

                    if(shoppingCart.itemCount==0){
                        System.out.println("Sepetinizde ürün yok");
                    }else{
                        endShopping(shoppingCart);
                    }

                    break;
                case 3:
                    // eðer sepette ürün yoksa çýkýþ yapýlacak, aksi halde menü yeniden yazdýrýlacak
                    if(shoppingCart.itemCount==0){
                        return;
                    }else{
                        System.out.println("Sepetinizde ürünler var. Ödeme yapýn!");
                    }                    
                    break;
            
                default:
                    System.out.println("Yanlýþ giriþ!");
                    break;
            }   
        }
    }
}