package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Department;

import java.util.List;

@Repository
public interface DepartmentDao extends CrudRepository<Department, Long> {
    //TODO Get a list of department IDS where the number of employees doesn't exceed 3 people
    @Query(
            value = "SELECT d.id AS Department\n" +
                    "FROM department d JOIN employee e \n" +
                    "ON e.department_id = d.id\n" +
                    "GROUP BY d.id\n" +
                    "HAVING COUNT(e.id) < 3",
            nativeQuery = true)
    List<Long> findAllWhereDepartmentDoesntExceedThreePeople();

    //TODO Get a list of departments IDs with the maximum total salary of employees
    @Query(
            value = "SELECT d.id\n" +
                    "FROM department d LEFT OUTER JOIN employee e ON (d.id = e.department_id) GROUP BY d.id\n" +
                    "ORDER BY sum(salary) DESC;",
            nativeQuery = true)
    List<Long> findAllByMaxTotalSalary();
}
