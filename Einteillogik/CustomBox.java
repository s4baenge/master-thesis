package Einteillogik;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;

class CustomBox extends Box {

    private String charactername;
    private String bookname;
    private String chaptername;

    PhongMaterial material = new PhongMaterial();

    public CustomBox (double x, double y, double z) {

        super (x, y, z);

    }

    public String getBookname () {
        return bookname;
    }

    public String getChaptername (){
        return chaptername;
    }

    public String getCharactername () {

        return charactername;
    }

    public void setBookname (String bookname) {

        this.bookname = bookname;

    }

    public void setChaptername (String chaptername) {

        this.chaptername = chaptername;

    }

    public void setCharactername (String chaptername) {

        this.chaptername = chaptername;

    }

}