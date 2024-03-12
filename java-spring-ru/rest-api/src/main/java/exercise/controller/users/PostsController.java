package exercise.controller.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {
    private List<Post> posts = Data.getPosts();

    @GetMapping("/users/{id}/posts")
    public ResponseEntity show(@PathVariable String id) {
        List<Post> usersPosts = posts.stream().filter(post -> post.getUserId() == Integer.parseInt(id)).collect(Collectors.toList());
        if(!usersPosts.isEmpty()){
            return ResponseEntity.ok().body(usersPosts);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/users/{id}/posts") // Обновление страницы
    public ResponseEntity createPost(@PathVariable String id,@RequestBody Post post) {
        var newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setSlug(post.getSlug());
        newPost.setBody(post.getBody());

       var response = ResponseEntity.status(HttpStatus.CREATED).body(newPost);

        newPost.setUserId(Integer.parseInt(id));
        posts.add(newPost);

        return response;
    }

}
// END


//    Реализуйте дополнительные обработчики маршрутов для Post:
//
//        GET /api/users/{id}/posts — список всех постов, написанных пользователем с таким же userId, как id в маршруте
//        POST /api/users/{id}/posts – создание нового поста, привязанного к пользователю по id.
//        Код должен возвращать статус 201, тело запроса должно содержать slug, title и body.
//        Обратите внимание, что userId берется из маршрута