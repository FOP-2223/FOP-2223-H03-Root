package h03;

import fopbot.Direction;
import h03.utils.ChildCollectionCriterionBuilder;
import h03.utils.OnePointCriterionBuilder;
import h03.utils.RubricBuilder;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.sourcegrade.jagr.api.testing.TestCycle;

public class H03_RubricProvider implements RubricProvider {
    // TODO: JavaDoc prüfen

    @Override
    public Rubric getRubric() {
        var H1_1_T1 = new OnePointCriterionBuilder("Die Klasse RobotWithOffspring ist korrekt deklariert.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod(
                "t01")));
        var H1_1_T2 = new OnePointCriterionBuilder("Die Attribute numberOfColumnsOfWorld und numberOfRowsOfWorld sind korrekt " +
            "deklariert.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod(
                "t02")));
        var H1_1_T3 = new OnePointCriterionBuilder("Der Konstruktor von RobotWithOffspring ist vollständig korrekt.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod("t03", int.class, int.class)));

        var H1_1 = new ChildCollectionCriterionBuilder("H1.1 | Abgeleitete Klasse, ihr Konstruktor und zusätzliche Attribute",
            H1_1_T1, H1_1_T2, H1_1_T3);

        var H1_2_T1 = new OnePointCriterionBuilder("Das Attribut offspring ist korrekt deklariert.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod("t01")));
        var H1_2_T2 = new OnePointCriterionBuilder("Die Methoden initOffspring und offspringIsInitialized sind vollständig " +
            "korrekt.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod("t02")),
            JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod("t04")));
        var H1_2_T3 = new OnePointCriterionBuilder("Die offspring-Getter in RobotWithOffspring sind vollständig korrekt.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod("t03")));

        var H1_2 = new ChildCollectionCriterionBuilder("H1.2 | Attribut vom Referenztyp und get-Methoden für dessen Attribute",
            H1_2_T1, H1_2_T2, H1_2_T3);

        var H1_3_T1 = new OnePointCriterionBuilder("Die Methode addToXOfOffspring ist vollständig korrekt.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_3.class.getMethod(
                "t01", int.class, int.class, Direction.class, int.class, int.class, int.class)));
        var H1_3_T2 = new OnePointCriterionBuilder("Die Methode addToYOfOffspring ist vollständig korrekt.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_3.class.getMethod(
                "t02", int.class, int.class, Direction.class, int.class, int.class, int.class)));
        var H1_3_T3 = new OnePointCriterionBuilder("Die Methode addToDirectionOfOffspring ist vollständig korrekt.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_3.class.getMethod(
                "t03", int.class, int.class, Direction.class, int.class, int.class,
                Direction.class)));
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
