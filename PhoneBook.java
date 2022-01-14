import java.util.*;
import java.io.*;

public class PhoneBook{
    public static Scanner keyboard = new Scanner(System.in);
    public static ArrayList<Person> people = new ArrayList<Person>();
    public static void main(String[] args) throws IOException, InterruptedException {
        
        //open file
        File dataFile = new File("phoneBook.txt");
        Scanner data = null;
        try{
            data = new Scanner(dataFile);
        }catch(FileNotFoundException e){
            System.out.println("Error opening file");
            System.exit(0);
        }
        
        //read each line of file as Person object
        while(data.hasNextLine()){
            people.add(new Person(data.nextLine()));
        }
        int option;
        do{//keep showing menu until exit
            option = menu();
            keyboard.nextLine();
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            switch (option) {
                case 1://view all records
                    for (Person person : people) {
                        person.print();
                        System.out.println();
                    }
                    break;
                case 2://add record
                    System.out.println("Add entry:\n----------");
                    System.out.println("First name: ");
                    String f = keyboard.nextLine();
                    System.out.println("Last name: ");
                    String l = keyboard.nextLine();
                    System.out.println("Email address: ");
                    String e = keyboard.nextLine();
                    System.out.println("Phone number: ");
                    String p = keyboard.nextLine();
                    people.add(new Person(f,l,e,p));
                    System.out.println("Added!");
                    break;
                case 3://search for records
                    int filter;
                    boolean valid;
                    System.out.println("Search by:");
                    System.out.println("1 - First Name");
                    System.out.println("2 - Last Name");
                    System.out.println("3 - Email");
                    System.out.println("4 - Phone number");
                    System.out.println("5 - All");
                    do{//validate input
                        filter = keyboard.nextInt();
                        keyboard.nextLine();
                        valid = filter>=1 && filter<=5;
                    }while(!valid);
                    //print results
                    ArrayList<Person> searchResults = search(filter);
                    System.out.println("Results:\n---------");
                    if(searchResults.size()<1){
                        System.out.println("No results found");
                    }else{
                        for (Person person : searchResults) {
                            person.print();
                            System.out.println();
                        }
                    }
                    break;
                case 4://edit record
                    //ask for record to edit
                    System.out.println("Search for record to edit:");
                    ArrayList<Person> editSearch = search(5);
                    if(editSearch.size()>1){
                        System.out.println("Multiple results found:");
                        for (Person person : editSearch) {
                            person.print();
                            System.out.println();
                        }
                        System.out.println("Please refine your search");
                    }else if(editSearch.size()<1){
                        System.out.println("No results found");
                    }else{//select which field to edit
                        int field;
                        boolean v;
                        Person person = people.get(people.indexOf(editSearch.get(0)));
                        person.print();
                        System.out.println("\nSelect a field to edit:");
                        System.out.println("1 - First Name");
                        System.out.println("2 - Last Name");
                        System.out.println("3 - Email");
                        System.out.println("4 - Phone number");
                        do{//validate input
                            field = keyboard.nextInt();
                            keyboard.nextLine();
                            v = field>=1 && field<=4;
                        }while(!v);

                        System.out.println("Enter new data:");
                        String n = keyboard.nextLine();
                        person.edit(field, n);
                    }

                    break;
                case 5://delete
                    //ask which record to delete
                    System.out.println("Search for record to delete:");
                    ArrayList<Person> deleteSearch = search(5);
                    if(deleteSearch.size()>1){
                        System.out.println("Multiple results found:");
                        for (Person person : deleteSearch) {
                            person.print();
                            System.out.println();
                        }
                        System.out.println("Please refine your search");
                    }else if(deleteSearch.size()<1){
                        System.out.println("No results found");
                    }else{//confirm
                        boolean yes;
                        System.out.println("Are you sure you want to delete this record?\n");
                        Person deletePerson = people.get(people.indexOf(deleteSearch.get(0)));
                        deletePerson.print();
                        System.out.print("\n1: yes   2: no  ");
                        yes = keyboard.nextInt()==1;
                        keyboard.nextLine();
                        if(yes){
                            people.remove(deletePerson);
                            System.out.println("Deleted!");
                        }
                    }
                    break;
                default:
                    break;
            }
            if(option!=6){
                System.out.println("press enter to return to menu");
                keyboard.nextLine();
            }
        }while(!(option==6));

        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        //create new file with changed data
        String changes = "";
        for (Person person : people) {
            changes += person.save();
        }
        try {//save 
            FileWriter f = new FileWriter(dataFile, false);
            f.write(changes);
            System.out.println("File saved!");
            f.close();
        } catch (IOException e) {
            System.out.println("Error saving file");
        } 
        data.close();
    }
    private static int menu() throws IOException, InterruptedException {
        //print menu and return chosen option
        int option;
        boolean valid;

        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("Phone book: choose an option");
        System.out.println("1 - view all entries");
        System.out.println("2 - add entry");
        System.out.println("3 - search entries");
        System.out.println("4 - edit an entry");
        System.out.println("5 - delete an entry");
        System.out.println("6 - save and exit");
        do{//validate
            option = keyboard.nextInt();
            valid = option>=1 && option<=6;
        }while(!valid);
        return option;
    }
    private static ArrayList<Person> search(int filter){
        //search each person for matches
        ArrayList<Person> results = new ArrayList<Person>();
        
        System.out.println("Search for:");
        String input = keyboard.nextLine();
        
        for (Person person : people) {
            if(person.searchText(filter, input)){
                results.add(person);
            }
        }
        return results;
    }
}
