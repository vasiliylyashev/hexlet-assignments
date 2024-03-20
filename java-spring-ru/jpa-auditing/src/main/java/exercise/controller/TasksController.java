package exercise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Task;
import exercise.repository.TaskRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(path = "")
    public List<Task> index() {
        return taskRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Task show(@PathVariable long id) {

        var task =  taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

        return task;
    }

    // BEGIN
    @PostMapping(consumes = "application/json")
    public ResponseEntity create(@RequestBody String json) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        taskRepository.save(om.readValue(json, Task.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(json);
    }

    @PutMapping(consumes = "application/json", path = "/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody String json) throws JsonProcessingException {
        var task =  taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        ObjectMapper om = new ObjectMapper();
        Task parsedTask = om.readValue(json, Task.class);

        task.setTitle(parsedTask.getTitle());
        task.setDescription(parsedTask.getDescription());

        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    // END

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        taskRepository.deleteById(id);
    }
}
