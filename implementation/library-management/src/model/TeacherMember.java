package model;

public class TeacherMember extends Member{

    public TeacherMember(int memberId, String name) {
        super(memberId, name);
    }

    @Override
    public int getMaxItems() {
        return 5;
    }

    @Override
    public double getFinePerDay() {
        return 5.0;
    }
}
