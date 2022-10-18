package employeePackage;

public abstract class abstEmpFact {

    public Employee createEmp(String[] args){
        Employee tempEmp = makeEmp(args);
        //Manipulate Employees here.

        return tempEmp;
    }


    protected abstract Employee makeEmp(String[] args);


}