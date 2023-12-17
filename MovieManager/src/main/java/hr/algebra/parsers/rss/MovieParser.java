/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.parsers.rss;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Movie;
import hr.algebra.utilities.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author branimir.maric
 */
public class MovieParser {

    private static final String RSS_URL = "https://www.blitz-cinestar-bh.ba/rss.aspx?id=2682";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    private static final String REPLACEMENT = "<[^>]+>";

    private MovieParser() {
    }

    public static List<Movie> parse() throws IOException, XMLStreamException {
        List<Movie> movies = new ArrayList<>();

        HttpURLConnection con = UrlConnectionFactory
                .getHttpUrlConnection(RSS_URL);

        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);

            Optional<TagType> tagType = Optional.empty();
            Movie movie = null;
            StartElement startElement = null;

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();

                        tagType = TagType.from(qName);
                        if (tagType.isPresent() && tagType.get().equals(TagType.ITEM)) {
                            movie = new Movie();
                            movies.add(movie);
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if (tagType.isPresent() && movie != null) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();

                            switch (tagType.get()) {
                                case TITLE:
                                    if (!data.isEmpty()) {
                                        movie.setTitle(data);
                                    }
                                    break;
                                case PUBLISHED_DATE:
                                    if (!data.isEmpty()) {
                                        movie.setPublishedDate(
                                                LocalDateTime.parse(
                                                        data,
                                                        Movie.DATE_FORMATTER
                                                )
                                        );
                                    }
                                    break;
                                case DESCRIPTION:
                                    if (!data.isEmpty()) {
                                        data = data.replaceAll(REPLACEMENT, "");
                                        movie.setDescription(data);
                                    }
                                    break;
                                case ORGINAL_TITLE:
                                    if (!data.isEmpty()) {
                                        movie.setOrginalTitle(data);
                                    }
                                    break;
                                case DURATION:
                                    if (!data.isEmpty()) {
                                        movie.setDuration(Integer.parseInt(data));
                                    }
                                    break;
                                case RELEASE_YEAR:
                                    if (!data.isEmpty()) {
                                        movie.setReleaseYear(Integer.parseInt(data));
                                    }
                                    break;
                                case GENRE:
                                    if (!data.isEmpty()) {
                                        movie.setGenre(data);
                                    }
                                    break;
                                case PICTURE_PATH:
                                    if (startElement != null && movie.getPicturePath() == null) {
                                        if (data != null) {
                                            handlePicture(movie, data);
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        }

        return movies;    
    }

    private static void handlePicture(Movie movie, String url) throws IOException {

        String ext = url.substring(url.lastIndexOf("."));
        if (ext.length() > 4) {
            ext = EXT;
        }
        String name = UUID.randomUUID() + ext;
        String localPath = DIR + File.separator + name;

        FileUtils.copyFrom(url, localPath);

        movie.setPicturePath(localPath);
    }

    private enum TagType {
        ITEM("item"),
        TITLE("title"),
        PUBLISHED_DATE("pubDate"),
        DESCRIPTION("description"),
        ORGINAL_TITLE("orignaziv"),
        DURATION("trajanje"),
        RELEASE_YEAR("godina"),
        GENRE("zanr"),
        PICTURE_PATH("plakat");

        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }

    }

}
