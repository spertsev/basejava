package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void addToStorage(int binarySearchIndex, Resume resumeForAdd) {
        int index = -binarySearchIndex - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resumeForAdd;
    }

    protected void moveElementsForDelete(int index) {
        int lengthOfMovePart = size - index;
        if (size == storage.length) {
            lengthOfMovePart--;
        }
        System.arraycopy(storage, index + 1, storage, index, lengthOfMovePart);
    }

}