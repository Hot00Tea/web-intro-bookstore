package mate.academy.web_intro_bookstore.controller;

import lombok.RequiredArgsConstructor;
import mate.academy.web_intro_bookstore.dto.BookDto;
import mate.academy.web_intro_bookstore.dto.CreateBookRequestDto;
import mate.academy.web_intro_bookstore.model.Book;
import mate.academy.web_intro_bookstore.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto findById (@PathVariable Long id) {
        return bookService.findById(id);
    }


    @PostMapping
    public BookDto save(@RequestBody CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }
}
