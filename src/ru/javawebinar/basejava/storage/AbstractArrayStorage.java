package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected abstract int getIndex(String uuid);

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            System.out.println("Resume " + r.getUuid() + " not exist");
        } else {
            storage[index] = r;
        }
    }

    protected abstract void addToStorage(Resume r);

    protected abstract boolean isResumeExisted(Resume r);

    public void save(Resume r) {
        if (isResumeExisted(r)) {
            System.out.println("Resume " + r.getUuid() + " already exist");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else {
            addToStorage(r);
        }
    }

    protected abstract void deleteFromStorage(String uuid);

    public void delete(String uuid) {
        Resume searchResume = new Resume();
        searchResume.setUuid(uuid);
        if (!isResumeExisted(searchResume)) {
            System.out.println("Resume " + uuid + " not exist");
        } else {
            deleteFromStorage(uuid);
        }
    }

}