package com.black.mono.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.black.mono.web.rest.TestUtil;

public class EnrolleeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enrollee.class);
        Enrollee enrollee1 = new Enrollee();
        enrollee1.setId(1L);
        Enrollee enrollee2 = new Enrollee();
        enrollee2.setId(enrollee1.getId());
        assertThat(enrollee1).isEqualTo(enrollee2);
        enrollee2.setId(2L);
        assertThat(enrollee1).isNotEqualTo(enrollee2);
        enrollee1.setId(null);
        assertThat(enrollee1).isNotEqualTo(enrollee2);
    }
}
