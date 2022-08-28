package ru.yandex.praktikum;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ingredients  {
    private static List<String> ingredients = new ArrayList<>();

    public Ingredients(List<String> ingredients) {

        this.ingredients = ingredients;
    }

    public Ingredients() {

    }

    public static Ingredients getNewIngredients(){
        List<String> ingredients = List.of(new String[]{"61c0c5a71d1f82001bdaaa6c", "61c0c5a71d1f82001bdaaa6c"});
        return new Ingredients(ingredients);
    }

    public static Ingredients getWrongIngredients(){
        List<String> ingredients = List.of(new String[]{"61c0c5a71d1f82001bdaaa61", "61c0c5a71d1f82001bdaaa6446"});
        return new Ingredients(ingredients);
    }

    public List<String> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

}
