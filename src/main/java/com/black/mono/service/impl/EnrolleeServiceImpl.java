package com.black.mono.service.impl;

import com.black.mono.service.EnrolleeService;
import com.black.mono.domain.Enrollee;
import com.black.mono.repository.EnrolleeRepository;
import com.black.mono.service.dto.EnrolleeDTO;
import com.black.mono.service.mapper.EnrolleeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Enrollee}.
 */
@Service
@Transactional
public class EnrolleeServiceImpl implements EnrolleeService {

    private final Logger log = LoggerFactory.getLogger(EnrolleeServiceImpl.class);

    private final EnrolleeRepository enrolleeRepository;

    private final EnrolleeMapper enrolleeMapper;

    public EnrolleeServiceImpl(EnrolleeRepository enrolleeRepository, EnrolleeMapper enrolleeMapper) {
        this.enrolleeRepository = enrolleeRepository;
        this.enrolleeMapper = enrolleeMapper;
    }

    @Override
    public EnrolleeDTO save(EnrolleeDTO enrolleeDTO) {
        log.debug("Request to save Enrollee : {}", enrolleeDTO);
        Enrollee enrollee = enrolleeMapper.toEntity(enrolleeDTO);
        enrollee = enrolleeRepository.save(enrollee);
        return enrolleeMapper.toDto(enrollee);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnrolleeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Enrollees");
        return enrolleeRepository.findAll(pageable)
            .map(enrolleeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EnrolleeDTO> findOne(Long id) {
        log.debug("Request to get Enrollee : {}", id);
        return enrolleeRepository.findById(id)
            .map(enrolleeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Enrollee : {}", id);
        enrolleeRepository.deleteById(id);
    }
}
