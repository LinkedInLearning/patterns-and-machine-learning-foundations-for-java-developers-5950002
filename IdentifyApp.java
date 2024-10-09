package DogIdentifier;

import javax.imageio.ImageIO;
import javax.visrec.ml.classification.ImageClassifier;
import javax.visrec.ml.classification.NeuralNetImageClassifier;
import javax.visrec.ml.model.ModelCreationException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public class IdentifyApp {
    private static String configFile = "training_data/id.properties";

       public static void main(String[] args) throws ModelCreationException, IOException {

           Properties prop = IdentifyApp.getConfigProperties();
           String imagesDir = prop.getProperty("id.imagesDir", "training_data/DogImages2");
           String modelFile = imagesDir + "/" + prop.getProperty("id.modelFile", "model-chihuahua.dnet");
           System.out.println("imagesDir: " + imagesDir);
           System.out.println("modelFile: " + modelFile);

           var cl = NeuralNetImageClassifier.builder()
                   .inputClass(BufferedImage.class)
                   .imageHeight(64)
                   .imageWidth(64)
                   .importModel(Paths.get(modelFile))       // The model was trained via Train.java
                   .build();                                // Create the classifier

           StopWatch sw = new StopWatch();
           sw.start();
           System.out.println("Predictions:");

           System.out.println(classifyImage(cl, imagesDir + "/" + "chi-1.jpg"));        // dog
           System.out.println(classifyImage(cl, imagesDir + "/" + "chi-2.jpg"));        // dog
           System.out.println(classifyImage(cl, imagesDir + "/" + "chi-3.jpg"));        // dog
           System.out.println(classifyImage(cl, imagesDir + "/" + "pizza.jpg"));        // pizza
           System.out.println(classifyImage(cl, imagesDir + "/" + "cow.jpg"));          // cow

           sw.stop();
           System.out.println("Classification completed in " + sw.getElapsedTimeSecs() + " secs ====================================");
       }

       private static Map<String, Float> classifyImage(ImageClassifier<BufferedImage> imc, String fname) throws IOException {
           File f = new File(fname);
           BufferedImage image = ImageIO.read(f);

           System.out.println("File: " + f.getAbsolutePath());

           Map<String, Float> results = imc.classify(image);
           return results;
       }

       private static Properties getConfigProperties() throws IOException {
               Properties prop = new Properties();
               InputStream in = new FileInputStream(configFile);

               prop.load(in);

               for (Enumeration e = prop.propertyNames(); e.hasMoreElements();) {
                   String key = e.nextElement().toString();
                   System.out.println(key + " = " + prop.getProperty(key));
               }
               return prop;
           }
}
