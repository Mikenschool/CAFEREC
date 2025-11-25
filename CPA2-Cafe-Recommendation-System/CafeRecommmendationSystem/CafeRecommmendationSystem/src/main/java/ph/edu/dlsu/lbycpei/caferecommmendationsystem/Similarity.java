package ph.edu.dlsu.lbycpei.caferecommmendationsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class Similarity {

    private HashMap<String, ArrayList<String>> similarityMap = new HashMap<>();

    public Similarity() {
        addSimilar("Matcha Latte", "Dubai Chocolate Brownie");
        addSimilar("Matcha Latte", "Espresso");
        addSimilar("Dirty Matcha Latte", "Espresso");
        addSimilar("Lemonade", "Iced Tea");
    }

    private void addSimilar(String item, String similar) {
        if (item == null || similar == null) return;

        String a = normalize(item);
        String b = normalize(similar);

        // add a -> b
        similarityMap.putIfAbsent(a, new ArrayList<>());
        if (!similarityMap.get(a).contains(similar)) {
            similarityMap.get(a).add(similar);
        }

        // add b -> a (bi-directional)
        similarityMap.putIfAbsent(b, new ArrayList<>());
        if (!similarityMap.get(b).contains(item)) {
            similarityMap.get(b).add(item);
        }
    }

    /**
     * Returns similar items for the given item. If none found, returns an empty list.
     * The returned list contains no duplicates and preserves insertion order.
     */
    public ArrayList<String> getSimilarItems(String item) {
        if (item == null) return new ArrayList<>();

        String key = normalize(item);
        ArrayList<String> list = similarityMap.getOrDefault(key, new ArrayList<>());

        // Remove duplicates while preserving order (just in case)
        LinkedHashSet<String> set = new LinkedHashSet<>(list);
        return new ArrayList<>(set);
    }

    private String normalize(String s) {
        return s.trim();
    }
}