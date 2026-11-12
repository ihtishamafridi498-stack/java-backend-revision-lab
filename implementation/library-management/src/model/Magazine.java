package model;

public class Magazine extends LibraryItem{

    private final int issueNumber;

    public Magazine(int id, String title,int issueNumber){
        super(id, title);
        if(issueNumber<=0){
            throw new IllegalArgumentException("issue number must be greater than zero");
        }
        this.issueNumber=issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    @Override
    public int getLoanDays() {
        return 7;
    }

    @Override
    public String getItemType() {
        return "Magazine";
    }

    @Override
    public String toString() {
        return super.toString()+"Magazine{" +
                "issueNumber=" + issueNumber +
                '}';
    }
}
