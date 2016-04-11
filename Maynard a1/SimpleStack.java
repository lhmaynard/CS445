import java.io.Serializable;

/**
 * A simple stack: items are fetched most-recent first
 * @author Lenny Maynard
 */
public class SimpleStack<T> implements SimpleStackInterface<T> , Serializable{

    T[] array;
    int capacity;
    int fill;

    /**
     * A no-arg constructor that initializes all fields
     */
    public SimpleStack(){

        array = (T[]) new Object[10];
        capacity = 10;
        fill = 0;
    }

    /**
     * A constructor that takes in desired capacity
     * and initializes all fields
     * @param capacity desired stack capacity
     */
    public SimpleStack(int capacity){
        array = (T[]) new Object[capacity];
        this.capacity = capacity;
        fill = 0;
    }

    /**
     * Adds a new T object to the top of the stack
     * @param item The item to be added
     * @return True if successful, false if there is no more room
     */
    public boolean add(T item){
        if(array[capacity-1] != null) return false;      //return false if the array is full
        array[fill] = item;                              //add new item
        fill++;                                          //increment counter
        return true;
    }

    /**
     * Removes the top T object
     * @return The item that is removed, or null if no item can be removed
     */
    public T remove(){
        if(fill == 0) return null;         //return null if the array is empty
        T hold = array[fill-1];            //preserve the item to be removed so it can be returned
        array[fill-1] = null;              //remove item
        fill--;                            //decrement counter
        return hold;
    }

    /**
     * Returns an array of size n containing the top n items from the stack. The most recently-added
     * items should be listed first.
     * @param howMany The number of items to return
     * @return An array of the most recently-added items, or null if the stack does not contain
     * enough items
     */
    public Object[] topItems(int howMany){
        if(howMany > fill) return null;            //return null if asking for too many items
        Object[] tops = new Object[howMany];
        for(int x = 0; x < howMany; x++) {         //fill array to be returned with top items
            tops[x] = array[fill - 1 - x];
        }
        return tops;
    }

    /**
     * Determines if the stack contains the specified item
     * @param item The item in question
     * @return True if the item is contained in the stack, false otherwise
     */
    public boolean contains(T item){
        for(int x = 0; x < fill; x++){                 //cycle through array looking for item
            if(array[x].equals(item)) return true;
        }
        return false;
    }

    /**
     * Determines if the stack is empty
     * @return True if the stack is empty, false otherwise
     */
    public boolean isEmpty(){
        if(fill == 0) return true;         //if first position is empty return true
        return false;
    }

    /**
     * Determines if the stack is full (at capacity)
     * @return True if the stack is full, false otherwise
     */
    public boolean isFull(){
        if(fill == capacity) return true;       //if final position is filled return true
        return false;
    }

    /**
     * Determines the size of the stack (the current number of objects in the stack, NOT the
     * capacity)
     * @return The number of objects in the stack
     */
    public int size(){
        return fill;
    }

}

