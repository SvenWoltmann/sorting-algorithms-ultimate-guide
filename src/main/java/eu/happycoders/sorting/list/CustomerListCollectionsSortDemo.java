package eu.happycoders.sorting.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomerListCollectionsSortDemo {
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

    // does not compile – Customer does not implement Comparable:
    // Collections.sort(customers);

    // does not compile either:
    // customers.sort(Comparator.naturalOrder());

    Collections.sort(customers, Comparator.comparing(Customer::lastName));
    customers.forEach(System.out::println);
  }
}
