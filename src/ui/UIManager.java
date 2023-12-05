package ui;

import constant.Constant;
import entity.*;
import exception.BirthDayException;
import exception.EmailException;
import exception.FullNameException;
import exception.PhoneException;
import service.EmployeeManager;
import service.ScannerService;
import service.ValidatorService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

public class UIManager {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final EmployeeManager employeeManager = new EmployeeManager();

    /**
     * @param type
     * @return Employee
     * type = 0 => Experience
     * type = 1 => Fresher
     * type = 2 => Intern
     */
    public void insert(int type) {
        Employee employee = null;
        if (type == Constant.EMPLOYEE_TYPE.EXPERIENCE) {
            employee = insertExperience();
        }
        if (type == Constant.EMPLOYEE_TYPE.FRESHER) {
            employee = insertFresher();
        }
        if (type == Constant.EMPLOYEE_TYPE.INTERN) {
            employee = insertIntern();
        }

        try {
            assert employee != null;
            checkData(employee);
        } catch (BirthDayException | PhoneException | EmailException | FullNameException e) {
            System.out.println(e.getMessage());
            insert(type);
        }
        this.employeeManager.insert(employee);
    }

    private Experience insertExperience() {
        Employee employee = this.getEmployeeData();
        int yearOfExperience = ScannerService.getIntInput("Input year of experience: ");
        String proSkill = ScannerService.getStringInput("Input pro skill : ");
        return new Experience(employee.getId(), employee.getFullName(),
                              employee.getBirthday(),
                              employee.getPhone(), employee.getEmail(),
                              employee.getCertificates(),
                              yearOfExperience, proSkill);
    }

    private Employee getEmployeeData() {

        String id = Constant.DEFAULT_INPUT.STRING;
        Boolean checkId = false;
        while (!checkId) {
            id = ScannerService.getStringInput("Input ID: ");
            if (employeeManager.checkExistEmployee(id)) {
                System.out.println("Employee id existed !");
                continue;
            }
            checkId = true;
        }
        String name = Constant.DEFAULT_INPUT.STRING;
        Boolean checkName = false;
        while (!checkName) {
            name = ScannerService.getStringInput("Input Name: ");
            try {
                ValidatorService.nameCheck(name);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            checkName = true;
        }

        Boolean checkPhone = false;
        String phone = Constant.DEFAULT_INPUT.STRING;
        while (!checkPhone) {
            phone = ScannerService.getStringInput("Input Phone: ");
            try {
                ValidatorService.phoneCheck(phone);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            checkPhone = true;
        }
        String birthday = Constant.DEFAULT_INPUT.STRING;
        boolean birthdayCheck = false;
        while (!birthdayCheck) {
            birthday = ScannerService.getStringInput("Input Birthday [yyyy-MM-dd] : ");
            try {
                ValidatorService.dayFormatCheck(birthday);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            birthdayCheck = true;
        }

        Date dateBirthday = null;
        try {

            dateBirthday = DATE_FORMAT.parse(birthday);
        } catch (ParseException e) {
            System.out.println("Input in format [yyyy-MM-dd]: " + e.getMessage());
        }

        Instant instant = dateBirthday != null ? dateBirthday.toInstant() : null;

        boolean checkEmail = false;
        String email = Constant.DEFAULT_INPUT.STRING;
        while (!checkEmail) {
            email = ScannerService.getStringInput("Input Email: ");
            try {
                ValidatorService.emailCheck(email);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            checkEmail = true;
        }


        int numOfCert = ScannerService.getIntInput("Input number of cert: ");

        List<Certificate> certificates = new ArrayList<Certificate>();
        Integer certId = 0;
        while (numOfCert > 0) {
            String certName = ScannerService.getStringInput("Input cert name:");
            String certRank = ScannerService.getStringInput("Input cert rank: ");

            boolean isCertDateChecked = false;
            do {
                String certDate = ScannerService.getStringInput("Input cert cate [yyyy-MM-dd]: ");
                try {
                    ValidatorService.dayFormatCheck(certDate);
                } catch (BirthDayException birthDayException) {
                    continue;
                }

                Date tempCertDate;
                try {
                    tempCertDate = DATE_FORMAT.parse(birthday);
                } catch (ParseException e) {
                    System.out.println("Input must in format [yyyy-MM-dd]");
                    continue;
                }
                Instant certDateInstant = tempCertDate != null ? tempCertDate.toInstant() : null;
                certificates.add(new Certificate(certId.toString(), certName, certRank,
                                                 certDateInstant != null ? certDateInstant.atZone(
                                                         ZoneId.systemDefault()).toLocalDate() : null
                ));
                isCertDateChecked = true;
            } while (!isCertDateChecked);
            numOfCert--;
        }
        return new Employee(id, name, instant != null ? instant.atZone(ZoneId.systemDefault()).toLocalDate() : null,
                            phone, email, certificates);
    }

    private Intern insertIntern() {
        Employee employee = this.getEmployeeData();

        String major = ScannerService.getStringInput("Input major: ");

        int semester = ScannerService.getIntInput("Input semester: ");

        String universityName = ScannerService.getStringInput("Input universityName: ");

        return new Intern(employee.getId(),
                          employee.getFullName(),
                          employee.getBirthday(),
                          employee.getPhone(),
                          employee.getEmail(),
                          employee.getCertificates(),
                          major,
                          semester,
                          universityName
        );

    }

    private Fresher insertFresher() {
        Employee employee = this.getEmployeeData();
        boolean graduationDateCheck = false;
        String graduationDate = "";
        while (!graduationDateCheck) {

            graduationDate = ScannerService.getStringInput("Input graduation date [yyyy-MM-dd] : ");
            try {
                ValidatorService.dayFormatCheck(graduationDate);
            } catch (Exception e) {
                System.out.println("[yyyy-MM-dd] format please:");
                continue;
            }
            graduationDateCheck = true;
        }

        Date dateGraduation = null;

        try {
            dateGraduation = DATE_FORMAT.parse(graduationDate);

        } catch (ParseException e) {
            System.out.println("Error parsing the date: " + e.getMessage());
        }

        Instant graduationDateInstant = dateGraduation != null ? dateGraduation.toInstant() : null;

        String graduationRank = ScannerService.getStringInput("Input graduationRank: ");

        String universityName = ScannerService.getStringInput("Input universityName : ");

        return new Fresher(employee.getId(),
                           employee.getFullName(),
                           employee.getBirthday(),
                           employee.getPhone(),
                           employee.getEmail(),
                           employee.getCertificates(),
                           graduationDateInstant != null ? graduationDateInstant.atZone(ZoneId.systemDefault())
                                   .toLocalDate() : null,
                           graduationRank,
                           universityName);
    }

    public void update() {
        if (employeeManager.findAll().isEmpty()) {
            System.out.println("No employee for update");
            return;
        }
        String id = Constant.DEFAULT_INPUT.STRING;
        boolean idCheck = false;
        while (!idCheck) {
            id = ScannerService.getStringInput("Input ID to update: ");
            if (employeeManager.checkExistEmployee(id)) {
                idCheck = true;
            } else {
                System.out.println("Can't find this employee ID");
            }
        }

        Employee employee = this.employeeManager.findById(id);
        System.out.println("Found employee: " + employee);
        if (employee instanceof Experience) {
            this.updateExperience((Experience) employee);
        } else if (
                employee instanceof Fresher
        ) {
            this.updateFresher((Fresher) employee);
        } else {
            this.updateIntern((Intern) employee);
        }
        System.out.println("update employee succeed !!! ");


    }


    private void updateExperience(Experience experience) {
        this.updateEmployee(experience);
        int yearOfExperience = ScannerService.getIntInput("Input year of experience: ");
        experience.setYearOfExperience(yearOfExperience);
        String proSkill = ScannerService.getStringInput("Input pro skill : ");
        experience.setProSkill(proSkill);
    }


    private void updateFresher(Fresher fresher) {
        this.updateEmployee(fresher);
        boolean graduationDateCheck = false;
        String graduationDate = "";
        Date dateGraduation = null;
        while (!graduationDateCheck) {
            graduationDate = ScannerService.getStringInput("Input graduation date [yyyy-MM-dd] : ");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dateGraduation = dateFormat.parse(graduationDate);

            } catch (ParseException e) {
                System.out.println("Input informat [yyyy-MM-dd]:");
                continue;
            }
            graduationDateCheck = true;
        }
        Instant graduationDateInstant = dateGraduation != null ? dateGraduation.toInstant() : null;
        fresher.setGraduationDate(graduationDateInstant != null ? graduationDateInstant.atZone(ZoneId.systemDefault())
                .toLocalDate() : null);
        String graduationRank = ScannerService.getStringInput("Input year of graduationRank: ");
        fresher.setGraduationRank(graduationRank);
        String universityName = ScannerService.getStringInput("Input universityName : ");
        fresher.setUniversityName(universityName);
    }

    private void updateIntern(Intern intern) {

        this.updateEmployee(intern);

        String major = ScannerService.getStringInput("Input major: ");
        intern.setMajor(major);
        int semester = ScannerService.getIntInput("Input semester: ");
        intern.setSemester(semester);
        String universityName = ScannerService.getStringInput("Input universityName: ");
        intern.setUniversityName(universityName);
    }

    private void updateEmployee(Employee employee) {
        boolean isNameChecked = false;
        do {
            String name = ScannerService.getStringInput("Input Name: ");
            try {
                ValidatorService.nameCheck(name);
            } catch (FullNameException fullNameException) {
                System.out.println(fullNameException.getMessage());
                continue;
            }
            isNameChecked = true;
            employee.setFullName(name);
        } while (!isNameChecked);

        boolean isPhoneChecked = false;
        do {

            String phone = ScannerService.getStringInput("Input Phone: ");
            try {
                ValidatorService.phoneCheck(phone);
            } catch (PhoneException phoneException) {
                System.out.println(phoneException.getMessage());
                continue;
            }
            isPhoneChecked = true;
            employee.setPhone(phone);
        } while (!isPhoneChecked);

        boolean isBirthdayChecked = false;
        do {
            String birthday = ScannerService.getStringInput("Input Birthday [yyyy-MM-dd] : ");
            try {
                ValidatorService.dayFormatCheck(birthday);
            } catch (BirthDayException birthDayException) {
                System.out.println(birthDayException.getMessage());
                continue;
            }
            isBirthdayChecked = true;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateBirthday = null;
            try {

                // Parse the string to obtain a Date object
                dateBirthday = dateFormat.parse(birthday);
                // Print the parsed Date object
                System.out.println("Parsed Date: " + dateBirthday);
            } catch (ParseException e) {
                System.out.println("Error parsing the date: " + e.getMessage());
            }
            Instant instant = dateBirthday != null ? dateBirthday.toInstant() : null;
            employee.setBirthday(instant != null ? instant.atZone(ZoneId.systemDefault()).toLocalDate() : null);
        } while (!isBirthdayChecked);

        boolean isEmailChecked = false;
        do {
            String email = ScannerService.getStringInput("Input Email: ");
            try {
                ValidatorService.emailCheck(email);
            } catch (EmailException emailException) {
                System.out.println(emailException.getMessage());
                continue;
            }
            isEmailChecked = true;
            employee.setEmail(email);
        } while (!isEmailChecked);


    }

    public void delete() {
        String id = ScannerService.getStringInput("Input ID to delete: ");
        if (this.employeeManager.deleteById(id)) {
            System.out.println("Delete success !!!");
            return;
        }
        System.out.println("Delete failed !!!");
    }

    public void showAllEmployee() {
        this.employeeManager.findAll().forEach(Employee::showInformation);
    }


    public void searchEmployee() {
        System.out.println("search employee: ");
        System.out.println("search all Experience [1]:");
        System.out.println("search all Fresher [2]:");
        System.out.println("search all Intern [3]:");
        System.out.println("search by Id [4]: ");
        System.out.println("search by name [5]: ");
        List<Integer> choseList = new ArrayList<>(List.of(
                Constant.USER_SEARCH_OPTION.ALL_EXPERIENCE,
                Constant.USER_SEARCH_OPTION.ALL_FRESHER,
                Constant.USER_SEARCH_OPTION.ALL_INTERN,
                Constant.USER_SEARCH_OPTION.BY_ID,
                Constant.USER_SEARCH_OPTION.BY_NAME
        ));
        int userSearchOption = -1;
        boolean isUserSearchOptionValid =false;
        while (!isUserSearchOptionValid) {
            try {
                userSearchOption = ScannerService.getIntInput("");
            }catch (InputMismatchException e){
                System.out.println("Input must be integer");
            }

            if (choseList.contains(userSearchOption)) {
                isUserSearchOptionValid=true;
            }
            System.out.println("chose from 1 -> 5");
        }
        switch (userSearchOption) {
            case Constant.USER_SEARCH_OPTION.ALL_EXPERIENCE:
                System.out.println(Arrays.toString(
                        this.employeeManager.findByType(Constant.EMPLOYEE_TYPE.EXPERIENCE).toArray()));
                break;
            case Constant.USER_SEARCH_OPTION.ALL_FRESHER:
                System.out.println(Arrays.toString(
                        this.employeeManager.findByType(Constant.EMPLOYEE_TYPE.FRESHER).toArray()));
                break;
            case Constant.USER_SEARCH_OPTION.ALL_INTERN:
                System.out.println(Arrays.toString(
                        this.employeeManager.findByType(Constant.EMPLOYEE_TYPE.INTERN).toArray()));
                break;
            case Constant.USER_SEARCH_OPTION.BY_ID:
                String id= ScannerService.getStringInput("input Employee Id :");
                if(this.employeeManager.checkExistEmployee(id)){
                    System.out.println(this.employeeManager.findById(id));
                }else {
                    System.out.println("cant find this employee");
                }
                break;
            case Constant.USER_SEARCH_OPTION.BY_NAME:
                String name = ScannerService.getStringInput("input Employee name:");
                System.out.println(Arrays.toString(this.employeeManager.findByName(name).toArray()));
                break;
            default:
                break;
        }
    }

    private void checkData(Employee employee) throws BirthDayException, PhoneException, EmailException,
            FullNameException {
        ValidatorService.dayFormatCheck(employee.getBirthday().toString());
        ValidatorService.phoneCheck(employee.getPhone());
        ValidatorService.emailCheck(employee.getEmail());
        ValidatorService.nameCheck(employee.getFullName());
    }

}
