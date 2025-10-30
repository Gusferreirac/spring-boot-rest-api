package com.gusferreirac.rest_api.unittests.service;

import com.gusferreirac.rest_api.controller.PersonController;
import com.gusferreirac.rest_api.data.dto.PersonDTO;
import com.gusferreirac.rest_api.exception.RequiredObjectIsNullException;
import com.gusferreirac.rest_api.exception.ResourceNotFoundException;
import com.gusferreirac.rest_api.model.Person;
import com.gusferreirac.rest_api.repository.PersonRepository;
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
public class PersonService {

    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());
    @Autowired
    PersonRepository repository;


    public List<PersonDTO> findAll() {

        logger.info("Finding all People!");

        var peopleDto = parseListObjects(repository.findAll(), PersonDTO.class);

        peopleDto.forEach(this::addHateoasLinks);

        return peopleDto;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person!");

        var entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        var person = parseObject(entity, PersonDTO.class);
        addHateoasLinks(person);

        return person;
    }

    public PersonDTO create(PersonDTO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Person!");

        var entity = parseObject(person, Person.class);
        var personDto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(personDto);

        return personDto;
    }

    public PersonDTO update(PersonDTO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Person!");
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var personDto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(personDto);

        return personDto;
    }

    public void delete(Long id) {

        logger.info("Deleting one Person!");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO personDto) {
        personDto.add(linkTo(methodOn(PersonController.class).findById(personDto.getId())).withSelfRel().withType("GET"));
        personDto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        personDto.add(linkTo(methodOn(PersonController.class).create(personDto)).withRel("create").withType("POST"));
        personDto.add(linkTo(methodOn(PersonController.class).update(personDto)).withRel("update").withType("PUT"));
        personDto.add(linkTo(methodOn(PersonController.class).delete(personDto.getId())).withRel("delete").withType("DELETE"));
    }
}