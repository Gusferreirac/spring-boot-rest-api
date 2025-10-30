package com.gusferreirac.rest_api.service;

import com.gusferreirac.rest_api.data.dto.BookDTO;
import com.gusferreirac.rest_api.exception.RequiredObjectIsNullException;
import com.gusferreirac.rest_api.model.Book;
import com.gusferreirac.rest_api.repository.BookRepository;
import com.gusferreirac.rest_api.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook input;

    @InjectMocks
    private BookService service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockBook();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        var result = service.findById(1L);

        assertNotNull(result); //Cannot be null
        assertNotNull(result.getId()); //Id cannot be null
        assertNotNull(result.getLinks()); //Links cannot be null

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET"))
        ); //Self link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET"))
        ); //findAll link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST"))
        ); //create link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT"))
        ); //update link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE"))
        ); //delete link check

        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(10.0, result.getPrice());
        assertEquals(LocalDateTime.of(2025, Month.NOVEMBER,15, 00, 00), result.getLaunchDate());
    }

    @Test
    void create() {
        Book book = input.mockEntity(1);
        Book persisted = book;
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);
        when(repository.save(book)).thenReturn(persisted);

        var result = service.create(dto);

        assertNotNull(result); //Cannot be null
        assertNotNull(result.getId()); //Id cannot be null
        assertNotNull(result.getLinks()); //Links cannot be null

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET"))
        ); //Self link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET"))
        ); //findAll link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST"))
        ); //create link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT"))
        ); //update link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE"))
        ); //delete link check

        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(10.0, result.getPrice());
        assertEquals(LocalDateTime.of(2025, Month.NOVEMBER,15, 00, 00), result.getLaunchDate());
    }

    @Test
    void testCreateWithNullPerson(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.create(null);
                });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Book book = input.mockEntity(1);
        Book persisted = book;
        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(persisted);

        var result = service.update(dto);

        assertNotNull(result); //Cannot be null
        assertNotNull(result.getId()); //Id cannot be null
        assertNotNull(result.getLinks()); //Links cannot be null

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET"))
        ); //Self link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET"))
        ); //findAll link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST"))
        ); //create link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT"))
        ); //update link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE"))
        ); //delete link check

        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(10.0, result.getPrice());
        assertEquals(LocalDateTime.of(2025, Month.NOVEMBER,15, 00, 00), result.getLaunchDate());
    }

    @Test
    void testUpdateWithNullPerson(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.update(null);
                });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Book person = input.mockEntity(1);
        person.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        service.delete(1L);
        verify(repository, times(1)).findById(anyLong()); // Verify if called findById once before deleting
        verify(repository, times(1)).delete(any(Book.class)); // Verify if called delete once
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        List<Book> books = input.mockEntityList();

        when(repository.findAll()).thenReturn(books);
        List<BookDTO> result = service.findAll();

        assertNotNull(result);
        assertEquals(14, result.size());

        var bookOne= result.get(1);
        assertNotNull(bookOne); //Cannot be null
        assertNotNull(bookOne.getId()); //Id cannot be null
        assertNotNull(bookOne.getLinks()); //Links cannot be null

        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET"))
        ); //Self link check

        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET"))
        ); //findAll link check

        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST"))
        ); //create link check

        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT"))
        ); //update link check

        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE"))
        ); //delete link check

        assertEquals("Title Test1", bookOne.getTitle());
        assertEquals("Author Test1", bookOne.getAuthor());
        assertEquals(10.0, bookOne.getPrice());
        assertEquals(LocalDateTime.of(2025, Month.NOVEMBER,15, 00, 00), bookOne.getLaunchDate());

        var bookFour = result.get(4);
        assertNotNull(bookFour); //Cannot be null
        assertNotNull(bookFour.getId()); //Id cannot be null
        assertNotNull(bookFour.getLinks()); //Links cannot be null

        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET"))
        ); //Self link check

        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET"))
        ); //findAll link check

        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST"))
        ); //create link check

        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT"))
        ); //update link check

        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE"))
        ); //delete link check

        assertEquals("Title Test4", bookFour.getTitle());
        assertEquals("Author Test4", bookFour.getAuthor());
        assertEquals(20.0, bookFour.getPrice());
        assertEquals(LocalDateTime.of(2025, Month.NOVEMBER,15, 00, 00), bookFour.getLaunchDate());

        var lastBook = result.getLast();
        assertNotNull(lastBook); //Cannot be null
        assertNotNull(lastBook.getId()); //Id cannot be null
        assertNotNull(lastBook.getLinks()); //Links cannot be null

        assertNotNull(lastBook.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET"))
        ); //Self link check

        assertNotNull(lastBook.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET"))
        ); //findAll link check

        assertNotNull(lastBook.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST"))
        ); //create link check

        assertNotNull(lastBook.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT"))
        ); //update link check

        assertNotNull(lastBook.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE"))
        ); //delete link check

        assertEquals("Title Test13", lastBook.getTitle());
        assertEquals("Author Test13", lastBook.getAuthor());
        assertEquals(10.0, lastBook.getPrice());
        assertEquals(LocalDateTime.of(2025, Month.NOVEMBER,15, 00, 00), lastBook.getLaunchDate());
    }
}