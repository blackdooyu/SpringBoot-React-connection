package boot.reactconnection.todo.controller;

import boot.reactconnection.todo.entity.Complete;
import boot.reactconnection.todo.entity.Todo;
import boot.reactconnection.todo.repository.TodoRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;

    @GetMapping("/todo")
    public List<Todo> findAllTodo() {
        return todoRepository.findAll();
    }

    @PostMapping("/todo")
    public String saveTodo(@RequestBody RequestData requestData) {

        if (requestData.getName() != null) {
            Todo todo = new Todo(requestData.getName());
            todoRepository.save(todo);

            return "성공";
        }

        return "실패";
    }

    @PutMapping("/todo")
    public String updateTodo(@RequestBody RequestData requestData) {
        if (requestData.getId() != null) {
            todoComplete(requestData.getId());

            return "성공";
        }
        return "실패";
    }

    @DeleteMapping("/todo")
    public String removeTodo(@RequestBody RequestData requestData){

        if (requestData.getId() != null) {
            Todo findTodo = todoRepository.getById(requestData.getId());
            todoRepository.delete(findTodo);

            return "성공";
        }

        return "실패";
    }

    private void todoComplete(Long id) {
        Todo findTodo = todoRepository.getById(id);
        findTodo.setComplete(Complete.Y);
        todoRepository.flush();
    }

    @Data
    static class RequestData{
        private Long id;
        private String name;
    }
}
