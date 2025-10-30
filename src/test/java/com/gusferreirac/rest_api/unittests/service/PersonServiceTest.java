package com.gusferreirac.rest_api.unittests.service;

import com.gusferreirac.rest_api.data.dto.PersonDTO;
import com.gusferreirac.rest_api.exception.RequiredObjectIsNullException;
import com.gusferreirac.rest_api.model.Person;
import com.gusferreirac.rest_api.repository.PersonRepository;
import com.gusferreirac.rest_api.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        var result = service.findById(1L);

        assertNotNull(result); //Cannot be null
        assertNotNull(result.getId()); //Id cannot be null
        assertNotNull(result.getLinks()); //Links cannot be null

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                .getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET"))
        ); //Self link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET"))
        ); //findAll link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST"))
        ); //create link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT"))
        ); //update link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE"))
        ); //delete link check

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void create() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);
        when(repository.save(person)).thenReturn(persisted);

        var result = service.create(dto);

        assertNotNull(result); //Cannot be null
        assertNotNull(result.getId()); //Id cannot be null
        assertNotNull(result.getLinks()); //Links cannot be null

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET"))
        ); //Self link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET"))
        ); //findAll link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST"))
        ); //create link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT"))
        ); //update link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE"))
        ); //delete link check

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
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
        Person person = input.mockEntity(1);
        Person persisted = person;
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(person)).thenReturn(persisted);

        var result = service.update(dto);

        assertNotNull(result); //Cannot be null
        assertNotNull(result.getId()); //Id cannot be null
        assertNotNull(result.getLinks()); //Links cannot be null

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET"))
        ); //Self link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET"))
        ); //findAll link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST"))
        ); //create link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT"))
        ); //update link check

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE"))
        ); //delete link check

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
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
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        service.delete(1L);
        verify(repository, times(1)).findById(anyLong()); // Verify if called findById once before deleting
        verify(repository, times(1)).delete(any(Person.class)); // Verify if called delete once
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        List<Person> people = input.mockEntityList();

        when(repository.findAll()).thenReturn(people);
        List<PersonDTO> result = service.findAll();

        assertNotNull(result);
        assertEquals(14, result.size());

        var personOne = result.getFirst();
        assertNotNull(personOne); //Cannot be null
        assertNotNull(personOne.getId()); //Id cannot be null
        assertNotNull(personOne.getLinks()); //Links cannot be null

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/0")
                        && link.getType().equals("GET"))
        ); //Self link check

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET"))
        ); //findAll link check

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST"))
        ); //create link check

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT"))
        ); //update link check

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/0")
                        && link.getType().equals("DELETE"))
        ); //delete link check

        assertEquals("Address Test0", personOne.getAddress());
        assertEquals("First Name Test0", personOne.getFirstName());
        assertEquals("Last Name Test0", personOne.getLastName());
        assertEquals("Male", personOne.getGender());

        var personFour = result.get(4);
        assertNotNull(personFour); //Cannot be null
        assertNotNull(personFour.getId()); //Id cannot be null
        assertNotNull(personFour.getLinks()); //Links cannot be null

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/4")
                        && link.getType().equals("GET"))
        ); //Self link check

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET"))
        ); //findAll link check

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST"))
        ); //create link check

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT"))
        ); //update link check

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/4")
                        && link.getType().equals("DELETE"))
        ); //delete link check

        assertEquals("Address Test4", personFour.getAddress());
        assertEquals("First Name Test4", personFour.getFirstName());
        assertEquals("Last Name Test4", personFour.getLastName());
        assertEquals("Male", personFour.getGender());

        var lastPerson = result.getLast();
        assertNotNull(lastPerson); //Cannot be null
        assertNotNull(lastPerson.getId()); //Id cannot be null
        assertNotNull(lastPerson.getLinks()); //Links cannot be null

        assertNotNull(lastPerson.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/13")
                        && link.getType().equals("GET"))
        ); //Self link check

        assertNotNull(lastPerson.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET"))
        ); //findAll link check

        assertNotNull(lastPerson.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST"))
        ); //create link check

        assertNotNull(lastPerson.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT"))
        ); //update link check

        assertNotNull(lastPerson.getLinks().stream()
                .anyMatch(link -> link
                        .getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/13")
                        && link.getType().equals("DELETE"))
        ); //delete link check

        assertEquals("Address Test13", lastPerson.getAddress());
        assertEquals("First Name Test13", lastPerson.getFirstName());
        assertEquals("Last Name Test13", lastPerson.getLastName());
        assertEquals("Female", lastPerson.getGender());
    }
}