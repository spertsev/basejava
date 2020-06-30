package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void addToStorage(Resume r) {
        int binarySearchIndex = getIndex(r.getUuid());
        int targetIndex = -binarySearchIndex - 1;
        System.arraycopy(storage, targetIndex, storage, targetIndex + 1, size - targetIndex);
        storage[targetIndex] = r;
        size++;
    }

    protected boolean isResumeExisted(Resume r) {
        return getIndex(r.getUuid()) >= 0;
    }

    protected void deleteFromStorage(String uuid) {
        int binarySearchIndex = getIndex(uuid);
        System.arraycopy(storage, binarySearchIndex + 1, storage, binarySearchIndex, size - binarySearchIndex);
        size--;
    }

}