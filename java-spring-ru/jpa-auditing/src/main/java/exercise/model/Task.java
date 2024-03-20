package exercise.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

// BEGIN
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table
public class Task{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    long id ; // – уникальный идентификатор id, генерируется автоматически базой данных
    String title; // — название задачи
    String description ; //– описание задачи

    @CreatedDate
    @Column(name = "createdAt", updatable = false)
    private LocalDate createdAt ; //– дата создания новой задачи

    @LastModifiedDate
    @Column(name = "updatedAt", updatable = true)
    private LocalDate updatedAt;  // – дата последнего обновления задачи
}
// END
