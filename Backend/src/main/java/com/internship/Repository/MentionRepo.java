package com.internship.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship.model.Mention;

public interface MentionRepo extends JpaRepository<Mention, Long> {

}
