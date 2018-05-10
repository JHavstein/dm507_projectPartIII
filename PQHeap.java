/**
DM507, project part III
	Mathilde Blicher Christensen - mathc17 - 01-03-1997
	Jeanette Frieda Aviaya Sommer - jeaso17 - 08-05-1984
	Jonas Alexander Havstein Eriksen - joeri15 - 16-02-1993
*/

public class PQHeap implements PQ {
    private Element[] A;
    // Index of the last element in the array.
    private int index = 0;
    
    // Constructor for making a new instance of PQHeap - which is a priority queue heap.
    // The heap is implemented with an array.
    public PQHeap(int maxElms) {
        this.A = new Element[maxElms];
    }

    // Method which returns the index position of the parent of the node at index position i.
    private int parent (int i) {
        return (i-1)/2;
    }

    // Method which returns the index position of left child of the node at index position i.
    private int left ( int i ) {
        return 2*i+1;
    }

    // Method which returns the index position of right child of the node at index position i.
    private int right ( int i ) {
        return 2*i+2;
    }

    // Method that ensures the min-heap property
    private void minHeapify(Element[] A, int i) {
        int smallest;
        int l = left(i);
        int r = right(i);
        if (l <= index && A[l].key < A[i].key) {
            // left child has a smaller key than the parent
            smallest = l;
        } else {
            // parent has the smallest key
            smallest = i;
        }
        if (r <= index && A[r].key < A[smallest].key ) {
            // right child has the smallest key
            smallest = r;
        }
        if (smallest != i) {
            // The node with the smallest key is not the parent node.
            // Therefore: exchange parent with child with the smallest key.
            Element temp = A[i];
            A[i] = A[smallest];
            A[smallest] = temp;
            minHeapify(A, smallest);
        }
    }

    // The extractMin method removes and returns the element with the smallest key.
    // The last element of the array is moved to the root and MinHeapify is called.
    public Element extractMin () {
        Element min = A[0];
        index--;
        A[0] = A[index];
        minHeapify(A, 0);
        return min;
    }

    // Method inserts an element at the end of the array and checks if the 
    // parent of the element has a larger key. If so the the elements are exchanged.
    public void insert (Element e) {
        A[index] = e;
        int i = index;  
        index++;
        while (i > 0 && A[parent(i)].key > A[i].key ) {
            Element temp = A[parent(i)];
            A[parent(i)] = A[i];
            A[i] = temp;
            i = parent(i);
        }
    }
}