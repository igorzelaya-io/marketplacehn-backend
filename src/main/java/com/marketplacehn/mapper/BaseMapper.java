package com.marketplacehn.mapper;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public interface BaseMapper <D, E> {

    E dtoToEntity(D dto);

    D toDto(E entity);

    List<D> entitiesToDtos(List<E> entities);

    List<E> dtosToEntities(List<D> dtos);

    default Page<D> pageOfEntitiesToDtos(Page<E> pageOfEntities) {
        return new PageImpl<>(entitiesToDtos(pageOfEntities.getContent()),
                pageOfEntities.getPageable(), pageOfEntities.getTotalElements());
    }

    default Page<E> pageOfDtosToEntities(Page<D> pageOfDtos) {
        return new PageImpl<>(dtosToEntities(pageOfDtos.getContent()),
                pageOfDtos.getPageable(), pageOfDtos.getTotalElements());
    }
}
