package com.internship.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship.model.Hashtag;

public interface HashtagRepo extends JpaRepository<Hashtag, Long>{
	Optional<Hashtag> findByName(String name);
}