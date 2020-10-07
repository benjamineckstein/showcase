package com.example.showcase.archunit;

import com.example.showcase.architecture.Boundary;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

import static com.example.showcase.archunit.CodingRulesTest.SHOWCASECLASSES;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static java.util.function.Predicate.not;

public class DesignRulesTest {

    @Test
    public void boundary_methods_transactional() {
        classes().that().areAnnotatedWith(Boundary.class).should(new ArchCondition<>("only have public methods annotated with @Transactional") {
            @Override
            public void check(JavaClass item, ConditionEvents events) {
                item.getMethods().stream()
                        .filter(isMethodPublicAndNotSynthetic())
                        .filter(isMethodNotAnnotatedWith(Transactional.class))
                        .forEach(m -> {
                            String message = String.format("Method %s.%s is not @Transactional", item.getName(), m.reflect().getName());
                            events.add(SimpleConditionEvent.violated(m, message));
                        });
            }

        }).check(SHOWCASECLASSES);
    }

    public static Predicate<JavaMethod> isMethodNotAnnotatedWith(Class<? extends Annotation> type) {
        return not(m -> m.isAnnotatedWith(type));
    }

    public static Predicate<JavaMethod> isMethodPublicAndNotSynthetic() {
        return isMethodPublic().and(isMethodNotSynthetic());
    }

    public static Predicate<JavaMethod> isMethodPublic() {
        return m -> m.getModifiers().contains(JavaModifier.PUBLIC);
    }

    public static Predicate<JavaMethod> isMethodNotSynthetic() {
        return not(m -> m.getModifiers().contains(JavaModifier.SYNTHETIC));
    }
}