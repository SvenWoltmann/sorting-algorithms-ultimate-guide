package eu.happycoders.sort.jdk.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomerListStableSortDemo {
  static void main() {
    List<Customer> customers =
        new ArrayList<>(
            List.of(
                new Customer(52118, "Thomas", "Mann"),
                new Customer(10503, "Phil", "Gruber"),
                new Customer(61157, "Patrick", "Sonnenberg"),
                new Customer(28378, "Marina", "Metz"),
                new Customer(57299, "Caroline", "Albers"),
                new Customer(43423, "Elizabeth", "Mann")));

    customers.sort(Comparator.comparingInt(Customer::id));
    customers.forEach(System.out::println);

    System.out.println();

    customers.sort(Comparator.comparing(Customer::lastName));
    customers.forEach(System.out::println);
  }
}
