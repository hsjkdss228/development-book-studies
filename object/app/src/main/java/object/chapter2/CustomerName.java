package object.chapter2;

public class CustomerName {
    public String name;

    private CustomerName(String name) {
        this.name = name;
    }

    public static CustomerName of(String name) {
        return new CustomerName(name);
    }

    public String value() {
        return name;
    }
}
