package com.black.mono.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnrolleeMapperTest {

    private EnrolleeMapper enrolleeMapper;

    @BeforeEach
    public void setUp() {
        enrolleeMapper = new EnrolleeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(enrolleeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(enrolleeMapper.fromId(null)).isNull();
    }
}
