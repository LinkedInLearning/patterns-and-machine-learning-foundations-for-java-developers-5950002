package DogIdentifier;

import javax.visrec.ml.classification.ImageClassifier;
import javax.visrec.ml.classification.NeuralNetImageClassifier;
import javax.visrec.ml.model.ModelCreationException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

public class ImageRecognizer {
    private String labelsFile;
    private String datasetFile;
    private String archFile;
    private String modelFile;
    private int height, width;
    private float maxerror;
    private int epochs;
    private float learningrate;

    public ImageRecognizer(String labels, String dataset, String arch, String model, int h, int w, float err, int ep, float rate) {
        labelsFile = labels;
        datasetFile = dataset;
        archFile = arch;
        modelFile = model;
        height = h;
        width = w;
        maxerror = err;
        epochs = ep;
        learningrate = rate;
    }

    public ImageClassifier train() {
        ImageClassifier imc = null;
        long startTime = System.currentTimeMillis();
        try {
            imc = NeuralNetImageClassifier.builder()
                    .inputClass(BufferedImage.class)                    // The class of the image type
                    .imageHeight(height)                                // image height
                    .imageWidth(width)                                  // image width
                    .labelsFile(new File(labelsFile).toPath())          // list of labels describing the images
                    .trainingFile(new File(datasetFile).toPath())       // list of image-path and label associations
                    .networkArchitecture(new File(archFile).toPath())   // Arch of the CNN
                    .exportModel(Paths.get(modelFile))                  // Where to store the model after we're done training
                    .maxError(maxerror)                                 // Maximum acceptable error that stops the training
                    .maxEpochs(epochs)                                  // Maximum number of training iterations ("epochs")
                    .learningRate(learningrate)                         // Amount of error used for adjusting internal parameters in each training iteration
                    .build();

        } catch (ModelCreationException mcx) {
            System.err.println("*****Cannot create Classifer (and model): " + mcx.getMessage());
        }
        System.out.println("Training completed in " + (System.currentTimeMillis() - startTime) + " ms" );
        return imc;
    }
}

