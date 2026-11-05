package eu.happycoders.sorting.list;

import java.util.ArrayList;
import java.util.List;

public class CustomerListSortComparableDemo {
  // shadows eu.happycoders.sorting.list.Customer – this variant implements Comparable
  record Customer(int id, String firstName, String lastName) implements Comparable<Customer> {

    @Override
    public int compareTo(Customer other) {
      return Integer.compare(id, other.id);
    }
  }

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

    customers.sort(null);
    customers.forEach(System.out::println);
  }
}
