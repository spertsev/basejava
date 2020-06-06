package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    public void save(Resume resume) {
        if (storage.length == size) {
            System.out.println("The storage is full, we can't add any more");
            return;
        }
        int resumeIndex = getResumeIndex(resume.getUuid());
        if (resumeIndex != -1) {
            System.out.println("A resume with the same uuid already exists in the storage");
            return;
        }
        storage[size] = resume;
        size++;
    }

    public void update(Resume resume) throws IOException {
        int resumeIndex = getResumeIndex(resume.getUuid());
        if (resumeIndex == -1) {
            System.out.println("There is no resume with this uuid in the storage");
            return;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите uuid для обновления: ");
        String uuidForUpdate = reader.readLine().trim().toLowerCase();
        Resume resumeForUpdate = new Resume();
        resumeForUpdate.setUuid(uuidForUpdate);
        storage[resumeIndex] = resumeForUpdate;
    }

    public Resume get(String uuid) {
        int resumeIndex = getResumeIndex(uuid);
        if (resumeIndex == -1) {
            System.out.println("There is no resume with this uuid in the storage");
            return null;
        }
        return storage[resumeIndex];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] filledStorage = new Resume[size];
        if (size != 0) {
            filledStorage = Arrays.copyOfRange(storage, 0, size);
        }
        return filledStorage;
    }

    public void delete(String uuid) {
        int resumeIndex = getResumeIndex(uuid);
        if (resumeIndex == -1) {
            System.out.println("There is no resume with this uuid in the storage");
            return;
        }
        storage[resumeIndex] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    public void clear() {
        if (size == 0) {
            return;
        }
        Arrays.fill(storage, 0, size - 1, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    private int getResumeIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
