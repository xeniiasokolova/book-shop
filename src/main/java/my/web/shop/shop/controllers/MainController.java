package my.web.shop.shop.controllers;

import my.web.shop.shop.models.Book;
import my.web.shop.shop.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private BookRepository bookRepository; //объект с помощью которого делаем запросы к таблице

    @GetMapping("/")
    public String main(Model model) {
        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "main";
    }

    @GetMapping("/book/add")
    public String addBook(Model model) {
        return "form";
    }

    //сохраняем в бд данные
    @PostMapping("/book/add")
    public String getBook(@RequestParam String title, @RequestParam String author, @RequestParam String info, @RequestParam int price) {
        Book book = new Book(title, author, info, price);
        bookRepository.save(book);
        return "redirect:/"; //переход на главную страницу

    }

    @GetMapping("/book/{id}") //название параметра не принципиально
    public String getBookById(@PathVariable(value = "id") long id, Model model) {
        Book book = bookRepository.findById(id).get();
      //  bookRepository.deleteById(id); //удаление из бд по ид
        model.addAttribute("book", book);
        return "card_good";
    }

    @GetMapping("/book/{id}/redactor")
    public String redactorBook(@PathVariable(value = "id") long id, Model model) {
        Book book = bookRepository.findById(id).get();
        model.addAttribute("book", book);
        return "redactorForm";
    }

    @PostMapping("/book/{id}/redactor")
    public String getRedactorBook(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String author, @RequestParam String info, @RequestParam int price) {
        Book book = bookRepository.findById(id).get();
        book.setAuthor(author);
        book.setPrice(price);
        book.setDescription(info);
        book.setTitle(title);
        bookRepository.save(book);
        return "redirect:/"; //переход на главную страницу
    }

    @GetMapping("/book/{id}/delete") //название параметра не принципиально
    public String deleteBook(@PathVariable(value = "id") long id) {
        Book book = bookRepository.findById(id).get();
        bookRepository.deleteById(id); //удаление из бд по ид
        return "redirect:/"; //переход на главную страницу
    }

}
