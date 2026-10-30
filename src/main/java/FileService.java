import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileService {

    public MyArrayList loadPlayers(String fileName) {
        MyArrayList players = new MyArrayList();

        try (BufferedReader reader =
                     Files.newBufferedReader(Path.of(fileName))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                String[] fields = line.split(",", -1);

                if (fields.length != 6) {
                    System.out.println(
                            "Строка " + lineNumber
                                    + " пропущена: должно быть 6 полей.");
                    continue;
                }

                String name = fields[0].trim();
                String club = fields[2].trim();
                String nationality = fields[3].trim();
                String position = fields[4].trim();

                if (!containsLetter(name)
                        || !containsLetter(club)
                        || !containsLetter(nationality)
                        || !containsLetter(position)) {
                    System.out.println(
                            "Строка " + lineNumber
                                    + " пропущена: проверьте текстовые поля.");
                    continue;
                }

                try {
                    int age = Integer.parseInt(fields[1].trim());
                    double marketValue =
                            Double.parseDouble(fields[5].trim());

                    if (age <= 0) {
                        System.out.println(
                                "Строка " + lineNumber
                                        + " пропущена: возраст должен быть "
                                        + "больше нуля.");
                        continue;
                    }

                    if (!Double.isFinite(marketValue)
                            || marketValue < 0) {
                        System.out.println(
                                "Строка " + lineNumber
                                        + " пропущена: неверная стоимость.");
                        continue;
                    }

                    players.add(new Player(
                            name,
                            age,
                            club,
                            nationality,
                            position,
                            marketValue
                    ));

                } catch (NumberFormatException e) {
                    System.out.println(
                            "Строка " + lineNumber
                                    + " пропущена: неверный числовой формат.");
                }
            }

        } catch (IOException e) {
            System.out.println(
                    "Ошибка чтения файла: " + e.getMessage());
        }

        return players;
    }

    public boolean savePlayers(
            MyArrayList players, String fileName) {

        try (BufferedWriter writer =
                     Files.newBufferedWriter(Path.of(fileName))) {

            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                writer.write(
                        player.getName() + ","
                                + player.getAge() + ","
                                + player.getClub() + ","
                                + player.getNationality() + ","
                                + player.getPosition() + ","
                                + player.getMarketValue()
                );

                writer.newLine();
            }

            return true;

        } catch (IOException e) {
            System.out.println(
                    "Ошибка сохранения файла: " + e.getMessage());
            return false;
        }
    }

    private boolean containsLetter(String text) {
        return text != null
                && !text.isBlank()
                && text.matches(".*\\p{L}.*");
    }
}
