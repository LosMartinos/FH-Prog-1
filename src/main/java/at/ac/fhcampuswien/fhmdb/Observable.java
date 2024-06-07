package at.ac.fhcampuswien.fhmdb;

public interface Observable {

    public void addObserver(Observer o, String event);
    public void removeObserver(Observer o);
    public void notifyObservers(String message, String event);
}
