package com.github.benjamineckstein.showcase.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.CompositeArchRule;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static com.tngtech.archunit.core.importer.ImportOption.Predefined.DO_NOT_INCLUDE_TESTS;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;

public class CodingRulesTest {

  public static final JavaClasses SHOWCASECLASSES =
      new ClassFileImporter()
          .withImportOption(DO_NOT_INCLUDE_TESTS)
          .importPackages("com.github.benjamineckstein.showcase");

  @Test
  void classesShouldNotAccessStandardStreamsDefinedByHand() {
    noClasses().should(ACCESS_STANDARD_STREAMS).check(SHOWCASECLASSES);
  }

  @Test
  void classesShouldNotAccessStandardStreamsFromLibrary() {
    NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS.check(SHOWCASECLASSES);
  }

  @Test
  void classesShouldNotThrowGenericExceptions() {
    NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.check(SHOWCASECLASSES);
  }

  @Test
  void classesShouldNotUseJavaUtilLogging() {
    NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING.check(SHOWCASECLASSES);
  }

  @Test
  void loggersShouldBePrivateStaticFinal() {
    fields()
        .that()
        .haveRawType(Logger.class)
        .should()
        .bePrivate()
        .andShould()
        .beStatic()
        .andShould()
        .beFinal()
        .because("we agreed on this convention")
        .check(SHOWCASECLASSES);
  }

  @Test
  void classesShouldNotUseJodatime() {
    NO_CLASSES_SHOULD_USE_JODATIME.check(SHOWCASECLASSES);
  }

  @Test
  void classesShouldNotUseFieldInjection() {
    NO_CLASSES_SHOULD_USE_FIELD_INJECTION.check(SHOWCASECLASSES);
  }

  @Test
  void noClassesShouldAccessStandardStreamsOrThrowGenericExceptions() {
    CompositeArchRule.of(NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS)
        .and(NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS)
        .check(SHOWCASECLASSES);
  }
}
