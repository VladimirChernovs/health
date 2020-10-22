package com.black.mono.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DependentMapperTest {

    private DependentMapper dependentMapper;

    @BeforeEach
    public void setUp() {
        dependentMapper = new DependentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(dependentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dependentMapper.fromId(null)).isNull();
    }
}
