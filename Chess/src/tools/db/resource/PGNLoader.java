package tools.db.resource;

import game.core.Game;
import oracle.jdbc.proxy.annotation.Pre;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Класс для загрузки в базу данных файлов в формате PGN.
 */
public class PGNLoader {
    final private File pgnFile;

    PGNLoader(File pgnFile) {
        this.pgnFile = pgnFile;
    }

    public static void writePGNtoDb(File pgnFile) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:gamesnotebook");
        PreparedStatement statement = connection.prepareStatement("insert into GAMESNOTEBOOK.PUBLIC.GAMES" +
                "(event, site, game_date, round, white, black, result, nic, moves) " +
                "values(?,?,?,?,?,?,?,?,?)");
        List<String> pgn = readFile(pgnFile);
        List<GameEntity> games = pgn.stream().map(PGNLoader::createEntity).collect(Collectors.toList());
        games.forEach(game -> {
            int i = 0;
            try {
                statement.setString(++i, game.getEvent());
                statement.setString(++i, game.getSite());
                statement.setString(++i, game.getDate());
                statement.setString(++i, game.getRound());
                statement.setString(++i, game.getWhite());
                statement.setString(++i, game.getBlack());
                statement.setString(++i, game.getResult());
                statement.setString(++i, game.getNic());
                statement.setString(++i, game.getMoves());
                statement.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        statement.executeBatch();
    }


    private static List<String> readFile(File pgnFile) {
        StringBuilder sb = new StringBuilder();
        List<String> games = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(pgnFile);

            try (BufferedReader reader = new BufferedReader(fileReader)) {
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.length() == 0) {
                        if (sb.length() != 0) {
                            if (sb.indexOf("[") == -1) {
                                String prev = games.get(games.size() - 1);
                                games.set(games.size() - 1, prev + "moves:" + (sb.toString()));
                            } else games.add(sb.toString());
                        }
                        sb = new StringBuilder();
                    } else {
                        sb.append(line);
                        sb.append("\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return games;

    }

    private static GameEntity createEntity(String game) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setEvent(findTagValue(game, "\\[Event.*\\]"));
        gameEntity.setSite(findTagValue(game, "\\[Site.*\\]"));
        gameEntity.setDate(findTagValue(game, "\\[Date.*\\]"));
        gameEntity.setRound(findTagValue(game, "\\[Round.*\\]"));
        gameEntity.setWhite(findTagValue(game, "\\[White.*\\]"));
        gameEntity.setBlack(findTagValue(game, "\\[Black.*\\]"));
        gameEntity.setResult(findTagValue(game, "\\[Result.*\\]"));
        gameEntity.setNic(findTagValue(game, "\\[NIC.*\\]"));
        gameEntity.setMoves(findMoves(game));

        return gameEntity;
    }

    private static String findTagValue(String game, String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(game);
        if (matcher.find()) {
            String tag = matcher.group();
            return tag.substring(tag.indexOf('"') + 1, tag.lastIndexOf('"'));
        }
        return "";
    }

    private static String findMoves(String game) {
        Pattern pattern = Pattern.compile("moves:(.*)");
        Matcher matcher = pattern.matcher(game);
        if (matcher.find()) {
            return matcher.group().replace("moves:", "");
        }
        return "";
    }
}
