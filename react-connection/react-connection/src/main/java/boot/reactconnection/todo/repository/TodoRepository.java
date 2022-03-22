package boot.reactconnection.todo.repository;

import boot.reactconnection.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> , TodoRepositoryCustom {
}
