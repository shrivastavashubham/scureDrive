package com.ssp.storage.repository;

import org.springframework.data.repository.CrudRepository;

import com.ssp.storage.domain.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {

}
