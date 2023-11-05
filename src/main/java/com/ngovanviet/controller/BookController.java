package com.ngovanviet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ngovanviet.model.Book;
import com.ngovanviet.repo.BookRepo;

@RestController
public class BookController {
	
	@Autowired
	private BookRepo bookRepo;
	
	
	@GetMapping("/getAllBooks")
	public ResponseEntity<List<Book>> getAllBooks() {
		try {
			List<Book> bookList = new ArrayList<Book>();
			bookRepo.findAll().forEach(bookList::add);
			if (bookList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(bookList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getBookById/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		Optional<Book> bookData = bookRepo.findById(id);
		if (bookData.isPresent()) {
			return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/addBook")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		Book bookObj = bookRepo.save(book);
		return new ResponseEntity<>(bookObj, HttpStatus.OK);
	}
	
	@PostMapping("/updateBookById/{id}")
	public ResponseEntity<Book> updateBookById(@PathVariable Long id, @RequestBody Book newBookData) {
		Optional<Book> oldBookData = bookRepo.findById(id);
		if (oldBookData.isPresent()) {
			Book updateBookData = oldBookData.get();
			updateBookData.setTitle(newBookData.getTitle());
			
			updateBookData.setAuthor(newBookData.getAuthor());
			Book bookObj = bookRepo.save(updateBookData);
			return new ResponseEntity<>(bookObj, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/deleteBookById/{id}")
	public ResponseEntity<Object> deleteBookById(@PathVariable Long id) {
		bookRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
