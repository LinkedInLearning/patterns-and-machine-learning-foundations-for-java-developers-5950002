package DogIdentifier;

import javax.visrec.ml.classification.ImageClassifier;
import javax.visrec.ml.classification.NeuralNetImageClassifier;
import javax.visrec.ml.model.ModelCreationException;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;


public class Train {
    public static void main(String[] args) {

        String iDir = "training_data/DogImages2/train";
        StopWatch sw = new StopWatch();

        sw.start();
        try {
            ImageClassifier<BufferedImage> classifier = NeuralNetImageClassifier.builder()
                    .inputClass(BufferedImage.class)                            // The class of the image type
                    .imageHeight(64)                                            // image height
                    .imageWidth(64)                                             // image width
                    .labelsFile(Paths.get(iDir + "/labels-chihuahua.txt"))          // list of labels describing the images
                    .trainingFile(Paths.get(iDir + "/dataset-chihuahua.txt"))       // list of image-path and label associations
                    .networkArchitecture(Paths.get(iDir + "/arch.json"))            // Arch of the CNN
                    .exportModel(Paths.get(iDir + "model-chihuahua.dnet"))          // Where to store the model after we're done training
                    .maxError(0.02f)                                            // Maximum acceptable error that stops the training
                    .maxEpochs(100)                                             // Maximum number of training iterations ("epochs")
                    .learningRate(0.001f)                                       // Amount of error used for adjusting internal parameters in each training iteration
                    .build();

        } catch (ModelCreationException mcx) {
            System.err.println("*****Cannot create Classifer (and model): " + mcx.getMessage());
        }
        System.out.println("Training completed in " + sw.getElapsedTime() + " ms");
    }
}

