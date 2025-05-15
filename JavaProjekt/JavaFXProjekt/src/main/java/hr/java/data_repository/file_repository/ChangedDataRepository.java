package hr.java.data_repository.file_repository;

import hr.java.entity.Entity;
import hr.java.entity.EntityChange;
import hr.java.exception.FileException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.javafxprojekt.main.Main.logger;

/**
 * The ChangedDataRepository class provides methods for saving and reading
 * changed data to and from a binary file.
 */
public class ChangedDataRepository {

    private ChangedDataRepository() {
    }

    /**
     * Saves a list of changed data to a binary file.
     *
     * @param changes the list of {@link EntityChange} objects to be saved
     * @throws FileException if an error occurs while saving the data
     */
    public static void saveChangedDataToBinaryFile(List<EntityChange<Entity>> changes) {
        try (ObjectOutputStream bos = new ObjectOutputStream(new FileOutputStream("data/changedData.bin"))) {
            bos.writeObject(changes);
        } catch (IOException e) {
            logger.error("Could not save changed data to Binary file!");
            throw new FileException("Could not save changed data to binary file!");
        }
    }

    /**
     * Reads changed data from a binary file.
     *
     * @return a list of {@link EntityChange} objects read from the file;
     * returns an empty list if the file does not exist or is empty
     * @throws FileException if an error occurs while reading the data
     */
    public static List<EntityChange<Entity>> readChangedDataFromBinaryFile() {
        File file = new File("data/changedData.bin");

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            List<EntityChange<Entity>> changes = (List<EntityChange<Entity>>) ois.readObject();
            return new ArrayList<>(changes);
        } catch (Exception e) {
            logger.error("Could not read changed data from binary file!");
            throw new FileException("Could not read changed data from binary file!");
        }
    }

    /**
     * Adds a single entity change to the binary file.
     *
     * @param change the {@link EntityChange} object to be added
     * @throws FileException if an error occurs while saving the data
     */
    public static void changeSingleEntity(EntityChange<Entity> change) {
        List<EntityChange<Entity>> changes = readChangedDataFromBinaryFile();
        changes.add(change);
        saveChangedDataToBinaryFile(changes);
    }
}

