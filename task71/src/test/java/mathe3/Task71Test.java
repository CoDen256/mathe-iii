package mathe3;

import static mathe3.Task71.addLeadingZeros;
import static mathe3.Task71.bits;
import static mathe3.Task71.multiply;
import static mathe3.Task71.removeLeadingZeros;
import static mathe3.Task71.str;
import static mathe3.Task71.sum;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Task71Test {

    @ParameterizedTest
    @CsvSource(value = {
            "1, 2, "+(1*2),
            "34353, 123, "+(34353*123),
            "123123, 12312, "+(123123*12312),
            "999999, 1, "+(999999*1),
            "0, 22222, "+(0*22222),
            "123132, 244444, "+(123132*244444),
            "9999, 9999, "+(9999*9999),
    })
    void test_multiply(int a, int b, int c) {
        assertArrayEquals(bits(Integer.toBinaryString(c)),
                multiply(bits(Integer.toBinaryString(a)), bits(Integer.toBinaryString(b))));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1, 2, "+(1+2),
            "34353, 123, "+(34353+123),
            "123123, 12312, "+(123123+12312),
            "999999, 1, "+(999999+1),
            "0, 22222, "+(0+22222),
            "123132, 244444, "+(123132+244444),
            "9999, 9999, "+(9999+9999),
    })
    void test_sum(int a, int b, int c) {
        assertArrayEquals(bits(Integer.toBinaryString(c)),
                sum(bits(Integer.toBinaryString(a)), bits(Integer.toBinaryString(b))));
    }

    @Test
    void test_addLeadingZeros() {
        assertArrayEquals(bits("1010"), addLeadingZeros(bits("1010"), 4));
        assertArrayEquals(bits("0001"), addLeadingZeros(bits("0001"), 4));
        assertArrayEquals(bits("000001"), addLeadingZeros(bits("000001"), 6));
        assertArrayEquals(bits("000001"), addLeadingZeros(bits("1"), 6));
        assertArrayEquals(bits("011111"), addLeadingZeros(bits("11111"), 6));
        assertArrayEquals(bits("00000011101010"), addLeadingZeros(bits("0011101010"), 14));

    }

    @Test
    void test_removeLeadingZeros() {
        assertArrayEquals(bits("10010"), removeLeadingZeros(bits("10010")));
        assertArrayEquals(bits("1"), removeLeadingZeros(bits("1")));
        assertArrayEquals(bits("1000"), removeLeadingZeros(bits("1000")));
        assertArrayEquals(bits("1000"), removeLeadingZeros(bits("0001000")));
        assertArrayEquals(bits("1"), removeLeadingZeros(bits("0001")));
        assertArrayEquals(bits("11111111"), removeLeadingZeros(bits("0000000011111111")));
        assertArrayEquals(bits("1000000"), removeLeadingZeros(bits("000000001000000")));
    }

    @Test
    void test_bits() {
        assertArrayEquals(new boolean[]{true, false, false, false, false,true, false}, bits("1000010"));
        assertArrayEquals(new boolean[]{true, true, true, true, true, true,true, true}, bits("11111111"));
        assertArrayEquals(new boolean[]{true, false, true, false, true, false}, bits("101010"));
    }

    @Test
    void test_str() {
        assertEquals("10000", str(bits(Integer.toBinaryString(Integer.parseInt("10000", 2)))));
        assertEquals("10101010101", str(bits(Integer.toBinaryString(Integer.parseInt("10101010101", 2)))));
        assertEquals("100001000100001", str(bits(Integer.toBinaryString(Integer.parseInt("100001000100001", 2)))));
        assertEquals("11111111", str(bits(Integer.toBinaryString(Integer.parseInt("11111111", 2)))));
        assertEquals("110011", str(bits(Integer.toBinaryString(Integer.parseInt("110011", 2)))));
    }
}