package com.phonebook.spring;

import com.phonebook.main.InMemoryRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

import static java.lang.String.format;

/**
 * Keeps phoneBook data in memory in ordered in accordance to addition.
 */
@Repository
public class InMemoryRepositoryIml implements InMemoryRepository {

    private Map<String, Set<String>> data;

    /**
     * no args constructor
     */
    public InMemoryRepositoryIml() {
        // LinkedHashMap is chosen because usually iteration order matters
        this(new LinkedHashMap<>());
    }

    /**
     * this constructor allows to inject initial data to the repository
     *
     * @param data
     */
    public InMemoryRepositoryIml(Map<String, Set<String>> data) {
        this.data = new LinkedHashMap<>(data);
    }

    @Override
    public Map<String, Set<String>> findAll() {
        return new LinkedHashMap<>(this.data);
    }

    @Override
    public Set<String> findAllPhonesByName(String name) {
        return this.data.get(name);
    }

    @Override
    public String findNameByPhone(String phone) {
        String name = "";
        for (Map.Entry<String, Set<String>> map : this.data.entrySet()) {
            for (String p : map.getValue()) {
                if (p.equals(phone))
                    name = map.getKey();
            }
        }
        return name;
    }

    @Override
    public void addPhone(String name, String phone) {
        if (this.data.containsKey(name)) {
            Set<String> setOfPhones = this.data.get(name);
            setOfPhones.add(phone);
            this.data.replace(name, setOfPhones);
        } else this.data.put(name, new HashSet<>(Collections.singletonList(phone)));
    }

    @Override
    public void removePhone(String phone) throws IllegalArgumentException {
        String nameByPhone = findNameByPhone(phone);
        Set<String> setOfPhones = this.data.get(nameByPhone);
        if (setOfPhones != null) {
            setOfPhones.remove(phone);
        } else throw new IllegalArgumentException(format("There is no such phone %s in repo", phone));
        if (setOfPhones.isEmpty()) {
            this.data.remove(nameByPhone);
        } else this.data.replace(nameByPhone, setOfPhones);
    }
}
