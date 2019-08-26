package ec.com.pablorcruh.goodrecipes.model;

import java.util.List;

public class Recipe {

    private String id;

    private String author;

    private List<String> ingredientes;

    private List<String> steps;

    private String name;

    private String recipeImageUrl;

    private String description;

    private boolean uploadImage=false;

    public Recipe() {
    }

    public Recipe(String author, List<String> ingredientes, List<String> steps, String name, String recipeImageUrl, String description, boolean uploadImage) {
        this.author = author;
        this.ingredientes = ingredientes;
        this.steps = steps;
        this.name = name;
        this.recipeImageUrl = recipeImageUrl;
        this.description = description;
        this.uploadImage =uploadImage;
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

    public String getRecipeImageUrl() {
        return recipeImageUrl;
    }

    public void setRecipeImageUrl(String recipeImageUrl) {
        this.recipeImageUrl = recipeImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(boolean uploadImage) {
        this.uploadImage = uploadImage;
    }
}
