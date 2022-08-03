package h03.ArgumentsProviders;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WorldArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        List<Arguments> arguments = new ArrayList<>();
        for (int width = 1; width <= 10; width++) {
            for (int height = 1; height <= 10; height++) {
                arguments.add(Arguments.of(width, height));
            }
        }

        return arguments.stream();
    }
}
