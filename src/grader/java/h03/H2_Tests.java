package h03;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static h03.Utilities.assertPublicMethodExists;
import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h03")
public class H2_Tests {
    @BeforeEach
    public void setup() {
        World.reset();
        World.setDelay(0);
        World.setVisible(false);
    }

    @Test
    public void inheritanceAndConstructorCorrect() {
        World.setSize(5, 6);

        assertEquals(RobotWithOffspring.class, RobotWithOffspring2.class.getSuperclass(),
            String.format("Die Klasse %s erbt nicht von %s.", RobotWithOffspring2.class.getTypeName(), RobotWithOffspring.class.getTypeName()));

        var sut = new RobotWithOffspring2(5, 6, Direction.DOWN, 29);
        // Since the constructor of RobotWithOffspring might be wrongly implemented, we still want to give points, if
        // the constructor of RobotWithOffspring2 is correctly calling the super-constructor. To check this, we
        // compare the impact of the two constructors on the properties.
        var robotWithOffspring = new RobotWithOffspring(5, 6, Direction.DOWN, 29);

        Assertions.assertAll(
            () -> assertEquals((int) Utilities.getValueOfField(RobotWithOffspring.class, "numberOfColumnsOfWorld", robotWithOffspring),
                (int) Utilities.getValueOfField(RobotWithOffspring.class, "numberOfColumnsOfWorld", sut), "Die Anzahl Spalten wird nicht korrekt an den " +
                    "super-Konstruktor übergeben."),
            () -> assertEquals((int) Utilities.getValueOfField(RobotWithOffspring.class, "numberOfRowsOfWorld", robotWithOffspring),
                (int) Utilities.getValueOfField(RobotWithOffspring.class, "numberOfRowsOfWorld", sut), "Die Anzahl Spalten wird nicht korrekt an den " +
                    "super-Konstruktor übergeben."),
            () -> assertEquals(robotWithOffspring.getDirection(), sut.getDirection(), "Die Blickrichtung wird nicht " +
                "korrekt an den super-Konstruktor übergeben."),
            () -> assertEquals(robotWithOffspring.getNumberOfCoins(), sut.getNumberOfCoins(), "Die Anzahl Münzen wird nicht " +
                "korrekt an den super-Konstruktor übergeben.")
        );
    }

    @Test
    public void directionAccuAttributeAndInitOffspringMethodCorrect() {
        World.setSize(5, 5);
        assertTrue(Arrays.stream(RobotWithOffspring2.class.getDeclaredFields())
                .anyMatch(f ->
                    f.getName().equals("directionAccu")
                        && (f.getType().isAssignableFrom(int.class)
                        || f.getType().isAssignableFrom(Integer.class))
                        && Modifier.isPrivate(f.getModifiers())),
            "Das Attribut directionAccu wurde nicht korrekt implementiert."
        );

        assertPublicMethodExists(RobotWithOffspring2.class, "initOffspring", new Class[]{Direction.class, int.class}, new Class[]{void.class});

        var robotWithOffspring = new RobotWithOffspring(5, 5, Direction.UP, 20);
        robotWithOffspring.initOffspring(Direction.RIGHT, 19);
        var sut = new RobotWithOffspring2(5, 5, Direction.UP, 20);
        sut.initOffspring(Direction.RIGHT, 19);

        try {
            var superOffspring = (Robot) Utilities.getValueOfField(RobotWithOffspring.class, "offspring", robotWithOffspring);
            var offspring = (Robot) Utilities.getValueOfField(RobotWithOffspring.class, "offspring", sut);
            assertEquals(superOffspring.getDirection(), offspring.getDirection(), "Die Blickrichtung wurde in initOffspring nicht korrekt an parent-Methode übergeben.");
            assertEquals(superOffspring.getNumberOfCoins(), offspring.getNumberOfCoins(), "Die Anzahl Münzen wurde in initOffspring nicht korrekt an parent-Methode übergeben.");
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail("Die Methode initOffspring wurde nicht korrekt implementiert.");
        }

        try {
            assertEquals(Direction.RIGHT.ordinal(), (int) Utilities.getValueOfField(RobotWithOffspring2.class, "directionAccu", sut));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail("Das Attribut directionAccu wurde nicht korrekt implementiert.");
        }
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,2", "3,3", "4,0", "5,1", "0,0", "-1,3", "-2,2", "-3,1", "-4,0"})
    public void getDirectionMethodsCorrectlyImplemented(String directionAccuString, String expectedDirectionString) {
        World.setSize(5, 5);

        var directionAccu = Integer.parseInt(directionAccuString);
        var expected = Integer.parseInt(expectedDirectionString);

        var sut = new RobotWithOffspring2(5, 5, Direction.UP, 0);

        try {
            setValueOfField(RobotWithOffspring2.class, "directionAccu", sut, directionAccu);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Das Feld directionAccu wurde nicht gefunden.");
        }

        var actualDirection = sut.getDirectionOfOffspring();
        var expectedDirection = Direction.values()[expected];

        assertEquals(expectedDirection, actualDirection,
            String.format("directionAccu hat den Wert %s und erwartet wurde die Richtung %s. Zurückgegeben wurde aber %s.",
                directionAccu, expectedDirection, actualDirection));
    }

    @Test
    public void addToDirectionOfOffspringCorrectlyImplemented() throws IllegalAccessException, InvocationTargetException {
        World.setSize(6, 7);

        Utilities.assertPublicMethodExists(RobotWithOffspring2.class, "addToDirectionOfOffspring", new Class[]{int.class}, new Class[]{void.class});

        var sut = new RobotWithOffspring2(6, 7, Direction.LEFT, 0);
        // Test that no NullPointerException occurs when offspring is not initialized.
        try {
            sut.addToDirectionOfOffspring(12);
        } catch (NullPointerException e) {
            fail("Die Methode addToDirectionOfOffspring wirft eine NullPointerException, wenn der offspring nicht initialisiert ist.");
        }

        try {
            var offspringField = RobotWithOffspring.class.getDeclaredField("offspring");
            offspringField.setAccessible(true);

            for (int i = -5; i <= 6; i++) {
                var offspring = initializeNewOffspring(offspringField, sut, 2, 3, Direction.DOWN, 3);
                setValueOfField(RobotWithOffspring2.class, "directionAccu", sut, 2);
                sut.addToDirectionOfOffspring(i);
                var actualDirectionAccu = (int) Utilities.getValueOfField(RobotWithOffspring2.class, "directionAccu", sut);
                assertEquals(2 + i, actualDirectionAccu, "Der Wert von directionAccu wurde nicht korrekt gesetzt.");
                var directionFromAccu = getValueOfParameterlessMethod(RobotWithOffspring2.class, "getDirectionFromAccu", sut);
                assertEquals(directionFromAccu, offspring.getDirection(), "Der offspring zeigt nicht in die korrekte Richtung.");
            }
        } catch (NoSuchFieldException e) {
            fail("Das Attribut offspring wurde in der Klasse RobotWithOffspring nicht gefunden.");
        } catch (NoSuchMethodException e) {
            fail("Die Methode getDirectionFromAccu wurde in der Klasse RobotWithOffspring nicht gefunden.");
        }
    }

    private <TValue, TClass> TValue getValueOfParameterlessMethod(@NotNull Class owner, String name, @NotNull TClass object) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method method = owner.getDeclaredMethod(name);
        method.setAccessible(true);
        return (TValue) method.invoke(object);
    }

    private <TValue, TClass> void setValueOfField(@NotNull Class owner, String name, @NotNull TClass object, TValue value) throws IllegalAccessException, NoSuchFieldException {
        Field field = owner.getDeclaredField(name);
        field.setAccessible(true);
        field.set(object, value);
    }

    private @NotNull Robot initializeNewOffspring(@NotNull Field offspringField, RobotWithOffspring2 parent, int x, int y, Direction direction, int numberOfCoins) throws IllegalAccessException {
        var offspring = new Robot(x, y, direction, numberOfCoins);
        offspringField.set(parent, offspring);
        return offspring;
    }
}
