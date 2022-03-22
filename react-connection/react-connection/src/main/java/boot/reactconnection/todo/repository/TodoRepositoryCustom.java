package boot.reactconnection.todo.repository;


import boot.reactconnection.todo.entity.Todo;

import java.util.List;

public interface TodoRepositoryCustom {

    List<Todo> searchTodo(String search);
}
