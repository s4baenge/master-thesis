package Einteillogik;

public class Chapter {

    String name;
    int pages;
    String[] characters;
    //Int [] charactersum;
    //Int [] sentiment;

    public Chapter(String name, int pages, String[] characters) {
        this.name = name;
        this.pages = pages;
        this.characters = characters;
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

    public String[] getCharacters() {
        return characters;
    }

    public void setCharacters(String[] characters) {
        this.characters = characters;
    }

}
