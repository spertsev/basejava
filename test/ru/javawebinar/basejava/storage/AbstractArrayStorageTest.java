package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private final static String UUID_1 = "uuid1";
    private final static String UUID_2 = "uuid2";
    private final static String UUID_3 = "uuid3";
    private final static String UUID_4 = "uuid4";

    private final static Resume firstResume = new Resume(UUID_1);
    private final static Resume secondResume = new Resume(UUID_2);
    private final static Resume thirdResume = new Resume(UUID_3);
    private final static Resume fourthResume = new Resume(UUID_4);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp(){
        storage.clear();
        storage.save(firstResume);
        storage.save(secondResume);
        storage.save(thirdResume);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume r = new Resume(UUID_1);
        storage.update(r);
        Assert.assertEquals(r, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void getAll() {
        Resume[] normArray = {firstResume, secondResume, thirdResume};
        Assert.assertArrayEquals(normArray, storage.getAll());
    }

    @Test
    public void save() {
        storage.save(fourthResume);
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void saveWithOverflow() {
        int storageLimit = 10;  //must be the same as value of AbstractArrayStorage.STORAGE_LIMIT
        int storageFreeSpace = storageLimit - storage.size();
        String currentUUID;
        Resume currentResume;
        for (int i = 0; i < storageFreeSpace; i++) {
            currentUUID = String.valueOf(i);
            currentResume = new Resume(currentUUID);
            try {
                storage.save(currentResume);
            } catch (StorageException e) {
                Assert.fail("StorageException during filling the storage");
            }
        }
        storage.save(new Resume("uuid_for_overflow"));
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void get() {
        Assert.assertEquals(UUID_1, storage.get(UUID_1).getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}