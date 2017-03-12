package model;

/**
 * Created by Alex Ichim on 10.03.2017.
 */
public class Node {

    private int level;
    private int cumValue;
    private int cumWeight;
    private double nodeBound;
    private Node leftChild;
    private Node rightChild;


    public Node(int level, int cumValue, int cumWeight, double nodeBound, Node leftChild, Node rightChild) {
        this.level = level;
        this.cumValue = cumValue;
        this.cumWeight = cumWeight;
        this.nodeBound = nodeBound;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCumValue() {
        return cumValue;
    }

    public void setCumValue(int cumValue) {
        this.cumValue = cumValue;
    }

    public int getCumWeight() {
        return cumWeight;
    }

    public void setCumWeight(int cumWeight) {
        this.cumWeight = cumWeight;
    }

    public double getNodeBound() {
        return nodeBound;
    }

    public void setNodeBound(double nodeBound) {
        this.nodeBound = nodeBound;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }
}
