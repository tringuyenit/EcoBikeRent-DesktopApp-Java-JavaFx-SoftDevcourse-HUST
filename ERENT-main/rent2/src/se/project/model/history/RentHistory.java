package se.project.model.history;

public class RentHistory {

  private String bikeName;
  private float money;

  private String start;
  private String end;

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }


  public String getBikeName() {
    return bikeName;
  }

  public void setBikeName(String bikeName) {
    this.bikeName = bikeName;
  }

  public float getMoney() {
    return money;
  }

  public void setMoney(Float float1) {
    this.money = float1;
  }
}
