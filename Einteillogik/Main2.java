package Einteillogik;

import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import javafx.scene.Scene;
import test.App;

public class Main2 extends Application {

    private static Random rnd = new Random();


    // variables for mouse interaction
    private double mousePosX, mousePosY;
    private double mouseOldX, mouseOldY;
    private final Rotate rotateX = new Rotate(20, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(-45, Rotate.Y_AXIS);

    private double anchorX, anchorY;




    @Override
    public void start(Stage primaryStage) {
        int width = 1500;
        int heigth = 900;
        Label label = new Label("name");


        //CSV einlesen....
        Book steinDerWeisen = new Book("Stein der Weisen", 1600);
        steinDerWeisen.setChapters(new Chapter[] {
                new Chapter("Chapter 1", 700, new String[] {"Hermine1","Potter1","Dumbledore1"}),
                new Chapter("Chapter 2", 300, new String[] {"Potter2","Ron1"}),
                new Chapter("Chapter 3", 300, new String[] {"Potter3","Ron2"}),
                new Chapter("Chapter 4", 300, new String[] {"Potter4","Ron3","Potter5","Ron4"})
        });

        Book kammerDesSchreckens = new Book("Kammer des Schreckens", 450);
        kammerDesSchreckens.setChapters(new Chapter[] {
                new Chapter("Chapter 1", 100, new String[] {"Dumbledore2","Ron5","Harry1"}),
                new Chapter("Chapter 2", 150, new String[] {"Harry2","Ron6"}),
                new Chapter("Chapter 3", 200, new String[] {"Dumbledore3","Hermine2"})
        });

        Book gefangeneVonAKB = new Book("Gefangene von Askaban", 250);
        gefangeneVonAKB.setChapters(new Chapter[] {
                new Chapter("Chapter 1", 100, new String[] {"Ron7","Harry3","Hermine3"}),
                new Chapter("Chapter 2", 150, new String[] {"Tonks","Dumbledore4"})
        });
        Book[] books = new Book[] {steinDerWeisen, kammerDesSchreckens, gefangeneVonAKB};



        /**
         * Horizontal -> Buecher
         * Vertikal -> Chapter
         * Horizontal -> Charaktere
         */

        ArrayList<CustomNode> harryPotterWorldNodes = new ArrayList<CustomNode>();

        int overallPages = 0;
        for (Book book : books) {
            overallPages = overallPages + book.getPages();
        }

        for (Book book : books) {

            CustomNode bookNode = new CustomNode();
            bookNode.setType(NodeType.BOOK);
            bookNode.setName(book.getName());
            bookNode.setMetric(getMetricFromPrimaryInt(book.getPages(),overallPages));

            for (Chapter chapter : book.chapters) {

                CustomNode chapterNode = new CustomNode();
                chapterNode.setType(NodeType.CHAPTER);
                chapterNode.setName(chapter.getName());
                chapterNode.setMetric(getMetricFromPrimaryInt(chapter.getPages(), book.getPages()));

                for (String character : chapter.characters) {

                    CustomNode characterNode = new CustomNode();
                    characterNode.setType(NodeType.CHARACTER);
                    characterNode.setName(character);
                    characterNode.setMetric(getMetricFromPrimaryInt(1, chapter.characters.length));

                    //Das neue child node dem parent hinzufügen
                    chapterNode.extendChildren(characterNode);

                }

                //Das neue child node dem parent hinzufügen
                bookNode.extendChildren(chapterNode);

            }

            harryPotterWorldNodes.add(bookNode);

        }


        //Für testen
        for (CustomNode n : harryPotterWorldNodes) {
            CustomNode worldNode = new CustomNode();
            worldNode.setMetric(1.00);
            worldNode.setChildren(harryPotterWorldNodes);			worldNode.computeSize(width, heigth);
            System.out.println("BOOK --> " + n.getName() + "  --  METRIC -> " + n.getMetric()*100 + "%" + "  -  HEIGHT -> " + n.getHeight() + "  -  WIDTH -> " + n.getWidth());
            for (CustomNode c1 : n.getChildren()) {
                System.out.println("  " + c1.getName() + "  --  METRIC -> " + c1.getMetric()*100 + "%" + "  -  HEIGHT -> " + c1.getHeight() + "  -  WIDTH -> " + c1.getWidth());
                for (CustomNode c2 : c1.getChildren()) {
                    System.out.println("    " + c2.getName() + "  --  METRIC -> " + c2.getMetric()*100 + "%" + "  -  HEIGHT -> " + c2.getHeight() + "  -  WIDTH -> " + c2.getWidth());
                }
            }
            break;

			/*System.out.println("BOOK --> " + n.getName() + "  --  METRIC -> " + n.getMetric()*100 + "%");
			for (Node c1 : n.getChildren()) {
			  System.out.println("  " + c1.getName() + "  --  METRIC -> " + c1.getMetric()*100 + "%");
			  for (Node c2 : c1.getChildren()) {
			  	System.out.println("    " + c2.getName() + "  --  METRIC -> " + c2.getMetric()*100 + "%");
			  }
			}*/
        }





        // create axis walls
        Group grid = new Group();

        // initial cube rotation
        grid.getTransforms().addAll(rotateX, rotateY);

        // add objects to scene
        StackPane root = new StackPane();
        root.getChildren().add(grid);


        double xStart = heigth/2;
        double yStart = width/2;

        double charhöhe = 0;

        ArrayList<CustomBox> Boxliste = new ArrayList<CustomBox>();




        for (CustomNode book : harryPotterWorldNodes) {

            CustomBox boxbook = new CustomBox(book.getHeight(), 2, book.getWidth());

            // color
            PhongMaterial matbook = new PhongMaterial();
            matbook.setDiffuseColor(randomColor());
            boxbook.setMaterial(matbook);

            // location
            boxbook.setTranslateX(xStart-(book.getHeight()/2));
            boxbook.setTranslateZ(yStart-(book.getWidth()/2));
            boxbook.setTranslateY(2);

            //labels
            boxbook.setBookname(book.getName());

            grid.getChildren().addAll(boxbook);

            for (CustomNode chapt:book.getChildren()) {

                CustomBox boxchap = new CustomBox(chapt.getHeight(), 4, chapt.getWidth());

                // color
                PhongMaterial matchap = new PhongMaterial();
                matchap.setDiffuseColor(randomColor());
                boxchap.setMaterial(matchap);

                // location
                boxchap.setTranslateX(xStart-(chapt.getHeight()/2));
                boxchap.setTranslateZ(yStart-(chapt.getWidth()/2));
                boxchap.setTranslateY(4);

                //labels
                boxchap.setBookname(book.getName());
                boxchap.setChaptername(chapt.getName());

                grid.getChildren().addAll(boxchap);

                for (CustomNode chars: chapt.getChildren()) {
                    double height = rnd.nextDouble() * 500;

                    CustomBox box = new CustomBox(chars.getHeight(), height, chars.getWidth());

                    // color
                    PhongMaterial mat = new PhongMaterial();
                    mat.setDiffuseColor(randomColor());
                    box.setMaterial(mat);

                    // location
                    box.setLayoutY(-height * 0.5);
                    box.setTranslateX(xStart-(chars.getHeight()/2));
                    box.setTranslateZ(yStart-(chars.getWidth()/2));

                    //labels
                    box.setBookname(book.getName());
                    box.setChaptername(chapt.getName());
                    box.setCharactername(chars.getName());

                    grid.getChildren().addAll(box);
                    Boxliste.add(box);


                    xStart = xStart - chars.getHeight();
                    charhöhe = charhöhe + chars.getHeight();
                }


                yStart = yStart - (chapt.getWidth()) - 50;
                xStart = xStart + charhöhe;
                charhöhe = 0;
            }
            yStart = width/2;

            charhöhe = 0;

            xStart = xStart - book.getHeight() - 200;
        }

        // scene
        Scene scene = new Scene(root, width, heigth, true, SceneAntialiasing.BALANCED);
        Camera camera = new PerspectiveCamera();
        scene.setCamera(camera);

        scene.setOnMousePressed(me ->
        {
            if (me.getButton() == MouseButton.PRIMARY) {
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            }

            else if (me.getButton() == MouseButton.SECONDARY) {

                anchorX = me.getSceneX();
                anchorY = me.getSceneY();

            }

        });

        scene.setOnMouseDragged(me ->
        {
            if (me.getButton() == MouseButton.PRIMARY) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
                rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
            }

            else if (me.getButton() == MouseButton.SECONDARY) {
                camera.setTranslateY(anchorY - me.getSceneY());
                camera.setTranslateX(anchorX - me.getSceneX());
            }

        });

        scene.setOnMouseReleased(me ->
        {
            if (me.getButton() == MouseButton.SECONDARY) {

            anchorX = camera.getTranslateX();
            anchorY = camera.getTranslateY();

        }});

        //labelview
        scene.setOnMouseClicked(t -> {
            PickResult result = t.getPickResult();
            javafx.scene.Node intersectedNode = result.getIntersectedNode();

            if (intersectedNode instanceof CustomBox) {
                CustomBox clickedBox = (CustomBox) intersectedNode;
                System.out.print("works");
            }

        });




        /*EventHandler<MouseEvent> hnd = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent ev) {

                for (Box b : Boxliste) {


                    if (ev.getSource() == b) {
                        System.out.print("works");
                    }

                }
            }
        };

        for (Box bo : Boxliste){
            bo.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, hnd);
        }
*/


        makeZoomable(root);


        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }





    //addtext
    /*
    public void addtext () {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        Label label1 = new Label("2.2.Harry");
        GridPane.setHalignment(label1, HPos.CENTER);

    }*/

    public void makeZoomable(StackPane control) {

        final double MAX_SCALE = 20.0;
        final double MIN_SCALE = 0.1;

        control.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {

            @Override
            public void handle(ScrollEvent event) {

                double delta = 1.2;

                double scale = control.getScaleX();

                if (event.getDeltaY() < 0) {
                    scale /= delta;
                } else {
                    scale *= delta;
                }

                scale = clamp(scale, MIN_SCALE, MAX_SCALE);

                control.setScaleX(scale);
                control.setScaleY(scale);

                event.consume();

            }

        });

    }


    public static double clamp(double value, double min, double max) {

        if (Double.compare(value, min) < 0)
            return min;

        if (Double.compare(value, max) > 0)
            return max;

        return value;
    }

    public static Color randomColor() {
        return Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
    }

    public static void main(String[] args) {
        launch(args);

    }

    public static double getMetricFromPrimaryInt(int val1, int val2) {
        return (double)val1 / (double)val2;
    }
}