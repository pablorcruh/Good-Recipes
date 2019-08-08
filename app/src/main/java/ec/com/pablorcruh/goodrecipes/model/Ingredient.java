package ec.com.pablorcruh.goodrecipes.model;

public class Ingredient {

    private String description;

    public Ingredient() {
    }

    public Ingredient(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
