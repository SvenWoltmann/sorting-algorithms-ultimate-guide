package eu.happycoders.sort.jdk.array;

import java.util.Arrays;

public class CustomerArraySortWithoutComparableDemo {
  static void main() {
    Customer[] customers = {
      new Customer(43423, "Elizabeth", "Mann"),
      new Customer(10503, "Phil", "Gruber"),
      new Customer(61157, "Patrick", "Sonnenberg"),
      new Customer(28378, "Marina", "Metz"),
      new Customer(57299, "Caroline", "Albers")
    };
    // throws a ClassCastException – Customer does not implement Comparable
    Arrays.sort(customers);
    System.out.println(Arrays.toString(customers));
  }
}
