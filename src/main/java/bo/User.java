package bo;

import java.util.ArrayList;
import java.util.Objects;

public class User {

    private final int id;
    private String username;
    private String password;
    private final ArrayList<Product> shoppingBag;

    protected User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.shoppingBag = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Product> getShoppingBag() {
        return new ArrayList<>(shoppingBag);
    }

    public boolean addProduct(Product product) {
        if (shoppingBag.contains(product)) {
            return false;
        }
        shoppingBag.add(product);
        return true;
    }

    public boolean removeProduct(Product product) {
        if (shoppingBag.contains(product)) {
            shoppingBag.remove(product);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}