public class Booking extends User{
    public String name;
    public String crname;
    public String dateofdeparture;
    public String noofppl;
    public float cost;
    public Booking( )
    {
        User user = new User();
        this.name= user.name;

    }
}
