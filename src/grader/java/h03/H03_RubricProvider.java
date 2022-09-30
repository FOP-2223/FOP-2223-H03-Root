package h03;

import fopbot.Direction;
import h03.transform.RobotWithOffspring2Transformer;
import org.sourcegrade.jagr.api.rubric.*;
import org.sourcegrade.jagr.api.testing.ClassTransformerOrder;
import org.sourcegrade.jagr.api.testing.RubricConfiguration;
import org.sourcegrade.jagr.api.testing.TestCycle;

@RubricForSubmission("h03")
public class H03_RubricProvider implements RubricProvider {
    // TODO: JavaDoc prüfen
    // TODO: generell Final und private optional prüfen bei Aufgaben, die damit gar nichts zu tun haben. Nur bei den
    //  Attributen selbst Punkte abziehen

    public static final Criterion H1_1_T1 = Criterion.builder()
        .shortDescription("Die Klasse RobotWithOffspring ist korrekt deklariert.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod(
                    "t01")))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_1_T2 = Criterion.builder()
        .shortDescription("Die Attribute numberOfColumnsOfWorld und numberOfRowsOfWorld sind korrekt deklariert.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod(
                    "t02")))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_1_T3 = Criterion.builder()
        .shortDescription("Der Konstruktor von RobotWithOffspring ist vollständig korrekt.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod(
                    "t03", int.class, int.class)))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_1 = Criterion.builder()
        .shortDescription("H1.1 | Abgeleitete Klasse, ihr Konstruktor und zusätzliche Attribute")
        .addChildCriteria(H1_1_T1, H1_1_T2, H1_1_T3)
        .build();

    public static final Criterion H1_2_T1 = Criterion.builder()
        .shortDescription("Das Attribut offspring ist korrekt deklariert.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod(
                    "t01")))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_2_T2 = Criterion.builder()
        .shortDescription("Die Methoden initOffspring und offspringIsInitialized sind vollständig korrekt.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.and(
                        JUnitTestRef.ofMethod(() ->
                            TutorTests_H1_2.class.getMethod("t02")),
                        JUnitTestRef.ofMethod(() ->
                            TutorTests_H1_2.class.getMethod("t04"))
                    )
                )
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_2_T3 = Criterion.builder()
        .shortDescription("Die offspring-Getter in RobotWithOffspring sind vollständig korrekt.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod(
                    "t03")))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_2 = Criterion.builder()
        .shortDescription("H1.2 | Attribut vom Referenztyp und get-Methoden für dessen Attribute")
        .addChildCriteria(H1_2_T1, H1_2_T2, H1_2_T3)
        .build();

    public static final Criterion H1_3_T1 = Criterion.builder()
        .shortDescription("Die Methode addToXOfOffspring ist vollständig korrekt.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_3.class.getMethod(
                    "t01", int.class, int.class, Direction.class, int.class, int.class, int.class)))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_3_T2 = Criterion.builder()
        .shortDescription("Die Methode addToYOfOffspring ist vollständig korrekt.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_3.class.getMethod(
                    "t02", int.class, int.class, Direction.class, int.class, int.class, int.class)))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_3_T3 = Criterion.builder()
        .shortDescription("Die Methode addToDirectionOfOffspring ist vollständig korrekt.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_3.class.getMethod(
                    "t03", int.class, int.class, Direction.class, int.class, int.class, Direction.class)))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_3_T4 = Criterion.builder()
        .shortDescription("Die Methode addToNumberOfCoinsOfOffspring ist vollständig korrekt.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_3.class.getMethod(
                    "t04", int.class, int.class, Direction.class, int.class, int.class, int.class)))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_3 = Criterion.builder()
        .shortDescription("H1.3 | Attributwerte relativ zum momentanen Wert ändern")
        .addChildCriteria(H1_3_T1, H1_3_T2, H1_3_T3, H1_3_T4)
        .build();

    public static final Criterion H1 = Criterion.builder()
        .shortDescription("H1 | Roboter mit Abkömmling")
        .addChildCriteria(H1_1, H1_2, H1_3)
        .build();

    public static final Criterion H2_T1 = Criterion.builder()
        .shortDescription("Die Klasse RobotWithOffspring ist korrekt deklariert.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H2.class.getMethod(
                    "t01")))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H2_T2 = Criterion.builder()
        .shortDescription("Das Attribut directionAccu ist korrekt deklariert.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H2.class.getMethod(
                    "t02")))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H2_T3 = Criterion.builder()
        .shortDescription("Der Konstruktor von RobotWithOffspring2 ist vollständig korrekt.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H2.class.getMethod(
                    "t03", TestCycle.class)))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H2 = Criterion.builder()
        .shortDescription("H1 | Roboter mit überschriebenen Methoden")
        .addChildCriteria(H2_T1, H2_T2, H2_T3)
        .build();

    public static final Criterion H3_1_T1 = Criterion.builder()
        .shortDescription("Das Attribut robots ist korrekt deklariert.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H3_1.class.getMethod(
                    "t01")))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H3_1 = Criterion.builder()
        .shortDescription("H3.1 | Klasse mit Robotern")
        .addChildCriteria(H3_1_T1)
        .build();

    public static final Criterion H3 = Criterion.builder()
        .shortDescription("H3 | Klasse mit Robotern und Tests")
        .addChildCriteria(H3_1)
        .build();

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H03 | Ihr Upgrade in die First Class")
        .addChildCriteria(H1, H2, H3)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
