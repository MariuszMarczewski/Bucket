import java.time.LocalDate;
import java.util.*;

public class ShoppingCart {

    private Map<Product, Integer> list = new HashMap<>();
    private GiftCard giftCard;
    private Delivery delivery;
    private Summary summary;
    private int totalPrice;
    private int leftToPay;
    private LocalDate expectedShippingDate;
    private LocalDate expectedDeliveryDate;
    private List<CartItem> cartItemList = new ArrayList<>();



    //Important: leave the default constructor
    public ShoppingCart() {
    }

    //Important: implement the method. Otherwise you won't be able to pass tests.
    Summary getSummary() {
        return new Summary(cartItemList, getTotalPrice(list),
                giftCard, leftToPay, delivery, expectedShippingDate, expectedDeliveryDate);
    }

    void addProduct(Product product, int quantity) {
        if ((product != null) && (quantity > 0)) {
            int inbasket = list.getOrDefault(product, 0);
            list.put(product, product.getPrice() * (inbasket + quantity));
        }
        System.out.println("Product has not been added!");
    }

    void removeProduct(Product product, int quantity) {
        if ((product != null) && (quantity > 0)) {
            int inbasket = list.getOrDefault(product, 0);
            int newQuantity = inbasket - quantity;

            if (newQuantity > 0) {
                list.put(product, product.getPrice() * newQuantity);
            } else if (newQuantity == 0) {
                list.remove(product);
            }
        }
        System.out.println("Product has not been removed!");
    }

    void addExtraService(Product product, AdditionalService service) {
        if ((product != null) && (service != null)) {
            product.getServices().add(service);
        }
    }

    void removeExtraService(Product product, AdditionalService service) {
        if ((product != null) && (service != null)) {
            product.getServices().remove(service);
        }
    }

    void setDelivery(Delivery delivery) {
        new Delivery(delivery.getName(), delivery.getPrice(), delivery.getExpectedDeliveryTime());
    }


    int getTotalPrice(Map<Product, Integer> list) {
        for (Map.Entry<Product, Integer> item : list.entrySet()) {
            totalPrice += item.getValue();
        }
        return totalPrice;
    }

    void addGiftCard(GiftCard giftCard) {
        if (giftCard.getValue() <= leftToPay) {
            leftToPay -= giftCard.getValue();
        } else {
            leftToPay = 0;
        }
    }

    public int getLeftToPay() {
        return leftToPay;
    }

    List<CartItem> getCartItemList(Product product, List<AdditionalService> services, Map<Product, Integer> list){
        for(Map.Entry<Product, Integer> listItem : list.entrySet()){
        CartItem cartItem = new CartItem(product, product.getServices(),
                        list.get(product) / product.getPrice(), product.getPrice());
        cartItemList.add(cartItem);
        }
        return cartItemList;
    }
}

