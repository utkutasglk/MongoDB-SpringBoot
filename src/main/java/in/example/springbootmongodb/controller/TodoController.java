package in.example.springbootmongodb.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.example.springbootmongodb.model.TodoDTO;
import in.example.springbootmongodb.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private ToDoRepository todoRepo;


    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(){
      List<TodoDTO> todos = todoRepo.findAll();

      if(todos.size() > 0){
          return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);
      }else {
          return new ResponseEntity<>("No todos avaliable", HttpStatus.NOT_FOUND);
      }
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody List<TodoDTO> todoDTO){
        try{
            for(int i = 0; i < todoDTO.size(); i++) {
                todoRepo.save(todoDTO.get(i));
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    /*
    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
        try{
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
            return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     */

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodos(@PathVariable("id") String id){
        Optional<TodoDTO> todo = todoRepo.findById(id);

        if(todo.isPresent()){
            return new ResponseEntity<>(todo.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Todo not found with id " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody TodoDTO todoNew){
        Optional<TodoDTO> todo = todoRepo.findById(id);

        if(todo.isPresent()){
            TodoDTO todoToSave = todo.get();
            todoToSave.setCompleted(todoNew.getCompleted() != null ? todoNew.getCompleted() : todoToSave.getCompleted());
            todoToSave.setTodo(todoNew.getTodo() != null ? todoNew.getTodo() : todoToSave.getTodo());
            todoToSave.setDescription(todoNew.getDescription() != null ? todoNew.getDescription() : todoToSave.getDescription());
            todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todoToSave);
            return new ResponseEntity<>(todoToSave, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Todo not found with id " + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id){
        try{
            todoRepo.deleteById(id);
            return new ResponseEntity<>("Successfully deleted by id " + id, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
