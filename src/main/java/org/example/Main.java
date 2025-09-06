package org.example;

import org.example.application.EarnPoints;
import org.example.application.RedeemPoints;
import org.example.domain.model.CustomerId;
import org.example.infra.ConsoleEventPublisher;
import org.example.infra.InMemoryPointsAccountRepository;

import java.util.Scanner;

public final class Main {
    private final InMemoryPointsAccountRepository repo = new InMemoryPointsAccountRepository();
    private final ConsoleEventPublisher publisher = new ConsoleEventPublisher();
    private final EarnPoints earn = new EarnPoints(repo);
    private final RedeemPoints redeem = new RedeemPoints(repo, publisher);

    public static void main(String[] args) {
        new Main().run(args);
    }

    void run(String[] args) {
        if (args.length == 0 || "interactive".equalsIgnoreCase(args[0])) {
            interactive();
            return;
        }
        int status = runSingle(args);
        if (status != 0) System.exit(status);
    }

    private int runSingle(String[] args) {
        if (args.length < 1) {
            printHelp();
            return 1;
        }
        String cmd = args[0];
        try {
            return switch (cmd) {
                case "earn" -> handleEarn(args);
                case "redeem" -> handleRedeem(args);
                case "balance" -> handleBalance(args);
                default -> { printHelp(); yield 1; }
            };
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 1;
        }
    }

    private int handleEarn(String[] args) {
        if (args.length != 3) { printHelp(); return 1; }
        String id = args[1];
        int points = parsePositiveInt(args[2]);
        int balance = earn.execute(id, points).balance();
        System.out.println("OK: Balance for " + id + " is now " + balance + " points.");
        return 0;
    }

    private int handleRedeem(String[] args) {
        if (args.length != 3) { printHelp(); return 1; }
        String id = args[1];
        int points = parsePositiveInt(args[2]);
        int balance = redeem.execute(id, points).balance();
        System.out.println("OK: Balance for " + id + " is now " + balance + " points.");
        return 0;
    }

    private int handleBalance(String[] args) {
        if (args.length != 2) { printHelp(); return 1; }
        String id = args[1];
        int balance = repo.findById(CustomerId.of(id))
                .map(acc -> acc.getBalance())
                .orElse(0);
        System.out.println("Balance for " + id + ": " + balance + " points.");
        return 0;
    }

    private void interactive() {
        System.out.println("Interactive mode. Type 'earn <id> <points>', 'redeem <id> <points>', 'balance <id>' or 'exit'.");
        Scanner sc = new Scanner(System.in);
        System.out.print("> ");
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("exit")) break;
            if (!line.isEmpty()) runSingle(line.split("\\s+"));
            System.out.print("> ");
        }
    }

    private int parsePositiveInt(String s) {
        try {
            int n = Integer.parseInt(s);
            if (n <= 0) throw new NumberFormatException();
            return n;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("<points> must be a positive integer");
        }
    }

    private void printHelp() {
        System.out.println("""
          Usage:
            java -jar loyalty-points-1.0.0-shaded.jar earn <customerId> <points>
            java -jar loyalty-points-1.0.0-shaded.jar redeem <customerId> <points>
            java -jar loyalty-points-1.0.0-shaded.jar balance <customerId>

          Interactive: run without args and type earn/redeem/balance/exit.
          """);
    }
}
