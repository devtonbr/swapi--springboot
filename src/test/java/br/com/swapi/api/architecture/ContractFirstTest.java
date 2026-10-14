package br.com.swapi.api.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;


@AnalyzeClasses(packages = "br.com.swapi.api", importOptions = ImportOption.DoNotIncludeTests.class)
class ContractFirstTest {

    static final ArchRule IMPLEMENTS_VALIDATOR = ArchRuleDefinition.classes()
            .that().areAnnotatedWith(RestController.class).or().areAnnotatedWith(Controller.class)
            .should().implement(JavaClass.Predicates.resideInAPackage("..openapi.resources.."))
            .as("Os controllers devem implementar as interfaces geradas do contrato (..openapi.resources..)");

    @ArchTest
    static void implementsValidator(JavaClasses classes) {
        IMPLEMENTS_VALIDATOR.check(classes);
    }


}
