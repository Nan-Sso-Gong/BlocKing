
package scoreBoard;

import junit.framework.Assert; //junit framework
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class ScoreListTest {
    ScoreList scoreList = ScoreList.getInstance();
    @AfterEach
    public void deleteAll(){
        scoreList.deleteAll();
    }

    @Test
    @DisplayName("점수 모두 삭제")
    public void 점수모두삭제(){
        //given
        User test1 = new User("test1", 100);
        User test2 = new User("test2", 100);
        //when
        scoreList.deleteAll();
        //then
        assertThat(scoreList.getList().size()).isEqualTo(0);

    }

    @Test
    @DisplayName("점수  push  기능")
    public void 점수푸쉬(){
        //given
        User test1 = new User("test1", 100);
        User test2 = new User("test2", 100);
        //when
        scoreList.push(test1);
        scoreList.push(test2);
        //then
        assertThat(test1).isSameAs(scoreList.getList().get(0));
        assertThat(test2).isSameAs(scoreList.getList().get(1));

    }

    @Test
    @DisplayName("점수 오름차순 정렬")
    public void ScoreListSortByDescTest() {
        //given
        User user1 = new User("test1", 100);
        User user2 = new User("test2", 60);
        User user3 = new User("test3", 80);
        scoreList.push(user1);
        scoreList.push(user2);
        scoreList.push(user3);

        //when
        scoreList.sortDescByScore();
        //then
        //비교하기 위해 내림차순으로 넣기
        assertThat(100).isEqualTo(scoreList.getList().get(0).getScore());
        assertThat(80).isEqualTo(scoreList.getList().get(1).getScore());
        assertThat(60).isEqualTo(scoreList.getList().get(2).getScore());

    }

}