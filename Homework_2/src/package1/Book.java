package package1;

import java.util.Comparator;

class Book implements Comparator<Book> {
    String name;
    int numberOfPages;
    int published;

    public Book(String name, int numberOfPages, int published){
        this.name = name;
        this.numberOfPages = numberOfPages;
        this.published = published;
    }

    public int getNumberOfPages(){
        return this.numberOfPages;
    }

    public int getPublished(){
        return this.published;
    }

    @Override
    public String toString() {
        return "{title = " + name + ", numberOfPages = " + numberOfPages + ", yearOfPublication = " + published + "}";
    }

    @Override
    public int compare(Book o1, Book o2) {
        return Integer.compare(o1.numberOfPages, o2.numberOfPages);
    }
}
