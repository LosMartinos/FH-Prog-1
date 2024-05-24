package at.ac.fhcampuswien.fhmdb;

public interface Observable {

    public void addObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers(String message);
}
