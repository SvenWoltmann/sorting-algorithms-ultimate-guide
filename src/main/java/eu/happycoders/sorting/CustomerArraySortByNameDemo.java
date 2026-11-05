package eu.happycoders.sorting;

import java.util.Arrays;
import java.util.Comparator;

public class CustomerArraySortByNameDemo {
  static void main() {
    Customer[] customers = {
      new Customer(43423, "Elizabeth", "Mann"),
      new Customer(10503, "Phil", "Gruber"),
      new Customer(61157, "Patrick", "Sonnenberg"),
      new Customer(28378, "Marina", "Metz"),
      new Customer(57299, "Caroline", "Albers")
    };
    Arrays.sort(
        customers, Comparator.comparing(Customer::lastName).thenComparing(Customer::firstName));
    System.out.println(Arrays.toString(customers));
  }
}
