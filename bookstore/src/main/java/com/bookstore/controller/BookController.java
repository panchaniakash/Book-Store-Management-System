package com.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bookstore.entities.Book;
import com.bookstore.entities.MyBookList;
import com.bookstore.service.BookService;
import com.bookstore.service.MyBookListService;

@Controller
public class BookController {

	@Autowired
	private BookService service;

	@Autowired
	private MyBookListService myBookService;

	@GetMapping("/")
	public String home() {
		return "home";
	}

//	@GetMapping("/")
//	public ResponseEntity<String> home() {
////		HttpHeaders header = new HttpHeaders();
////		header.add("Location","/");
//		return  ResponseEntity.of(Optional.of("home"));
//	}

//	@GetMapping("/")
//	public ResponseEntity<Void> home() {
////		HttpHeaders headers = new HttpHeaders();
////		headers.add("Location", "/");
//		return ResponseEntity.status(HttpStatus.SEE_OTHER).build();
//	}

	@GetMapping("/book_register")
	public String bookRegister() {
		return "bookRegister";
	}

	@GetMapping("/available_books")
	public ModelAndView getAllBooks() {
		List<Book> list = service.getAllBook();
		ModelAndView m = new ModelAndView();
		m.setViewName("bookList");
		m.addObject("book", list);

		return new ModelAndView("bookList", "book", list);
	}

//	@GetMapping("/available_books")
//	public ResponseEntity<List<Book>> getAllBooks() {
//		List<Book> list = service.getAllBook();
//
//		if (list.size() <= 0) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		}
////		ModelAndView m = new ModelAndView();
////		m.setViewName("bookList");
////		m.addObject("book", list);
//
//		return ResponseEntity.of(Optional.of(list));
//	}

	@PostMapping("/save")
	public String addBook(@ModelAttribute Book b) {
		service.save(b);
		return "redirect:/available_books";

	}

	@GetMapping("/my_books")
	public String getMyBooks(Model model) {
		List<MyBookList> list = myBookService.getAllMyBooks();
		model.addAttribute("book", list);
		return "mybooks";
	}

	@RequestMapping("/mylist/{id}")
	public String getMyList(@PathVariable("id") int id) {

		Book b = service.getBoobyId(id);
		MyBookList mb = new MyBookList(b.getId(), b.getName(), b.getAuthor(), b.getPrice());
		myBookService.saveMyBook(mb);
		return "redirect:/my_books";
	}

	@RequestMapping(value = "/editBook/{id}")

	public String editBook(@PathVariable("id") int id, Model model) {

		Book b = service.getBoobyId(id);
		model.addAttribute("book", b);
		return "bookEdit";
	}

	@RequestMapping("/deleteBook/{id}")

	public String deleteBook(@PathVariable("id") int id) {

		service.deleteById(id);
		return "redirect:/available_books";
	}

}
