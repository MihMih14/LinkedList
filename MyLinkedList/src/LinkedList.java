
import java.lang.reflect.Array;
import java.util.*;

public class LinkedList<T> implements List<T> {

    private Item<T> first = null;

    private Item<T> last = null;

    private int size;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(final Object o) {
        // BEGIN (write your solution here)
        return indexOf(o) != -1;
        // END
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator();
    }

    @Override
    public Object[] toArray() {
        // BEGIN (write your solution here)
        Object[] object = new Object[this.size()];

        Item<T> item = this.first;
        for(int i = 0; i < this.size(); i++) {
            object[i] = item.getElement();
            item = item.getNext();
        }

        return object;
        // END
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        // BEGIN (write your solution here)
        if(a.length < this.size()) {
            T1[] array = (T1[]) this.toArray();
            return (T1[])Arrays.copyOf(array,this.size(),a.getClass());

        }
        Item<T> item = this.first;
        for(int i = 0; i < this.size(); i++) {
            a[i] = (T1)item.getElement();
            item = item.getNext();
        }

        if(a.length > size()) {
            a[size()] = null;
        }

        return a;
        // END
    }

    @Override
    public boolean add(final T t) {
        // BEGIN (write your solution here)

        if(isEmpty()) {
            Item<T> item = new Item<>(t,null,null);
            this.first = item;
            this.last = item;
            size++;
            return true;
        }

        Item<T> item = new Item<>(t,last,null);
        last.next = item;
        this.last = item;
        size++;
        return  true;

        // END
    }

    @Override
    public boolean remove(final Object o) {
        // BEGIN (write your solution here)
        Item<T> item = first;

        for(int i = 0; i < this.size(); i++ ) {
            if(item.getElement().equals(o)) {
                //Remove when in List one element.
                if(size() == 1) {
                    this.first = null;
                    this.last = null;
                    size--;
                    return true;
                }

                //Remove in List first element.
                if(i == 0) {
                    item.next.prev = null;
                    this.first = item.getNext();
                    size--;
                    return true;
                }

                //Remove in List last element
                if(i == this.size() - 1) {
                    item.prev.next = null;
                    this.last = item.getPrev();
                    size--;
                    return true;
                }

                item.next.prev = item.getPrev();
                item.prev.next = item.getNext();
                size--;
                return true;
            }
            item = item.getNext();

        }

        return false;
        // END
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        for (final Object item : this) {
            if (!c.contains(item)) this.remove(item);
        }
        return true;
    }

    @Override
    public void clear() {
        // BEGIN (write your solution here)
        ElementsIterator iterator = new ElementsIterator();

        while(iterator.hasNext()) {
            remove(iterator.next());
        }
        // END
    }

    @Override
    public T remove(final int index)  {
        // BEGIN (write your solution here)

        if(index < 0 || index >= this.size()){
            throw new IndexOutOfBoundsException();
        }
        Item<T> item = first;

        for(int i = 0; i < size(); i++) {
            if(i == index) {

                if(size() == 1) {
                    this.first = null;
                    this.last = null;
                    size--;
                    return item.getElement();
                }

                //Remove in List first element.
                if(i == 0) {
                    item.next.prev = null;
                    this.first = item.getNext();
                    size--;
                    return item.getElement();
                }

                //Remove in List last element
                if(i == this.size() - 1) {
                    item.prev.next = null;
                    this.last = item.getPrev();
                    size--;
                    return item.getElement();
                }

                item.next.prev = item.getPrev();
                item.prev.next = item.getNext();
                size--;
                return item.getElement();



            }
            item = item.getNext();
        }
        // END

        throw new IllegalStateException();
    }

    // BEGIN (write your solution here)

    // END
    @Override
    public List<T> subList(final int start, final int end) {

        if(start < 0 || end > size()) {
            throw new IndexOutOfBoundsException();
        }

        if(start > end) {
            throw new IllegalArgumentException();
        }
        List<T> list = new ArrayList<>();

        for(int i = start; i < end; i++) {
            list.add(this.get(i));
        }

        return list;


    }

    @Override
    public ListIterator listIterator() {
        return new ElementsIterator(0);
    }

    @Override
    public ListIterator listIterator(final int index) {
        return new ElementsIterator(index);
    }

    @Override
    public int lastIndexOf(final Object target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(final Object target) {
        // BEGIN (write your solution here)
        for(int i = 0; i < this.size(); i++) {
            if(this.get(i).equals(target)) {
                return i;
            }
        }

        return -1;
        // END
    }

    @Override
    public void add(final int index, final T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(final int index, final Collection elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(final int index, final T element) {
        // BEGIN (write your solution here)
        if(index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }


        ElementsIterator iterator = new ElementsIterator(index);
        T removedElement = this.get(index);
        iterator.current.element = element;
        return removedElement;
        // END
    }

    @Override
    public T get(final int index) {
        // BEGIN (write your solution here)
        if(index < 0 || index >= size() || isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        Item<T> item = this.first;
        for(int i = 0; i <= index; i++) {
            if(i == index) {
                return item.element;
            }

            item = item.getNext();

        }

        throw new UnsupportedOperationException();
        // END
    }

    // BEGIN (write your solution here)

    // END

    private class ElementsIterator implements ListIterator<T> {

        private Item<T> current;

        private Item<T> last;

        private int index;

        public ElementsIterator() {
            index = 0;
            this.current = LinkedList.this.first;

        }

        public ElementsIterator(final int index) {
            // BEGIN (write your solution here)
            this.index = index;
            this.current = LinkedList.this.first;


            // END
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            // BEGIN (write your solution here)
            if(!hasNext() || LinkedList.this.isEmpty()) {
                throw new NoSuchElementException();
            }



            this.last = this.current;
            this.current = this.current.getNext();
            index++;
            return this.last.getElement();
            // END
        }

        @Override
        public void add(final T element) {
            LinkedList.this.add(element);
        }

        @Override
        public void set(final T element) {
            // BEGIN (write your solution here)
            if(LinkedList.this.isEmpty()) {
                throw  new UnsupportedOperationException();
            }
            if(element == null) {
                throw new IllegalStateException();
            }

            LinkedList.this.set(LinkedList.this.size() - 1,element);
            // END
        }

        @Override
        public int previousIndex(){
            // BEGIN (write your solution here)

            return index - 1;
            // END
        }

        @Override
        public int nextIndex() {
            // BEGIN (write your solution here)

            return  index;
            // END
        }

        @Override
        public boolean hasPrevious() {
            // BEGIN (write your solution here)

            return last != null;
            // END
        }

        @Override
        public T previous() {
            // BEGIN (write your solution here)
            if(!hasPrevious() ) {
                throw  new NoSuchElementException();
            }

            if(current == null) {
                current = this.last;
                return this.last.getElement();

            }


            index--;
            this.last = current;
            this.current = this.current.getPrev();
            return this.current.getElement();
            // END
        }

        @Override
        public void remove() {
            // BEGIN (write your solution here)
            if(last == null) {
                throw new IllegalStateException();
            }

            if(LinkedList.this.size() == 1) {
                LinkedList.this.remove(0);
            }
            if(LinkedList.this.size() != 1 && index < LinkedList.this.size()){
                LinkedList.this.remove(index-1);
                index--;

            }
            last = last.getPrev();











            // END
        }

    }

    private static class Item<T> {

        private T element;

        private Item<T> next;

        private Item<T> prev;

        public Item(final T element, final Item<T> prev, final Item<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

        public T getElement() {
            return element;
        }

        public Item<T> getNext() {
            return next;
        }

        public Item<T> getPrev() {
            return prev;
        }

    }

}
