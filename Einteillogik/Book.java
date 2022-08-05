package Einteillogik;

public class Book {

    String name;
    int pages;
    Chapter[] chapters;

    public Book(String name, int pages) {
        this.name = name;
        this.pages = pages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Chapter[] getChapters() {
        return chapters;
    }
    public void setChapters(Chapter[] chapters) {
        this.chapters = chapters;
    }


}
