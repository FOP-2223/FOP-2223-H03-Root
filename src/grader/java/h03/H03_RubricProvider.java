package h03;

import org.sourcegrade.jagr.api.rubric.*;

@RubricForSubmission("h03")
public class H03_RubricProvider implements RubricProvider {
    // TODO: JavaDoc prüfen

    public static final Criterion H1_1_T1 = Criterion.builder()
        .shortDescription("Klasse RobotWithOffspring ist korrekt deklariert.")
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
                    "t01")))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_3_T2 = Criterion.builder()
        .shortDescription("Die Methode addToYOfOffspring ist vollständig korrekt.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_3.class.getMethod(
                    "t02")))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_3_T3 = Criterion.builder()
        .shortDescription("Die Methode addToDirectionOfOffspring ist vollständig korrekt.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_3.class.getMethod(
                    "t03")))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_3_T4 = Criterion.builder()
        .shortDescription("Die Methode addToNumberOfCoinsOfOffspring ist vollständig korrekt.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_3.class.getMethod(
                    "t04")))
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
        .addChildCriteria(
            H1_1, H1_2, H1_3)
        .build();

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H03 | Ihr Upgrade in die First Class")
        .addChildCriteria(H1)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
