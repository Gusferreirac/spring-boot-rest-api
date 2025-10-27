package com.gusferreirac.rest_api.service;

import com.gusferreirac.rest_api.data.dto.PersonDTO;
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

@Service
public class PersonService {

    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());
    @Autowired
    PersonRepository repository;


    public List<PersonDTO> findAll() {

        logger.info("Finding all People!");

        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person!");

        var entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        return parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person) {

        logger.info("Creating one Person!");

        var entity = parseObject(person, Person.class);
        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person) {

        logger.info("Updating one Person!");
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {

        logger.info("Deleting one Person!");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}