package util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class FileUtilTest {

    @Test
    void isJavClass_true() {
        //GIVEN
        //WHEN
        boolean result = FileUtil.isJavClass("Class.java");

        //THEN
        assertThat(result).isTrue();
    }

    @Test
    void isJavClass_false() {
        //GIVEN
        //WHEN
        boolean result = FileUtil.isJavClass("Class.txt");

        //THEN
        assertThat(result).isFalse();
    }
}