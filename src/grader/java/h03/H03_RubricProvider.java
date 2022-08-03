package h03;

import org.jetbrains.annotations.NotNull;
import org.sourcegrade.jagr.api.rubric.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

@RubricForSubmission("h03")
public class H03_RubricProvider implements RubricProvider {
    @SafeVarargs
    private static Criterion singlePointCriterion(String shortDescription, Callable<Method> @NotNull ... methodsRequiredToPass) {
        var testAwareGrader = Grader.testAwareBuilder();

        for (var method : methodsRequiredToPass) {
            testAwareGrader = testAwareGrader.requirePass(JUnitTestRef.ofMethod(method));
        }

        var grader = testAwareGrader.pointsFailedMin().pointsPassedMax().build();
        return Criterion.builder().shortDescription(shortDescription).grader(grader).build();
    }

    private static final Criterion CRITERION_H1_1 = criterion("H1.1: Abgeleitete Klasse, ihr Konstruktor und zusätzliche Attribute",
        singlePointCriterion("Die Klasse RobotWithOffspring erbt von Robot und verfügt über die geforderten Attribute.",
            () -> H1_1_Tests.class.getDeclaredMethod("classInheritsFromRobotAndHasCorrectAttributes")),
        singlePointCriterion("Der Konstruktor setzt den Roboter auf die korrekte Position in der Mitte der Welt.",
            () -> H1_1_Tests.class.getDeclaredMethod("constructorSetsCorrectPosition", int.class, int.class)),
        singlePointCriterion("Der Konstruktor setzt die beiden Attribute numberOfColumnsOfWorld und numberOfRowsOfWorld.",
            () -> H1_1_Tests.class.getDeclaredMethod("constructorSetsAttributes", int.class, int.class))
    );

    private static final Criterion CRITERION_H1_2 = criterion("H1.2: Attribut vom Referenztyp und get-Methoden für dessen Attribute",
        singlePointCriterion("Das Attribut offspring und die Methode initOffspring wurden korrekt implementiert.",
            () -> H1_2_Tests.class.getDeclaredMethod("offspringFieldAndInitOffspringMethodCorrectlyImplemented")),
        singlePointCriterion("Die Getter in RobotWithOffspring wurden korrekt implementiert.",
            () -> H1_2_Tests.class.getDeclaredMethod("gettersCorrectlyImplemented")),
        singlePointCriterion("Die Methode offspringIsInitialized wurde korrekt implementiert.",
            () -> H1_2_Tests.class.getDeclaredMethod("offspringIsInitializedCorrectlyImplemented"))
    );

    private static final Criterion CRITERION_H1_3 = criterion("H1.3: Attributwerte relativ zum momentanen Wert ändern",
        singlePointCriterion("Die Methode addToXOfOffspring wurde korrekt implementiert.",
            () -> H1_3_Tests.class.getDeclaredMethod("addToXOfOffspringCorrectlyImplemented")),
        singlePointCriterion("Die Methode addToYOfOffspring wurde korrekt implementiert.",
            () -> H1_3_Tests.class.getDeclaredMethod("addToYOfOffspringCorrectlyImplemented")),
        singlePointCriterion("Die Methode addToDirectionOfOffspring wurde korrekt implementiert.",
            () -> H1_3_Tests.class.getDeclaredMethod("addToDirectionOfOffspringCorrectlyImplemented", String.class, String.class)),
        singlePointCriterion("Die Methode addToNumberOfCoinsOfOffspring wurde korrekt implementiert.",
            () -> H1_3_Tests.class.getDeclaredMethod("addToNumberOfCoinsOfOffspringCorrectlyImplemented"))
    );

    private static final Criterion CRITERION_H3_1 = criterion("H3.1: Klasse mit Robotern",
        singlePointCriterion("Der robots Array wurde korrekt implementiert.",
            () -> H3_1_Tests.class.getDeclaredMethod("robotsArrayCorrectlyImplemented")),
        singlePointCriterion("Die Methode getRobotByIndex wurde korrekt implementiert.",
            () -> H3_1_Tests.class.getDeclaredMethod("getRobotByIndexCorrectlyImplemented")),
        singlePointCriterion("Die Methode addToDirectionOfBothOffsprings wurde korrekt implementiert.",
            () -> H3_1_Tests.class.getDeclaredMethod("addToDirectionOfBothOffspringsCorrectlyImplemented"))
    );

    private static final Criterion CRITERION_H3_2 = criterion("H3.2: Testen");

    private static final Criterion CRITERION_H1 = criterion("H1: Roboter mit Abkömmling", CRITERION_H1_1, CRITERION_H1_2, CRITERION_H1_3);

    private static final Criterion CRITERION_H2 = criterion("H2: Roboter mit überschriebenen Methoden",
        singlePointCriterion("Die Klasse RobotWithOffspring2 erbt von RobotWithOffspring und besitzt einen öffentlichen Konstruktor, der seine aktualen Parameterwerte wie beschrieben an den Konstruktor von RobotWithOffspring weiterreicht.",
            () -> H2_Tests.class.getDeclaredMethod("inheritanceAndConstructorCorrect")),
        singlePointCriterion("Das Attribut directionAccu und die Methode initOffspring wurden korrekt implementiert.",
            () -> H2_Tests.class.getDeclaredMethod("directionAccuAttributeAndInitOffspringMethodCorrect")),
        singlePointCriterion("Die Methoden getDirectionFromAccu und getDirectionOfOffspring wurden korrekt implementiert.",
            () -> H2_Tests.class.getDeclaredMethod("getDirectionMethodsCorrectlyImplemented", String.class, String.class)),
        singlePointCriterion("Die Methode addToDirectionOfOffspring wurde korrekt implementiert.",
            () -> H2_Tests.class.getDeclaredMethod("addToDirectionOfOffspringCorrectlyImplemented"))
    );

    private static final Criterion CRITERION_H3 = criterion("H3: Klasse mit Robotern und Tests", CRITERION_H3_1, CRITERION_H3_2);

    private static Criterion criterion(String shortDescription, Criterion... childCriteria) {
        return Criterion.builder()
            .shortDescription(shortDescription)
            .addChildCriteria(childCriteria)
            .build();
    }

    private static final Rubric RUBRIC = Rubric.builder()
        .title("H03: Ihr Upgrade in die First Class")
        .addChildCriteria(CRITERION_H1, CRITERION_H2, CRITERION_H3)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
