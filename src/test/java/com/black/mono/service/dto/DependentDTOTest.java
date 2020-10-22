package com.black.mono.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.black.mono.web.rest.TestUtil;

public class DependentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DependentDTO.class);
        DependentDTO dependentDTO1 = new DependentDTO();
        dependentDTO1.setId(1L);
        DependentDTO dependentDTO2 = new DependentDTO();
        assertThat(dependentDTO1).isNotEqualTo(dependentDTO2);
        dependentDTO2.setId(dependentDTO1.getId());
        assertThat(dependentDTO1).isEqualTo(dependentDTO2);
        dependentDTO2.setId(2L);
        assertThat(dependentDTO1).isNotEqualTo(dependentDTO2);
        dependentDTO1.setId(null);
        assertThat(dependentDTO1).isNotEqualTo(dependentDTO2);
    }
}
