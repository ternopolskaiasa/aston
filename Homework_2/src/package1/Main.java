package package1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
     public static void main (String[] args) {
          List<Student> students = new ArrayList<>();
          students.add(new Student("Gregory Jeff", 13524,
                       Arrays.asList(new Book("War and Peace", 1225, 1869),
                       new Book("Gone with the Wind", 1024, 1936),
                       new Book("Harry Potter and the Half-Blood Prince", 435, 2005),
                       new Book("The Little Prince", 96, 1943),
                       new Book("Anxious people", 328, 2019),
                       new Book("The Kingdom of thorns and roses", 512, 2015),
                       new Book("The Great Gatsby", 180, 1925))));
          students.add(new Student("Sophia Parker", 13523,
                       Arrays.asList(new Book("One Hundred years of solitude", 426, 1967),
                       new Book("The Master and Margarita", 528, 1966),
                       new Book("The Alchemist", 160, 1988),
                       new Book("The Da Vinci Code", 489, 2003),
                       new Book("After", 480, 2014),
                       new Book("The Guardians", 480, 2005),
                       new Book("The girl on the train", 384, 2015),
                       new Book("The Catcher in the Rye", 277, 1951),
                       new Book("Three Comrades", 416, 1938))));
          students.add(new Student("Alison Chen", 13522,
                       Arrays.asList(new Book("Flowers for Algernon", 288, 1966),
                       new Book("Shadow in the Maze", 256, 2025),
                       new Book("Portrait of Dorian Gray", 320, 1890),
                       new Book("The Maze Runner", 384, 2009),
                       new Book("Martin Eden", 384, 1909),
                       new Book("Fifty Shades Freed", 672, 2012),
                       new Book("The Divine Comedy", 672, 1321),
                       new Book("Three Sisters", 96, 1901))));
          students.add(new Student("Ocean Parks", 13521,
                       Arrays.asList(new Book("Journey to Eleusis", 635, 2023),
                       new Book("To Kill a Mockingbird", 336, 1960),
                       new Book("Game of Thrones", 736, 1996),
                       new Book("The Three-body Problem", 464, 2008),
                       new Book("Fifty Shades Of Grey", 532, 2011),
                       new Book("The Girl in the fog", 352, 2015),
                       new Book("Zuleikha opens her eyes", 512, 2015))));
          students.add(new Student("Shara Bishop", 13520,
                       Arrays.asList(new Book("Metro 2033", 416, 2005),
                       new Book("The house in which...", 960, 2009),
                       new Book("All the light invisible to us", 480, 2014),
                       new Book("The Fault in Our Stars", 352, 2012),
                       new Book("The Guardians", 480, 2005),
                       new Book("A Song of Ice and Fire", 1024, 1996),
                       new Book("Disappeared", 448, 2012))));
          students.add(new Student("Luther Jefferson", 13519,
                       Arrays.asList(new Book("Palaces of Reason", 320, 2018),
                       new Book("Seven Sisters", 480,2014),
                       new Book("The Book of new recipes", 512, 2019),
                       new Book("The Girl with the Dragon Tattoo", 512, 2015),
                       new Book("Divergent", 480, 2011),
                       new Book("The Hunger Games", 384, 2008),
                       new Book("Faust", 384, 1808))));

          students.stream()
                  .peek(System.out::println)                                          // print each student
                  .map(x -> x.getBooks())                                             // get lists of books
                  .flatMap(List::stream)
                  .sorted(Comparator.comparingInt(x -> x.getNumberOfPages()))         // sort by amount of pages
                  .distinct()                                                         // keep only unique books
                  .filter(book -> book.getPublished() > 2000)                         // filter books with published year > 2000
                  .limit(3)                                                           // keep 3 elements
                  .map(x -> x.getPublished())                                         // get publishing years
                  .findFirst()                                                        // short circuit
                  .ifPresentOrElse(
                          x -> System.out.println("Found published year: " + x),
                          () -> System.out.println("There is no suitable book")
                  );                                                                  // result through Optional
     }
}
