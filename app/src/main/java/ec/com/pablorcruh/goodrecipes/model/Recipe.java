package ec.com.pablorcruh.goodrecipes.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

import java.util.List;

public class Recipe implements Parcelable {

    private String id;

    private String author;

    private List<String> ingredientes;

    private List<String> steps;

    private String name;

    private String recipeImageUrl;

    private String description;

    private Timestamp creationDate;

    public Recipe() {
    }

    public Recipe(String author, List<String> ingredientes, List<String> steps, String name,
                  String recipeImageUrl, String description, Timestamp creationDate) {
        this.author = author;
        this.ingredientes = ingredientes;
        this.steps = steps;
        this.name = name;
        this.recipeImageUrl = recipeImageUrl;
        this.description = description;
        this.creationDate = creationDate;
    }

    protected Recipe(Parcel in) {
        id = in.readString();
        author = in.readString();
        ingredientes = in.createStringArrayList();
        steps = in.createStringArrayList();
        name = in.readString();
        recipeImageUrl = in.readString();
        description = in.readString();
        creationDate = in.readParcelable(Timestamp.class.getClassLoader());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(author);
        parcel.writeStringList(ingredientes);
        parcel.writeStringList(steps);
        parcel.writeString(name);
        parcel.writeString(recipeImageUrl);
        parcel.writeString(description);
        parcel.writeParcelable(creationDate, i);
    }
}
