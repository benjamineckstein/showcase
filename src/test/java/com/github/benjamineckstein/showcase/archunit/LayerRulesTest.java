package com.github.benjamineckstein.showcase.archunit;

import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.github.benjamineckstein.showcase.archunit.CodingRulesTest.SHOWCASECLASSES;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class LayerRulesTest {

  @Test
  void layerControllerMayNotBeAccessed() {
    getLayeredArchitectur()
        .whereLayer("Controller")
        .mayNotBeAccessedByAnyLayer()
        .check(SHOWCASECLASSES);
  }

  @Test
  void layerBoundaryMayOnlyBeAccessedFromController() {
    getLayeredArchitectur()
        .whereLayer("Boundary")
        .mayOnlyBeAccessedByLayers("Controller")
        .check(SHOWCASECLASSES);
  }

  @Test
  void layerRepositoryMayOnlyBeAccessedFromBoundary() {
    getLayeredArchitectur()
        .whereLayer("Repository")
        .mayOnlyBeAccessedByLayers("Boundary")
        .check(SHOWCASECLASSES);
  }

  @Test
  void layerEntityMayNotAccessOtherLayers() {
    noClasses()
        .that()
        .resideInAnyPackage("..entity..")
        .should()
        .accessClassesThat()
        .resideInAnyPackage("..controller..", "..services..", "respository..")
        .check(SHOWCASECLASSES);
  }

  @Test
  void noUnwantedNewPackages() {
    noClasses()
        .that()
        .areNotAnnotatedWith(SpringBootApplication.class)
        .should()
        .resideOutsideOfPackages(
            "..controller",
            "..boundary",
            "..repository",
            "..entity",
            "..dto",
            "..common",
            "..architecture")
        .check(SHOWCASECLASSES);
  }

  @Test
  void freeOfCycles() {
    // sort classes by the first package after 'myapp'
    // then check those slices for cyclic dependencies
    SlicesRuleDefinition.slices()
        .matching("..benjamineckstein.(*)..")
        .should()
        .beFreeOfCycles()
        .check(SHOWCASECLASSES);
  }

  @Test
  @Disabled
  void testThatTopTierPackagesAreIndependend() {
    // checks all subpackages of 'myapp' for cycles
    SlicesRuleDefinition.slices()
        .matching("..benjamineckstein.(**)")
        .should()
        .notDependOnEachOther()
        .check(SHOWCASECLASSES);
  }

  @Test
  void freeOfCycles3() {

    // sort classes by packages between 'myapp' and 'service'
    // then check those slices for not having any dependencies on each other
    SlicesRuleDefinition.slices()
        .matching("..benjamineckstein.(**).service..")
        .should()
        .notDependOnEachOther()
        .check(SHOWCASECLASSES);
  }

  private Architectures.LayeredArchitecture getLayeredArchitectur() {
    return layeredArchitecture()
        .layer("Controller")
        .definedBy("..controller..")
        .layer("Boundary")
        .definedBy("..boundary..")
        .layer("Entity")
        .definedBy("..entity..")
        .layer("Repository")
        .definedBy("..repository..");
  }
}
