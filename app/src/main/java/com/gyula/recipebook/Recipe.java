package com.gyula.recipebook;

public class Recipe {
    String categories;
    String sampleType;
    String title;
    String shortStory;
    int time;
    String timeType;
    String difficulty;
    String ingredients;
    String instructions;

    public Recipe(String categories, String sampleType, String title, String shortStory, int time, String timeType, String difficulty, String ingredients, String instructions) {
        this.categories = categories;
        this.sampleType = sampleType;
        this.title = title;
        this.shortStory = shortStory;
        this.time = time;
        this.timeType = timeType;
        this.difficulty = difficulty;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Recipe(String categories, String title, int time, String timeType) {
        this.categories = categories;
        this.title = title;
        this.time = time;
        this.timeType = timeType;
    }
    Recipe(){

    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortStory() {
        return shortStory;
    }

    public void setShortStory(String shortStory) {
        this.shortStory = shortStory;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instruction) {
        instructions = instruction;
    }
}
