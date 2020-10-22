package com.black.mono.service.mapper;


import com.black.mono.domain.*;
import com.black.mono.service.dto.EnrolleeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Enrollee} and its DTO {@link EnrolleeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EnrolleeMapper extends EntityMapper<EnrolleeDTO, Enrollee> {


    @Mapping(target = "dependents", ignore = true)
    @Mapping(target = "removeDependent", ignore = true)
    Enrollee toEntity(EnrolleeDTO enrolleeDTO);

    default Enrollee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Enrollee enrollee = new Enrollee();
        enrollee.setId(id);
        return enrollee;
    }
}
