package eu.happycoders.sorting.list;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StringListCollatorDemo {
  static void main() {
    List<String> names =
        new ArrayList<>(List.of("Zimmermann", "de Vries", "Özdemir", "Becker", "Ohm"));

    names.sort(null);
    System.out.println(names);

    names.sort(String.CASE_INSENSITIVE_ORDER);
    System.out.println(names);

    names.sort(Collator.getInstance(Locale.GERMAN));
    System.out.println(names);
  }
}
