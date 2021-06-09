package corona;


import java.util.Objects;

public class CoronaQueue {

    Person[] data;
    int[] next;
    int[] prev;
    int[] ref; 			 // the reference array
    int size;			 // the current number of subjects in the queue
    int head; 			 // the index of the lists 'head'. -1 if the list is empty.
    int tail;			 // the index of the lists 'tail'. -1 if the list is empty.
    int free;			 // the index of the first 'free' element.

    /**
     * Creates an empty data structure with the given capacity.
     * The capacity dictates how many different subjects may be put into the data structure.
     * Moreover, the capacity gives an upper bound to the ID number of a Person to be put in the data structure.
     *
     */
    public CoronaQueue(int capacity){
        data = new Person[capacity];
        next = new int[capacity];
        prev = new int[capacity];
        ref = new int [capacity+1];
        for(int i=0;i<capacity;i++){
            data[i] = new Person(0,null);
            next[i] = i + 1;
            prev[i] = i - 1;
            ref[i] = -1;
        }
        next[capacity - 1] = -1;
        ref[capacity] = -1;

        size = 0;
        head = -1;
        tail = -1;
        free = 0;
    }

    /**
     * Returns the size of the queue.
     *
     * @return the size of the queue
     */
    public int size(){
        return size ;
    }

    /**
     * Inserts a given Person into the queue.
     * Inesertion should be done at the tail of the queue.
     * If the given person is already in the queue this function should do nothing.
     *	Throws an illegal state exception if the queue is full.
     *
     * @param t - the Task to be inserted.
     * @throws IllegalStateException - if queue is full.
     */
    public void enqueue(Person p){
        if (free == -1) {
            throw new IllegalStateException("Sorry , the queue is full  ");
        }
        if(ref[p.id] != -1){
            return;
        }
        if(size == 0){
            head = free;
        }

        data[free] = p;
        prev[free] = tail;
        if(tail != -1){
            next[tail] = free;
        }
        tail = free;
        size ++;
        ref[p.id] = free;
        free = next[free];
        next[tail] = -1;
    }


    /**
     * Removes and returns a Person from the queue.
     * The person removed is the one which sits at the head of the queue.
     * If the queue is empty returns null.
     */
    public Person dequeue(){
        if(size == 0 ){
            return null;
        }
        Person vacPerson = data[head];
        int headIndex = next[head];
        data[head] = null;
        ref[vacPerson.id] = -1;
        if(size == 1) {
            next[tail] = free;
            free = tail;
            head = -1;
            tail = -1;
            size--;
            return vacPerson;
        }

        changeMode(head);
        head = headIndex;
        prev[head] = -1;

        return vacPerson;
    }

    /**
     * Removes a Person from (possibly) the middle of the queue.
     *
     * Does nothing if the Person is not already in the queue.
     * Recall that you are not allowed to traverse all elements in the queue. In particular no loops or recursion.
     * Think about all the different edge cases and the variables which need to be updated.
     * Make sure you understand the role of the reference array for this function.
     *
     * @param p - the Person to remove
     */
    public void remove(Person p){
        int index = ref[p.id];

        if(index == -1){
            return;
        }
        if(index == head){                             // or size is one
            Person tempPerson = dequeue();
            return;
        }
        changeMode(index);
        ref[p.id] = -1;
        data[index] = null;
    }

    /*
    * order the fields when remove a person from the queue .
    * @param index of the person removed (from data array)
    */
    private void changeMode(int index){
         if(index == tail){
            tail = prev[tail];
        }else if(index != head){
            int prevIndex = prev[index];
            int nextIndex = next[index];
            next[prevIndex] = next[index];             //skip the removed index in next array . reduce the queue
            prev[nextIndex] = prev[index];            //skip the removed index in prev array . reduce the queue
        }
        if(free != -1){
            prev[free] = index;
        }
        next[index] = free;
        prev[index] = tail;
        free = index;
        size --;
    }
    /*
     * The following functions may be used for debugging your code.
     */
    private void debugNext(){
        for (int i = 0; i < next.length; i++) {
            System.out.println(next[i]);
        }
        System.out.println();
    }
    private void debugPrev(){
        for (int i = 0; i < prev.length; i++) {
            System.out.println(prev[i]);
        }
        System.out.println();
    }
    private void debugData(){
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
        }
        System.out.println();
    }

    private void debugRef(){
        for (int i = 0; i < ref.length; i++) {
            System.out.println(ref[i]);
        }
        System.out.println();
    }

    /*
     * Test code; output should be:
		Aaron, ID number: 1
		Baron, ID number: 2
		Cauron, ID number: 3
		Dareon, ID number: 4
		Aaron, ID number: 1
		Baron, ID number: 2
		Aaron, ID number: 1

		Baron, ID number: 2
		Cauron, ID number: 3

		Aaron, ID number: 1
		Dareon, ID number: 4

		Aaron, ID number: 1
		Cauron, ID number: 3
     */
    public static void main (String[] args){
        CoronaQueue demo = new CoronaQueue(4);
        Person a = new Person(1, "Aaron");
        Person b = new Person(2, "Baron");
        Person c = new Person(3, "Cauron");
        Person d = new Person(4, "Dareon");

        demo.enqueue(a);
        demo.enqueue(b);
        demo.enqueue(c);
        demo.enqueue(d);
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());
        demo.enqueue(a);
        System.out.println(demo.dequeue());
        demo.enqueue(b);
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());
        demo.enqueue(a);
        System.out.println(demo.dequeue());

        System.out.println();
        demo.enqueue(a);
        demo.enqueue(b);
        demo.enqueue(c);
        demo.enqueue(d);
        demo.remove(a);
        System.out.println(demo.dequeue());
        demo.remove(d);
        System.out.println(demo.dequeue());

        System.out.println();
        demo.enqueue(a);
        demo.enqueue(b);
        demo.enqueue(c);
        demo.enqueue(d);
        demo.remove(b);
        demo.remove(c);
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());

        System.out.println();
        demo.enqueue(a);
        demo.enqueue(b);
        demo.enqueue(c);
        demo.enqueue(d);
        demo.remove(b);
        demo.remove(d);
        System.out.println(demo.dequeue());
        System.out.println(demo.dequeue());





    }
}
