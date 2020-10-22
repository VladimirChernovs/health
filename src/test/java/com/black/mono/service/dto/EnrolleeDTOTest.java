package com.black.mono.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.black.mono.web.rest.TestUtil;

public class EnrolleeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrolleeDTO.class);
        EnrolleeDTO enrolleeDTO1 = new EnrolleeDTO();
        enrolleeDTO1.setId(1L);
        EnrolleeDTO enrolleeDTO2 = new EnrolleeDTO();
        assertThat(enrolleeDTO1).isNotEqualTo(enrolleeDTO2);
        enrolleeDTO2.setId(enrolleeDTO1.getId());
        assertThat(enrolleeDTO1).isEqualTo(enrolleeDTO2);
        enrolleeDTO2.setId(2L);
        assertThat(enrolleeDTO1).isNotEqualTo(enrolleeDTO2);
        enrolleeDTO1.setId(null);
        assertThat(enrolleeDTO1).isNotEqualTo(enrolleeDTO2);
    }
}
