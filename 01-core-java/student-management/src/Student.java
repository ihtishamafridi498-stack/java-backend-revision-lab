public class Student {

    private int id;
    private String name;
    private int age;
    private String department;


    public Student(){

        this(2,"unknown",20,"physics");
    }

    public Student (String name, int age){
        this(3,name,age,"computer science");
    }
    public Student(int id,String name,int age,String department){
        this.id=id;
        this.name=name;
        this.age=age;
        this.department=department;
    }

    public int getAge() {
        return age;
    }

    public void displayDetails(){
        System.out.println("student id : " +id +
                " student name: " + name
        +" student age: " + age +" department :"+ department);
    }

    public boolean isEligibleForAdmission(){
        return age>=18;
    }
}

