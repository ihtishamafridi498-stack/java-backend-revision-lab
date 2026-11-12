package model;

public class StudentMember extends Member{

    public StudentMember(int memberId,String memberName){
        super(memberId,memberName);
    }

    @Override
    public int getMaxItems() {
        return 3;
    }

    @Override
    public double getFinePerDay() {
        return 10.0;
    }
}
