package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    protected void addToStorage(int foundIndex, Resume resumeForAdd) {
        storage[size] = resumeForAdd;
    }

    protected void moveElementsForDelete(int index) {
        storage[index] = storage[size - 1];
    }

}
