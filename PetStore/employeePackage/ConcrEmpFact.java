package employeePackage;

public class ConcrEmpFact extends abstEmpFact {

    //Args takes on the following structure:
    //args[0] = employee type. "clerk", "trainer"
    //args[1] = employee name.
    //args[2] = if the employee is a trainer, is the training type
        //"hapazard", "negitiveRefT", "positiveRenfT"
    public Employee makeEmp(String[] args){
        if(args[0].equals("clerk")){
            return new Clerk(args[1]);
        }else if(args[0].equals("trainer")){
            TrainType trainType;
            if(args[2].equals("haphazard")){
                trainType = new HaphazardT();
            }else if(args[2].equals("negitiveRenfT")){
                trainType = new NegitiveRenfT();
            }else if(args[2].equals("positiveRenfT")){
                trainType = new PositiveRenfT();
            }else{
                trainType = new PositiveRenfT();
            }
            return new Trainer(args[1], trainType);
        }else{
            return null;
        }

    }



}
