package exercise;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts") // Список страниц
    public ResponseEntity index(@RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok()
                .header("X-Total-Count",
                String.valueOf(posts.stream().toList().size())).body(posts.stream().limit(limit).toList());
    }

    @PostMapping("/posts") // Создание страницы
    public ResponseEntity create(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping("/posts/{id}") // Вывод страницы
    public ResponseEntity show(@PathVariable String id) {
        var page = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst().orElse(null);
        if(null != page){
            return ResponseEntity.ok().body(page);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/posts/{id}") // Обновление страницы
    public ResponseEntity update(@PathVariable String id, @RequestBody Post data) {
        var maybePost = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (maybePost.isPresent()) {
            var page = maybePost.get();
            page.setId(data.getId());
            page.setTitle(data.getTitle());
            page.setBody(data.getBody());
            return ResponseEntity.status(HttpStatus.OK).body(page);
        } else{
           return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}