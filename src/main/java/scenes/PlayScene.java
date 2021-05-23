package scenes;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import xmlmodels.Maze;

import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class PlayScene {

    private Scene scene;

    public static Maze maze;

    public PlayScene(String levelName) throws IOException, SAXException, ParserConfigurationException, JAXBException {
        var jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
                .createContext(new Class[]{Maze.class}, null);
        Unmarshaller jaxbUnmarshall = jaxbContext.createUnmarshaller();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new File( "src/main/resources/mazes/" + levelName + ".xml"));

        maze = (Maze) jaxbUnmarshall.unmarshal(document);
        scene = FXMLLoader.load(getClass().getResource("..\\views\\play.fxml"));
    }

    public Scene getScene() {
        return scene;
    }
}
