import java.io.Serializable;

/**
 * A very basic social media profile
 * @author Lenny Maynard
 */
public class Profile implements ProfileInterface, Serializable {

    private String name;
    private String about;
    private SimpleStack friends;

    /**
     * A no-args constructor that initializes all fields
     */
    public Profile(){
        name = "";
        about = "";
        friends = new SimpleStack(10);
    }

    /**
     * A constructor that takes in name and about me and
     * initializes all fields
     * @param n the profile's name
     * @param a the profile's about me
     */
    public Profile(String n, String a){
        name = n;
        about = a;
        friends = new SimpleStack(10);
    }

    /**
     * Sets the profile's name
     * @param name The new name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the profile's name
     * @return The name
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the profile's "about me" blurb
     * @param about The new blurb
     */
    public void setAbout(String about){
        this.about = about;
    }

    /**
     * Gets the profile's "about me" blurb
     * @return The blurb
     */
    public String getAbout(){
        return about;
    }

    /**
     * Adds another profile to this profile's following stack. The most obvious failure is when this
     * profile already follows the maximum number of other profiles. Although the stack may be
     * capable of holding duplicate items, this method should also return false if this profile is
     * already following other.
     * @param other The profile to follow
     * @return True if successful, false otherwise
     */
    public boolean follow(ProfileInterface other){
        boolean check;
        if(friends.contains(other)){          //if this person is already following the given profile return false
            check = false;
        }
        else{
            check = friends.add(other);       //add the given profile to the friend stack
        }
        return check;
    }

    /**
     * Removes the most recently-followed profile from this profile's following stack
     * @return The profile that was unfollowed
     */
    public ProfileInterface unfollow(){
        ProfileInterface person = (ProfileInterface)friends.remove();
        return person;
    }

    /**
     * Returns this profile's most recent follows
     * @param howMany The number of profiles to return
     * @return An array of size howMany, containing the most recently-followed profiles
     */
    public ProfileInterface[] following(int howMany){
        ProfileInterface[] people = new ProfileInterface[howMany];
        if(howMany>friends.size()) howMany = friends.size();         //if user asks for more friends than there are, only
                                                                     //return how many there are to avoid NullPointerException
        Object[] back = friends.topItems(howMany);
        for(int i = 0; i < howMany; i++){                       //put profiles in a ProfileInterface[] to return
            people[i] = (ProfileInterface)back[i];
        }
        return people;
    }

    /**
     * Recommend a profile for this profile to follow. Returns the most recently added friend
     * of this profile's most recently added friend
     * @return The profile to suggest, or null if no suitable profile is found.
     */
    public ProfileInterface recommend(){
        Object[] receive = friends.topItems(1);            //get this profile's top friend
        Profile yours = (Profile)receive[0];
        ProfileInterface[] theirs = yours.following(1);    //get the new profile's top friend
        ProfileInterface recommended = theirs[0];
        return recommended;
    }
}
