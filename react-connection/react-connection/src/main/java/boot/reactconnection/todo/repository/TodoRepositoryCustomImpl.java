package boot.reactconnection.todo.repository;

import boot.reactconnection.todo.entity.Todo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static boot.reactconnection.todo.entity.QTodo.*;
import static org.springframework.util.StringUtils.*;

@RequiredArgsConstructor
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Todo> searchTodo(String search) {

      return queryFactory
                .selectFrom(todo)
                .where(todoNameLike(search))
                .fetch();
    }

    private BooleanExpression todoNameLike(String search) {
        return hasText(search) ? todo.todoName.like("%"+search+"%") :null;
    }
}
