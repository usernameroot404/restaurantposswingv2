import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem item) {
        for (CartItem i : items) {
            if (i.getMenuId() == item.getMenuId()) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }

    public void clear() {
        items.clear();
    }

    public java.math.BigDecimal getTotal() {
        return items.stream()
            .map(CartItem::getTotalPrice)
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }
}
