package com.gusferreirac.rest_api.service;

import com.gusferreirac.rest_api.controller.BookController;
import com.gusferreirac.rest_api.data.dto.BookDTO;
import com.gusferreirac.rest_api.exception.RequiredObjectIsNullException;
import com.gusferreirac.rest_api.exception.ResourceNotFoundException;
import com.gusferreirac.rest_api.model.Book;
import com.gusferreirac.rest_api.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gusferreirac.rest_api.mapper.ObjectMapper.parseListObjects;
import static com.gusferreirac.rest_api.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private Logger logger = LoggerFactory.getLogger(BookService.class.getName());
    @Autowired
    BookRepository repository;


    public List<BookDTO> findAll() {

        logger.info("Finding all Books!");

        var booksDto = parseListObjects(repository.findAll(), BookDTO.class);

        booksDto.forEach(this::addHateoasLinks);

        return booksDto;
    }

    public BookDTO findById(Long id) {
        logger.info("Finding one Book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        var book = parseObject(entity, BookDTO.class);
        addHateoasLinks(book);

        return book;
    }

    public BookDTO create(BookDTO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Book!");

        var entity = parseObject(person, Book.class);
        var bookDto = parseObject(repository.save(entity), BookDTO.class);
        addHateoasLinks(bookDto);

        return bookDto;
    }

    public BookDTO update(BookDTO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Book!");
        Book entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(person.getAuthor());
        entity.setLaunchDate(person.getLaunchDate());
        entity.setPrice(person.getPrice());
        entity.setTitle(person.getTitle());

        var bookDto = parseObject(repository.save(entity), BookDTO.class);
        addHateoasLinks(bookDto);

        return bookDto;
    }

    public void delete(Long id) {

        logger.info("Deleting one Book!");

        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

    private void addHateoasLinks(BookDTO bookDto) {
        bookDto.add(linkTo(methodOn(BookController.class).findById(bookDto.getId())).withSelfRel().withType("GET"));
        bookDto.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));
        bookDto.add(linkTo(methodOn(BookController.class).create(bookDto)).withRel("create").withType("POST"));
        bookDto.add(linkTo(methodOn(BookController.class).update(bookDto)).withRel("update").withType("PUT"));
        bookDto.add(linkTo(methodOn(BookController.class).delete(bookDto.getId())).withRel("delete").withType("DELETE"));
    }
}
