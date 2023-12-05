package service;

import constant.Constant;
import entity.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeManager {

    private final List<Employee> employees;

    public EmployeeManager() {
        List<Certificate> certificateList = new ArrayList<>(
                List.of(
                        new Certificate("1", "OCP", "A", LocalDate.of(2022, Month.DECEMBER, 1)),
                        new Certificate("2", "OCP", "B", LocalDate.of(2022, Month.DECEMBER, 1)),
                        new Certificate("3", "OCP", "C", LocalDate.of(2022, Month.DECEMBER, 1)),
                        new Certificate("4", "OCP", "D", LocalDate.of(2022, Month.DECEMBER, 1))
                        ));
        Fresher fresher1 = new Fresher("1", "Nguyen Thi My Duyen", LocalDate.of(2001, Month.FEBRUARY,
                                                                                26)
                , "0961130569", "dyndyn@gmail.com", new ArrayList<Certificate>(),
                                       LocalDate.of(2023, Month.NOVEMBER, 20), "A", "Dai Hoc Hanoi");
        Fresher fresher2 = new Fresher("2", "Nguyen My Duyen", LocalDate.of(2001, Month.FEBRUARY,
                                                                            26)
                , "0971130569", "dyndyn@gmail.com", new ArrayList<Certificate>(),
                                       LocalDate.of(2023, Month.NOVEMBER, 20), "B", "Dai Hoc Hanoi");

        Experience experience1 = new Experience("3", "Do Phuc Dai", LocalDate.of(2001, Month.MARCH,
                                                                                26)
                , "0961130081", "daidodev@gmail.com", certificateList,2,"Java, Angular" );
        Experience experience2 = new Experience("4", "Do Phuc Dai", LocalDate.of(2001, Month.MARCH,
                                                                                26)
                , "0961130082", "daidodev2@gmail.com", certificateList,2,"Java, Angular" );

        this.employees = new ArrayList<Employee>(List.of(fresher1, fresher2,experience1,experience2));
        Employee.count= this.employees.size();
    }

    public boolean checkExistEmployee(String id) {
        return findById(id) != null;
    }

    public void insert(Employee employee) {
        this.employees.add(employee);
        Employee.count++;
        System.out.println("Insert new employee success !!! ");
        System.out.println(Arrays.toString(this.employees.toArray()));
        System.out.println("TOTAL EMPLOYEE: " + Employee.count);
    }

    public Employee findById(String id) {
        return this.employees.stream().filter(employee -> employee.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Employee> findByName(String name) {
        return this.employees.stream().filter(employee -> employee.getFullName().contains(name))
                .collect(Collectors.toList());
    }

    public boolean deleteById(String id) {
        if(employees.isEmpty()){
            System.out.println("No employee for delete");
            return false;
        }
        Employee employee = this.findById(id);
        if (employee == null) {
            System.out.println("Cant find employee id");
            return false;
        }
        this.employees.remove(employee);
        Employee.count--;
        System.out.println(Arrays.toString(this.employees.toArray()));
        System.out.println("TOTAL EMPLOYEE: " + Employee.count);
        return true;
    }

    public List<Employee> findByType(int type) {
        return this.employees.stream()
                .filter(employee -> {
                    if (type == Constant.EMPLOYEE_TYPE.EXPERIENCE) {
                        return employee instanceof Experience;
                    }
                    if (type == Constant.EMPLOYEE_TYPE.FRESHER) {
                        return employee instanceof Fresher;
                    }
                    if (type == Constant.EMPLOYEE_TYPE.INTERN) {
                        return employee instanceof Intern;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public List<Employee> findAll() {
        return this.employees;
    }


}
