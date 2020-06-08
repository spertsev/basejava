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
        String resumeUuid = resume.getUuid();
        int resumeIndex = getResumeIndex(resumeUuid);
        if (resumeIndex != -1) {
            System.out.printf("A resume with uuid '%s' already exists in the storage \n", resumeUuid);
            return;
        }
        storage[size] = resume;
        size++;
    }

    public void update(Resume resume) throws IOException {
        String resumeUuid = resume.getUuid();
        int resumeIndex = getResumeIndex(resumeUuid);
        if (resumeIndex == -1) {
            System.out.printf("There is no resume with uuid '%s' in the storage \n", resumeUuid);
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
            System.out.printf("There is no resume with uuid '%s' in the storage \n", uuid);
            return null;
        }
        return storage[resumeIndex];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void delete(String uuid) {
        int resumeIndex = getResumeIndex(uuid);
        if (resumeIndex == -1) {
            System.out.printf("There is no resume with uuid '%s' in the storage \n", uuid);
            return;
        }
        storage[resumeIndex] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
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
