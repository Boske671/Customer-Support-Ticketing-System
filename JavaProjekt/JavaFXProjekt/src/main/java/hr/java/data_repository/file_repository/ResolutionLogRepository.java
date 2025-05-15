package hr.java.data_repository.file_repository;

import hr.java.entity.Agent;
import hr.java.file_paths.FilePath;
import hr.java.entity.ResolutionLogEntry;
import hr.java.entity.Ticket;
import hr.java.exception.FileException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import static org.example.javafxprojekt.main.Main.logger;

/**
 * The class ResolutionLogRepository used for writing assigned and resolved
 * log entries into a txt file
 */
public class ResolutionLogRepository
{
    private ResolutionLogRepository() {}
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");

    /**
     * Appends the resolution log to a txt file
     *
     * @param resolutionLogEntry the resolution log entry
     * @throws FileException if there is an error writing to the file
     */
    public static void assignTicketResolutionLog(ResolutionLogEntry<Ticket, Agent> resolutionLogEntry)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FilePath.RESOLUTION_LOG.getPath(), true)))
        {
            String formattedDateTime = resolutionLogEntry.getDateTime().format(formatter);

            bw.write("Ticket (ID: " + resolutionLogEntry.getFirstEntity().getId() + ") has been ASSIGNED to agent (ID: "
                    + resolutionLogEntry.getSecondEntity().getId() + ") at " + formattedDateTime);
            bw.newLine();
        } catch (IOException e)
        {
            logger.error("Could not write resolution log to file!");
            throw new FileException("Error writing resolution log to file");
        }
    }

    /**
     * Appends the resolution log to a txt file
     *
     * @param resolutionLogEntry the resolution log entry
     * @throws FileException if there is an error writing to the file
     */
    public static void resolveTicketResolutionLog(ResolutionLogEntry<Ticket, Agent> resolutionLogEntry)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FilePath.RESOLUTION_LOG.getPath(), true)))
        {
            String formattedDateTime = resolutionLogEntry.getDateTime().format(formatter);

            bw.write("Ticket (ID: " + resolutionLogEntry.getFirstEntity().getId() + ") has been RESOLVED by agent (ID: "
                    + resolutionLogEntry.getSecondEntity().getId() + ") at " + formattedDateTime);
            bw.newLine();
        } catch (IOException e)
        {
            logger.error("Could not write resolve ticket resolution log to file!");
            throw new FileException("Error writing resolution log to file");
        }
    }
}
