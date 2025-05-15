package hr.java.thread_managmenet;

import hr.java.entity.Agent;
import hr.java.data_repository.database_repository.AgentDatabase;
import hr.java.data_repository.database_repository.TicketDatabase;
import hr.java.data_repository.file_repository.ResolutionLogRepository;
import hr.java.entity.ResolutionLogEntry;
import hr.java.entity.Ticket;
import hr.java.exception.TicketManagerException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.example.javafxprojekt.main.Main.logger;

/**
 * The TicketManager class is responsible for managing the assignment and resolution of tickets.
 * It runs two tasks in parallel:
 * - Assigning unassigned tickets to agents.
 * - Resolving in-progress tickets and logging the resolutions.
 * This class implements the Runnable interface and performs the tasks in separate threads.
 */
public class TicketManager implements Runnable {
    private boolean running = false;

    /**
     * Starts the ticket assignment and resolution tasks in separate threads.
     * The method will start two threads to manage the ticket assignments and resolutions
     */
    @Override
    public void run() {
        Thread assignTicketThread = new Thread(this::assignTicketsTask);
        Thread resolveTicketThread = new Thread(this::resolveTicketsTask);

        assignTicketThread.start();
        resolveTicketThread.start();

        try {
            assignTicketThread.join();
            resolveTicketThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error while waiting for threads to finish - ", e);
        }
    }

    /**
     * The task for assigning unassigned tickets to available agents.
     * This method assigns tickets based on priority and logs the assignment in the resolution log.
     * It runs in a separate thread.
     */
    private synchronized void assignTicketsTask() {
        while (running) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new TicketManagerException("Thread interrupted during ticket assignment", e);
            }
        }
        running = true;
        List<Ticket> unassignedTickets = TicketDatabase.getUnassignedTickets();
        unassignedTickets.sort(Comparator.comparing(Ticket::getTicketPriority));
        List<Agent> agents = AgentDatabase.getAllAgents();

        if (!unassignedTickets.isEmpty() && !agents.isEmpty()) {
            try {
                for (int i = 0; i < unassignedTickets.size(); i++) {
                    Ticket ticket = unassignedTickets.get(i);
                    Agent agent = agents.get(i % agents.size());
                    AgentDatabase.assignTicketToAgent(ticket.getId(), agent.getId());
                    ResolutionLogEntry<Ticket, Agent> resolutionLogEntry = new ResolutionLogEntry<>(ticket, agent, LocalDateTime.now());
                    ResolutionLogRepository.assignTicketResolutionLog(resolutionLogEntry);
                    wait(2000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new TicketManagerException("Thread interrupted during ticket assignment", e);
            } finally {
                running = false;
                notifyAll();
            }
        }
    }

    /**
     * The task for resolving in-progress tickets and logging the resolution.
     * This method processes tickets that are currently in progress and resolves them.
     * It also records the resolution in the resolution log.
     * It runs in a separate thread.
     */
    private synchronized void resolveTicketsTask() {
        while (running) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new TicketManagerException("Thread interrupted during ticket resolution", e);
            }
        }
        running = true;
        List<Ticket> inProgressTickets = TicketDatabase.getInProgressTickets();
        inProgressTickets.sort(Comparator.comparing(Ticket::getTicketPriority));

        if (!inProgressTickets.isEmpty()) {
            try {
                for (int i = 0; i < inProgressTickets.size(); i++) {
                    Ticket ticket = inProgressTickets.get(i);
                    TicketDatabase.resolveTicket(ticket);
                    Agent agent = AgentDatabase.getAgentByTicketID(ticket.getId());
                    ResolutionLogEntry<Ticket, Agent> resolutionLogEntry = new ResolutionLogEntry<>(ticket, agent, LocalDateTime.now());
                    ResolutionLogRepository.resolveTicketResolutionLog(resolutionLogEntry);
                    wait(2000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new TicketManagerException("Thread interrupted during ticket resolution", e);
            } finally {
                running = false;
                notifyAll();
            }
        }
    }
}
