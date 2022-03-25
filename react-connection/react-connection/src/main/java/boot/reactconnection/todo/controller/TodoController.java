package boot.reactconnection.todo.controller;

import boot.reactconnection.securityoauth2.SessionUser;
import boot.reactconnection.todo.entity.Complete;
import boot.reactconnection.todo.entity.Todo;
import boot.reactconnection.todo.repository.TodoRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;
    private final HttpSession httpSession;

    @GetMapping("/todo/info")
    public SessionUser findUser(HttpServletRequest request) {
        String requestedSessionId = request.getRequestedSessionId();
        System.out.println(requestedSessionId);
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        return user;
    }

    @GetMapping("/todo")
    public List<Todo> findAllTodo(@RequestParam String search) {

        return todoRepository.searchTodo(search);
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
            todoCompleteUpdate(requestData.getId());

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

    private void todoCompleteUpdate(Long id) {
        Todo findTodo = todoRepository.getById(id);
        if (findTodo.getComplete() == Complete.N) {
            findTodo.setComplete(Complete.Y);
        }else{
            findTodo.setComplete(Complete.N);
        }
        todoRepository.flush();
    }

    @Data
    static class RequestData{
        private Long id;
        private String name;
        private String search;
    }
}
