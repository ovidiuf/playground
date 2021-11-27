package playground.stanford.tsp;

import java.util.Iterator;

public interface Subset {

    int id();

    void setId(int id);

    int size();

    /**
     * Create a new subset with larger size, based on this subset, by adding the given element. The newly created subset
     * does not have an ID, the ID will have to be set externally.
     * @param element 1-based element
     * @return a new subset with a size of current size + 1. If the element already exists in the current set, return null
     * @throws IllegalArgumentException if the element is invalid
     */

    Subset add(int element);

    /**
     * If the element does not exist, return the same set. If it does exist, return a new set with fewer elements.
     */
    Subset remove(int element);

    Iterator<Integer> elements();
}
