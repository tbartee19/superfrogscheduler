package edu.tcu.cs.superfrogscheduler.model;

// three event types
// on campus event - $100/hour
// public schools or non-profit - $100/hour
// private or residential - $175/hour
public enum EventType {
    TCU(100), PUBLIC(100), PRIVATE(175);

    private int hourlyRate;

    EventType(int hourlyRate){
        this.hourlyRate = hourlyRate;
    }

    public int getHourlyRate(){
        return hourlyRate;
    }

}