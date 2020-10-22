package com.black.mono.service.impl;

import com.black.mono.service.DependentService;
import com.black.mono.domain.Dependent;
import com.black.mono.repository.DependentRepository;
import com.black.mono.service.dto.DependentDTO;
import com.black.mono.service.mapper.DependentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Dependent}.
 */
@Service
@Transactional
public class DependentServiceImpl implements DependentService {

    private final Logger log = LoggerFactory.getLogger(DependentServiceImpl.class);

    private final DependentRepository dependentRepository;

    private final DependentMapper dependentMapper;

    public DependentServiceImpl(DependentRepository dependentRepository, DependentMapper dependentMapper) {
        this.dependentRepository = dependentRepository;
        this.dependentMapper = dependentMapper;
    }

    @Override
    public DependentDTO save(DependentDTO dependentDTO) {
        log.debug("Request to save Dependent : {}", dependentDTO);
        Dependent dependent = dependentMapper.toEntity(dependentDTO);
        dependent = dependentRepository.save(dependent);
        return dependentMapper.toDto(dependent);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DependentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dependents");
        return dependentRepository.findAll(pageable)
            .map(dependentMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DependentDTO> findOne(Long id) {
        log.debug("Request to get Dependent : {}", id);
        return dependentRepository.findById(id)
            .map(dependentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dependent : {}", id);
        dependentRepository.deleteById(id);
    }
}
