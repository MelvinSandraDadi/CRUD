package uas.melvin;

import java.util.ArrayList;
import java.util.List;

public class PersonService {
    private List<Person> daftarPerson = new ArrayList<>();
    private int nextId = 1;

    public void tambahPerson(String nama, String alamat) {
        Person person = new Person(nextId++, nama, alamat);
        daftarPerson.add(person);
    }

    public List<Person> getAll() { return daftarPerson; }

    public Person cariById(int id) {
        return daftarPerson.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public boolean updatePerson(int id, String namaBaru, String alamatBaru) {
        Person person = cariById(id);
        if (person != null) {
            person.setNama(namaBaru);
            person.setAlamat(alamatBaru);
            return true;
        }
        return false;
    }

    public boolean hapusPerson(int id) {
        Person person = cariById(id);
        return person != null && daftarPerson.remove(person);
    }
}
