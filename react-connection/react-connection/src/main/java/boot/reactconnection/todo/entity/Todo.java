package boot.reactconnection.todo.entity;

import boot.reactconnection.CreateDate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Todo extends CreateDate {

    @Id
    @GeneratedValue
    @Column(name = "todo_id")
    private Long id;

    @Column(nullable = false)
    private String todoName;

    @Enumerated(EnumType.STRING)
    private Complete complete;

    @PrePersist
    private void completeSet(){
        complete = Complete.N;
    }

    public Todo(String todoName) {
        this.todoName = todoName;

    }

    protected Todo() {
    }
}
