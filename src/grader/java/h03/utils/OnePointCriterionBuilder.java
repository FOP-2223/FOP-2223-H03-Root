package h03.utils;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Grader;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;

public class OnePointCriterionBuilder extends CriterionBuilder {
    private final Class<?> testClass;
    private final Tuple<String, Class<?>[]>[] testMethods;

    public OnePointCriterionBuilder(String shortDescription, Class<?> testClass, Tuple<String, Class<?>[]>... testMethods) {
        super(shortDescription);
        this.testClass = testClass;
        this.testMethods = testMethods;
    }

    @Override
    public Criterion build() {
        var testAwareBuilder = Grader.testAwareBuilder();

        for (var testMethod : testMethods) {
            testAwareBuilder = testAwareBuilder.requirePass(JUnitTestRef.ofMethod(
                () -> testClass.getMethod(testMethod.left, testMethod.right)));
        }

        return builderWithShortDescription()
            .grader(testAwareBuilder.pointsPassedMax().pointsFailedMin().build())
            .build();
    }
}
