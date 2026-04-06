package org.example.expert.domain.todo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "todos")
public class Todo extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String contents;
    private String weather;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "todo", cascade = CascadeType.PERSIST)
    // 수정: Todo 저장 시 managers 리스트에 있는 Manager도 함께 DB에 저장되도록 cascade 설정 추가
    // 개념 정리: cascade = PERSIST → 부모(Todo) 저장 시 자식(Manager)도 같이 저장됨
    private List<Manager> managers = new ArrayList<>();

    public Todo(String title, String contents, String weather, User user) {
        this.title = title;
        this.contents = contents;
        this.weather = weather;
        this.user = user;

        // 수정 포인트 설명:
        // Todo 생성 시 작성자를 자동으로 담당자로 등록하기 위해 Manager 객체를 생성해서 리스트에 추가
        // 위에서 cascade = PERSIST 설정을 했기 때문에 todoRepository.save(todo)만 해도 이 Manager도 같이 저장됨
        this.managers.add(new Manager(user, this));
    }
}