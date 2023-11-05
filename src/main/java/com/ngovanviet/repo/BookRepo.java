package com.ngovanviet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngovanviet.model.Book;

public interface BookRepo extends JpaRepository<Book, Long> {

}
