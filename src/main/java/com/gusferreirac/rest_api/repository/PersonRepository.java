package com.gusferreirac.rest_api.repository;


import com.gusferreirac.rest_api.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}