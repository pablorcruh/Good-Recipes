package ec.com.pablorcruh.goodrecipes.model;

public class Recipe {
    private String author;

    private String[] ingredientes;

    private String[] steps;

    private String name;

    public Recipe() {
    }

    public Recipe(String author, String[] ingredientes, String[] steps, String name) {
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

    public String[] getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String[] ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String[] getSteps() {
        return steps;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
