package Einteillogik;

import javafx.scene.Node;

import java.util.ArrayList;

public class CustomNode extends Node {

    NodeType type;
    String name;
    double width;
    double height;
    double metric;
    ArrayList<CustomNode> children;


    public CustomNode() {
        this.children = new ArrayList<CustomNode>();
    }

    public NodeType getType() {
        return type;
    }
    public void setType(NodeType type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public double getMetric() {
        return metric;
    }
    public void setMetric(double metric) {
        this.metric = metric;
    }
    public ArrayList<CustomNode> getChildren() {
        return children;
    }
    public void setChildren(ArrayList<CustomNode> children) {
        this.children = children;
    }

    public void extendChildren(CustomNode node) {

        this.children.add(node);
    }

    public void computeSize(double tw, double th) {

        this.width = tw;
        this.height = th;

        for (CustomNode c : this.children) {

            boolean horizontal = false;

            switch (c.getType()) {
                case BOOK:
                    horizontal = true;
                    break;
                case CHAPTER:
                    horizontal = false;
                    break;
                case CHARACTER:
                    horizontal = true;
                    break;
            }

            //System.out.println(c.getName() + (horizontal==true?"Y":"N"));

            if (horizontal) {
                c.computeSize(tw, c.getMetric() *th);
            } else {
                c.computeSize(c.getMetric()*tw, th);
            }

        }
    }

}
