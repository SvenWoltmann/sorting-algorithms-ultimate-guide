package eu.happycoders.sorting;

import java.util.Arrays;
import java.util.Comparator;

public class CustomerArraySortByIdDescDemo {
  // the record with a natural order by customer number
  record Customer(int id, String firstName, String lastName) implements Comparable<Customer> {

    @Override
    public int compareTo(Customer other) {
      return Integer.compare(id, other.id);
    }
  }

  static void main() {
    Customer[] customers = {
      new Customer(43423, "Elizabeth", "Mann"),
      new Customer(10503, "Phil", "Gruber"),
      new Customer(61157, "Patrick", "Sonnenberg"),
      new Customer(28378, "Marina", "Metz"),
      new Customer(57299, "Caroline", "Albers")
    };
    Arrays.sort(customers, Comparator.reverseOrder());
    System.out.println(Arrays.toString(customers));
  }
}
