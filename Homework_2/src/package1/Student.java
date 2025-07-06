package package1;

import java.util.List;

public class Student {
    String name;
    int groupNumber;
    List<Book> books;

    public Student(String name, int groupNumber, List<Book> books) {
        this.name = name;
        this.groupNumber = groupNumber;
        this.books = books;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    @Override
    public String toString() {
        return "Student{name = " + name + ", groupNumber = " + groupNumber + ", books = " + books + "}";
    }
}
