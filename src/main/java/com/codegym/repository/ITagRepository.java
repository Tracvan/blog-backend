package com.codegym.repository;

import com.codegym.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITagRepository extends JpaRepository<Tag,Long> {
}
