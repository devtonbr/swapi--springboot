package br.com.swapi.api.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.springframework.scheduling.annotation.Scheduled;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;

@AnalyzeClasses(packages = "br.com.swapi.api", importOptions = ImportOption.DoNotIncludeTests.class)
class HexagonalTest {

    static final ArchRule LAYERS_VALIDATOR = Architectures.layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer("Config").definedBy("..config..")
            .layer("Controller").definedBy("..controller..")
            .layer("UseCase").definedBy("..usecase..")
            .layer("Repository").definedBy("..repository..")
            .layer("DataSource").definedBy("..datasource..")
            .layer("Mapper").definedBy("..mapper..")
            .layer("Entities").definedBy("..entity..")
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Config").mayOnlyBeAccessedByLayers("Controller", "UseCase", "DataSource")
            .whereLayer("Mapper").mayOnlyBeAccessedByLayers("Controller", "DataSource")
            .whereLayer("UseCase").mayOnlyBeAccessedByLayers("Controller", "Config")
            .whereLayer("Repository").mayOnlyBeAccessedByLayers("UseCase", "DataSource")
            .whereLayer("DataSource").mayOnlyBeAccessedByLayers("Repository", "Mapper");

    static final ArchRule SCHEDULED_METHODS_MUST_STAY_IN_CONFIG = noMethods()
            .that().areDeclaredInClassesThat().resideOutsideOfPackage("..config..")
            .should().beAnnotatedWith(Scheduled.class);

    static final ArchRule DATASOURCES_MUST_NOT_DEPEND_ON_OTHER_DATASOURCES = noClasses()
            .that().haveSimpleNameEndingWith("DataSource")
            .should().dependOnClassesThat().haveSimpleNameEndingWith("DataSource");

    // regras declaradas como metodo: o surefire ignora regras @ArchTest declaradas como campo
    @ArchTest
    static void layersValidator(JavaClasses classes) {
        LAYERS_VALIDATOR.check(classes);
    }

    @ArchTest
    static void scheduledMethodsMustStayInConfig(JavaClasses classes) {
        SCHEDULED_METHODS_MUST_STAY_IN_CONFIG.allowEmptyShould(true).check(classes);
    }

    @ArchTest
    static void datasourcesMustNotDependOnOtherDatasources(JavaClasses classes) {
        DATASOURCES_MUST_NOT_DEPEND_ON_OTHER_DATASOURCES.check(classes);
    }


}
