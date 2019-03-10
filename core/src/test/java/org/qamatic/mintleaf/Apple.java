package org.qamatic.mintleaf;

public class Apple {
    private String color;
    private Double weight;

    public Apple(String c, Double w) {
        this.color = c;
        this.weight = w;
    }

    @Override
    public String toString() {
        return "Apple{color:" + this.getColor() + ",weight:" + this.getWeight() + "}";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}