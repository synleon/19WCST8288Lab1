package test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import raycast.animator.AbstractAnimator;
import raycast.animator.TextAnimator;
import utility.Point;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author leon Feb 16 2019
 *
 * Test data set:
 * Points coordinates data
 * A(100,100) B(200,100) C(200,200) D(100,200) F(150,100) H(150,200) I(100,150) G(200,150) E(150,150)
 *
 */
class AbstractAnimatorTest {

    private static AbstractAnimator animator;
    private static Point ptA, ptB, ptC, ptD, ptE, ptF, ptG, ptH, ptI;

    private final double EPSILON = 0.00000001f;

    @BeforeAll
    static void setUp() {
        animator = new TextAnimator();
        ptA = new Point(100d, 100d);
        ptB = new Point(200d, 100d);
        ptC = new Point(200d, 200d);
        ptD = new Point(100d, 200d);
        ptE = new Point(150d, 150d);
        ptF = new Point(150d, 100d);
        ptG = new Point(200d, 150d);
        ptH = new Point(150d, 200d);
        ptI = new Point(100d, 150d);
    }

    @AfterAll
    static void tearDown() {
        animator = null;
        ptA = null;
        ptB = null;
        ptC = null;
        ptD = null;
        ptE = null;
        ptF = null;
        ptG = null;
        ptH = null;
        ptI = null;
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

    /**
     * Test dataset for intersected
     * A(100,100) B(200,100) C(200,200) D(100,200) F(150,100) H(150,200) I(100,150) G(200,150) E(150,150)
     * Ray  Segment Intersect   Point   Scalar
     * AB   AD      TRUE        A       0.0
     * AB   FH      TRUE        F       0.5
     * AB   BC      TRUE        B       1.0
     * BA   AD      TRUE        A       1.0
     * BA   FH      TRUE        F       0.5
     * BA   BC      TRUE        B       0.0
     * AF   AD      TRUE        A       0.0
     * AF   FH      TRUE        F       1.0
     * AF   BC      TRUE        B       2.0
     * FA   AD      TRUE        A       1.0
     * FA   FH      TRUE        F       0.0
     */
    static Stream<Arguments> intersectDataProvider() {
        return Stream.of(
                Arguments.of(ptA.x(),ptA.y(), ptB.x(),ptB.y(), ptA.x(),ptA.y(), ptD.x(),ptD.y(), ptA.x(),ptA.y(), 0.0d),
                Arguments.of(ptA.x(),ptA.y(), ptB.x(),ptB.y(), ptF.x(),ptF.y(), ptH.x(),ptH.y(), ptF.x(),ptF.y(), 0.5d),
                Arguments.of(ptA.x(),ptA.y(), ptB.x(),ptB.y(), ptB.x(),ptB.y(), ptC.x(),ptC.y(), ptB.x(),ptB.y(), 1.0d),

                Arguments.of(ptB.x(),ptB.y(), ptA.x(),ptA.y(), ptA.x(),ptA.y(), ptD.x(),ptD.y(), ptA.x(),ptA.y(), 1.0d),
                Arguments.of(ptB.x(),ptB.y(), ptA.x(),ptA.y(), ptF.x(),ptF.y(), ptH.x(),ptH.y(), ptF.x(),ptF.y(), 0.5d),
                Arguments.of(ptB.x(),ptB.y(), ptA.x(),ptA.y(), ptB.x(),ptB.y(), ptC.x(),ptC.y(), ptB.x(),ptB.y(), 0.0d),

                Arguments.of(ptA.x(),ptA.y(), ptF.x(),ptF.y(), ptA.x(),ptA.y(), ptD.x(),ptD.y(), ptA.x(),ptA.y(), 0.0d),
                Arguments.of(ptA.x(),ptA.y(), ptF.x(),ptF.y(), ptF.x(),ptF.y(), ptH.x(),ptH.y(), ptF.x(),ptF.y(), 1.0d),
                Arguments.of(ptA.x(),ptA.y(), ptF.x(),ptF.y(), ptB.x(),ptB.y(), ptC.x(),ptC.y(), ptB.x(),ptB.y(), 2.0d),

                Arguments.of(ptF.x(),ptF.y(), ptA.x(),ptA.y(), ptA.x(),ptA.y(), ptD.x(),ptD.y(), ptA.x(),ptA.y(), 1.0d),
                Arguments.of(ptF.x(),ptF.y(), ptA.x(),ptA.y(), ptF.x(),ptF.y(), ptH.x(),ptH.y(), ptF.x(),ptF.y(), 0.0d)
        );
    }

    @ParameterizedTest
    @MethodSource("notIntersectDataProvider")
    void TestForNotIntersect(double rsx, double rsy, double rex, double rey,
                             double ssx, double ssy, double sex, double sey) {
        boolean isIntersect = animator.getIntersection(rsx, rsy, rex, rey, ssx, ssy, sex, sey);
        assertFalse(isIntersect);
    }

    /**
     * Test dataset for intersected
     * Ray  Segment Intersect
     * AB   ID      FALSE
     * AB   EH      FALSE
     * AB   GC      FALSE
     * AB   IG      FALSE
     * BA   ID      FALSE
     * BA   EH      FALSE
     * BA   GC      FALSE
     * BA   IG      FALSE
     * FA   BC      FALSE
     */
    static Stream<Arguments> notIntersectDataProvider() {
        return Stream.of(
                Arguments.of(ptA.x(),ptA.y(), ptB.x(),ptB.y(), ptI.x(),ptI.y(), ptD.x(),ptD.y()),
                Arguments.of(ptA.x(),ptA.y(), ptB.x(),ptB.y(), ptE.x(),ptE.y(), ptH.x(),ptH.y()),
                Arguments.of(ptA.x(),ptA.y(), ptB.x(),ptB.y(), ptG.x(),ptG.y(), ptC.x(),ptC.y()),
                Arguments.of(ptA.x(),ptA.y(), ptB.x(),ptB.y(), ptI.x(),ptI.y(), ptG.x(),ptG.y()),

                Arguments.of(ptB.x(),ptB.y(), ptA.x(),ptA.y(), ptI.x(),ptI.y(), ptD.x(),ptD.y()),
                Arguments.of(ptB.x(),ptB.y(), ptA.x(),ptA.y(), ptE.x(),ptE.y(), ptH.x(),ptH.y()),
                Arguments.of(ptB.x(),ptB.y(), ptA.x(),ptA.y(), ptG.x(),ptG.y(), ptC.x(),ptC.y()),
                Arguments.of(ptB.x(),ptB.y(), ptA.x(),ptA.y(), ptI.x(),ptI.y(), ptG.x(),ptG.y()),

                Arguments.of(ptF.x(),ptF.y(), ptA.x(),ptA.y(), ptB.x(),ptB.y(), ptC.x(),ptC.y())
        );
    }
}