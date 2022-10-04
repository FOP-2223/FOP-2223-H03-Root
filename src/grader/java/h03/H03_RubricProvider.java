package h03;

import fopbot.Direction;
import h03.utils.ChildCollectionCriterionBuilder;
import h03.utils.OnePointCriterionBuilder;
import h03.utils.RubricBuilder;
import h03.utils.Tuple;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Grader;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.sourcegrade.jagr.api.testing.TestCycle;

public class H03_RubricProvider implements RubricProvider {
    // TODO: JavaDoc prüfen

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
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod("t02")))
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod("t04")))
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
                    "t03", int.class, int.class, Direction.class, int.class, int.class,
                    Direction.class)))
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

    @Override
    public Rubric getRubric() {
        var H1_3_T4 = new OnePointCriterionBuilder("Die Methode addToNumberOfCoinsOfOffspring ist vollständig korrekt.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_3.class.getMethod("t04", int.class, int.class, Direction.class, int.class
                , int.class, int.class)));
        var H1_3 = new ChildCollectionCriterionBuilder("H1.3 | Attributwerte relativ zum momentanen Wert ändern", H1_3_T1,
            H1_3_T2, H1_3_T3, H1_3_T4);
        var H1 = new ChildCollectionCriterionBuilder("H1 | Roboter mit Abkömmling", H1_1, H1_2, H1_3);

        var H2_T1 = new OnePointCriterionBuilder("Das Attribut directionAccu ist korrekt deklariert.",
            JUnitTestRef.ofMethod(() -> TutorTests_H2.class.getMethod("t01")));
        var H2_T2 = new OnePointCriterionBuilder("Das Attribut directionAccu ist korrekt deklariert.",
            JUnitTestRef.ofMethod(() -> TutorTests_H2.class.getMethod("t03", TestCycle.class)));
        var H2_T3 = new OnePointCriterionBuilder("Der Konstruktor von RobotWithOffspring2 ist vollständig korrekt.",
            JUnitTestRef.ofMethod(() -> TutorTests_H2.class.getMethod("t03", TestCycle.class)));
        var H2 = new ChildCollectionCriterionBuilder("H2 | Roboter mit überschriebenen Methoden", H2_T1, H2_T2, H2_T3);

        var H3_1_T1 = new OnePointCriterionBuilder("Das Attribut robots ist korrekt deklariert.",
            JUnitTestRef.ofMethod(() -> TutorTests_H3_1.class.getMethod("t01")));
        var H3_1 = new ChildCollectionCriterionBuilder("H3.1 | Klasse mit Robotern", H3_1_T1);
        var H3 = new ChildCollectionCriterionBuilder("H3 | Klasse mit Robotern und Tests", H3_1);

        var rubricBuilder = new RubricBuilder("H03 | Ihr Upgrade in die First Class", H1, H2, H3);
        return rubricBuilder.build();
    }
}
