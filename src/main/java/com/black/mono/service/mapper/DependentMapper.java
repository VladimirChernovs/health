package com.black.mono.service.mapper;


import com.black.mono.domain.*;
import com.black.mono.service.dto.DependentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dependent} and its DTO {@link DependentDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnrolleeMapper.class})
public interface DependentMapper extends EntityMapper<DependentDTO, Dependent> {

    @Mapping(source = "enrollee.id", target = "enrolleeId")
    @Mapping(source = "enrollee.name", target = "enrolleeName")
    DependentDTO toDto(Dependent dependent);

    @Mapping(source = "enrolleeId", target = "enrollee")
    Dependent toEntity(DependentDTO dependentDTO);

    default Dependent fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dependent dependent = new Dependent();
        dependent.setId(id);
        return dependent;
    }
}
