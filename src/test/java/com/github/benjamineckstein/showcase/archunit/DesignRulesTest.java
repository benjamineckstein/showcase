package com.github.benjamineckstein.showcase.archunit;

import com.github.benjamineckstein.showcase.architecture.Boundary;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

import static com.github.benjamineckstein.showcase.archunit.CodingRulesTest.SHOWCASECLASSES;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static java.util.function.Predicate.not;

class DesignRulesTest {

  @Test
  void controllerAnnotatedWithRestcontroller() {
    classes()
        .that()
        .haveNameMatching(".*[cC]ontroller")
        .should()
        .beAnnotatedWith(RestController.class)
        .check(SHOWCASECLASSES);
  }

  @Test
  void controllerNamingPattern() {
    classes()
        .that()
        .areAnnotatedWith(RestController.class)
        .should()
        .haveNameMatching(".*[cC]ontroller")
        .check(SHOWCASECLASSES);
  }

  @Test
  void controllerMethodsReturnResponseEntity() {

    classes()
        .that()
        .areAnnotatedWith(RestController.class)
        .should(
            new ArchCondition<>("only have methods returning Responseentity") {
              @Override
              public void check(JavaClass item, ConditionEvents events) {
                item.getMethods().stream()
                    .filter(isMethodPublicAndNotSynthetic())
                    .filter(m -> !m.getRawReturnType().isAssignableTo(ResponseEntity.class))
                    .forEach(
                        m -> {
                          String message =
                              String.format(
                                  "Method %s.%s is returning %s instead of ResponseEntity",
                                  item.getName(),
                                  m.reflect().getName(),
                                  m.getRawReturnType().getSimpleName());
                          events.add(SimpleConditionEvent.violated(m, message));
                        });
              }
            })
        .check(SHOWCASECLASSES);
  }

  /**
   * more readable version of "controller_methods_return_responseEntity" with better error message
   * as well.
   */
  @Test
  void controllerMethodsReturnResponseEntity2() {
    ArchRuleDefinition.methods()
        .that()
        .arePublic()
        .and()
        .areDeclaredInClassesThat()
        .areAnnotatedWith(RestController.class)
        .should()
        .haveRawReturnType(ResponseEntity.class)
        .check(SHOWCASECLASSES);
  }

  @Test
  void boundaryMethodsTransactional() {
    classes()
        .that()
        .areAnnotatedWith(Boundary.class)
        .should(
            new ArchCondition<>("only have methods annotated with @Transactional") {
              @Override
              public void check(JavaClass item, ConditionEvents events) {
                item.getMethods().stream()
                    .filter(isMethodPublicAndNotSynthetic())
                    .filter(isMethodNotAnnotatedWith(Transactional.class))
                    .forEach(
                        m -> {
                          String message =
                              String.format(
                                  "Method %s.%s is not @Transactional",
                                  item.getName(), m.reflect().getName());
                          events.add(SimpleConditionEvent.violated(m, message));
                        });
              }
            })
        .check(SHOWCASECLASSES);
  }

  /**
   * more readable version of "boundary_methods_transactional" with better error message as well.
   */
  @Test
  void boundaryMethodsTransactional2() {

    ArchRuleDefinition.methods()
        .that()
        .arePublic()
        .and()
        .areDeclaredInClassesThat()
        .areAnnotatedWith(Boundary.class)
        .should()
        .beAnnotatedWith(Transactional.class)
        .check(SHOWCASECLASSES);
  }

  private static Predicate<JavaMethod> isMethodNotAnnotatedWith(Class<? extends Annotation> type) {
    return not(m -> m.isAnnotatedWith(type));
  }

  private static Predicate<JavaMethod> isMethodPublicAndNotSynthetic() {
    return isMethodPublic().and(isMethodNotSynthetic());
  }

  private static Predicate<JavaMethod> isMethodPublic() {
    return m -> m.getModifiers().contains(JavaModifier.PUBLIC);
  }

  private static Predicate<JavaMethod> isMethodNotSynthetic() {
    return not(m -> m.getModifiers().contains(JavaModifier.SYNTHETIC));
  }
}
