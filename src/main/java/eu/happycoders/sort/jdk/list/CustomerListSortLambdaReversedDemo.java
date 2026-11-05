package eu.happycoders.sort.jdk.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomerListSortLambdaReversedDemo {
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

    // does not compile – without a target type, c is of type Object:
    // customers.sort(Comparator.comparing(c -> c.lastName()).reversed());

    customers.sort(Comparator.comparing((Customer c) -> c.lastName()).reversed());
    customers.forEach(System.out::println);
  }
}
