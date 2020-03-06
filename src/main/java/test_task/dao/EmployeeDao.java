package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Employee;

import java.util.List;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Long> {

    //TODO Get a list of employees receiving a salary greater than that of the boss
    @Query(
            value = "SELECT *\n" +
                    "  FROM employee\n" +
                    "  JOIN employee AS bosses\n" +
                    "    ON employee.boss_id = bosses.id\n" +
                    " WHERE employee.salary > bosses.salary\n",
            nativeQuery = true)
    List<Employee> findAllWhereSalaryGreaterThatBoss();

    //TODO Get a list of employees receiving the maximum salary in their department
    @Query(
            value = "SELECT *FROM employee e\n" +
                    "LEFT JOIN department d ON e.department_id = d.id\n" +
                    "WHERE NOT exists (SELECT 1 FROM employee t WHERE t.department_id = e.department_id\n" +
                    "AND t.Salary > e.Salary);",
            nativeQuery = true)
    List<Employee> findAllByMaxSalary();

    //TODO Get a list of employees who do not have boss in the same department
    @Query(
            value = "SELECT *\n" +
                    "FROM employee e\n" +
                    "JOIN employee boss ON e.boss_id = boss.id\n" +
                    "WHERE e.department_id != boss.department_id",
            nativeQuery = true)
    List<Employee> findAllWithoutBoss();
}
