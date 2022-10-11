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
    /*
     * Punktekriterien:
     * - H1.1 4 Punkte
     *      - Klassendeklaration korrekt 1
     *      - Attribute korrekt deklariert 1
     *      - Konstruktor korrekt deklariert und setzt Attribute korrekt 1
     *      - Konstruktor ruft super-Konstruktor korrekt auf 1
     * - H1.2 4 Punkte
     *      - offspring-Attribut korrekt deklariert 1
     *      - offspringIsInitialized Methode korrekt deklariert und implementiert 1
     *      - alle Getter korrekt implementiert 1
     *      - Methode initOffspring korrekt deklariert und implementiert 1
     * - H1.3 4 Punkte
     *      - addToXOfOffspring, addToYOfOffspring korrekt implementiert 1
     *      - addToNumberOfCoinsOfOffspring korrekt implementiert 1
     *      - addToDirectionOfOffspring besteht Standardfälle 1
     *      - addToDirectionOfOffspring besteht Randfälle 1
     * - H2 6 Punkte
     *      - directionAccu Attribut korrekt deklariert
     *         und getDirectionOfOffspring korrekt implementiert 1
     *      - Konstruktor korrekt deklariert und implementiert 1
     *      - initOffspring korrekt implementiert 1
     *      - getDirectionFromAccu besteht Standardfälle 1
     *      - getDirectionFromAccu besteht Randfälle 1
     *      - addToDirectionOfOffspring korrekt implementiert 1
     * - H3.1 4 Punkte
     *     - robots Attribut korrekt deklariert 1
     *     - Konstruktor korrekt implementiert 1
     *     - getRobotByIndex korrekt implementiert 1
     *     - addToDirectionOfBothOffsprings korrekt implementiert 1
     * - H3.2 3 Punkte
     *     - ein zusätzlicher Aufruf mit negativem Parameter vorhanden 1
     *     - ein zusätzlicher Aufruf mit negativem directionAccu-Wert vorhanden 1
     *     - insgesamt mindestens 3 zusätzliche Aufrufe 1
     */

    // TODO: JavaDoc prüfen
    // TODO: bei Methoden, wo ein Punkt für Deklaration und Implementierung zusammen vergeben wird: Toleranz für Schreibfehler in der Deklaration einbauen

    @Override
    public Rubric getRubric() {
        var H1_1_T1 = new OnePointCriterionBuilder("Die Klasse \"RobotWithOffspring\" wurde korrekt deklariert.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod("classRobotWithOffspringDeclaredCorrectly")));

        var H1_1_T2 = new OnePointCriterionBuilder("Die Attribute \"numberOfColumnsOfWorld\" und \"numberOfRowsOfWorld\" wurden korrekt deklariert.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod("numberOfColumnsOfWorldDeclaredCorrectly")),
            JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod("numberOfRowsOfWorldDeclaredCorrectly")));

        var H1_1_T3 = new OnePointCriterionBuilder(
            "Der Konstruktor von \"RobotWithOffspring\" wurde korrekt deklariert und setzt die Attribute \"numberOfColumnsOfWorld\" und \"numberOfRowsOfWorld\" korrekt.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod("constructorDeclaredCorrectly")),
            JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod("constructorSetsAttributesCorrectly", int.class, int.class)));

        // TODO: constructorCallsSuperConstructorCorrectly

        var H1_1 = new ChildCollectionCriterionBuilder("H1.1 | Abgeleitete Klasse, ihr Konstruktor und zusätzliche Attribute",
            H1_1_T1, H1_1_T2, H1_1_T3);

        // H1.2 DONE
        var H1_2_T1 = new OnePointCriterionBuilder("Das Attribut \"offspring\" wurde korrekt deklariert.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod("offspringDeclaredCorrectly")));

        var H1_2_T2 = new OnePointCriterionBuilder("Die Methode \"initOffspring\" wurde korrekt deklariert und implementiert.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod("initOffspringDeclaredCorrectly")),
            JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod("initOffspringImplementedCorrectly", int.class, int.class, Direction.class, int.class)));

        var H1_2_T3 = new OnePointCriterionBuilder("Die get-Methoden für den offspring wurden korrekt deklariert und implementiert.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod("allOffspringGetterMethodsCorrectlyDeclaredAndImplemented", int.class, int.class, Direction.class, int.class)));

        var H1_2_T4 = new OnePointCriterionBuilder("Die Methode \"offspringIsInitialized\" wurde korrekt deklariert und implementiert.",
            JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod("offspringIsInitializedDeclaredAndImplementedCorrectly")));

        var H1_2 = new ChildCollectionCriterionBuilder("H1.2 | Attribut vom Referenztyp und get-Methoden für dessen Attribute",
            H1_2_T1, H1_2_T2, H1_2_T3, H1_2_T4);

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
