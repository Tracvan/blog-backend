package com.codegym.repository;

import com.codegym.model.Mode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IModeRepository extends JpaRepository<Mode, Long> {
}
