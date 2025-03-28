package ro.mpp2024.laborator4.model;

public interface Identifiable<Tid> {
    Tid getID();
    void setID(Tid id);
}
