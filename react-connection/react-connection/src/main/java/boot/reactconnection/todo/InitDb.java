package boot.reactconnection.todo;

import boot.reactconnection.todo.entity.Todo;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.StringTokenizer;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final Init init;

    @PostConstruct
    public void dataSet() {
        init.initData();
    }



    @Component
    static class Init{

        @Autowired
        EntityManager em;

        @Transactional
        public void initData() {
            String list = "공부 과제 운동 밥먹기 청소";
            StringTokenizer st = new StringTokenizer(list);

            for (int i = 0; i < 5; i++) {
                em.persist(new Todo(st.nextToken()));
            }
        }
    }
}
