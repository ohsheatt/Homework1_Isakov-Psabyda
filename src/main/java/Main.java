import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FileService fileService = new FileService();
        List<Player> players =
                fileService.loadPlayers("data/players.csv");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n FOOTBALL PLAYERS");
            System.out.println("1. Показать всех футболистов");
            System.out.println("2. Добавить футболиста");
            System.out.println("3. Найти футболистов по клубу");
            System.out.println("4. Футболисты дороже заданной суммы");
            System.out.println("5. Сортировать по рыночной стоимости");
            System.out.println("6. Показать имена футболистов по клубу");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (players.isEmpty()) {
                        System.out.println("Список футболистов пуст.");
                    } else {
                        players.forEach(System.out::println);
                    }
                    break;

                case "2":
                    Player newPlayer = createPlayer(scanner);

                    players.add(newPlayer);

                    boolean saved = fileService.savePlayers(
                            players, "data/players.csv");

                    if (saved) {
                        System.out.println(
                                "Футболист добавлен и сохранён!");
                    } else {
                        players.remove(newPlayer);
                        System.out.println(
                                "Не удалось сохранить футболиста.");
                    }
                    break;

                case "3":
                    System.out.print("Введите название клуба: ");
                    String club = scanner.nextLine().trim();

                    List<Player> byClub = players.stream()
                            .filter(player -> player.getClub()
                                    .equalsIgnoreCase(club))
                            .toList();

                    if (byClub.isEmpty()) {
                        System.out.println("Футболисты не найдены.");
                    } else {
                        byClub.forEach(System.out::println);
                    }
                    break;

                case "4":
                    double minValue = readNonNegativeDouble(
                            scanner,
                            "Минимальная стоимость (млн евро): ");

                    players.stream()
                            .filter(player ->
                                    player.getMarketValue() > minValue)
                            .forEach(System.out::println);
                    break;

                case "5":
                    players.stream()
                            .sorted((p1, p2) ->
                                    Double.compare(
                                            p1.getMarketValue(),
                                            p2.getMarketValue()))
                            .forEach(System.out::println);
                    break;

                case "6":
                    System.out.print("Введите название клуба: ");
                    String selectedClub = scanner.nextLine().trim();

                    List<String> names = players.stream()
                            .filter(player -> player.getClub()
                                    .equalsIgnoreCase(selectedClub))
                            .map(Player::getName)
                            .toList();

                    if (names.isEmpty()) {
                        System.out.println("Футболисты не найдены.");
                    } else {
                        names.forEach(System.out::println);
                    }
                    break;

                case "0":
                    running = false;
                    System.out.println("Программа завершена.");
                    break;

                default:
                    System.out.println(
                            "Неизвестная команда. Попробуйте ещё раз.");
            }
        }

        scanner.close();
    }

    private static Player createPlayer(Scanner scanner) {
        String name = readTextField(
                scanner, "Имя футболиста: ");
        int age = readPositiveInt(
                scanner, "Возраст: ");
        String club = readTextField(
                scanner, "Клуб: ");
        String nationality = readTextField(
                scanner, "Национальность: ");
        String position = readTextField(
                scanner, "Позиция: ");
        double marketValue = readNonNegativeDouble(
                scanner, "Рыночная стоимость (млн евро): ");

        return new Player(
                name, age, club, nationality, position, marketValue);
    }

    private static String readTextField(
            Scanner scanner, String prompt) {

        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (containsLetter(value)) {
                return value;
            }

            System.out.println(
                    "Ошибка: поле должно содержать хотя бы одну букву.");
        }
    }

    private static boolean containsLetter(String text) {
        return text != null
                && !text.isBlank()
                && text.matches(".*\\p{L}.*");
    }

    private static int readPositiveInt(
            Scanner scanner, String prompt) {

        while (true) {
            System.out.print(prompt);

            try {
                int value = Integer.parseInt(
                        scanner.nextLine().trim());

                if (value > 0) {
                    return value;
                }

                System.out.println(
                        "Ошибка: возраст должен быть больше нуля.");

            } catch (NumberFormatException e) {
                System.out.println(
                        "Ошибка: введите возраст целым числом.");
            }
        }
    }

    private static double readNonNegativeDouble(
            Scanner scanner, String prompt) {

        while (true) {
            System.out.print(prompt);

            try {
                double value = Double.parseDouble(
                        scanner.nextLine().trim());

                if (Double.isFinite(value) && value >= 0) {
                    return value;
                }

                System.out.println(
                        "Ошибка: введите конечное "
                                + "неотрицательное число.");

            } catch (NumberFormatException e) {
                System.out.println(
                        "Ошибка: введите стоимость числом.");
            }
        }
    }
}
