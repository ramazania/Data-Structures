/*
 * Name: Ali Ramazani
 * Email: ramazani@carleton.edu
 * My email is actually ramazania@carleton.edu but the checkstyle shows it as error.
 * Description: Optimizing the Doubly Linked List class using two dummy nodes.
 */
 
import java.util.NoSuchElementException;
 
public class DoublyLinkedList<E> {
 
    private class ListNode {
        private E value;        // data value at node
        private ListNode prev;  // reference to previous node
        private ListNode next;  // reference to next node
 
        // construct a ListNode and adjust neighboring next and prev
        public ListNode(E val, ListNode before, ListNode after) {
            value = val;
            prev = before;
            next = after;
            // adjust prev and next of neighboring nodes (if they exist) to refer to this node
            if (before != null) {
                before.next = this;
            }
            if (after != null) {
                after.prev = this;
            }
        }
    }
 
    /**
     * Number of elements within the list.
     */
    private int count;
    /**
     * Reference to head of the list.
     */
    private ListNode head;
    /**
     * Reference to tail of the list.
     */
    private ListNode tail;
 
    /**
     * Constructs an empty list.
     *
     * @post constructs an empty list
     * 
     */
    public DoublyLinkedList() {
        // Students: modify this code.
        head = new ListNode(null, null, tail);
        tail = new ListNode(null, head, null);
        count = 0;
    }
    
    /**
     * Determine the number of elements in the list.
     *
     * @post returns the number of elements in list
     * 
     * @return The number of elements found in the list.
     */
    public int size() {
        return count;
    }
 
    /**
     * Determine if the list is empty.
     *
     * @post returns true iff the list has no elements.
     * 
     * @return True if list has no values.
     */
    public boolean isEmpty() {
        return size() == 0;
    }
 
    /**
     * Remove all values from list.
     *
     * @post removes all the elements from the list
     */
    public void clear() {
        // Students: modify this code
        head.next = tail;
        tail.prev = head;
        count = 0;
    }
 
    /**
     * A private routine to add an element after a node.
     * @param value the value to be added
     * @param previous the node the come before the one holding value
     * @pre previous is non null
     * @post list contains a node following previous that contains value
     */
    private void insertAfter(E value, ListNode previous) {
        // Students: write this code.
        previous.next = new ListNode(value, previous, previous.next);
        count++;
    }

    /**
     * A private routine to remove a node.
     * @param node the node to be removed
     * @pre node is non null
     * @post node node is removed from the list
     * @return the value of the node removed
     */
    private E remove(ListNode node) {
        // Students: write this code
        node.prev.next = node.next;
        node.next.prev = node.prev;
        count--;
        return node.value;
    }
    
    /**
     * Iterate over the list to retrieve the node at index
     * 
     * @pre index >= 0 && index <= size()
     * 
     * @param index The index of the node to be found
     */
    private ListNode find(int index) {
        // Students: write this code
        ListNode list = head.next;
        for (int i = 0; i < index; i++) {
            list = list.next;
        }
        return list;
    }
 
    /**
     * Add a value to the head of the list.
     *
     * @pre value is not null
     * @post adds element to head of list
     * 
     * @param value The value to be added.
     */
    public void addFirst(E value) {
        // Students: modify this code.
        insertAfter(value, head);
    }
 
    /**
     * Add a value to the tail of the list.
     *
     * @pre value is not null
     * @post adds new value to tail of list
     * 
     * @param value The value to be added.
     */
    public void addLast(E value) {
        // Students: modify this code.
       insertAfter(value, tail.prev);
    }
 
    /**
     * Remove a value from the head of the list.
     * Value is returned.
     *
     * @pre list is not empty
     * @post removes first value from list
     * 
     * @return The value removed from the list.
     */
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from an empty list.");
        }
        // Students: modify this code.

        ListNode newNode = head.next;
        remove(head.next);
        return newNode.value;
    }
 
    /**
     * Remove a value from the tail of the list.
     *
     * @pre list is not empty
     * @post removes value from tail of list
     * 
     * @return The value removed from the list.
     */
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from an empty list.");
        }
        // Students: modify this code.
        ListNode newNode = tail.prev;
        remove(tail.prev);
        return newNode.value;
    }
 
    /**
     * Get a copy of the first value found in the list.
     *
     * @pre list is not empty
     * @post returns first value in list.
     * 
     * @return A reference to first value in list.
     */
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot get from an empty list.");
        }
        // Students: modify this code.
        return head.next.value;
    }
 
    /**
     * Get a copy of the last value found in the list.
     *
     * @pre list is not empty
     * @post returns last value in list.
     * 
     * @return A reference to the last value in the list.
     */
    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot get from an empty list.");
        }
        // Students: modify this code.
        return tail.prev.value;
    }
 
    /**
     * Insert the value at location.
     *
     * @pre 0 <= index <= size()
     * @post adds the indexth entry of the list to value
     * @param index the index of this new value
     * @param value the the value to be stored
     */
    public void add(int index, E value) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index " + index + 
            " out of bounds for list of length " + size());
        }
        // Students: modify this code.
        insertAfter(value, find(index).prev);
    }

    /**
     * Remove and return the value at index.
     *
     * @pre 0 <= index < size()
     * @post removes and returns the object found at that location.
     *
     * @param index the position of the value to be retrieved.
     * @return the value retrieved from index
     */
    public E remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index " + index + 
            " out of bounds for list of length " + size());
        }
        // Students: modify this code.
        return remove(find(index));
       
    }

    /**
     * Get the value at index.
     *
     * @pre 0 <= index < size()
     * @post returns the object found at that location.
     *
     * @param index the position of the value to be retrieved.
     * @return the value retrieved from index
     */
    public E get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index " + index + 
            " out of bounds for list of length " + size());
        }
        ListNode current = head.next;
        current = find(index);
        return current.value;
    }
    /**
     * Set the value stored at index to object value, returning the old value.
     *
     * @pre 0 <= index < size()
     * @post sets the indexth entry of the list to value, returns the old value.
     * @param index the location of the entry to be changed.
     * @param value the new value
     * @return the former value of the indexth entry of the list.
     */
    public E set(int index, E value) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index " + index + 
            " out of bounds for list of length " + size());
        }
        // Students: modify this code.
        ListNode current = head.next;
        current = find(index);
        E result = current.value;
        current.value = value;
        return result;
    }
 
    /**
     * Determine the first location of a value in the list.
     *
     * @pre value is not null
     * @post returns the (0-origin) index of the value,
     *   or -1 if the value is not found
     * 
     * @param value The value sought.
     * @return the index (0 is the first element) of the value, or -1
     */
    public int indexOf(E value) {
        // Students: modify this code.
        int i = 0;
        ListNode current = head.next;
        // search for value or end of list, counting along the way
        while (current != tail && !current.value.equals(value)) {
            current = current.next;
            i++;
        }
        // current points to value, i is index
        if (current == tail) {
            // value not found, return indicator
            return -1;
        } else {
            // value found, return index
            return i;
        }
    }

    /**
     * Determine the last location of a value in the list.
     *
     * @pre value is not null
     * @post returns the (0-origin) index of the value,
     *   or -1 if the value is not found
     * 
     * @param value the value sought.
     * @return the index (0 is the first element) of the value, or -1
     */
    public int lastIndexOf(E value) {
        // Students: modify this code.
        int i = size() - 1;
        ListNode current = tail.prev;
        // search for the last matching value, result is desired index
        while (current != head && !current.value.equals(value)) {
            current = current.prev;
            i--;
        }
        if (current == tail) {
            // value not found, return indicator
            return -1;
        } else {
            // value found, return index
            return i;
        }
    }
 
    /**
     * Check to see if a value is within the list.
     *
     * @pre value not null
     * @post returns true iff value is in the list
     * 
     * @param value A value to be found in the list.
     * @return True if value is in list.
     */
    public boolean contains(E value) {
        // Students: modify this code.
        ListNode current = head.next;
        while (current != tail && !current.value.equals(value)) {
            current = current.next;
        }
        return current != tail;
    }
 
    /**
     * Remove a value from the list.  At most one value is removed.
     * Any duplicates remain.  Because comparison is done with "equals,"
     * the actual value removed is returned for inspection.
     *
     * @pre value is not null.  List can be empty.
     * @post first element matching value is removed from list
     * 
     * @param value The value to be removed.
     * @return The value actually removed.
     */
    public E remove(E value) {
        // Students: modify this code.
        ListNode current = head.next;
        while (current != tail && !current.value.equals(value)) {
            current = current.next;
        }
        if (current != tail) {
            // fix next field of previous node
            if (current.prev != tail) {
                current.prev.next = current.next;
            } else {
                head = current.next;
            }
            // fix prev field of next node
            if (current.next != tail) {
                current.next.prev = current.prev;
            } else {
                tail = current.prev;
            }
            count--;            // fewer elements
            return current.value;
        }
        // matching value not found
        return null;
    }
 
    /**
     * Construct a string representation of the list.
     *
     * @post returns a string representing list
     * 
     * @return A string representing the elements of the list.
     */
    public String toString() {
        if (size() == 0) {
            return "[]";
        }
        // Students: modify this code
        StringBuilder s = new StringBuilder();
        s.append("[");
        ListNode current = head.next;
        // append all the values in the list to our string, except the tail
        while (current.next != tail) {
            s.append(current.value + ", ");
            current = current.next;
        }
        // handle the last node after the loop, so we don't put a comma after it
        s.append(current.value + "]");
        return s.toString();
    }

}