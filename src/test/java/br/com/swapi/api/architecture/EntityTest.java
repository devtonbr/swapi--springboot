package br.com.swapi.api.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "br.com.swapi.api", importOptions = ImportOption.DoNotIncludeTests.class)
class EntityTest {

    static final ArchRule ENTITIES_MUST_BE_RECORDS = classes()
            .that().resideInAPackage("..entity..")
            .should().beRecords()
            .as("As classes do pacote entity devem ser records");

    @ArchTest
    static void entitiesMustBeRecords(JavaClasses classes) {
        ENTITIES_MUST_BE_RECORDS.check(classes);
    }


}
