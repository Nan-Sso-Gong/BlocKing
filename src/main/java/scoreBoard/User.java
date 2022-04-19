package scoreBoard;

public class User implements Comparable<User>{

    private String name;

    private int score;

    public User(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }




    //내림차순 정렬
    @Override
    public int compareTo(User o) {
        return o.score - this.getScore();
    }
}
