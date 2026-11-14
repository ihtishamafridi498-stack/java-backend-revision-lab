public class Main {
    public static void main(String[] args) {

        Student s1=new Student(1,"afridi",20,"data science");

        Student s2=new Student();

        Student s3=new Student("khan",22);



        s1.displayDetails();
        s2.displayDetails();
        s3.displayDetails();

    }
}