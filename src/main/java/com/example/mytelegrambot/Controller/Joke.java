package com.example.mytelegrambot.Controller;

import com.example.mytelegrambot.model.DBJokes;
import com.example.mytelegrambot.service.JokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/jokes")
public class Joke {

    private final JokeService service;

    @PostMapping
    ResponseEntity<Void> addjoke(@RequestBody DBJokes text) {
        service.addjoke(text);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<List<DBJokes>> showAllJokes() {
        return ResponseEntity.ok(service.getAllJokes());
    }

    //сделать пагинацию
    @GetMapping("/pages/{page}")
    public Page<DBJokes> jokesPage(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 2); // Задаем размер страницы по умолчанию
        return service.pagejoke(pageable);
    }

    @GetMapping("/top5")
    List<DBJokes> getTop5Jokes() {
        return service.getTop5JokesByLikes();
    }

    @PostMapping("/top5/{id}")
    ResponseEntity<String> likejoke(@PathVariable Long id, @RequestBody DBJokes likes) {
        return service.likeJoke(id, likes.getLikes());
    }

    @GetMapping("/{id}")
    ResponseEntity<DBJokes> showjokebyid(@PathVariable Long id) {
        return service.getJokeById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deletejokebyid(@PathVariable Long id) {
        return service.deleteJokeById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<DBJokes> editjokebyid(@PathVariable Long id, @RequestBody DBJokes text) {
        return service.editJokeById(id, text.getText()).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/random")
    ResponseEntity<DBJokes> randomjoke() {
        return ResponseEntity.ok(service.getRandomJoke());
    }

    public Long getNumber() {
        return service.getNumberid();
    }
}
