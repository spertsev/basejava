/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int sizeOfFilledStorage = 0;

    void clear() {
        for (int i = 0; i < sizeOfFilledStorage; i++) {
            storage[i] = null;
        }
        sizeOfFilledStorage = 0;
    }

    void save(Resume resume) {
        if (get(resume.uuid) != null) {
            System.out.println("A resume with the same uuid already exists in the storage");
            return;
        }
        storage[sizeOfFilledStorage] = resume;
        sizeOfFilledStorage++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < sizeOfFilledStorage; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        if (get(uuid) == null) {
            System.out.println("There is no resume with this uuid in the storage");
        } else {
            for (int i = 0; i < sizeOfFilledStorage; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    storage[i] = storage[sizeOfFilledStorage - 1];
                    storage[sizeOfFilledStorage - 1] = null;
                    sizeOfFilledStorage--;
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] filledStorage = new Resume[sizeOfFilledStorage];
        for (int i = 0; i < sizeOfFilledStorage; i++) {
            filledStorage[i] = storage[i];
        }
        return filledStorage;
    }

    int size() {
        return sizeOfFilledStorage;
    }
}
