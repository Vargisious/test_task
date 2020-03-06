package test_task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_task.dao.EmployeeDao;
import test_task.model.Employee;
import test_task.service.EmployeeService;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public List<Employee> findAllBySalaryGreaterThatBoss() {
        return employeeDao.findAllWhereSalaryGreaterThatBoss();
    }

    @Override
    public List<Employee> findAllByMaxSalary() {
        return employeeDao.findAllByMaxSalary();
    }

    @Override
    public List<Employee> findAllWithoutBoss() {
        return employeeDao.findAllWithoutBoss();
    }

    @Override
    public Long fireEmployee(String name) {
        Iterable<Employee> employees = employeeDao.findAll();

        //TODO Implement method using Collection
        // ---write your code here
        //This is all so wrong..
        //Getting it all out, doing it all with java code and then persisting it all back.
        //All of this should be done by the DB.
        Long id = 0L;
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getName().equals(name)) {
                iterator.remove();
                id = employee.getId();
                break;
            }
        }
        return id;
    }

    @Override
    public Long changeSalary(String name) {
        Iterable<Employee> employees = employeeDao.findAll();

        //TODO Implement method using Collection
        // ---write your code here
        Long id = 0L;
        for (Employee employee : employees) {
            if (employee.getName().equals(name)) {
                employee.setSalary(BigDecimal.valueOf(10000));
                id = employee.getId();
                break;
            }
        }
        return id;
    }

    @Override
    public Long hireEmployee(Employee employee) {
        //TODO Implement method using Collection and DAO
        // ---write your code here
        Iterable<Employee> employees = employeeDao.findAll();
        List<Employee> list = StreamSupport.
                stream(employees.spliterator(), false).
                collect(Collectors.toList());
        if (list.stream().noneMatch((n) -> n.getName().equals(employee.getName()))) {
            employeeDao.save(employee);
        } else {
            throw new RuntimeException("already exists");
        }
        return employee.getId();
    }
}
