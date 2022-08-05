package Einteillogik;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.PickResult;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("name");
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-10);

        Group group3d = new Group(camera, new CustomSphere("violet sphere ", Color.BLUEVIOLET, 0.8, 0),
                new CustomSphere("coral sphere ", Color.CORAL, 0.7, 2),
                new CustomSphere("yellow-green sphere ", Color.YELLOWGREEN, 0.9, -2));

        SubScene subScene = new SubScene(group3d, 480, 320, true, SceneAntialiasing.DISABLED);
        subScene.setOnMouseClicked(t -> {
            PickResult result = t.getPickResult();
            Node intersectedNode = result.getIntersectedNode();

            if (intersectedNode instanceof CustomSphere) {
                CustomSphere clickedSphere = (CustomSphere) intersectedNode;
                label.setText(clickedSphere.getName());

            }

        });
        subScene.setCamera(camera);
        StackPane stackPane = new StackPane(subScene, label);
        StackPane.setAlignment(label, Pos.TOP_CENTER);

        Scene scene = new Scene(stackPane, 480, 320, true, SceneAntialiasing.BALANCED);
        stage.setTitle("pickresult");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

    class CustomSphere extends Sphere {

        private String name;
        PhongMaterial material = new PhongMaterial();

        public CustomSphere(String name, Color color, double d, double x) {
            super(d);
            this.name = name;
            material.setDiffuseColor(color);
            this.setMaterial(material);
            this.setTranslateX(x);
        }

        public String getName() {
            return name;
        }

    }

}