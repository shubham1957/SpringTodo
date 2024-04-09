package com.example.todolistapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/todos")
public class TodoController {
    ArrayList<Todo> todoList;

    public  TodoController(){
        todoList = new ArrayList<>();
        todoList.add(new Todo(1,false,"Todo 1",1));
        todoList.add(new Todo(2,true,"Todo 2",2));
        todoList.add(new Todo(3,false,"Todo 3",3));
    }

    @GetMapping
    //- @ResponseStatus--Method 1
    public ResponseEntity<ArrayList<Todo>> getTodos(@RequestParam(required = false) Boolean isCompleted) {
        System.out.println("Incoming query params: " + isCompleted);
        return ResponseEntity.ok(todoList);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //
    public ResponseEntity<Todo> createBook(@RequestBody Todo newBook) { // @requestBody for body params
        todoList.add(newBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook); // method 3
    }

    //Search the book in the book list
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) { // "?" - MEANS ANYTHING

        for (Todo book : todoList) {
            if (book.getId() == id) {
                return ResponseEntity.ok(book);
            }
        }
        // IF TODO NOT FOUND THEN IT ILL SEND A CUSTOM MESSAGE THAT TODO_NOT_FOUND
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TODO_NOT_FOUND");

    }

    // delete a book using the book id
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id) { // @PathVariable for URL params
        for (Todo todo : todoList) {
            if (todo.getId() == id) {
                todoList.remove(todo);
                return ResponseEntity.accepted().build();
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo Not Found");

    }


}
