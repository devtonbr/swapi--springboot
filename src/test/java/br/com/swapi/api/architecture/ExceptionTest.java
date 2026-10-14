package br.com.swapi.api.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "br.com.swapi.api", importOptions = ImportOption.DoNotIncludeTests.class)
class ExceptionTest {

    static final ArchRule NO_THROWS_GENERIC_EXCEPTION = noClasses()
            .that().resideOutsideOfPackage("..openapi..")
            .should(GeneralCodingRules.THROW_GENERIC_EXCEPTIONS);

    @ArchTest
    static void noThrowsGenericException(JavaClasses classes) {
        NO_THROWS_GENERIC_EXCEPTION.check(classes);
    }


}
