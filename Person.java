package corona;

public class Person {
    int id;
    String name;

    public Person(int id, String name){
        this.id = id;
        this.name = name;
    }


    /**
     * Returns a textual representation of this person.
     * The output string should be in the format:
     * <name>, ID number: <ID>.
     * For example if a person is named "John Locke" with ID 57, the output will be
     * "Jhon Locke, ID number: 57".
     */
    public String toString(){
        return  name + " , ID number: " + id ;
    }

}
