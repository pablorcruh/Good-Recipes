package ec.com.pablorcruh.goodrecipes.model;

import java.util.List;

public class Recipe {
    private String author;

    private List<String> ingredientes;

    private List<String> steps;

    private String name;

    public Recipe() {
    }

    public Recipe(String author, List<String> ingredientes, List<String> steps, String name) {
        this.author = author;
        this.ingredientes = ingredientes;
        this.steps = steps;
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
