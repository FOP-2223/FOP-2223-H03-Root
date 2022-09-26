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
        .shortDescription("Die Methode initOffspring ist vollständig korrekt.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod(
                    "t02")))
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

    // "H1.2 Attribut vom Referenztyp und get-Methoden für dessen Attribute"
    // "H1.3 Attributwerte relativ zum momentanen Wert ändern"

//    private static final Criterion CRITERION_H1_2 = criterion("H1.2: Attribut vom Referenztyp und get-Methoden für " +
//            "dessen Attribute",
//        singlePointCriterion("Das Attribut offspring und die Methode initOffspring wurden korrekt implementiert.",
//            () -> TutorTests_H1_2.class.getDeclaredMethod
//            ("offspringFieldAndInitOffspringMethodCorrectlyImplemented")),
//        singlePointCriterion("Die Getter in RobotWithOffspring wurden korrekt implementiert.",
//            () -> TutorTests_H1_2.class.getDeclaredMethod("gettersCorrectlyImplemented")),
//        singlePointCriterion("Die Methode offspringIsInitialized wurde korrekt implementiert.",
//            () -> TutorTests_H1_2.class.getDeclaredMethod("offspringIsInitializedCorrectlyImplemented"))
//    );
//
//    private static final Criterion CRITERION_H1_3 = criterion("H1.3: Attributwerte relativ zum momentanen Wert
//    ändern",
//        singlePointCriterion("Die Methode addToXOfOffspring wurde korrekt implementiert.",
//            () -> H1_3_Tests.class.getDeclaredMethod("addToXOfOffspringCorrectlyImplemented")),
//        singlePointCriterion("Die Methode addToYOfOffspring wurde korrekt implementiert.",
//            () -> H1_3_Tests.class.getDeclaredMethod("addToYOfOffspringCorrectlyImplemented")),
//        singlePointCriterion("Die Methode addToDirectionOfOffspring wurde korrekt implementiert.",
//            () -> H1_3_Tests.class.getDeclaredMethod("addToDirectionOfOffspringCorrectlyImplemented", String.class,
//                String.class)),
//        singlePointCriterion("Die Methode addToNumberOfCoinsOfOffspring wurde korrekt implementiert.",
//            () -> H1_3_Tests.class.getDeclaredMethod("addToNumberOfCoinsOfOffspringCorrectlyImplemented"))
//    );
//
//    private static final Criterion CRITERION_H3_1 = criterion("H3.1: Klasse mit Robotern",
//        singlePointCriterion("Der robots Array wurde korrekt implementiert.",
//            () -> H3_1_Tests.class.getDeclaredMethod("robotsArrayCorrectlyImplemented")),
//        singlePointCriterion("Die Methode getRobotByIndex wurde korrekt implementiert.",
//            () -> H3_1_Tests.class.getDeclaredMethod("getRobotByIndexCorrectlyImplemented")),
//        singlePointCriterion("Die Methode addToDirectionOfBothOffsprings wurde korrekt implementiert.",
//            () -> H3_1_Tests.class.getDeclaredMethod("addToDirectionOfBothOffspringsCorrectlyImplemented"))
//    );
//
//    private static final Criterion CRITERION_H3_2 = criterion("H3.2: Testen");

    public static final Criterion H1 = Criterion.builder()
        .shortDescription("H1 | Roboter mit Abkömmling")
        .addChildCriteria(
            H1_1, H1_2)
        .build();

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H03 | Ihr Upgrade in die First Class")
        .addChildCriteria(H1)
        .build();

//    private static final Criterion CRITERION_H2 = criterion("H2: Roboter mit überschriebenen Methoden",
//        singlePointCriterion("Die Klasse RobotWithOffspring2 erbt von RobotWithOffspring und besitzt einen " +
//                "öffentlichen Konstruktor, der seine aktualen Parameterwerte wie beschrieben an den Konstruktor von
//                " +
//                "RobotWithOffspring weiterreicht.",
//            () -> H2_Tests.class.getDeclaredMethod("inheritanceAndConstructorCorrect")),
//        singlePointCriterion("Das Attribut directionAccu und die Methode initOffspring wurden korrekt implementiert.",
//            () -> H2_Tests.class.getDeclaredMethod("directionAccuAttributeAndInitOffspringMethodCorrect")),
//        singlePointCriterion("Die Methoden getDirectionFromAccu und getDirectionOfOffspring wurden korrekt " +
//                "implementiert.",
//            () -> H2_Tests.class.getDeclaredMethod("getDirectionMethodsCorrectlyImplemented", String.class,
//                String.class)),
//        singlePointCriterion("Die Methode addToDirectionOfOffspring wurde korrekt implementiert.",
//            () -> H2_Tests.class.getDeclaredMethod("addToDirectionOfOffspringCorrectlyImplemented"))
//    );

//    private static final Criterion CRITERION_H3 = criterion("H3: Klasse mit Robotern und Tests", CRITERION_H3_1,
//        CRITERION_H3_2);

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
