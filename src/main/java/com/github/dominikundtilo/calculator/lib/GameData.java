package com.github.dominikundtilo.calculator.lib;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class GameData {

    static final Gson GSON = new Gson();

    public final ArrayList<Recipe> recipes;
    public final HashMap<String, ArrayList<Recipe>> recipesByCategory = new HashMap<>();
    public final HashMap<Item, ArrayList<Recipe>> recipesForItem = new HashMap<>();

    public final ArrayList<Item> items = new ArrayList<>();
    public final ArrayList<Item> craftableItems = new ArrayList<>();
    public final HashMap<String, ArrayList<Item>> itemsByType = new HashMap<>();

    public GameData() throws IOException {
        Path path = getPath();
        if (path == null)
            throw new IOException();
        String json = new String(Files.readAllBytes(path), "UTF-8");

        recipes = new ArrayList<>(Arrays.asList(GSON.fromJson(json, Recipe[].class)));
        Collections.sort(recipes, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));

        for (Recipe recipe : recipes) {
            if (!recipesByCategory.containsKey(recipe.getCategory()))
                recipesByCategory.put(recipe.getCategory(), new ArrayList<>());
            recipesByCategory.get(recipe.getCategory()).add(recipe);

            for (IItem iItem : recipe.getIngredients())
                //noinspection SuspiciousMethodCalls
                if (!items.contains(iItem))
                    items.add(new Item(iItem));

            for (IItem iItem : recipe.getProducts()) {
                //noinspection SuspiciousMethodCalls
                if (!items.contains(iItem))
                    items.add(new Item(iItem));

                Item item = getItem(iItem);
                if (!recipesForItem.containsKey(item))
                    recipesForItem.put(item, new ArrayList<>());
                recipesForItem.get(item).add(recipe);
            }

        }
        Collections.sort(items, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        for (Item item : items) {
            if (!itemsByType.containsKey(item.getType()))
                itemsByType.put(item.getType(), new ArrayList<>());
            itemsByType.get(item.getType()).add(item);
            if (recipesForItem.containsKey(item))
                craftableItems.add(item);
        }

    }

    private Item getItem(IItem item) {
        //noinspection SuspiciousMethodCalls
        return items.get(items.indexOf(item));
    }

    private Path getPath() {
        String os = System.getProperty("os.name");
        if (os.equals("Linux")) {
            return new File(System.getProperty("user.home") + "/.factorio/script-output/calculator-dump.json").toPath();
        } else if (os.startsWith("Windows")) {
            return new File(System.getProperty("user.home") + "\\AppData\\Roaming\\Factorio\\script-output\\calculator-dump.json").toPath();
        }
        return null;
    }
}
