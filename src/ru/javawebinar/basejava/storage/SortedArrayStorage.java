package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    public void save(Resume r) {
        int binarySearchIndex = getIndex(r.getUuid());
        if (binarySearchIndex >= 0) {
            System.out.println("Resume " + r.getUuid() + " already exist");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else {
            int targetIndex = -binarySearchIndex - 1;
            System.arraycopy(storage, targetIndex, storage, targetIndex + 1, size - targetIndex);
            storage[targetIndex] = r;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int binarySearchIndex = getIndex(uuid);
        if (binarySearchIndex < 0) {
            System.out.println("Resume " + uuid + " not exist");
        } else {
            System.arraycopy(storage, binarySearchIndex + 1, storage, binarySearchIndex, size - binarySearchIndex);
            size--;
        }
    }
}