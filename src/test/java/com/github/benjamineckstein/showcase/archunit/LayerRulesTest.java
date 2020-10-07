package com.github.benjamineckstein.showcase.archunit;

import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

import static com.github.benjamineckstein.showcase.archunit.CodingRulesTest.SHOWCASECLASSES;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

class LayerRulesTest {


    @Test
    void layerControllerMayNotBeAccessed() {
        getLayeredArchitectur()
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .check(SHOWCASECLASSES);
    }

    @Test
    void layerBoundaryMayOnlyBeAccessedFromController() {
        getLayeredArchitectur()
                .whereLayer("Boundary").mayOnlyBeAccessedByLayers("Controller")
                .check(SHOWCASECLASSES);
    }

    @Test
    void layerRepositoryMayOnlyBeAccessedFromBoundary() {
        getLayeredArchitectur()
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Boundary")
                .check(SHOWCASECLASSES);
    }

    @Test
    void freeOfCycles() {
        slices().matching("com.github.benjamineckstein.(*)..").should().beFreeOfCycles().check(SHOWCASECLASSES);
    }

    private Architectures.LayeredArchitecture getLayeredArchitectur() {
        return layeredArchitecture()
                .layer("Controller").definedBy("..controller..")
                .layer("Boundary").definedBy("..boundary..")
                .layer("Entity").definedBy("..entity..")
                .layer("Repository").definedBy("..repository..");
    }
}