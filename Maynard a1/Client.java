
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

/**
 * A client class to allow the user to interact with the profiles
 * @author Lenny Maynard
 */
public class Client{
    static SimpleStack<Profile> data;
    static Scanner scan;

    public static void main(String[] args){
        scan   = new Scanner(System.in);
        data = new SimpleStack<Profile>();
        String fileName = "save.bin";

        restore(fileName);                       //attempts to restore data from a file

        boolean exit = false;
        while(exit ==false){
            displayMenu();                                  //display the menu and ask user for choice,
            System.out.print("Make a selection: ");         //then execute the corresponding method
            int menuOp = scan.nextInt();
            scan.nextLine();
            switch(menuOp){
                case 1: list();
                    break;
                case 2: create();
                    break;
                case 3: show();
                    break;
                case 4: edit();
                    break;
                case 5: follow();
                    break;
                case 6: unfollow();
                    break;
                case 7: suggest();
                    break;
                case 8: exit = true;
                    break;
            }
        }
        save(fileName);       //attempts to save data to a file


    }

    /**
     * Displays the menu of options
     */
    public static void displayMenu(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("1. List profiles");
        System.out.println("2. Create profile");
        System.out.println("3. Show profile");
        System.out.println("4. Edit profile");
        System.out.println("5. Follow");
        System.out.println("6. Unfollow");
        System.out.println("7. Suggest a follow");
        System.out.println("8. Quit");
    }

    /**
     * Displays a list of numbered profile names
     */
    public static void list(){
        System.out.println("Profiles: ");
        Object[] people = data.topItems(data.size());         //gets array of profiles in data
        for(int i = 0; i<data.size(); i++){
            Profile temp = (Profile) people[i];                   //cycle through each profile in  array and print name
            System.out.println("\t"+(i+1)+". "+temp.getName());
        }
    }

    /**
     * Creates a new profile with a name and about me blurb
     * @return true if the profile was created, false if it wasn't
     */
    public static boolean create(){
        System.out.print("Enter in a name: ");
        String name = scan.nextLine();
        System.out.print("Enter your \"about me\": ");
        String about = scan.nextLine();
        Profile person = new Profile(name, about);        //add new profile to data with given name and about me
        return data.add(person);
    }

    /**
     * Choose a profile and see their name, about me,
     * and 3 most recent follows
     */
    public static void show(){
        list();
        System.out.print("Choose a profile: ");             //user selects a profile from printed list() method
        int select = scan.nextInt();
        Object[] people = data.topItems(data.size());
        Profile chosen = (Profile)people[select-1];         //take profile from entry in Object[] to a Profile
        System.out.println(chosen.getName());                 //get and print profile's name, about me
        System.out.println("About Me:\n"+chosen.getAbout());
        System.out.println("Recently followed: ");
        ProfileInterface[] top = chosen.following(3);        //get ProfileInterface[] array with recently followed

        for(int i =0; i < 3;i++){                            //cycle through recently followed and print names
            if(top[i] != null) System.out.println("\t"+top[i].getName());
        }
    }

    /**
     * Choose a profile and edit its name and about me blurb
     */
    public static void edit(){
        list();
        System.out.print("Choose a profile: ");            //user selects a profile from printed list() method
        int select = scan.nextInt();
        scan.nextLine();
        Object[] people = data.topItems(data.size());
        Profile chosen = (Profile) people[select-1];
        System.out.print("New name: ");                   //user sets new name and about me
        chosen.setName(scan.nextLine());
        System.out.print("New \"about me\" : ");
        chosen.setAbout(scan.nextLine());
    }

    /**
     * Choose one profile to follow another profile
     */
    public static void follow(){
        list();
        System.out.print("Choose a profile: ");               //user selects a profile from printed list() method
        int select = scan.nextInt();
        Object[] people = data.topItems(data.size());
        Profile follower = (Profile) people[select - 1];
        System.out.print("Choose a profile to follow: ");     //user selects profile to be followed
        select = scan.nextInt();
        Profile followed = (Profile)people[select-1];
        follower.follow(followed);                       //first profile follows second profile
    }

    /**
     * Choose a profile and unfollow their most recent follow
     */
    public static void unfollow(){
        list();
        System.out.print("Who should unfollow their last person: ");    //user selects a profile from printed list() method
        int select = scan.nextInt();
        Object[] people = data.topItems(data.size());
        Profile rid = (Profile)people[select-1];
        rid.unfollow();                       //that profile removes their last follow
    }

    /**
     * Choose a profile and recommend a follow based on a mutual friend
     */
    public static void suggest(){
        list();                                                //user selects a profile from printed list() method
        System.out.print("Choose a profile to make a recommendation for: ");
        int select = scan.nextInt();
        Object[] people = data.topItems(data.size());
        Profile person = (Profile)people[select-1];
        Profile rec = (Profile)person.recommend();         //execute recommend() method for that profile
        System.out.println(rec.getName());                 //print name of recommended profile
    }

    /**
     * Attempts to save the data object to a file
     * @param fileName The filename of the save
     */
    public static void save(String fileName){
        try {
            ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(fileName));
            saveStream.writeObject(data);              //save data to file
        }
        catch(IOException e) {                         //catch exception
            System.err.println("Something went wrong saving to " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Attempts to restore from a previous save
     * @param filename The filename of the save
     * @return true on success, false on failure
     */
    public static boolean restore(String filename) {
        try {
            ObjectInputStream restoreStream = new ObjectInputStream(new FileInputStream(filename));
            data = (SimpleStack<Profile>)restoreStream.readObject();       //restore data from file
        }
        catch(FileNotFoundException e) {                             //catch exceptions
            System.err.println(filename + " does not exist.");
            return false;
        }
        catch(IOException e) {
            System.err.println("Error resuming from " + filename);
            return false;
        }
        catch(ClassNotFoundException e) {
            System.err.println("Error resuming from " + filename);
            return false;
        }
        return true;
    }

}
