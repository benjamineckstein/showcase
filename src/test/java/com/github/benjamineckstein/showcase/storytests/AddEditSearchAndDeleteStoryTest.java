package com.github.benjamineckstein.showcase.storytests;

import com.github.benjamineckstein.showcase.employees.controller.EmployeesController;
import com.github.benjamineckstein.showcase.employees.dto.EmployeeCreateDto;
import com.github.benjamineckstein.showcase.employees.dto.EmployeeDto;
import com.github.benjamineckstein.showcase.expertise.controller.ExpertiseController;
import com.github.benjamineckstein.showcase.expertise.dto.ExpertiseCreateDto;
import com.github.benjamineckstein.showcase.expertise.dto.ExpertiseDto;
import com.github.benjamineckstein.showcase.expertise.dto.ExpertiseUpdateDto;
import com.github.benjamineckstein.showcase.expertise.entity.ExpertiseLevel;
import com.github.benjamineckstein.showcase.search.controller.SearchController;
import com.github.benjamineckstein.showcase.skills.controller.SkillsController;
import com.github.benjamineckstein.showcase.skills.dto.SkillCreateDto;
import com.github.benjamineckstein.showcase.skills.dto.SkillDto;
import com.github.benjamineckstein.showcase.util.TestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static com.github.benjamineckstein.showcase.util.TestHelper.getBody;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class AddEditSearchAndDeleteStoryTest {

  @Autowired DataSource dataSource;

  @Autowired SkillsController skillsController;

  @Autowired EmployeesController employeesController;

  @Autowired ExpertiseController expertiseController;

  @Autowired SearchController searchController;

  @Test
  public void addSomeEmployees() throws SQLException {

    // verfify that we will work on an empty database
    assertThat(getBody(skillsController.getSkills(), HttpStatus.OK).getSkills()).isEmpty();
    assertThat(getBody(employeesController.getEmployees(), HttpStatus.OK).getEmployees()).isEmpty();
    assertThat(getBody(expertiseController.getExpertiseList(), HttpStatus.OK).getExpertise())
        .isEmpty();

    // add 3 skills to our database
    SkillDto webDevelopment =
        getBody(
            skillsController.createSkill(SkillCreateDto.builder().name("Web Development").build()));
    SkillDto mobileAppDevelopment =
        getBody(
            skillsController.createSkill(
                SkillCreateDto.builder().name("Mobile App Development").build()));
    SkillDto databaseEngineering =
        getBody(
            skillsController.createSkill(
                SkillCreateDto.builder().name("Database Engineering").build()));

    // add 3 employees to our database
    EmployeeDto emma_harding =
        getBody(
            employeesController.createEmployee(
                EmployeeCreateDto.builder().name("Emma Harding").build()));

    EmployeeDto peter_peterson =
        getBody(
            employeesController.createEmployee(
                EmployeeCreateDto.builder().name("Peter Peterson").build()));

    EmployeeDto harald_watson =
        getBody(
            employeesController.createEmployee(
                EmployeeCreateDto.builder().name("Harald Watson").build()));

    // add some expertise to our database
    ExpertiseDto emma_webdev =
        getBody(
            expertiseController.createExpertise(
                ExpertiseCreateDto.builder()
                    .employeeId(emma_harding.getId())
                    .skillId(webDevelopment.getId())
                    .level(ExpertiseLevel.PRINCIPAL)
                    .description("5 Years of Experience")
                    .build()));

    ExpertiseDto harald_webdev =
        getBody(
            expertiseController.createExpertise(
                ExpertiseCreateDto.builder()
                    .employeeId(emma_harding.getId())
                    .skillId(webDevelopment.getId())
                    .level(ExpertiseLevel.JUNIOR)
                    .description("Zero experience. Learning from Emma.")
                    .build()));

    ExpertiseDto peter_database =
        getBody(
            expertiseController.createExpertise(
                ExpertiseCreateDto.builder()
                    .employeeId(peter_peterson.getId())
                    .skillId(databaseEngineering.getId())
                    .level(ExpertiseLevel.MIDDLE)
                    .description("2 Years of experience. Knows good stuff about indices.")
                    .build()));

    assertThat(getBody(searchController.findExpertiseByKeyword("emma")).getExpertise())
        .hasSize(2)
        .containsExactlyInAnyOrder(emma_webdev, harald_webdev);
    assertThat(getBody(searchController.findExpertiseByKeyword("peterson")).getExpertise())
        .hasSize(1)
        .containsExactlyInAnyOrder(peter_database);
    assertThat(getBody(searchController.findExpertiseByKeyword("Learning")).getExpertise())
        .hasSize(1)
        .containsExactlyInAnyOrder(harald_webdev);
    assertThat(getBody(searchController.findExpertiseByKeyword("Nothing To Return")).getExpertise())
        .isEmpty();

    assertThat(getBody(searchController.findEmployeesByName(emma_harding.getName())).getEmployees())
        .hasSize(1)
        .containsExactly(emma_harding);

    assertThat(
            getBody(searchController.findSkillsByName(mobileAppDevelopment.getName())).getSkills())
        .hasSize(1)
        .containsExactly(mobileAppDevelopment);

    // emma leaves the company
    assertThat(employeesController.deleteEmployee(emma_harding.getId()).getStatusCode())
        .isEqualTo(HttpStatus.NO_CONTENT);
    // emma expertise should be deleted as well
    assertThatThrownBy(
            () ->
                expertiseController.updateExpertise(
                    ExpertiseUpdateDto.builder()
                        .id(emma_webdev.getId())
                        .version(0)
                        .level(ExpertiseLevel.PRINCIPAL)
                        .description("New Description")
                        .build()))
        .isInstanceOf(NoSuchElementException.class);

    // peter marries
    EmployeeDto peter = getBody(employeesController.getEmployee(peter_peterson.getId()));
    peter.setName("Peter Matters");
    assertThat(getBody(employeesController.updateEmployee(peter)).getName())
        .isEqualTo(peter.getName());

    // skill web development is no longer needed
    assertThat(skillsController.deleteSkill(webDevelopment.getId()).getStatusCode())
        .isEqualTo(HttpStatus.NO_CONTENT);

    // accidentally updated a deleted skill
    assertThatThrownBy(() -> skillsController.updateSkill(webDevelopment))
        .isInstanceOf(NoSuchElementException.class);

    // clean up database
    TestHelper.clearDatabase(dataSource);
  }
}
