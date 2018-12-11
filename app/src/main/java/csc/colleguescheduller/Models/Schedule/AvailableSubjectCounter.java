package csc.colleguescheduller.Models.Schedule;

import java.util.HashMap;

public class AvailableSubjectCounter {
    
    private HashMap<Section, Integer> theoretical;
    private HashMap<Section, Integer> applied;
    private HashMap<Section, Integer> practical;

    public AvailableSubjectCounter() {
        theoretical = new HashMap<Section, Integer>();
        applied = new HashMap<Section, Integer>();
        practical = new HashMap<Section, Integer>();
    }

    public AvailableSubjectCounter(
        HashMap<Section, Integer> theoretical,
        HashMap<Section, Integer> applied,
        HashMap<Section, Integer> practical
    ) {
        this.theoretical = theoretical;
        this.applied = applied;
        this.practical = practical;
    }

    public HashMap<Section, Integer> getTheoretical() {
        return theoretical;
    }

    public void setTheoretical(HashMap<Section, Integer> theoretical) {
        this.theoretical = theoretical;
    }

    public HashMap<Section, Integer> getApplied() {
        return applied;
    }

    public void setApplied(HashMap<Section, Integer> applied) {
        this.applied = applied;
    }

    public HashMap<Section, Integer> getPractical() {
        return practical;
    }

    public void setPractical(HashMap<Section, Integer> practical) {
        this.practical = practical;
    }

}