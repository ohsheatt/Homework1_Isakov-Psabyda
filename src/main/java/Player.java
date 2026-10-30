public class Player {

    private String name;
    private int age;
    private String club;
    private String nationality;
    private String position;
    private double marketValue;

    public Player(String name, int age, String club,
                  String nationality, String position,
                  double marketValue) {
        this.name = name;
        this.age = age;
        this.club = club;
        this.nationality = nationality;
        this.position = position;
        this.marketValue = marketValue;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getClub() {
        return club;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPosition() {
        return position;
    }

    public double getMarketValue() {
        return marketValue;
    }

    @Override
    public String toString() {
        return name + " | " + age + " | " + club
                + " | " + nationality + " | " + position
                + " | " + marketValue + " млн евро";
    }
}
