package test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import raycast.animator.AbstractAnimator;
import raycast.animator.TextAnimator;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AbstractAnimatorTest {

    private static AbstractAnimator animator;

    private final double EPSILON = 0.00000001f;

    @BeforeAll
    static void setUp() {
        animator = new TextAnimator();
    }

    @AfterAll
    static void tearDown() {
        animator = null;
    }

//    @Test
//    void testABADIntersect() {
//        assertTrue(animator.getIntersection(100, 100, 200,100, 100, 100, 100, 200));
//    }
//
//    @Test
//    void testABFHIntersect() {
//        assertTrue(animator.getIntersection(100, 100, 200,100, 150, 100, 150, 200));
//    }
    // A(100,100) B(200,100) C(200,200) D(100,200) F(150,100) H(150,200) I(100,150) G(200,150) E(150,150)
    // ABAD - 100,100, 200,100, 100,100, 100,200, 100,100, 0.0
    // ABFH - 100,100, 200,100, 150,100, 150,200
    // ABBC - 100,100, 200,100,
    //
    //
    //
    //
    //
    // ABID - 100,100, 200,100, 100,150, 100,200
    //
    //
    //
    //
    //
    //
    //
    //
    //

    @ParameterizedTest
    @ValueSource(strings = { "Hello", "JUnit" })
    void parameterizedTest(String word) {
        assertNotNull(word);
    }

    @ParameterizedTest
    @MethodSource("intersectDataProvider")
    void TestForIntersect(double rsx, double rsy, double rex, double rey,
                          double ssx, double ssy, double sex, double sey,
                          double inx, double iny, double sca) {
        boolean isIntersect = animator.getIntersection(rsx, rsy, rex, rey, ssx, ssy, sex, sey);
        double intersects[] = animator.intersect();
        assertTrue(isIntersect);
        assertEquals(intersects[0], inx, EPSILON);
        assertEquals(intersects[1], iny, EPSILON);
        assertEquals(intersects[2], sca, EPSILON);

    }

    static Stream<Arguments> intersectDataProvider() {
        return Stream.of(
                Arguments.of(100d, 100d, 200d, 100d, 100d, 100d, 100d, 200d, 100d, 100d, 0.0d)
        );
    }

    @ParameterizedTest
    @MethodSource("notIntersectDataProvider")
    void TestForNotIntersect(double rsx, double rsy, double rex, double rey,
                             double ssx, double ssy, double sex, double sey) {
        boolean isIntersect = animator.getIntersection(rsx, rsy, rex, rey, ssx, ssy, sex, sey);
        assertFalse(isIntersect);
    }

    static Stream<Arguments> notIntersectDataProvider() {
        return Stream.of(
                Arguments.of(100d, 100d, 200d, 100d, 100d, 150d, 100d, 200d)
        );
    }
}