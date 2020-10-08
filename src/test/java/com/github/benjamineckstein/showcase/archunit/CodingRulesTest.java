package com.github.benjamineckstein.showcase.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.CompositeArchRule;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static com.tngtech.archunit.core.importer.ImportOption.Predefined.DO_NOT_INCLUDE_TESTS;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

public class CodingRulesTest {

  public static final JavaClasses SHOWCASECLASSES =
      new ClassFileImporter()
          .withImportOption(DO_NOT_INCLUDE_TESTS)
          .importPackages("com.github.benjamineckstein.showcase");

  @Test
  public void classes_should_not_access_standard_streams_defined_by_hand() {
    noClasses().should(ACCESS_STANDARD_STREAMS).check(SHOWCASECLASSES);
  }

  @Test
  public void classes_should_not_access_standard_streams_from_library() {
    NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS.check(SHOWCASECLASSES);
  }

  @Test
  public void classes_should_not_throw_generic_exceptions() {
    NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.check(SHOWCASECLASSES);
  }

  @Test
  public void classes_should_not_use_java_util_logging() {
    NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING.check(SHOWCASECLASSES);
  }

  @Test
  public void loggers_should_be_private_static_final() {
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
  public void classes_should_not_use_jodatime() {
    NO_CLASSES_SHOULD_USE_JODATIME.check(SHOWCASECLASSES);
  }

  @Test
  public void classes_should_not_use_field_injection() {
    NO_CLASSES_SHOULD_USE_FIELD_INJECTION.check(SHOWCASECLASSES);
  }

  @Test
  public void no_classes_should_access_standard_streams_or_throw_generic_exceptions() {
    CompositeArchRule.of(NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS)
        .and(NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS)
        .check(SHOWCASECLASSES);
  }
}
