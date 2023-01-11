package com.marketplacehn.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;

public abstract class AbstractService<R extends JpaRepository<E, K>, E, K> {

    @Autowired
    R entityRepository;

    E findById(K id) {
        return entityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

}
