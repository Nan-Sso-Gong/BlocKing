package scoreBoard;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class ScoreList{
    private List<User> list = new ArrayList<>();

    private static ScoreList scoreList = new ScoreList();

    //싱글톤
    public static ScoreList getInstance() {
        return scoreList;
    }

    public List<User> getList() {
        return list;
    }

    public List<User> push(User user){
        this.list.add(user);
        return this.list;
    }

    public void deleteAll(){
        this.list.clear();
    }

    public List<User> sortDescByScore(){
        Collections.sort(list);
        return this.list;
    }





}
