package com.gusferreirac.rest_api.unittests.mapper.mocks;

import com.gusferreirac.rest_api.data.dto.BookDTO;
import com.gusferreirac.rest_api.model.Book;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MockBook {
    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setTitle("Title Test" + number);
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(LocalDateTime.of(2025, Month.NOVEMBER,15, 00, 00));
        book.setPrice(((number % 2) == 0) ? 20.0 : 10.0);
        book.setId(number.longValue());
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setTitle("Title Test" + number);
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(LocalDateTime.of(2025, Month.NOVEMBER,15, 00, 00));
        book.setPrice(((number % 2) == 0) ? 20.0 : 10.0);
        book.setId(number.longValue());
        return book;
    }
}
